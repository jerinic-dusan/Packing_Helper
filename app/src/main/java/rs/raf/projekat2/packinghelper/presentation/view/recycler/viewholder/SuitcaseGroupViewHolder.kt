package rs.raf.projekat2.packinghelper.presentation.view.recycler.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.suitcase_group.*
import kotlinx.android.synthetic.main.suitcase_group.view.*
import kotlinx.android.synthetic.main.suitcase_group_item.view.*
import kotlinx.android.synthetic.main.suitcase_group_item_separator.view.*
import rs.raf.projekat2.packinghelper.R
import rs.raf.projekat2.packinghelper.data.models.SuitcaseGroup
import rs.raf.projekat2.packinghelper.data.models.SuitcaseItem
import timber.log.Timber


class SuitcaseGroupViewHolder(override val containerView: View,
                                        deleteGroup: (Int) -> Unit, addGroup: (Int) -> Unit, editGroup: (Int) -> Unit): RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        delete_group.setOnClickListener {
            deleteGroup(layoutPosition)
        }

        add_suitcase_group.setOnClickListener {
            group_layout.isPressed = false
            group_layout.isPressed = true
            addGroup(layoutPosition)
        }

        edit_suitcase_group.setOnClickListener {
            editGroup(layoutPosition)
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
        setListeners(group_name, "group_name", group_name, layout, suitcaseGroup)
        layout.removeAllViews()
        val inflater = LayoutInflater.from(containerView.context)

        suitcaseGroup.items.forEachIndexed { index, it ->
            val rowView: View = inflater.inflate(R.layout.suitcase_group_item, null)
            val name = rowView.group_item
            name.setText(it.name)
            setListeners(name, "item_name", name, layout, suitcaseGroup, index)
            val amount = rowView.group_item_amount
            amount.setText(it.amount.toString())
            setListeners(amount, "item_amount", rowView, layout, suitcaseGroup, index)
            layout.addView(rowView, layout.childCount)
        }

        val rowView: View = inflater.inflate(R.layout.suitcase_group_item_separator, null)
        layout.addView(rowView, layout.childCount)

        rowView.add_suitcase_item.setOnClickListener {
            setListenerToSeparator(it, layout, inflater, suitcaseGroup)
        }

    }

    @SuppressLint("InflateParams")
    private fun setListenerToSeparator(view: View, layout: LinearLayout, inflater: LayoutInflater, suitcaseGroup: SuitcaseGroup) {
        layout.removeView(view.parent as View)
        var newView: View = inflater.inflate(R.layout.suitcase_group_item, null)

        newView.group_item.hint = "Name"
        newView.group_item_amount.hint = "1"
        suitcaseGroup.items.add(SuitcaseItem(0,0, "", suitcaseGroup.name, 0))
        setListeners(newView.group_item, "item_name", newView.group_item, layout, suitcaseGroup, suitcaseGroup.items.size - 1)
        setListeners(newView.group_item_amount, "item_amount", newView, layout, suitcaseGroup, suitcaseGroup.items.size - 1)

        layout.addView(newView, layout.childCount)
        newView.group_item.requestFocus()

        newView = inflater.inflate(R.layout.suitcase_group_item_separator, null)
        layout.addView(newView, layout.childCount)
        newView.add_suitcase_item.setOnClickListener{
            setListenerToSeparator(it, layout, inflater, suitcaseGroup)
        }
    }

    private fun setListeners(editText: EditText, action: String, rowView: View, layout: LinearLayout, suitcaseGroup: SuitcaseGroup, index: Int = 0){
        editText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                when(action){
                    "group_name" -> {
                        suitcaseGroup.name = editText.text.toString().trim()
                        suitcaseGroup.items.forEach {
                            it.group = suitcaseGroup.name
                        }
                    }
                    "item_name" -> suitcaseGroup.items[index].name = editText.text.toString().trim()
                    "item_amount" -> {
                        if (editText.text.toString() == "0") {
                            layout.removeView(rowView)
                            suitcaseGroup.items.removeAt(index)
                            return@OnEditorActionListener true
                        }
                        if(editText.text.toString() == ""){
                            return@OnEditorActionListener true
                        }
                        suitcaseGroup.items[index].amount = editText.text.toString().trim().toInt()
                    }
                }
                editText.clearFocus()
                edit_suitcase_group.callOnClick()
                return@OnEditorActionListener true
            }
            false
        })

        editText.onFocusChangeListener = OnFocusChangeListener OnEditorActionListener@{ _, hasFocus ->
            if (!hasFocus) {
                when(action){
                    "group_name" -> {
                        suitcaseGroup.name = editText.text.toString().trim()
                        suitcaseGroup.items.forEach {
                            it.group = suitcaseGroup.name
                        }
                    }
                    "item_name" -> suitcaseGroup.items[index].name = editText.text.toString().trim()
                    "item_amount" -> {
                        if (editText.text.toString() == "0") {
                            layout.removeView(rowView)
                            suitcaseGroup.items.removeAt(index)
                            return@OnEditorActionListener
                        }
                        if(editText.text.toString() == ""){
                            return@OnEditorActionListener
                        }
                        suitcaseGroup.items[index].amount = editText.text.toString().trim().toInt()
                    }
                }
                editText.clearFocus()
                edit_suitcase_group.callOnClick()
            }
        }
    }

}