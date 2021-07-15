package ru.softmine.translator.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import org.koin.android.viewmodel.ext.android.viewModel
import ru.softmine.descriptionscreen.DescriptionActivity
import ru.softmine.translator.R
import ru.softmine.translator.databinding.ActivityMainBinding
import ru.softmine.model.data.AppState
import ru.softmine.model.data.DataModel
import ru.softmine.translator.di.injectDependencies
import ru.softmine.translator.utils.convertAltTranslations
import ru.softmine.translator.utils.convertMeaningsToString
import ru.softmine.utils.network.isOnline
import ru.softmine.translator.view.MainInteractor
import ru.softmine.translator.view.MainViewModel
import ru.softmine.translator.view.adapter.MainAdapter

private const val HISTORY_ACTIVITY_PATH =
    "ru.softmine.historyfeature.view.history.HistoryActivity"
private const val HISTORY_ACTIVITY_FEATURE_NAME = "historyScreen"
private const val REQUEST_CODE = 0xFEAD


class MainActivity : ru.softmine.core.BaseActivity<AppState, MainInteractor>() {

    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var splitInstallManager: SplitInstallManager
    private val ui: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override lateinit var model: MainViewModel
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }
    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
        }
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Log.d("MainActivity", data.toString())
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings!![0].imageUrl,
                        data.meanings!![0].transcription,
                        convertAltTranslations(data.meanings!![0]),
                    )
                )
            }
        }
    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(applicationContext)
                if (isNetworkAvailable) {
                    model.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    private val stateUpdatedListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { state ->
            state.let {
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
        }

    private fun initViewModel() {
        check(ui.mainActivityRecyclerview.adapter == null) {
            "The ViewModel should be initialised first"
        }
        injectDependencies()
        val vm: MainViewModel by viewModel()
        model = vm
        model.subscribe().observe(this@MainActivity, {
            renderData(it)
        })
    }

    private fun initView() {
        ui.searchFab.setOnClickListener(fabClickListener)
        ui.mainActivityRecyclerview.layoutManager = LinearLayoutManager(applicationContext)
        ui.mainActivityRecyclerview.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.root)

        initViewModel()
        initView()
        checkForUpdates()
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startHistoryScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getEmptyDataMessage(): String {
        return getString(R.string.empty_server_response_on_success)
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE
                    )
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Update flow failed! Result code: $resultCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun startHistoryScreen() {
        splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
        val request = SplitInstallRequest
            .newBuilder()
            .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
            .build()
        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener {
                val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Couldn't download feature: " + it.message,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)

        val appUpdateInfo = appUpdateManager.appUpdateInfo
        appUpdateInfo.addOnSuccessListener { appUpdateIntent ->
            if (appUpdateIntent.updateAvailability() ==
                UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateIntent.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.registerListener(stateUpdatedListener)
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateIntent,
                    AppUpdateType.IMMEDIATE,
                    this,
                    REQUEST_CODE
                )
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(ui.root,
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }
}
