package rs.raf.projekat2.packinghelper.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.packinghelper.R
import rs.raf.projekat2.packinghelper.data.models.SuitcaseGroup
import rs.raf.projekat2.packinghelper.presentation.view.recycler.diff.SuitcaseGroupDiffItemCallback
import rs.raf.projekat2.packinghelper.presentation.view.recycler.viewholder.SuitcaseGroupViewHolder

class SuitcaseGroupAdapter(suitcaseGroupDiffItemCallback: SuitcaseGroupDiffItemCallback,
                           private val deleteGroup: (SuitcaseGroup) -> Unit,
                           private val addGroup: (SuitcaseGroup) -> Unit,
                           private val editGroup: (SuitcaseGroup) -> Unit): ListAdapter<SuitcaseGroup, SuitcaseGroupViewHolder>(suitcaseGroupDiffItemCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuitcaseGroupViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.suitcase_group, parent, false)
        return SuitcaseGroupViewHolder(containerView, {deleteGroup(getItem(it))}, {addGroup(getItem(it))}, {editGroup(getItem(it))})
    }

    override fun onBindViewHolder(holder: SuitcaseGroupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}