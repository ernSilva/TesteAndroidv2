package com.emerson.bankapp.homeScreen

import java.lang.ref.WeakReference


enum class HomeConfigurator {
    INSTANCE;

    fun configure(activity: HomeActivity) {
        val router = HomeRouter()
        router.activity = WeakReference(activity)
        val presenter = HomePresenter()
        presenter.output = WeakReference<HomeActivityInput>(activity)
        val interactor = HomeInteractor()
        interactor.output = presenter
        if (activity.output == null) {
            activity.output = interactor
        }
        if (activity.router == null) {
            activity.router = router
        }
    }
}
