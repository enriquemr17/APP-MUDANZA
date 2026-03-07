package com.example.appmudanza.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmudanza.data.entity.MudanzaVehicle

@Dao
interface MudanzaVehicleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mudanzas: List<MudanzaVehicle>)

    @Query("SELECT * FROM mudanza_vehicles ORDER BY rating DESC")
    suspend fun getAll(): List<MudanzaVehicle>
}



