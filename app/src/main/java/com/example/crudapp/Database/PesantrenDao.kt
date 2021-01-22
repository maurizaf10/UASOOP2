package com.example.crudapp.Database

import androidx.room.*

@Dao
interface PesantrenDao {
    @Insert
    suspend fun addPesantren(pesantren: Pesantren)

    @Update
    suspend fun updatePesantren(pesantren: Pesantren)

    @Delete
    suspend fun deletePesantren(pesantren: Pesantren)

    @Query("SELECT * FROM pesantren")
    suspend fun getAllPesantren(): List<Pesantren>

    @Query("SELECT * FROM pesantren WHERE id=:pesantren_id")
    suspend fun getPesantren(pesantren_id: Int) : List<Pesantren>
}