package ru.softmine.translator.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel
import ru.softmine.translator.R
import ru.softmine.translator.databinding.ActivityMainBinding
import ru.softmine.translator.model.data.AppState
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.utils.convertMeaningsToString
import ru.softmine.translator.utils.network.isOnline
import ru.softmine.translator.view.BaseActivity
import ru.softmine.translator.view.MainInteractor
import ru.softmine.translator.view.MainViewModel
import ru.softmine.translator.view.adapter.MainAdapter

class MainActivity : BaseActivity<AppState, MainInteractor>() {

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
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings[0].imageUrl
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

    private fun initViewModel() {
        check(ui.mainActivityRecyclerview.adapter == null) {
            "The ViewModel should be initialised first"
        }

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
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                val data = appState.data
                if (data.isNullOrEmpty()) {
                    showAlertDialog(
                        getString(R.string.dialog_tittle_sorry),
                        getString(R.string.empty_server_response_on_success)
                    )
                } else {
                    adapter.setData(data)
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    ui.progressBarHorizontal.visibility = VISIBLE
                    ui.progressBarRound.visibility = GONE
                    ui.progressBarHorizontal.progress = appState.progress
                } else {
                    ui.progressBarHorizontal.visibility = GONE
                    ui.progressBarRound.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
        }
    }

    private fun showViewWorking() {
        ui.loadingFrameLayout.visibility = GONE
    }

    private fun showViewLoading() {
        ui.loadingFrameLayout.visibility = VISIBLE
    }
}
