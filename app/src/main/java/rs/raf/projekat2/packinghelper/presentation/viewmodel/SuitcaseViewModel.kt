package rs.raf.projekat2.packinghelper.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat2.packinghelper.data.models.TripData
import rs.raf.projekat2.packinghelper.data.repositories.SuitcaseRepository
import rs.raf.projekat2.packinghelper.presentation.contract.SuitcaseContract
import rs.raf.projekat2.packinghelper.presentation.view.states.SuitcaseState
import timber.log.Timber

class SuitcaseViewModel(private val suitcaseRepository: SuitcaseRepository): ViewModel(), SuitcaseContract.ViewModel {

    override val suitcaseState: MutableLiveData<SuitcaseState> = MutableLiveData()
    private val list = mutableListOf<TripData>()
    private val subscriptions = CompositeDisposable()

    override fun getAll() {
        val subscription = suitcaseRepository.getAll().
            subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    suitcaseState.value = SuitcaseState.Success(it)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun create(data: TripData) {
        val subscription = suitcaseRepository.create(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

}