package com.lenatopoleva.dictionary.view.historyscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lenatopoleva.dictionary.R
import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.model.data.DataModel
import com.lenatopoleva.dictionary.view.BackButtonListener
import com.lenatopoleva.dictionary.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.ext.android.getKoin

class HistoryFragment : BaseFragment<AppState>(), BackButtonListener {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override val model: HistoryViewModel by lazy {
        ViewModelProvider(this, getKoin().get()).get(HistoryViewModel::class.java)
    }

    private val observer = Observer<AppState> { renderData(it)  }
    private var adapter: HistoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = View.inflate(context, R.layout.fragment_history, null)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("history fragment viewmodel: $model ")
        model.subscribe().observe(viewLifecycleOwner, observer)
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
            history_fragment_recyclerview.adapter = HistoryAdapter(data)
        } else {
            adapter!!.setData(data)
        }
    }

}