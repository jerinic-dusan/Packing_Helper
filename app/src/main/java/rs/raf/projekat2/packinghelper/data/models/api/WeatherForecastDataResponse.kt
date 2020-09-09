package rs.raf.projekat2.packinghelper.data.models.api

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class WeatherForecastDataResponse(
    @Json(name = "pop") val rainChance : Int,
    @Json(name = "valid_date") val validDate : String,
    @Json(name = "snow") val snowChance : Int,
    @Json(name = "weather") val image : WeatherForecastImageResponse,
    @Json(name = "datetime") val datetime : String,
    @Json(name = "temp") val temp : Double
)