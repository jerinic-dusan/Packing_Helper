package rs.raf.projekat2.packinghelper.presentation.view.recycler.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.activity_suitcase.*
import kotlinx.android.synthetic.main.suitcase_group.*
import kotlinx.android.synthetic.main.suitcase_group.view.*
import kotlinx.android.synthetic.main.suitcase_group_item.view.*
import kotlinx.android.synthetic.main.suitcase_group_item_separator.view.*
import rs.raf.projekat2.packinghelper.R
import rs.raf.projekat2.packinghelper.R.drawable.round_text_view
import rs.raf.projekat2.packinghelper.data.models.SuitcaseGroup
import timber.log.Timber

class SuitcaseGroupViewHolder(override val containerView: View,
                                        deleteGroup: (Int) -> Unit, addGroup: (Int) -> Unit): RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        delete_group.setOnClickListener { deleteGroup(layoutPosition) }
        add_suitcase_group.setOnClickListener {
            group_layout.isPressed = false
            group_layout.isPressed = true
            addGroup(layoutPosition)
        }
    }

    @SuppressLint("InflateParams")
    fun bind(suitcaseGroup: SuitcaseGroup){
        if(suitcaseGroup.name == "Add"){
            add_suitcase_group.visibility = View.VISIBLE
            delete_group.visibility = View.GONE
            return
        }

        group_name.setText(suitcaseGroup.name)
        val layout = containerView.suitcase_group_items
        layout.removeAllViews()
        val inflater = LayoutInflater.from(containerView.context)
        suitcaseGroup.items.forEach {
            val rowView: View = inflater.inflate(R.layout.suitcase_group_item, null)
            rowView.group_item.setText(it.name)
            val amount = rowView.group_item_amount
            amount.setText(it.amount.toString())
            setListenerToItems(amount, rowView, layout)
            layout.addView(rowView, layout.childCount)
        }
        val rowView: View = inflater.inflate(R.layout.suitcase_group_item_separator, null)
        layout.addView(rowView, layout.childCount)

        rowView.add_suitcase_item.setOnClickListener {
            setListenerToSeparator(it, layout, inflater)
        }

    }

    @SuppressLint("InflateParams")
    private fun setListenerToSeparator(view: View, layout: LinearLayout, inflater: LayoutInflater) {
        Timber.e("here1")
        layout.removeView(view.parent as View)
        var newView: View = inflater.inflate(R.layout.suitcase_group_item, null)

        newView.group_item.hint = "Name"
        newView.group_item_amount.hint = "1"
        setListenerToItems(newView.group_item_amount, newView, layout)

        layout.addView(newView, layout.childCount)
        newView.group_item.requestFocus()

        newView = inflater.inflate(R.layout.suitcase_group_item_separator, null)
        layout.addView(newView, layout.childCount)
        newView.add_suitcase_item.setOnClickListener{
            Timber.e("here2")
            setListenerToSeparator(it, layout, inflater)
        }
    }


    private fun setListenerToItems(amount: EditText, rowView: View, layout: LinearLayout) {
        amount.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if(actionId == EditorInfo.IME_ACTION_DONE){
                handled = true
                amount.clearFocus()
                if (amount.text.toString() == "0") {
                    layout.removeView(rowView)
                }
            }
            return@setOnEditorActionListener handled
        }
    }


}