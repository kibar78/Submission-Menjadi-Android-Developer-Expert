package com.example.dicodingevent.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dicodingevent.core.data.source.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EventDatabase : RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao

//    companion object{
//        @Volatile
//        private var INSTANCE: EventDatabase? = null
//
//        @JvmStatic
//        fun getDatabase(context: Context): EventDatabase {
//            if (INSTANCE == null){
//                synchronized(EventDatabase::class.java){
//                    INSTANCE = Room.databaseBuilder(context.applicationContext,
//                        EventDatabase::class.java,"favorite_database")
//                        .build()
//                }
//            }
//            return INSTANCE as EventDatabase
//        }
//    }
}