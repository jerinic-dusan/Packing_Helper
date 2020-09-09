package rs.raf.projekat2.packinghelper.presentation.view.recycler.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.suitcase_item.*
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems
import rs.raf.projekat2.packinghelper.data.models.TripData

class SuitcaseViewHolder(override val containerView: View,
                        editSuitcase: (Int) -> Unit,
                        deleteSuitcase: (Int) -> Unit): RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        card_view.setOnClickListener { editSuitcase(layoutPosition) }
        edit_suitcase.setOnClickListener { editSuitcase(layoutPosition) }
        delete_suitcase.setOnClickListener { deleteSuitcase(layoutPosition) }
    }

    @SuppressLint("SetTextI18n")
    fun bind(suitcaseWithItems: SuitcaseWithItems){
        suitcase_title.text = suitcaseWithItems.suitcase.name
        location_text.text = suitcaseWithItems.suitcase.location
        occasion_text.text = suitcaseWithItems.suitcase.occasion
        if(suitcaseWithItems.suitcase.occasion == "Business Casual"){ occasion_text.text = "Business c..." }
        duration_text.text = suitcaseWithItems.suitcase.days.toString() + " days"
        temperature_text.text = suitcaseWithItems.suitcase.temp
    }
}