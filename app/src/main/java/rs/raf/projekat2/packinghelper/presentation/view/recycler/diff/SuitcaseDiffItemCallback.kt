package rs.raf.projekat2.packinghelper.presentation.view.recycler.diff

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems

class SuitcaseDiffItemCallback: DiffUtil.ItemCallback<SuitcaseWithItems>(){

    override fun areItemsTheSame(oldItem: SuitcaseWithItems, newItem: SuitcaseWithItems): Boolean {
        return oldItem.suitcase.id == newItem.suitcase.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SuitcaseWithItems, newItem: SuitcaseWithItems): Boolean {
        return oldItem.suitcase.location == newItem.suitcase.location &&
                oldItem.suitcase.startDate == newItem.suitcase.startDate &&
                oldItem.suitcase.endDate == newItem.suitcase.endDate &&
                oldItem.suitcase.gender == newItem.suitcase.gender &&
                oldItem.suitcase.occasion == newItem.suitcase.occasion
    }
}