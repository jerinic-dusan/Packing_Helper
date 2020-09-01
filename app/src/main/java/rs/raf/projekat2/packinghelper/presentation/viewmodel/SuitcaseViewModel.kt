package rs.raf.projekat2.packinghelper.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rs.raf.projekat2.packinghelper.data.models.SuitcaseSettings
import rs.raf.projekat2.packinghelper.presentation.contract.SuitcaseContract
import rs.raf.projekat2.packinghelper.presentation.view.states.SuitcaseState

class SuitcaseViewModel(): ViewModel(), SuitcaseContract.ViewModel {

    override val suitcaseState: MutableLiveData<SuitcaseState> = MutableLiveData()
    private val list = mutableListOf<SuitcaseSettings>()

    override fun setSuitcaseSettings(ss: SuitcaseSettings) {
        list.add(ss)
        suitcaseState.value = SuitcaseState.Success(list.toList())
    }

}