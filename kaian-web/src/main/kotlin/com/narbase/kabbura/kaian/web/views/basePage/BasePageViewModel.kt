package com.narbase.kabbura.kaian.web.views.basePage

import com.narbase.kabbura.kaian.web.utils.notifications.NotificationsController


class BasePageViewModel {

    fun onViewCreated() {
        NotificationsController.connect()
    }

    data class RouteDetails(var href: String, val title: String, val image: String? = null)

}
