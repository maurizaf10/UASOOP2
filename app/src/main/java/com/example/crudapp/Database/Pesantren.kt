package com.example.crudapp.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pesantren")
data class Pesantren(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "namapesantren") val namapesantren: String,
    @ColumnInfo(name = "alamat") val alamat: String
)