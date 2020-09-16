package rs.raf.projekat2.packinghelper.data.datasources.calculations

import rs.raf.projekat2.packinghelper.data.models.*
import java.util.*

class Calculations: CalculationInterface {

    companion object{
        const val BUSINESS = "Business"
        const val BUSINESS_CASUAL = "Business Casual"
        const val CASUAL = "Casual"
        const val MALE = "Male"
        const val FEMALE = "Female"
    }

    override fun calculateNeededItems(td: TripData, fd: ForecastData): SuitcaseWithItems {
        when(td.gender){
            "Male" -> {
                val suitcase = createSuitcase(td, fd)
                if (td.travelOccasion == "Custom"){ return SuitcaseWithItems(suitcase, listOf())  }
                if (td.travelOccasion == "Business" || td.travelOccasion == "Business Casual"){
                    val items = mutableListOf(
                        calculateSlacks(suitcase.days, MALE, BUSINESS), calculateJeansOrShorts(suitcase.days, fd.temp, BUSINESS), calculateDressShirts(suitcase.days, MALE),
                        calculateDressShoes(suitcase.days, MALE), calculateUndershirts(suitcase.days), calculateUnderwear(suitcase.days),
                        calculateSocks(suitcase.days), calculateBelts(suitcase.days, MALE, BUSINESS), calculatePocketSquares(suitcase.days),
                        calculateSweaters(suitcase.days), calculateTshirts(suitcase.days, BUSINESS),
                        calculateJewelry(MALE), calculateUmbrella(fd.rain), calculatePajamas(suitcase.days), calculateCasualShoe(suitcase.days, fd.temp, MALE)
                    )
                    when(td.travelOccasion){
                        "Business" -> {
                            items.add(calculateSuitJackets(suitcase.days, MALE))
                            items.add(calculateSuitPants(suitcase.days, MALE))
                            items.add(calculateBlazers(suitcase.days, MALE, BUSINESS))
                            items.add(calculateTies(suitcase.days))
                            items.add(calculateJackets(fd.temp, BUSINESS))
                        }
                        "Business Casual" -> {
                            items.add(calculateSportsJacket(suitcase.days))
                            items.add(calculateChinos(suitcase.days))
                            items.add(calculateJackets(fd.temp, BUSINESS_CASUAL))
                        }
                    }
                    items.removeIf { it.amount == 0 }
                    items.sortBy { it.group }
                    return SuitcaseWithItems(suitcase, items.toList())
                }
                if (td.travelOccasion == "Casual"){
                    val items = mutableListOf(
                        calculateJackets(fd.temp, CASUAL), calculateHoodie(suitcase.days, MALE, fd.temp), calculateJeansOrShorts(suitcase.days, fd.temp, CASUAL),
                        calculateSneakers(suitcase.days), calculateUnderwear(suitcase.days), calculateSocks(suitcase.days),
                        calculateBelts(suitcase.days, MALE, CASUAL), calculateUmbrella(fd.rain), calculatePajamas(suitcase.days), calculateTshirts(suitcase.days, CASUAL)
                    )
                    items.removeIf { it.amount == 0 }
                    items.sortBy { it.group }
                    return SuitcaseWithItems(suitcase, items.toList())
                }
            }
            "Female" -> {
                val suitcase = createSuitcase(td, fd)
                if (td.travelOccasion == "Custom"){ return SuitcaseWithItems(suitcase, listOf()) }
                if (td.travelOccasion == "Business" || td.travelOccasion == "Business Casual"){
                    val items = mutableListOf(
                        calculateBras(suitcase.days), calculateTights(suitcase.days, fd.temp), calculateJeansOrShorts(suitcase.days, fd.temp, BUSINESS), calculateDressShirts(suitcase.days, FEMALE),
                        calculateDressShoes(suitcase.days, FEMALE), calculateUnderwear(suitcase.days),
                        calculateSocks(suitcase.days), calculateTshirts(suitcase.days, BUSINESS),
                        calculateJewelry(FEMALE), calculateUmbrella(fd.rain), calculatePajamas(suitcase.days),
                        calculateCasualShoe(suitcase.days, fd.temp, FEMALE), calculateDress(suitcase.days)
                    )
                    when(td.travelOccasion){
                        "Business" -> {
                            items.add(calculateSuitJackets(suitcase.days, FEMALE))
                            items.add(calculateSuitPants(suitcase.days, FEMALE))
                            items.add(calculateBlazers(suitcase.days, FEMALE, BUSINESS))
                            items.add(calculateBelts(suitcase.days, FEMALE, BUSINESS))
                            items.add(calculateSlacks(suitcase.days, FEMALE, BUSINESS))
                            items.add(calculateJackets(fd.temp, BUSINESS))
                            items.add(calculateSkirts(suitcase.days, BUSINESS))
                        }
                        "Business Casual" -> {
                            items.add(calculateBlazers(suitcase.days, FEMALE, BUSINESS_CASUAL))
                            items.add(calculateBelts(suitcase.days, FEMALE, BUSINESS_CASUAL))
                            items.add(calculateSlacks(suitcase.days, FEMALE, BUSINESS_CASUAL))
                            items.add(calculateJackets(fd.temp, BUSINESS_CASUAL))
                            items.add(calculateSkirts(suitcase.days, BUSINESS_CASUAL))
                        }
                    }
                    items.removeIf { it.amount == 0 }
                    items.sortBy { it.group }
                    return SuitcaseWithItems(suitcase, items.toList())
                }
                if (td.travelOccasion == "Casual"){
                    val items = mutableListOf(
                        calculateJackets(fd.temp, CASUAL), calculateHoodie(suitcase.days, FEMALE, fd.temp), calculateJeansOrShorts(suitcase.days, fd.temp, CASUAL),
                        calculateSneakers(suitcase.days), calculateUnderwear(suitcase.days), calculateSocks(suitcase.days), calculateTshirts(suitcase.days, CASUAL),
                        calculateBelts(suitcase.days, FEMALE, CASUAL), calculateUmbrella(fd.rain), calculatePajamas(suitcase.days), calculateBras(suitcase.days)
                    )
                    items.removeIf { it.amount == 0 }
                    items.sortBy { it.group }
                    return SuitcaseWithItems(suitcase, items.toList())
                }
            }
        }
        //THIS WILL NEVER HAPPEN
        val suitcase = createSuitcase(td, fd)
        return SuitcaseWithItems(suitcase, listOf())
    }

    private fun calculateDaysBetween(start: Date, end: Date): Int{
        return ((end.time - start.time)/(1000 * 60 * 60 * 24)).toInt()
    }

    private fun createSuitcase(td: TripData, fd: ForecastData): Suitcase{
        return Suitcase(
            0,
            "${td.location.featureName} trip",
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

    //ITEMS

    private fun calculateSuitJackets(days: Int, gender: String): SuitcaseItem{
        return if (gender == "Male") {
            return if (days <= 3) {
                SuitcaseItem(0, 0, "Suit Jacket", "Tops", 1)
            } else if (days in 4..7) {
                SuitcaseItem(0, 0, "Suit Jacket", "Tops", 2)
            } else {
                SuitcaseItem(0, 0, "Suit Jacket", "Tops", 3)
            }
        } else {
            if (days <= 3) {
                SuitcaseItem(0, 0, "Suit Jacket", "Tops", 1)
            } else if (days in 4..7) {
                SuitcaseItem(0, 0, "Suit Jacket", "Tops", 1)
            } else {
                SuitcaseItem(0, 0, "Suit Jacket", "Tops", 2)
            }
        }
    }

    private fun calculateSuitPants(days: Int, gender: String): SuitcaseItem{
        return if (gender == "Male") {
            if(days <= 3){
                SuitcaseItem(0,0, "Suit Pants", "Bottoms", 1)
            }else if (days in 4..7){
                SuitcaseItem(0,0, "Suit Pants", "Bottoms", 2)
            }else{
                SuitcaseItem(0,0, "Suit Pants", "Bottoms", 3)
            }
        } else {
            if(days <= 3){
                SuitcaseItem(0,0, "Suit Pants", "Bottoms", 1)
            }else if (days in 4..7){
                SuitcaseItem(0,0, "Suit Pants", "Bottoms", 1)
            }else{
                SuitcaseItem(0,0, "Suit Pants", "Bottoms", 2)
            }
        }
    }

    private fun calculateBlazers(days: Int, gender: String, occasion: String): SuitcaseItem{
        return if (occasion == "Casual"){
            SuitcaseItem(0,0, "Blazer", "Tops", 0)
        }else{
            if (gender == "Male"){
                if(days <= 1){
                    SuitcaseItem(0,0, "Blazer", "Tops", 0)
                }else if (days <= 3){
                    SuitcaseItem(0,0, "Blazer", "Tops", 1)
                }else if (days in 4..7){
                    SuitcaseItem(0,0, "Blazer", "Tops", 1)
                }else{
                    SuitcaseItem(0,0, "Blazer", "Tops", 2)
                }
            }else{
                if (occasion == "Business"){
                    if(days <= 1){
                        SuitcaseItem(0,0, "Blazer", "Tops", 0)
                    }else if (days <= 3){
                        SuitcaseItem(0,0, "Blazer", "Tops", 1)
                    }else if (days in 4..7){
                        SuitcaseItem(0,0, "Blazer", "Tops", 2)
                    }else{
                        SuitcaseItem(0,0, "Blazer", "Tops", 3)
                    }
                }else{
                    if(days <= 1){
                        SuitcaseItem(0,0, "Blazer", "Tops", 1)
                    }else if (days <= 3){
                        SuitcaseItem(0,0, "Blazer", "Tops", 2)
                    }else if (days in 4..7){
                        SuitcaseItem(0,0, "Blazer", "Tops", 3)
                    }else{
                        SuitcaseItem(0,0, "Blazer", "Tops", 3)
                    }
                }
            }
        }
    }

    private fun calculateSportsJacket(days: Int): SuitcaseItem{
        return if(days <= 3){
                SuitcaseItem(0,0, "Sports Jacket", "Tops", 1)
            }else if (days in 4..7){
                SuitcaseItem(0,0, "Sports Jacket", "Tops", 2)
            }else{
                SuitcaseItem(0,0, "Sports Jacket", "Tops", 3)
            }
    }

    private fun calculateChinos(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Chinos", "Bottoms", 1)
        }else{
            SuitcaseItem(0,0, "Chinos", "Bottoms", 2)
        }
    }

    private fun calculateSlacks(days: Int, gender: String, occasion: String): SuitcaseItem{
        return if (occasion == "Casual"){
            SuitcaseItem(0,0, "Slacks", "Bottoms", 0)
        }else{
            if (gender == "Male"){
                if(occasion == "Business"){
                    if(days <= 1){
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 0)
                    }else if(days <= 3){
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 1)
                    }else if (days in 4..7){
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 1)
                    }else{
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 3)
                    }
                }else{
                    if(days <= 3){
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 1)
                    }else if (days in 4..7){
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 1)
                    }else{
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 3)
                    }
                }
            }else{
                if(occasion == "Business"){
                    if(days <= 7){
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 0)
                    }else{
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 1)
                    }
                }else{
                    if(days <= 1){
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 0)
                    }else if(days <= 3){
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 1)
                    }else if (days in 4..7){
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 1)
                    }else{
                        SuitcaseItem(0,0, "Slacks", "Bottoms", 2)
                    }
                }
            }
        }

    }

    private fun calculateJeansOrShorts(days: Int, temp: Int, occasion: String): SuitcaseItem {
        return if (occasion == "Casual"){
            if (temp >= 20) {
                if (days <= 4) {
                    SuitcaseItem(0, 0, "Shorts", "Bottoms", days)
                } else {
                    SuitcaseItem(0, 0, "Shorts", "Bottoms", 5)
                }
            }else{
                if (days <= 4) {
                    SuitcaseItem(0, 0, "Jeans", "Bottoms", days)
                }else {
                    SuitcaseItem(0, 0, "Jeans", "Bottoms", 5)
                }
            }
        }else{
            if (temp >= 20) {
                if (days <= 3) {
                    SuitcaseItem(0, 0, "Shorts", "Bottoms", 0)
                } else if (days in 4..7) {
                    SuitcaseItem(0, 0, "Shorts", "Bottoms", 1)
                } else {
                    SuitcaseItem(0, 0, "Shorts", "Bottoms", 2)
                }
            }else{
                if (days <= 3) {
                    SuitcaseItem(0, 0, "Jeans", "Bottoms", 0)
                } else if (days in 4..7) {
                    SuitcaseItem(0, 0, "Jeans", "Bottoms", 1)
                } else {
                    SuitcaseItem(0, 0, "Jeans", "Bottoms", 2)
                }
            }
        }
    }

    private fun calculateDressShirts(days: Int, gender: String): SuitcaseItem{
        return if (gender == "Male"){
            if(days <= 3){
                SuitcaseItem(0,0, "Dress Shirt", "Tops", days)
            }else if (days in 4..7){
                SuitcaseItem(0,0, "Dress Shirt", "Tops", days)
            }else{
                SuitcaseItem(0,0, "Dress Shirt", "Tops", 7)
            }
        }else{
            if(days <= 3){
                SuitcaseItem(0,0, "Blouse", "Tops", days)
            }else if (days in 4..7){
                SuitcaseItem(0,0, "Blouse", "Tops", days)
            }else{
                SuitcaseItem(0,0, "Blouse", "Tops", 7)
            }
        }

    }

    private fun calculateDressShoes(days: Int, gender: String): SuitcaseItem{
        return if (gender == "Male") {
            if (days <= 3) {
                SuitcaseItem(0, 0, "Dress Shoes", "Footwear", 1)
            } else if (days in 4..7) {
                SuitcaseItem(0, 0, "Dress Shoes", "Footwear", 2)
            } else {
                SuitcaseItem(0, 0, "Dress Shoes", "Footwear", 3)
            }
        }else{
            if (days <= 3) {
                SuitcaseItem(0, 0, "Heels", "Footwear", 1)
            } else if (days in 4..7) {
                SuitcaseItem(0, 0, "Heels", "Footwear", 2)
            } else {
                SuitcaseItem(0, 0, "Heels", "Footwear", 3)
            }
        }
    }

    private fun calculateUndershirts(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Undershirt", "Undergarments", days)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Undershirt", "Undergarments", (days/4).toInt()*3)
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

    private fun calculateBras(days: Int): SuitcaseItem{
        return if (days <= 1){
            SuitcaseItem(0,0, "Bras", "Undergarments", 1)
        }else if (days <= 3){
            SuitcaseItem(0,0, "Bras", "Undergarments", 2)
        }else{
            SuitcaseItem(0,0, "Bras", "Undergarments", 3)
        }
    }

    private fun calculateBelts(days: Int, gender: String, occasion: String): SuitcaseItem{
        if(days <= 1 && gender == "Female" && occasion == "Business Casual"){ return SuitcaseItem(0,0, "Belt", "Accessories", 0) }
        return if(days <= 3){
            SuitcaseItem(0,0, "Belt", "Accessories", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Belt", "Accessories", 1)
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

    private fun calculateTshirts(days: Int, occasion: String): SuitcaseItem{
        if (occasion == "Casual"){ return SuitcaseItem(0,0, "T-Shirt", "Tops", days) }
        return if(days <= 1){
            SuitcaseItem(0,0, "T-Shirt", "Tops", 0)
        }else if(days <= 3){
            SuitcaseItem(0,0, "T-Shirt", "Tops", 1)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "T-Shirt", "Tops", 2)
        }else{
            SuitcaseItem(0,0, "T-Shirt", "Tops", 3)
        }
    }

    private fun calculateJewelry(gender: String): SuitcaseItem{
        return if(gender == "Male"){
            SuitcaseItem(0,0, "Watch", "Accessories", 1)
        }else{
            SuitcaseItem(0,0, "Jewelry", "Accessories", 1)
        }
    }

    private fun calculateJackets(temp: Int, occasion: String): SuitcaseItem{
        return if (occasion == "Business"){
            if(temp <= 10){
                SuitcaseItem(0,0, "Coat", "Outer wear", 1)
            }else{
                SuitcaseItem(0,0, "Coat", "Outer wear", 0)
            }
        }else{
            if(temp <= 10){
                SuitcaseItem(0,0, "Jacket", "Outer wear", 1)
            }else{
                SuitcaseItem(0,0, "Jacket", "Outer wear", 0)
            }
        }
    }

    private fun calculateUmbrella(rain: Boolean): SuitcaseItem{
        return if(rain){
            SuitcaseItem(0,0, "Umbrella", "Other", 1)
        }else{
            SuitcaseItem(0,0, "Umbrella", "Other", 0)
        }
    }

    private fun calculatePajamas(days: Int): SuitcaseItem{
        return if(days <= 1){
            SuitcaseItem(0,0, "Pajamas", "Other", 0)
        }else if(days <= 7){
            SuitcaseItem(0,0, "Pajamas", "Other", 1)
        }else{
            SuitcaseItem(0,0, "Pajamas", "Other", 2)
        }
    }

    private fun calculateCasualShoe(days: Int, temp: Int, gender: String): SuitcaseItem{
        return if (gender == "Male"){
            if(temp <= 8){
                if(days <= 1){
                    SuitcaseItem(0,0, "Boots", "Footwear", 0)
                }else if(days <= 7){
                    SuitcaseItem(0,0, "Boots", "Footwear", 1)
                }else{
                    SuitcaseItem(0,0, "Boots", "Footwear", 2)
                }
            }else{
                if(days <= 1){
                    SuitcaseItem(0,0, "Casual Shoes", "Footwear", 0)
                }else if(days <= 7){
                    SuitcaseItem(0,0, "Casual Shoes", "Footwear", 1)
                }else{
                    SuitcaseItem(0,0, "Casual Shoes", "Footwear", 2)
                }
            }
        }else{
            if(temp <= 8){
                if(days <= 1){
                    SuitcaseItem(0,0, "Boots", "Footwear", 0)
                }else if(days <= 7){
                    SuitcaseItem(0,0, "Boots", "Footwear", 1)
                }else{
                    SuitcaseItem(0,0, "Boots", "Footwear", 2)
                }
            }else{
                if(days <= 1){
                    SuitcaseItem(0,0, "Flats", "Footwear", 0)
                }else if(days <= 7){
                    SuitcaseItem(0,0, "Flats", "Footwear", 1)
                }else{
                    SuitcaseItem(0,0, "Flats", "Footwear", 2)
                }
            }
        }
    }

    private fun calculateSneakers(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Casual Shoes", "Footwear", 1)
        }else if(days <= 7){
            SuitcaseItem(0,0, "Casual Shoes", "Footwear", 2)
        }else{
            SuitcaseItem(0,0, "Casual Shoes", "Footwear", 3)
        }
    }

    private fun calculateHoodie(days: Int, gender: String, temp: Int): SuitcaseItem{
        if (temp >= 20){ return SuitcaseItem(0,0, "Hoodie", "Tops", 0) }
        return if (gender == "Male"){
            if(days <= 1){
                SuitcaseItem(0,0, "Hoodie", "Tops", 1)
            }else if(days <= 3){
                SuitcaseItem(0,0, "Hoodie", "Tops", 2)
            }else if(days <= 7){
                SuitcaseItem(0,0, "Hoodie", "Tops", 3)
            }else{
                SuitcaseItem(0,0, "Hoodie", "Tops", 5)
            }
        }else{
            if (temp > 10){
                if(days <= 1){
                    SuitcaseItem(0,0, "Hoodie", "Tops", 1)
                }else if(days <= 3){
                    SuitcaseItem(0,0, "Hoodie", "Tops", 2)
                }else if(days <= 7){
                    SuitcaseItem(0,0, "Hoodie", "Tops", 3)
                }else{
                    SuitcaseItem(0,0, "Hoodie", "Tops", 5)
                }
            }else{
                if(days <= 1){
                    SuitcaseItem(0,0, "Sweater", "Tops", 1)
                }else if(days <= 3){
                    SuitcaseItem(0,0, "Sweater", "Tops", 2)
                }else if(days <= 7){
                    SuitcaseItem(0,0, "Sweater", "Tops", 3)
                }else{
                    SuitcaseItem(0,0, "Sweater", "Tops", 5)
                }
            }
        }
    }

    private fun calculateSkirts(days: Int, occasion: String): SuitcaseItem{
        return if (occasion == "Business Casual"){
            if(days <= 2){
                SuitcaseItem(0,0, "Skirt", "Bottoms", 1)
            }else if(days <= 3){
                SuitcaseItem(0,0, "Skirt", "Bottoms", 2)
            }else if (days in 4..7){
                SuitcaseItem(0,0, "Skirt", "Bottoms", 3)
            }else{
                SuitcaseItem(0,0, "Skirt", "Bottoms", 4)
            }
        }else{
            if(days <= 1){
                SuitcaseItem(0,0, "Skirt", "Bottoms", 0)
            }else if(days <= 3){
                SuitcaseItem(0,0, "Skirt", "Bottoms", 1)
            }else if (days in 4..7){
                SuitcaseItem(0,0, "Skirt", "Bottoms", 2)
            }else{
                SuitcaseItem(0,0, "Skirt", "Bottoms", 3)
            }
        }
    }

    private fun calculateTights(days: Int, temp: Int): SuitcaseItem{
        if (temp > 5){ return SuitcaseItem(0,0, "Tights", "Undergarments", 0) }
        return if (days <= 3){
            SuitcaseItem(0,0, "Tights", "Undergarments", 1)
        }else if(days <= 7){
            SuitcaseItem(0,0, "Tights", "Undergarments", 2)
        }else{
            SuitcaseItem(0,0, "Tights", "Undergarments", 3)
        }
    }

    private fun calculateDress(days: Int): SuitcaseItem{
        return if(days <= 3){
            SuitcaseItem(0,0, "Dress", "Tops", 0)
        }else if (days in 4..7){
            SuitcaseItem(0,0, "Dress", "Tops",  1)
        }else{
            SuitcaseItem(0,0, "Dress", "Tops", 2)
        }
    }
}