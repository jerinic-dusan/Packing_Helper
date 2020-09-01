package rs.raf.projekat2.packinghelper.presentation.contract

import android.location.Address
import android.location.Location
import androidx.lifecycle.LiveData
import rs.raf.projekat2.packinghelper.data.models.SuitcaseSettings
import rs.raf.projekat2.packinghelper.presentation.view.states.SuitcaseState
import java.util.*

interface SuitcaseContract {

    interface ViewModel{

        val suitcaseState: LiveData<SuitcaseState>
        fun setSuitcaseSettings(ss: SuitcaseSettings)

    }

}