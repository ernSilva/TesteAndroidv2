package com.emerson.bankapp.home

import com.emerson.bankapp.data.models.BaseResponse
import com.emerson.bankapp.data.models.ErrorResponse
import com.emerson.bankapp.domain.models.Statement
import com.emerson.bankapp.domain.models.UserInfo
import com.emerson.bankapp.domain.useCases.AuthUseCase
import com.emerson.bankapp.domain.useCases.StatementUseCase
import com.emerson.bankapp.presentation.home.HomeViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var statementUseCase: StatementUseCase

    @Mock
    private lateinit var authUseCase: AuthUseCase

    @Mock
    private lateinit var statementListTest: Observer<List<Statement>>

    @Mock
    private lateinit var currentUserTest: Observer<UserInfo>

    @Mock
    private lateinit var logoutResultTest: Observer<Boolean>

    @Mock
    private lateinit var statementListResultTest: Observer<Boolean>

    private lateinit var homeViewModel: HomeViewModel
    private val userInfo = UserInfo(
        1,
        "Sr Tadeu",
        "2050",
        "012314564",
        3.3445f,
        "a@a.com",
        "aA@1"
    )
    private val userError = ErrorResponse(0, "USER NOT FOUND")
    private val statementList = listOf(
        Statement(
            "Pagamento",
            "Conta de internet",
            "2018-05-12",
            -73.4f
        )
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        statementUseCase = Mockito.mock(StatementUseCase::class.java)
        authUseCase = Mockito.mock(AuthUseCase::class.java)
        statementListTest = Mockito.mock(Observer::class.java) as Observer<List<Statement>>
        currentUserTest = Mockito.mock(Observer::class.java) as Observer<UserInfo>
        logoutResultTest = Mockito.mock(Observer::class.java) as Observer<Boolean>
        statementListResultTest = Mockito.mock(Observer::class.java) as Observer<Boolean>
    }

    @Test
    fun `list statements`() = runBlocking {
        Mockito.`when`(authUseCase.getCurrentUser()).thenReturn(BaseResponse.Success(userInfo))
        Mockito.`when`(statementUseCase.getStatementList(userInfo.id.toString()))
            .thenReturn(BaseResponse.Success(statementList))

        homeViewModel = HomeViewModel(statementUseCase, authUseCase).apply {
            statementList.observeForever(statementListTest)
            currentUser.observeForever(currentUserTest)
            logoutResult.observeForever(logoutResultTest)
            statementListResult.observeForever(statementListResultTest)
        }

        Mockito.verify(currentUserTest, times(1)).onChanged(userInfo)
        Mockito.verify(statementListTest, times(1)).onChanged(statementList)
        Mockito.verify(logoutResultTest, never()).onChanged(true)
        Mockito.verify(statementListResultTest, never()).onChanged(true)
    }

    @Test
    fun `current user error`() = runBlocking {
        Mockito.`when`(authUseCase.getCurrentUser()).thenReturn(BaseResponse.Error(userError))
        Mockito.`when`(authUseCase.postLogout()).thenReturn(BaseResponse.Success(true))

        homeViewModel = HomeViewModel(statementUseCase, authUseCase).apply {
            statementList.observeForever(statementListTest)
            currentUser.observeForever(currentUserTest)
            logoutResult.observeForever(logoutResultTest)
            statementListResult.observeForever(statementListResultTest)
        }

        Mockito.verify(currentUserTest, never()).onChanged(userInfo)
        Mockito.verify(statementListTest, never()).onChanged(statementList)
        Mockito.verify(statementListResultTest, never()).onChanged(true)
        Mockito.verify(logoutResultTest, times(1)).onChanged(true)
    }

    @Test
    fun `statement list error`() = runBlocking {
        Mockito.`when`(authUseCase.getCurrentUser()).thenReturn(BaseResponse.Success(userInfo))
        Mockito.`when`(statementUseCase.getStatementList(userInfo.id.toString()))
            .thenReturn(BaseResponse.Error(ErrorResponse(0, "EMPTY LIST")))

        homeViewModel = HomeViewModel(statementUseCase, authUseCase).apply {
            statementList.observeForever(statementListTest)
            currentUser.observeForever(currentUserTest)
            logoutResult.observeForever(logoutResultTest)
            statementListResult.observeForever(statementListResultTest)
        }

        Mockito.verify(statementListTest, never()).onChanged(statementList)
        Mockito.verify(currentUserTest, times(1)).onChanged(userInfo)
        Mockito.verify(logoutResultTest, never()).onChanged(true)
        Mockito.verify(statementListResultTest, times(1)).onChanged(true)
    }
}