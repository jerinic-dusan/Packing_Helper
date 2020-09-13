package rs.raf.projekat2.packinghelper.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "suitcase_items")
data class SuitcaseItem(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "suitcase_id") var suitcaseId: Long,
    var name: String,
    var group: String,
    var amount: Int
): Parcelable