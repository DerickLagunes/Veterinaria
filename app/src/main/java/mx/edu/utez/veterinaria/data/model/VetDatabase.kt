package mx.edu.utez.veterinaria.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.edu.utez.veterinaria.data.model.dao.PetDao
import kotlin.jvm.java

@Database(entities = [Pet::class], version = 1)
abstract class VetDatabase: RoomDatabase() {
    abstract fun petDao(): PetDao

    companion object {
        @Volatile
        private var INSTANCE: VetDatabase? = null

        fun getDatabase(context: Context): VetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VetDatabase::class.java,
                    "pet_database" // Nombre del archivo de la DB
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}