package rs.raf.projekat2.packinghelper.presentation.view.recycler.viewholder

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

    fun bind(suitcaseSettings: SuitcaseSettings){
        suitcase_title.text = suitcaseSettings.title
        when(suitcaseSettings.travelOccasion){
            "Business" -> { suitcase_image.setImageResource(R.drawable.ic_business) }
            "Business Casual" -> { suitcase_image.setImageResource(R.drawable.ic_businessman_casual) }
            "Casual" -> { suitcase_image.setImageResource(R.drawable.ic_casual_man) }
        }
        location_text.text = suitcaseSettings.location.featureName
        occasion_text.text = suitcaseSettings.travelOccasion
        duration_text.text = suitcaseSettings.days
        temperature_text.text = suitcaseSettings.temperature
    }
}