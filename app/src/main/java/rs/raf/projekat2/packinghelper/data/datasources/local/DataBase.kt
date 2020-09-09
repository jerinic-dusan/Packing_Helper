package rs.raf.projekat2.packinghelper.data.datasources.local

import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import rs.raf.projekat2.packinghelper.data.models.Suitcase
import rs.raf.projekat2.packinghelper.data.models.SuitcaseItem

@Database(
    entities = [Suitcase::class, SuitcaseItem::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class DataBase: RoomDatabase() {
    abstract fun getSuitcaseDao(): SuitcaseDao
}