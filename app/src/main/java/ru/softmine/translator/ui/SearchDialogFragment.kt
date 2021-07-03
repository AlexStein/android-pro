package ru.softmine.translator.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.softmine.translator.databinding.SearchDialogFragmentBinding

class SearchDialogFragment : BottomSheetDialogFragment() {

    private val logTag = "SearchDialogFragment"

    private lateinit var ui: SearchDialogFragmentBinding
    private var onSearchClickListener: OnSearchClickListener? = null

    private val textWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (ui.searchEditText.text != null && ui.searchEditText.text.toString().isNotEmpty()) {
                ui.searchButtonTextview.isEnabled = true
                ui.clearTextImageview.visibility = View.VISIBLE
            } else {
                ui.searchButtonTextview.isEnabled = false
                ui.clearTextImageview.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    private val onSearchButtonClickListener =
        View.OnClickListener {
            onSearchClickListener?.onClick(ui.searchEditText.text.toString())
            dismiss()
        }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        Log.d(logTag, "setOnSearchClickListener")
        onSearchClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = SearchDialogFragmentBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ui.searchButtonTextview.setOnClickListener(onSearchButtonClickListener)
        ui.searchEditText.addTextChangedListener(textWatcher)

        ui.clearTextImageview.setOnClickListener {
            ui.searchEditText.text?.clear()
            ui.searchButtonTextview.isEnabled = false
        }
    }

    override fun onDestroyView() {
        onSearchClickListener = null
        super.onDestroyView()
    }

    interface OnSearchClickListener {
        fun onClick(searchWord: String)
    }

    companion object {
        fun newInstance(): SearchDialogFragment {
            return SearchDialogFragment()
        }
    }
}
