package rs.raf.projekat2.packinghelper.data.datasources.calculations

import rs.raf.projekat2.packinghelper.data.models.*
import java.util.*

class Calculations: CalculationInterface {

    override fun calculateNeededItems(td: TripData, fd: ForecastData): SuitcaseWithItems {
        when(td.travelOccasion){
            "Custom" -> return SuitcaseWithItems(createSuitcase(td, fd), listOf())
            "Business" -> {
                val suitcase = createSuitcase(td, fd)
                return SuitcaseWithItems(suitcase, listOf(
                    calculateSuitJackets(suitcase.days),
                    calculateSuitPants(suitcase.days),
                    calculateBlazers(suitcase.days),
                    calculateSlacks(suitcase.days),
                    calculateJeans(suitcase.days),
                    calculateDressShirts(suitcase.days),
                    calculateDressShoes(suitcase.days),
                    calculateUndershirts(suitcase.days),
                    calculateUnderwear(suitcase.days),
                    calculateSocks(suitcase.days),
                    calculateBelts(suitcase.days),
                    calculateTies(suitcase.days),
                    calculatePocketSquares(suitcase.days),
                    calculateSweaters(suitcase.days),
                    calculateTshirts(suitcase.days),
                    calculateJoggersOrShorts(suitcase.days, fd.temp),
                    calculateJackets(fd.temp)
                ))
            }
        }

        return SuitcaseWithItems(
            createSuitcase(td, fd),
            listOf(
                SuitcaseItem(0,0,"Test1", "Tops", 1),
                SuitcaseItem(1,0,"Test2", "Bottoms", 1)
            )
        )
    }

    private fun calculateDaysBetween(start: Date, end: Date): Int{
        return ((end.time - start.time)/(1000 * 60 * 60 * 24)).toInt()
    }

    private fun createSuitcase(td: TripData, fd: ForecastData): Suitcase{
        return Suitcase(
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
        )
    }

    private fun calculateSuitJackets(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Suit Jacket", "Tops", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Suit Jacket", "Tops", 2)
        }else{
            SuitcaseItem(0,0, "Suit Jacket", "Tops", 3)
        }
    }

    private fun calculateSuitPants(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Suit Pants", "Bottoms", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Suit Pants", "Bottoms", 2)
        }else{
            SuitcaseItem(0,0, "Suit Pants", "Bottoms", 3)
        }
    }

    private fun calculateBlazers(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Blazer", "Tops", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Blazer", "Tops", 1)
        }else{
            SuitcaseItem(0,0, "Blazer", "Tops", 2)
        }
    }

    private fun calculateSlacks(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Slacks", "Bottoms", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Slacks", "Bottoms", 1)
        }else{
            SuitcaseItem(0,0, "Slacks", "Bottoms", 3)
        }
    }

    private fun calculateJeans(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Dark Jeans", "Bottoms", 0)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Dark Jeans", "Bottoms", 1)
        }else{
            SuitcaseItem(0,0, "Dark Jeans", "Bottoms", 1)
        }
    }

    private fun calculateDressShirts(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Dress Shirt", "Tops", days)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Dress Shirt", "Tops", days)
        }else{
            SuitcaseItem(0,0, "Dress Shirt", "Tops", 7)
        }
    }

    private fun calculateDressShoes(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Dress Shoes", "Footwear", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Dress Shoes", "Footwear", 2)
        }else{
            SuitcaseItem(0,0, "Dress Shoes", "Footwear", 3)
        }
    }

    private fun calculateUndershirts(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Undershirt", "Undergarments", days)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Undershirt", "Undergarments", days - 2)
        }else{
            SuitcaseItem(0,0, "Undershirt", "Undergarments", 5)
        }
    }

    private fun calculateUnderwear(days: Int): SuitcaseItem{
        return SuitcaseItem(0,0, "Underwear", "Undergarments", days)
    }

    private fun calculateSocks(days: Int): SuitcaseItem{
        return SuitcaseItem(0,0, "Socks", "Undergarments", days)
    }

    private fun calculateBelts(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Belt", "Accessories", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Belt", "Accessories", 2)
        }else{
            SuitcaseItem(0,0, "Belt", "Accessories", 2)
        }
    }

    private fun calculateTies(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Neck Tie", "Accessories", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Neck Tie", "Accessories", 2)
        }else{
            SuitcaseItem(0,0, "Neck Tie", "Accessories", 3)
        }
    }

    private fun calculatePocketSquares(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Pocket Square", "Accessories", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Pocket Square", "Accessories", 2)
        }else{
            SuitcaseItem(0,0, "Pocket Square", "Accessories", 3)
        }
    }

    private fun calculateSweaters(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Sweater", "Tops", 0)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Sweater", "Tops", 1)
        }else{
            SuitcaseItem(0,0, "Sweater", "Tops", 2)
        }
    }

    private fun calculateTshirts(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "T-Shirt", "Tops", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "T-Shirt", "Tops", 3)
        }else{
            SuitcaseItem(0,0, "T-Shirt", "Tops", 4)
        }
    }

    private fun calculateJoggersOrShorts(days: Int, temp: Int): SuitcaseItem{
        return if(days <= 3){
            if (temp < 20){
                SuitcaseItem(0,0, "Joggers", "Bottoms", 1)
            }else{
                SuitcaseItem(0,0, "Shorts", "Bottoms", 1)
            }
        }else if (days in 4..7){
            if (temp < 20){
                SuitcaseItem(0,0, "Joggers", "Bottoms", 2)
            }else{
                SuitcaseItem(0,0, "Shorts", "Bottoms", 2)
            }
        }else{
            if (temp < 20){
                SuitcaseItem(0,0, "Joggers", "Bottoms", 3)
            }else{
                SuitcaseItem(0,0, "Shorts", "Bottoms", 3)
            }
        }
    }

    private fun calculateJackets(temp: Int): SuitcaseItem{
        return if(temp <= 10){
            SuitcaseItem(0,0, "Coat", "Outer wear", 1)
        }else{
            SuitcaseItem(0,0, "Coat", "Outer wear", 0)
        }
    }
}