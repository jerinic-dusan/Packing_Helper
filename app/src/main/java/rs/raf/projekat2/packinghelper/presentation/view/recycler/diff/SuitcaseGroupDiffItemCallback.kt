package rs.raf.projekat2.packinghelper.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.packinghelper.data.models.SuitcaseGroup

class SuitcaseGroupDiffItemCallback: DiffUtil.ItemCallback<SuitcaseGroup>() {

    override fun areItemsTheSame(oldItem: SuitcaseGroup, newItem: SuitcaseGroup): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SuitcaseGroup, newItem: SuitcaseGroup): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.name == newItem.name &&
                oldItem.items == newItem.items
    }

}