package rs.raf.projekat2.packinghelper.data.datasources.calculations

import rs.raf.projekat2.packinghelper.data.models.ForecastData
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems
import rs.raf.projekat2.packinghelper.data.models.TripData

interface CalculationInterface {

    fun calculateNeededItems(td: TripData, fd: ForecastData): SuitcaseWithItems

}