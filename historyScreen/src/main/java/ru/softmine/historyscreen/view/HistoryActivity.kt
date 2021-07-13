package ru.softmine.historyscreen.view

import android.os.Bundle
import ru.softmine.historyscreen.R
import ru.softmine.historyscreen.databinding.ActivityHistoryBinding
import ru.softmine.model.data.AppState
import ru.softmine.model.data.DataModel
import ru.softmine.historyscreen.convertMeaningsToString
import ru.softmine.core.BaseActivity
import ru.softmine.historyscreen.view.adapter.HistoryAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import ru.softmine.descriptionscreen.DescriptionActivity

class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {

    private val ui: ActivityHistoryBinding by lazy {
        ActivityHistoryBinding.inflate(layoutInflater)
    }

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter(onListItemClickListener) }
    private val onListItemClickListener: HistoryAdapter.OnListItemClickListener =
        object : HistoryAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {

                startActivity(
                    DescriptionActivity.getIntent(
                        this@HistoryActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        null,
                        null,
                        null,
                    )
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.root)
        isNetworkAvailable = false
        initViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = false
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun initViewModel() {
        if (ui.historyActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val vm: HistoryViewModel by viewModel()
        model = vm
        model.subscribe().observe(this@HistoryActivity, { renderData(it) })
    }

    private fun initViews() {
        ui.historyActivityRecyclerview.adapter = adapter
    }

    override fun getEmptyDataMessage(): String {
        return getString(R.string.empty_history_response_on_success)
    }
}
