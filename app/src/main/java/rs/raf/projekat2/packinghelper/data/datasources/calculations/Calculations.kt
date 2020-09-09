package rs.raf.projekat2.packinghelper.data.datasources.calculations

import rs.raf.projekat2.packinghelper.data.models.*
import java.util.*

class Calculations: CalculationInterface {

    override fun calculateNeededItems(td: TripData, fd: ForecastData): SuitcaseWithItems {
        return SuitcaseWithItems(
            Suitcase(
                0,
                "Trip to ${td.location.featureName}!",
                "I hope weather will favor us.",
                td.location.featureName,
                td.location.latitude,
                td.location.longitude,
                td.travelOccasion,
                td.startDate,
                td.endDate,
                calculateDaysBetween(td.startDate, td.endDate),
                td.gender,
                "~" + fd.temp  + "°C",
                fd.rain,
                fd.snow
            ),
            listOf()
        )
    }

    private fun calculateDaysBetween(start: Date, end: Date): Int{
        return ((end.time - start.time)/(1000 * 60 * 60 * 24)).toInt()
    }

}