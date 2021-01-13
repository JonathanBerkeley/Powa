package com.powapp.powa.data

import com.powapp.powa.NEW_ENTRY_ID
import java.util.*

data class DataEntity(
        var id: Int,
        var date: Date,
        var title: String,
        var target: String,
        var target_name: String,
        var password: String?,
        var username: String?
) {
    //todo encrypt?
    constructor() : this(NEW_ENTRY_ID, Date(), "", "", "", "", "")
    constructor(date: Date, title: String, target: String, target_name: String, password: String?, username: String?)
            : this(NEW_ENTRY_ID, date, title, target, target_name, password, username)

    //New constructors
    /*
    constructor(id: Int, title: String, target: String, target_name: String
                , password: String, username: String)
            : this(NEW_ENTRY_ID, title, target, target_name, password, username)
    */
}