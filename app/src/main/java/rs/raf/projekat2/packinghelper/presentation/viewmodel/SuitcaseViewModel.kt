package rs.raf.projekat2.packinghelper.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat2.packinghelper.data.models.ForecastResponse
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems
import rs.raf.projekat2.packinghelper.data.models.TripData
import rs.raf.projekat2.packinghelper.data.repositories.SuitcaseRepository
import rs.raf.projekat2.packinghelper.presentation.contract.SuitcaseContract
import rs.raf.projekat2.packinghelper.presentation.view.states.SuitcaseState
import timber.log.Timber

class SuitcaseViewModel(private val suitcaseRepository: SuitcaseRepository): ViewModel(), SuitcaseContract.ViewModel {

    override val suitcaseState: MutableLiveData<SuitcaseState> = MutableLiveData()
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

    override fun getForecast(data: TripData, context: Context) {
        val subscription = suitcaseRepository.getForecast(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Toast.makeText(context, "Fetching weather forecast.", Toast.LENGTH_SHORT).show()
                    calculations(it, context)
                },
                {
                    Toast.makeText(context, "Please connect to internet to use weather forecast functionality.", Toast.LENGTH_LONG).show()
                }
            )
        subscriptions.add(subscription)
    }

    override fun calculations(forecastResponse: ForecastResponse, context: Context) {
        val subscription = suitcaseRepository.calculations(forecastResponse)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
//                    Toast.makeText(context, "Calculating suitcase items.", Toast.LENGTH_SHORT).show()
                    insert(it, context)
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insert(suitcaseWithItems: SuitcaseWithItems, context: Context) {
        val subscription = suitcaseRepository.create(suitcaseWithItems)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
//                    Toast.makeText(context, "Created new suitcase.", Toast.LENGTH_SHORT).show()
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun delete(suitcaseWithItems: SuitcaseWithItems) {
        val subscription = suitcaseRepository.delete(suitcaseWithItems)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Deleted $suitcaseWithItems")
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun update(suitcaseWithItems: SuitcaseWithItems) {
        val subscription = suitcaseRepository.update(suitcaseWithItems)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.e("Updated $suitcaseWithItems")
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