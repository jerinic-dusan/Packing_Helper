package rs.raf.projekat2.packinghelper.data.models

import android.location.Address
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class TripData(
    var location: Address,
    var startDate: Date,
    var endDate: Date,
    var gender: String,
    var travelOccasion: String,
    val key: String
): Parcelable