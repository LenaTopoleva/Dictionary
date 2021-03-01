package com.lenatopoleva.dictionary.view.wordslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.inflate
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenatopoleva.dictionary.R
import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.utils.network.isOnline
import com.lenatopoleva.dictionary.view.BackButtonListener
import com.lenatopoleva.dictionary.view.base.BaseFragment
import com.lenatopoleva.dictionary.view.wordslist.adapter.WordsListRVAdapter
import kotlinx.android.synthetic.main.fragment_words_list.*
import org.koin.android.ext.android.getKoin

class WordsListFragment : BaseFragment<AppState>(), BackButtonListener {

    override val model: WordsListViewModel by lazy {
        ViewModelProvider(this, getKoin().get()).get(WordsListViewModel::class.java)
    }

    private val observer = Observer<AppState> { renderData(it)  }

    private var adapter: WordsListRVAdapter? = null
    private val onListItemClickListener: WordsListRVAdapter.OnListItemClickListener =
        object : WordsListRVAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                model.wordClicked(data)
            }
        }

    companion object {
        fun newInstance() = WordsListFragment()
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "12345"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? = inflate(context, R.layout.fragment_words_list, null)


    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("model: $model ")
        model.subscribe().observe(viewLifecycleOwner, observer)

        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    isNetworkAvailable = isOnline(activity!!.applicationContext)
                    if (isNetworkAvailable) {
                        model.getData(searchWord, isNetworkAvailable)
                    } else {
                        showNoInternetConnectionDialog()
                    }
                }
            })
            searchDialogFragment.show(childFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun backPressed(): Boolean = model.backPressed()

    override fun setDataToAdapter(data: List<DataModel>) {
        if (adapter == null) {
            words_list_recyclerview.layoutManager = LinearLayoutManager(context)
            words_list_recyclerview.adapter = WordsListRVAdapter(onListItemClickListener, data)
        } else {
            adapter!!.setData(data)
        }
    }

}