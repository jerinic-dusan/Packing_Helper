package rs.raf.projekat2.packinghelper.presentation.contract

import android.content.Context
import androidx.lifecycle.LiveData
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems
import rs.raf.projekat2.packinghelper.data.models.TripData
import rs.raf.projekat2.packinghelper.presentation.view.states.SuitcaseState

interface SuitcaseContract {

    interface ViewModel{

        val suitcaseState: LiveData<SuitcaseState>

        fun create(data: TripData, context: Context)
        fun update(suitcaseWithItems: SuitcaseWithItems)
        fun delete(suitcaseWithItems: SuitcaseWithItems)
        fun getAll()

    }

}