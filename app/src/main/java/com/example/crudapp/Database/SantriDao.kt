package com.example.crudapp.Database

import androidx.room.*

@Dao
interface SantriDao {
    @Insert
    suspend fun addSantri(santri: Santri)

    @Update
    suspend fun updateSantri(santri: Santri)

    @Delete
    suspend fun deleteSantri(santri: Santri)

    @Query("SELECT * FROM santri")
    suspend fun getAllSantri(): List<Santri>

    @Query("SELECT * FROM santri WHERE id=:santri_id")
    suspend fun getSantri(santri_id: Int) : List<Santri>

}