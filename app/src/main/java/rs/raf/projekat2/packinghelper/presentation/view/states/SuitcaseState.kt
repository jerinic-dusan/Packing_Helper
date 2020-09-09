package rs.raf.projekat2.packinghelper.presentation.view.states

import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems
import rs.raf.projekat2.packinghelper.data.models.TripData

sealed class SuitcaseState {
    object Loading: SuitcaseState()
    data class Success(val suitcases: List<SuitcaseWithItems>): SuitcaseState()
    data class Error(val message: String): SuitcaseState()
}