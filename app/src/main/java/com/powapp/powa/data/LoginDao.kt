package com.powapp.powa.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLogin(login: DataEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(login: List<DataEntity>)

    //Gets all from the SQLite room database
    @Query("SELECT * FROM logins ORDER BY date ASC")
    fun getAll(): LiveData<List<DataEntity>>

    @Query("SELECT * FROM logins WHERE id = :id")
    fun getLoginById(id: Int): DataEntity?

    @Query("SELECT COUNT(*) FROM logins")
    fun getCount(): Int
}