package com.powapp.powa.data

import androidx.lifecycle.LiveData
import androidx.room.*

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

    @Query("DELETE FROM logins WHERE 1 = 1")
    fun emptyDatabase()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'logins'")
    fun resetDatabasePK()

    @Query("DELETE FROM logins WHERE id = :id")
    fun deleteLoginById(id: Int)

    @Delete
    fun deleteLoginData(login: DataEntity)
}