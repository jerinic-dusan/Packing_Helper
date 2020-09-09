package rs.raf.projekat2.packinghelper.data.datasources.remote


import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat2.packinghelper.application.App
import rs.raf.projekat2.packinghelper.data.models.api.WeatherForecastResponse

interface WeatherForecast {

    @GET("daily")
    fun forecast(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("key") api: String): Observable<WeatherForecastResponse>

}