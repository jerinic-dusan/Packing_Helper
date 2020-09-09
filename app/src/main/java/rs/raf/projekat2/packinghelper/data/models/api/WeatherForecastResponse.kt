package rs.raf.projekat2.packinghelper.data.models.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherForecastResponse(
    @Json(name = "data") val data: List<WeatherForecastDataResponse>
)