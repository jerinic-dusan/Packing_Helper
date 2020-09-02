package rs.raf.projekat2.packinghelper.presentation.view.recycler.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.suitcase_item.*
import kotlinx.android.synthetic.main.suitcase_item.view.*
import rs.raf.projekat2.packinghelper.R
import rs.raf.projekat2.packinghelper.data.models.SuitcaseSettings

class SuitcaseViewHolder(override val containerView: View,
                        editSuitcase: (Int) -> Unit,
                        deleteSuitcase: (Int) -> Unit): RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        edit_suitcase.setOnClickListener { editSuitcase(layoutPosition) }
        delete_suitcase.setOnClickListener { deleteSuitcase(layoutPosition) }
    }

    @SuppressLint("SetTextI18n")
    fun bind(suitcaseSettings: SuitcaseSettings){
        suitcase_title.text = suitcaseSettings.title
        location_text.text = suitcaseSettings.location.featureName
        occasion_text.text = suitcaseSettings.travelOccasion
        if(suitcaseSettings.travelOccasion == "Business Casual"){ occasion_text.text = "Business c..." }
        duration_text.text = suitcaseSettings.days
        temperature_text.text = suitcaseSettings.temperature
    }
}