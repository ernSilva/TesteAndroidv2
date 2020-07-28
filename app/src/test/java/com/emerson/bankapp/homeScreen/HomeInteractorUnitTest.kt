package com.emerson.bankapp.homeScreen


import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
//import org.junit.runner.RunWith
//import org.robolectric.RobolectricTestRunner

//@RunWith(RobolectricTestRunner::class)
class HomeInteractorUnitTest {
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun fetchHomeMetaData_with_vaildInput_shouldCall_presentHomeMetaData() { //Given
        val homeInteractor = HomeInteractor()
        val homeRequest = HomeRequest()
        homeRequest.userId = 1
        val homePresenterInputSpy = HomePresenterInputSpy()
        homeInteractor.output = homePresenterInputSpy

        //When
        System.out.println(homeRequest.toString())
        homeInteractor.fetchHomeMetaData(homeRequest)

        System.out.println(homePresenterInputSpy.presentHomeMetaDataIsCalled)

        //Then
        Assert.assertFalse(
            "When the valid input is passed to HomeInteractor " +
                    "Then presentHomeMetaData should be called",
            homePresenterInputSpy.presentHomeMetaDataIsCalled
        )
    }

    private class HomePresenterInputSpy : HomePresenterInput {
        var presentHomeMetaDataIsCalled : Boolean = false
        var homeResponseCopy: HomeResponse? = null
        override fun presentHomeMetaData(response: HomeResponse?) {
            System.out.println(response.toString())
            presentHomeMetaDataIsCalled = true
            homeResponseCopy = response
        }
    }

}