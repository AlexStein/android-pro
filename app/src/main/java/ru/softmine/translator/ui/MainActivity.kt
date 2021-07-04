package ru.softmine.translator.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getEmptyDataMessage(): String {
        return getString(R.string.empty_server_response_on_success)
    }
}
