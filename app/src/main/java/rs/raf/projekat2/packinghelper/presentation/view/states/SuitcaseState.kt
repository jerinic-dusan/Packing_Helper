package rs.raf.projekat2.packinghelper.presentation.view.states

import rs.raf.projekat2.packinghelper.data.models.SuitcaseSettings

sealed class SuitcaseState {
    object Loading: SuitcaseState()
    data class Success(val suitcases: List<SuitcaseSettings>): SuitcaseState()
    data class Error(val message: String): SuitcaseState()
}