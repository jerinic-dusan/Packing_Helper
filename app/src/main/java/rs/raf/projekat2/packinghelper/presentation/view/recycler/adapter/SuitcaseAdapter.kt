package rs.raf.projekat2.packinghelper.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.packinghelper.R
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems
import rs.raf.projekat2.packinghelper.data.models.TripData
import rs.raf.projekat2.packinghelper.presentation.view.recycler.diff.SuitcaseDiffItemCallback
import rs.raf.projekat2.packinghelper.presentation.view.recycler.viewholder.SuitcaseViewHolder

class SuitcaseAdapter(suitcaseDiffItemCallback: SuitcaseDiffItemCallback,
                      private val editSuitcase: (SuitcaseWithItems) -> Unit,
                      private val deleteSuitcase: (SuitcaseWithItems) -> Unit): ListAdapter<SuitcaseWithItems, SuitcaseViewHolder>(suitcaseDiffItemCallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuitcaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.suitcase_item, parent, false)
        return SuitcaseViewHolder(containerView, {editSuitcase(getItem(it))}, {deleteSuitcase(getItem(it))})
    }

    override fun onBindViewHolder(holder: SuitcaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}