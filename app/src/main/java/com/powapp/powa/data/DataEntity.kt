package com.powapp.powa.data

import com.powapp.powa.NEW_ENTRY_ID
import java.util.*

data class DataEntity (
    var id: Int,
    var date: Date,
    var text: String
) {
    //todo encrypt?
    constructor(): this(NEW_ENTRY_ID, Date(), "")
    constructor(date: Date, text: String) : this(NEW_ENTRY_ID, date, text)
}