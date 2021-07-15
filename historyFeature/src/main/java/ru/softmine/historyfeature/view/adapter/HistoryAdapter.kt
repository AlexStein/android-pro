package ru.softmine.historyfeature.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.softmine.historyfeature.R
import ru.softmine.historyfeature.databinding.HistoryRecyclerviewItemBinding
import ru.softmine.model.data.DataModel

class HistoryAdapter(private var onListItemClickListener: OnListItemClickListener) : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<DataModel> = arrayListOf()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.history_recyclerview_item, parent, false)

        return RecyclerItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ui: HistoryRecyclerviewItemBinding by lazy {
            HistoryRecyclerviewItemBinding.bind(itemView)
        }

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                ui.headerHistoryTextviewRecyclerItem.text = data.text
                ui.root.setOnClickListener {
                    itemView.setOnClickListener { openInNewWindow(data) }
                }
            }
        }
    }

    private fun openInNewWindow(listItemData: DataModel) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }
}
