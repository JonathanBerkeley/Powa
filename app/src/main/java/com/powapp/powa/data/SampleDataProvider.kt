package com.powapp.powa.data

import java.util.*

class SampleDataProvider {

    companion object {
        private fun getDate(difference: Long) : Date {
            return Date( Date().time + difference)
        }

        fun getSampleLogins() = arrayListOf(
            DataEntity(1, getDate(0), "My Google password", "google", "https://gmail.com", "testpassword", "testusername@gmail.com"),
            DataEntity(2, getDate(1), "My IADT password", "iadt", "https://iadt.ie", "iadtpassword123", "jonathanberkeley1999@gmail.com"),
            DataEntity(3, getDate(2), "My passwordless/usernameless login", "example", "https://ebay.com", null, null),
            DataEntity(4, getDate(3), "My usernameless login", "test2", "https://domain.com", "testpassword123", null)
        )
    }
}