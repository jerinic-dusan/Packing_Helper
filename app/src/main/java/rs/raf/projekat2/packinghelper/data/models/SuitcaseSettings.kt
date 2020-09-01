package rs.raf.projekat2.packinghelper.data.models

import android.location.Address
import java.util.*

data class SuitcaseSettings(
    val id: Int,
    var location: Address,
    var startDate: Date,
    var endDate: Date,
    var gender: String,
    var travelOccasion: String,
    val title: String = "Trip to ${location.featureName}!!!",
    val description: String = "I hope weather will favor us.",
    val temperature: String = "23°C",
    val days: String = "${((endDate.time - startDate.time)/(1000 * 60 * 60 * 24)).toInt()} days"
)