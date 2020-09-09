package rs.raf.projekat2.packinghelper.data.models.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherForecastImageResponse(
    @Json(name = "icon") val icon : String,
    @Json(name = "code") val code : Int,
    @Json(name = "description") val description : String
)