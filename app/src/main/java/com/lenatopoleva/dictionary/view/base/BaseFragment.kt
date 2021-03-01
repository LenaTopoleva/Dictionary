package com.lenatopoleva.dictionary.view.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lenatopoleva.dictionary.R
import com.lenatopoleva.dictionary.model.data.AppState
import com.lenatopoleva.dictionary.utils.network.isOnline
import com.lenatopoleva.dictionary.utils.ui.AlertDialogFragment
import com.lenatopoleva.dictionary.viewmodel.BaseViewModel


abstract class BaseFragment<T : AppState> : Fragment() {

    abstract val model: BaseViewModel<T>

    protected var isNetworkAvailable: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isNetworkAvailable = isOnline(requireActivity().applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(requireActivity().applicationContext)
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message).show(requireFragmentManager(), DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return requireFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    abstract fun renderData(dataModel: T)

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    }
}

