package rs.raf.projekat2.packinghelper.data.repositories

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.packinghelper.data.datasources.calculations.CalculationInterface
import rs.raf.projekat2.packinghelper.data.datasources.local.SuitcaseDao
import rs.raf.projekat2.packinghelper.data.datasources.remote.WeatherForecast
import rs.raf.projekat2.packinghelper.data.models.ForecastData
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems
import rs.raf.projekat2.packinghelper.data.models.TripData
import rs.raf.projekat2.packinghelper.data.models.api.WeatherForecastDataResponse
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class SuitcaseRepositoryImpl(private val localDataSource: SuitcaseDao, private val remoteDataSource: WeatherForecast, private val calculations: CalculationInterface): SuitcaseRepository {

    override fun getAll(): Observable<List<SuitcaseWithItems>> {
        return localDataSource.getSuitcaseWithItems()
    }

    @SuppressLint("SimpleDateFormat")
    override fun create(data: TripData): Completable {
        return Completable.fromCallable {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd")

            val weatherForecastResponse = remoteDataSource.forecast(data.location.latitude, data.location.longitude, data.key).blockingLast()
            var between = false
            var rain = false
            var snow = false
            var temp = 0.0
            val list = mutableListOf<WeatherForecastDataResponse>()

            weatherForecastResponse.data.forEach {
                if(it.validDate == dateFormatter.format(data.startDate)){ between = true }
                if(it.validDate == dateFormatter.format(data.endDate)){ between = false }
                if(it.rainChance >= 50){ rain = true }
                if(it.snowChance >= 50){ snow = true }
                if(between){
                    list.add(it)
                    temp += it.temp
                }
            }
            temp /= list.size
            localDataSource.insert(calculations.calculateNeededItems(data, ForecastData(temp.roundToInt(), rain, snow)))
        }
    }


}