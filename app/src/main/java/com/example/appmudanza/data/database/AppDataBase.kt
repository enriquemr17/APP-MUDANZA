package com.example.appmudanza.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appmudanza.data.entity.AlquilerVehicle
import com.example.appmudanza.data.entity.MudanzaVehicle
import com.example.appmudanza.data.dao.AlquilerVehicleDao
import com.example.appmudanza.data.dao.MudanzaVehicleDao
import com.example.appmudanza.data.dao.UserDao
import com.example.appmudanza.data.entity.User

@Database(
    entities = [User::class, MudanzaVehicle::class, AlquilerVehicle::class],
    version = 3,  // Incrementa versão para nova estrutura
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun mudanzaVehicleDao(): MudanzaVehicleDao
    abstract fun alquilerVehicleDao(): AlquilerVehicleDao
}