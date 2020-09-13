package rs.raf.projekat2.packinghelper.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuitcaseGroup(
    val id: Int,
    var name: String,
    var items: MutableList<SuitcaseItem>
): Parcelable