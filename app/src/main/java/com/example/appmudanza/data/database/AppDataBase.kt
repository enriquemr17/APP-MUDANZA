package com.example.appmudanza.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appmudanza.data.entity.AlquilerVehicle
import com.example.appmudanza.data.entity.MudanzaVehicle
import com.example.appmudanza.data.dao.AlquilerVehicleDao
import com.example.appmudanza.data.dao.MoveDao
import com.example.appmudanza.data.dao.MudanzaVehicleDao
import com.example.appmudanza.data.dao.UserDao
import com.example.appmudanza.data.dao.VehicleDao
import com.example.appmudanza.data.entity.User
import com.example.appmudanza.data.entity.Vehicle
import com.example.appmudanza.data.entity.Move



@Database(
    entities = [User::class, MudanzaVehicle::class, AlquilerVehicle::class, Vehicle::class, Move::class],
    version = 5,  // Incrementa versão para nova estrutura
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun mudanzaVehicleDao(): MudanzaVehicleDao
    abstract fun alquilerVehicleDao(): AlquilerVehicleDao

    abstract fun vehicleDao(): VehicleDao

    abstract fun moveDao(): MoveDao
}