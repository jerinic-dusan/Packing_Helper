package rs.raf.projekat2.packinghelper.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import rs.raf.projekat2.packinghelper.data.models.Suitcase
import rs.raf.projekat2.packinghelper.data.models.SuitcaseItem
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems

@Dao
abstract class SuitcaseDao{

    @Transaction
    @Query("SELECT * FROM suitcases")
    abstract fun getSuitcaseWithItems(): Observable<List<SuitcaseWithItems>>

    @Update
    abstract fun updateSuitcase(suitcase: Suitcase): Completable

    @Query("DELETE FROM suitcase_items WHERE suitcase_id == :id")
    abstract fun deleteById(id: Long): Completable

    @Transaction
    @Delete
    abstract fun delete(suitcase: Suitcase, suitcaseItems: List<SuitcaseItem>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSuitcase(suitcase: Suitcase): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertItems(items: List<SuitcaseItem>): Completable

    @Transaction
    open fun insert(suitcaseWithItems: SuitcaseWithItems){
        val id = insertSuitcase(suitcaseWithItems.suitcase).blockingGet()
        suitcaseWithItems.suitcaseItems.forEach { it.suitcaseId = id }
        insertItems(suitcaseWithItems.suitcaseItems).blockingAwait()
    }

    @Transaction
    open fun update(suitcaseWithItems: SuitcaseWithItems){
        updateSuitcase(suitcaseWithItems.suitcase).blockingAwait()
        deleteById(suitcaseWithItems.suitcase.id).blockingAwait()
        insertItems(suitcaseWithItems.suitcaseItems).blockingAwait()
        getSuitcaseWithItems()
    }

}