package rs.raf.projekat2.packinghelper.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems
import rs.raf.projekat2.packinghelper.data.models.TripData

interface SuitcaseRepository {


    fun create(data: TripData): Completable
    fun update(suitcaseWithItems: SuitcaseWithItems): Completable
    fun delete(suitcaseWithItems: SuitcaseWithItems): Completable
    fun getAll(): Observable<List<SuitcaseWithItems>>

}