package ru.softmine.translator.ui

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ru.softmine.translator.R
import ru.softmine.translator.databinding.ActivityMainBinding
import ru.softmine.translator.mvp.model.data.AppState
import ru.softmine.translator.mvp.model.data.DataModel
import ru.softmine.translator.mvp.presenter.MainPresenterImpl
import ru.softmine.translator.mvp.presenter.interfaces.Presenter
import ru.softmine.translator.mvp.view.BaseActivity
import ru.softmine.translator.mvp.view.interfaces.View
import ru.softmine.translator.mvp.view.adapter.MainAdapter

class MainActivity : BaseActivity<AppState>() {

    private val log_tag = "MainActivity"

    private val ui: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun createPresenter(): Presenter<AppState, View> {
        return MainPresenterImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.root)

        Log.d(log_tag, "onCreated")
        ui.searchFab.setOnClickListener {
            Log.d(log_tag, "setOnClickListener")
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        ui.mainActivityRecyclerview.layoutManager = LinearLayoutManager(applicationContext)
                        ui.mainActivityRecyclerview.adapter = MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
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
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        ui.errorTextview.text = error ?: getString(R.string.undefined_error)
        ui.reloadButton.setOnClickListener {
            presenter.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        ui.successLinearLayout.visibility = VISIBLE
        ui.loadingFrameLayout.visibility = GONE
        ui.errorLinearLayout.visibility = GONE
    }

    private fun showViewLoading() {
        ui.successLinearLayout.visibility = GONE
        ui.loadingFrameLayout.visibility = VISIBLE
        ui.errorLinearLayout.visibility = GONE
    }

    private fun showViewError() {
        ui.successLinearLayout.visibility = GONE
        ui.loadingFrameLayout.visibility = GONE
        ui.errorLinearLayout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}
