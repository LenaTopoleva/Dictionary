package com.lenatopoleva.dictionary.view.historyscreen

import android.os.Bundle
import android.view.*
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenatopoleva.core.BackButtonListener
import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.core.base.BaseFragment
import com.lenatopoleva.dictionary.utils.ui.recordInitialMarginForView
import com.lenatopoleva.dictionary.utils.ui.requestApplyInsetsWhenAttached
import com.lenatopoleva.dictionary.view.wordslist.SearchDialogFragment
import com.lenatopoleva.historyscreen.R
import com.lenatopoleva.historyscreen.di.injectDependencies
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.scope.currentScope


class HistoryFragment : BaseFragment<AppState>(), BackButtonListener {

    companion object {
        fun newInstance() = HistoryFragment()
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "com.lenatopoleva.dictionary.view.historyscreen.bottomsheetfragmentgialog"
    }

    override val model: HistoryViewModel by lazy {
        injectDependencies()
        currentScope.get()
    }

    private val observer = Observer<AppState> { renderData(it)  }
    private var adapter: HistoryAdapter? = null
    private val onListItemClickListener: HistoryAdapter.OnListItemClickListener =
        object : HistoryAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                model.wordClicked(data)
            }
        }


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        val v: View = inflater.inflate(R.layout.fragment_history, parent, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_menu_item -> {
                println("Search pressed!")
                openSearchDialogFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun openSearchDialogFragment(){
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                model.getData(searchWord, false)
            }
        })
        searchDialogFragment.show(childFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("history fragment viewmodel: $model ")
        model.subscribe().observe(viewLifecycleOwner, observer)

        fixMarginsWhenApplyWindowInsets(view)

    }

    fun fixMarginsWhenApplyWindowInsets(view: View){
        val rvInitialMargin = recordInitialMarginForView(history_fragment_recyclerview)

        ViewCompat.setOnApplyWindowInsetsListener(view.rootView) { v, insets ->
            val params = history_fragment_recyclerview?.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = rvInitialMargin.top + insets.systemWindowInsetTop
//                    (requireActivity() as AppCompatActivity).supportActionBar?.height!!
            history_fragment_recyclerview?.layoutParams = params

            insets.consumeSystemWindowInsets()
        }
        view.rootView.requestApplyInsetsWhenAttached()
    }

    override fun onResume() {
        super.onResume()
        // Ask for data from local repo (isOnline = false)
        model.getData("", false)
    }

    override fun backPressed(): Boolean = model.backPressed()

    override fun setDataToAdapter(data: List<DataModel>) {
        if (adapter == null) {
            history_fragment_recyclerview.layoutManager = LinearLayoutManager(context)
            history_fragment_recyclerview.adapter = HistoryAdapter(onListItemClickListener, data)
        } else {
            adapter!!.setData(data)
        }
    }

}