package rs.raf.projekat2.packinghelper.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "suitcases")
data class Suitcase(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var name: String,
    val desc: String,
    val location: String,
    val lat: Double,
    val lng: Double,
    val occasion: String,
    val startDate: Date,
    val endDate: Date,
    val days: Int,
    val gender: String,
    val temp: String,
    val rain: Boolean,
    val snow: Boolean
): Parcelable