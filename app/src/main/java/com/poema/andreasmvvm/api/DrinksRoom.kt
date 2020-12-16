import android.content.Context
import androidx.room.Room

import com.poema.andreasmvvm.database.AppDatabase


object DrinksRoom {

    private lateinit var INSTANCE: AppDatabase

    fun getInstance(context: Context): AppDatabase {
        if (!::INSTANCE.isInitialized) {
            synchronized(AppDatabase::class) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,
                        "drinksRoom").fallbackToDestructiveMigration().build()
                }
            }
        }
        return INSTANCE
    }
}