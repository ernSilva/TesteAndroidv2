package com.emerson.bankapp.presentation.auth

import com.emerson.bankapp.R
import com.emerson.bankapp.domain.models.UserInfo
import com.emerson.bankapp.presentation.BankActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_auth.*
import org.koin.android.viewmodel.ext.android.viewModel

class AuthFragment : Fragment() {

    private val authViewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setListeners()
        initLiveData()
    }

    private fun initViews() {
        progressBar.isIndeterminate = true
        progressBar.visibility = View.INVISIBLE
        progressBar.indeterminateDrawable.colorFilter = BlendModeColorFilterCompat
            .createBlendModeColorFilterCompat(
                getColor(requireContext(), R.color.colorPrimary),
                BlendModeCompat.SRC_IN
            )
    }

    private fun setListeners() {
        loginButton.setOnClickListener(onLoginButtonClicked)
    }

    private val onLoginButtonClicked = View.OnClickListener {
        progressBar.visibility = View.VISIBLE
        loginButton.isEnabled = false
        authViewModel.postLogin(
            userEditText.text.toString(),
            passwordEditText.text.toString()
        )
    }

    private fun initLiveData() = with(authViewModel) {
        loginError.observe(viewLifecycleOwner, bindMessageError)
        loginResult.observe(viewLifecycleOwner, bindLoginResult)
        currentUser.observe(viewLifecycleOwner, bindCurrentUser)
    }

    private val bindMessageError = Observer { message: Int ->
        progressBar.visibility = View.INVISIBLE
        loginButton.isEnabled = true
        Snackbar.make(authPageRootView, message, Snackbar.LENGTH_SHORT).show()
    }

    private val bindLoginResult = Observer { isSuccessfully: Boolean ->
        if (isSuccessfully) {
            (requireActivity() as BankActivity).goToHomePage()
        }
    }

    private val bindCurrentUser = Observer { user: UserInfo ->
        userEditText.setText(user.login)
        passwordEditText.setText(user.password)
    }
}