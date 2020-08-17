package com.emerson.bankapp.auth

import com.emerson.bankapp.R
import com.emerson.bankapp.data.models.BaseResponse
import com.emerson.bankapp.data.models.ErrorResponse
import com.emerson.bankapp.domain.models.UserInfo
import com.emerson.bankapp.domain.useCases.AuthUseCase
import com.emerson.bankapp.presentation.auth.AuthViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class AuthViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authUseCase: AuthUseCase

    @Mock
    private lateinit var loginErrorTest: Observer<Int>

    @Mock
    private lateinit var loginResultTest: Observer<Boolean>

    @Mock
    private lateinit var currentUserTest: Observer<UserInfo>

    private lateinit var authViewModel: AuthViewModel
    private val userInfo = UserInfo(
        1,
        "Sr Tadeu ",
        "2050",
        "012314564",
        3.3445f,
        "a@a.com",
        "aA@1"
    )
    private val userError = ErrorResponse(0, "USER NOT FOUND")

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        authUseCase = mock(AuthUseCase::class.java)
        loginErrorTest = mock(Observer::class.java) as Observer<Int>
        loginResultTest = mock(Observer::class.java) as Observer<Boolean>
        currentUserTest = mock(Observer::class.java) as Observer<UserInfo>
    }

    @Test
    fun `first access success`() = runBlocking {
        `when`(authUseCase.getCurrentUser()).thenReturn(BaseResponse.Error(userError))
        `when`(authUseCase.postLogin(userInfo.login, userInfo.password)).thenReturn(BaseResponse.Success(userInfo))
        `when`(authUseCase.validateLogin(userInfo.login)).thenReturn(true)
        `when`(authUseCase.validatePassword(userInfo.password)).thenReturn(true)

        authViewModel = AuthViewModel(authUseCase).apply {
            loginError.observeForever(loginErrorTest)
            loginResult.observeForever(loginResultTest)
            currentUser.observeForever(currentUserTest)
        }

        authViewModel.postLogin(userInfo.login, userInfo.password)

        verify(currentUserTest, never()).onChanged(null)
        verify(currentUserTest, never()).onChanged(userInfo)
        verify(loginErrorTest, never()).onChanged(null)
        verify(loginErrorTest, never()).onChanged(R.string.login_error)
        verify(loginErrorTest, never()).onChanged(R.string.login_error_incorrect_user)
        verify(loginErrorTest, never()).onChanged(R.string.login_error_incorrect_password)
        verify(loginResultTest, never()).onChanged(false)
        verify(loginResultTest, times(1)).onChanged(true)
    }

    @Test
    fun `first access error`() = runBlocking {
        `when`(authUseCase.getCurrentUser()).thenReturn(BaseResponse.Error(userError))
        `when`(authUseCase.postLogin(userInfo.login, userInfo.password)).thenReturn(BaseResponse.Error(userError))
        `when`(authUseCase.validateLogin(userInfo.login)).thenReturn(true)
        `when`(authUseCase.validatePassword(userInfo.password)).thenReturn(true)

        authViewModel = AuthViewModel(authUseCase).apply {
            loginError.observeForever(loginErrorTest)
            loginResult.observeForever(loginResultTest)
            currentUser.observeForever(currentUserTest)
        }

        authViewModel.postLogin(userInfo.login, userInfo.password)

        verify(currentUserTest, never()).onChanged(null)
        verify(currentUserTest, never()).onChanged(userInfo)
        verify(loginErrorTest, never()).onChanged(null)
        verify(loginErrorTest, times(1)).onChanged(R.string.login_error)
        verify(loginErrorTest, never()).onChanged(R.string.login_error_incorrect_user)
        verify(loginErrorTest, never()).onChanged(R.string.login_error_incorrect_password)
        verify(loginResultTest, never()).onChanged(false)
        verify(loginResultTest, never()).onChanged(true)
    }

    @Test
    fun `first access invalid login`() = runBlocking {
        `when`(authUseCase.getCurrentUser()).thenReturn(BaseResponse.Error(userError))
        `when`(authUseCase.validateLogin(userInfo.login)).thenReturn(false)

        authViewModel = AuthViewModel(authUseCase).apply {
            loginError.observeForever(loginErrorTest)
            loginResult.observeForever(loginResultTest)
            currentUser.observeForever(currentUserTest)
        }

        authViewModel.postLogin(userInfo.login, userInfo.password)

        verify(currentUserTest, never()).onChanged(null)
        verify(currentUserTest, never()).onChanged(userInfo)
        verify(loginErrorTest, never()).onChanged(null)
        verify(loginErrorTest, never()).onChanged(R.string.login_error)
        verify(loginErrorTest, times(1)).onChanged(R.string.login_error_incorrect_user)
        verify(loginErrorTest, never()).onChanged(R.string.login_error_incorrect_password)
        verify(loginResultTest, never()).onChanged(false)
        verify(loginResultTest, never()).onChanged(true)
    }

    @Test
    fun `first access invalid password`() = runBlocking {
        `when`(authUseCase.getCurrentUser()).thenReturn(BaseResponse.Error(userError))
        `when`(authUseCase.validateLogin(userInfo.login)).thenReturn(true)
        `when`(authUseCase.validatePassword(userInfo.password)).thenReturn(false)

        authViewModel = AuthViewModel(authUseCase).apply {
            loginError.observeForever(loginErrorTest)
            loginResult.observeForever(loginResultTest)
            currentUser.observeForever(currentUserTest)
        }

        authViewModel.postLogin(userInfo.login, userInfo.password)

        verify(currentUserTest, never()).onChanged(null)
        verify(currentUserTest, never()).onChanged(userInfo)
        verify(loginErrorTest, never()).onChanged(null)
        verify(loginErrorTest, never()).onChanged(R.string.login_error)
        verify(loginErrorTest, never()).onChanged(R.string.login_error_incorrect_user)
        verify(loginErrorTest, times(1)).onChanged(R.string.login_error_incorrect_password)
        verify(loginResultTest, never()).onChanged(false)
        verify(loginResultTest, never()).onChanged(true)
    }

    @Test
    fun `second access success`() = runBlocking {
        `when`(authUseCase.getCurrentUser()).thenReturn(BaseResponse.Success(userInfo))
        `when`(authUseCase.postLogin(userInfo.login, userInfo.password)).thenReturn(BaseResponse.Success(userInfo))
        `when`(authUseCase.validateLogin(userInfo.login)).thenReturn(true)
        `when`(authUseCase.validatePassword(userInfo.password)).thenReturn(true)

        authViewModel = AuthViewModel(authUseCase).apply {
            loginError.observeForever(loginErrorTest)
            loginResult.observeForever(loginResultTest)
            currentUser.observeForever(currentUserTest)
        }

        authViewModel.postLogin(userInfo.login, userInfo.password)

        verify(currentUserTest, never()).onChanged(null)
        verify(currentUserTest, times(1)).onChanged(userInfo)
        verify(loginErrorTest, never()).onChanged(null)
        verify(loginErrorTest, never()).onChanged(R.string.login_error)
        verify(loginErrorTest, never()).onChanged(R.string.login_error_incorrect_user)
        verify(loginErrorTest, never()).onChanged(R.string.login_error_incorrect_password)
        verify(loginResultTest, never()).onChanged(false)
        verify(loginResultTest, times(1)).onChanged(true)
    }
}