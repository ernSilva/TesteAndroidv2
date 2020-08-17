package com.emerson.bankapp.presentation.home

import com.emerson.bankapp.R
import com.emerson.bankapp.domain.models.Statement
import com.emerson.bankapp.domain.models.UserInfo
import com.emerson.bankapp.presentation.BankActivity
import com.emerson.bankapp.presentation.home.list.StatementAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.progressBar
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initViews()
        initLiveData()
    }

    private fun initListeners() {
        logoutButton.setOnClickListener(onLogoutClicked)
    }

    private val onLogoutClicked = View.OnClickListener {
        homeViewModel.postLogout()
    }

    private fun initViews() {
        progressBar.isIndeterminate = true
        progressBar.indeterminateDrawable.colorFilter = BlendModeColorFilterCompat
            .createBlendModeColorFilterCompat(
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                BlendModeCompat.SRC_IN
            )
        statementList.adapter = StatementAdapter()
    }

    private fun initLiveData() {
        with(homeViewModel) {
            currentUser.observe(viewLifecycleOwner, bindUserInformation)
            statementList.observe(viewLifecycleOwner, bindList)
            logoutResult.observe(viewLifecycleOwner, bindLogout)
            statementListResult.observe(viewLifecycleOwner, bindListError)
        }
    }

    private val bindUserInformation = Observer { user: UserInfo ->
        userNameTextView.text = user.name
        accountNumberTextView.text = user.getAccountNumber()
        accountBalanceTextView.text = user.getBalanceFormatted()
    }

    private val bindLogout = Observer<Boolean> {
        (requireActivity() as BankActivity).goToAuthPage()
    }

    private val bindList = Observer { list: List<Statement> ->
        progressBar.visibility = View.INVISIBLE
        (statementList.adapter as? StatementAdapter)?.submitList(list)
    }

    private val bindListError = Observer { hasError: Boolean ->
        if (hasError) Snackbar.make(homeRootView, R.string.statement_list_error, Snackbar.LENGTH_LONG).show()
    }
}