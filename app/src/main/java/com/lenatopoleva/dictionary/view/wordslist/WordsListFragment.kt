package com.lenatopoleva.dictionary.view.wordslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.inflate
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenatopoleva.dictionary.R
import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.utils.network.isOnline
import com.lenatopoleva.dictionary.view.BackButtonListener
import com.lenatopoleva.dictionary.view.base.BaseFragment
import com.lenatopoleva.dictionary.view.wordslist.adapter.WordsListRVAdapter
import kotlinx.android.synthetic.main.fragment_words_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class WordsListFragment : BaseFragment<AppState>(), BackButtonListener {

    override val model: WordsListViewModel by viewModel()

    private val observer = Observer<AppState> { renderData(it)  }

    private var adapter: WordsListRVAdapter? = null
    private val onListItemClickListener: WordsListRVAdapter.OnListItemClickListener =
        object : WordsListRVAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                // some logic
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

    override fun renderData(dataModel: AppState) {
        when (dataModel) {
            is AppState.Success -> {
                showViewWorking()
                val dataModel = dataModel.data
                if (dataModel.isNullOrEmpty()) {
                    showAlertDialog(
                        getString(R.string.dialog_tittle_sorry),
                        getString(R.string.empty_server_response_on_success)
                    )
                } else {
                    if (adapter == null) {
                        main_activity_recyclerview.layoutManager = LinearLayoutManager(context)
                        main_activity_recyclerview.adapter = WordsListRVAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (dataModel.progress != null) {
                    progress_bar_horizontal.visibility = android.view.View.VISIBLE
                    progress_bar_round.visibility = android.view.View.GONE
                    progress_bar_horizontal.progress = dataModel.progress
                } else {
                    progress_bar_horizontal.visibility = android.view.View.GONE
                    progress_bar_round.visibility = android.view.View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error_stub), dataModel.error.message)            }
        }
    }

    private fun showViewWorking() {
        loading_frame_layout.visibility = android.view.View.GONE
    }

    private fun showViewLoading() {
        loading_frame_layout.visibility = android.view.View.VISIBLE
    }

    override fun backPressed(): Boolean = model.backPressed()

}