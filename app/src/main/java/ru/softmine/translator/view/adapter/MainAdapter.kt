package ru.softmine.translator.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.softmine.translator.R
import ru.softmine.translator.databinding.RecyclerviewItemBinding
import ru.softmine.translator.model.data.DataModel
import ru.softmine.translator.utils.convertMeaningsToString

class MainAdapter(private var onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: List<DataModel> = arrayListOf()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclerview_item, parent, false)

        return RecyclerItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val ui: RecyclerviewItemBinding by lazy { RecyclerviewItemBinding.bind(itemView) }

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                ui.headerTextviewRecyclerItem.text = data.text
                ui.descriptionTextviewRecyclerItem.text = convertMeaningsToString(data.meanings!!)
                itemView.setOnClickListener { openInNewWindow(data) }
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
