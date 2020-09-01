package rs.raf.projekat2.packinghelper.presentation.view.recycler.diff

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.packinghelper.data.models.SuitcaseSettings

class SuitcaseDiffItemCallback: DiffUtil.ItemCallback<SuitcaseSettings>(){

    override fun areItemsTheSame(oldItem: SuitcaseSettings, newItem: SuitcaseSettings): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SuitcaseSettings, newItem: SuitcaseSettings): Boolean {
        return oldItem.location == newItem.location &&
                oldItem.startDate == newItem.startDate &&
                oldItem.endDate == newItem.endDate &&
                oldItem.gender == newItem.gender &&
                oldItem.travelOccasion == newItem.travelOccasion
    }
}