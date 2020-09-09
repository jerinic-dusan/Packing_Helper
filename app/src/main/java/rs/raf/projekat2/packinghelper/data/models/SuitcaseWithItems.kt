package rs.raf.projekat2.packinghelper.data.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuitcaseWithItems(
    @Embedded val suitcase: Suitcase,
    @Relation(
        parentColumn = "id",
        entityColumn = "suitcase_id"
    )
    val suitcaseItems: List<SuitcaseItem>
): Parcelable