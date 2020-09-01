package rs.raf.projekat2.packinghelper.presentation.view.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_maps.*
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment(str: String) : DialogFragment(), DatePickerDialog.OnDateSetListener  {

    @SuppressLint("SimpleDateFormat")
    val dateFormatter = SimpleDateFormat("EEE-MMM-yyyy")
    private val calledFrom = str

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        if (calledFrom == "from"){
            calendar.set(year, month, dayOfMonth)
            from_date.setText(dateFormatter.format(calendar.time))
        }else{
            calendar.set(year, month, dayOfMonth)
            to_date.setText(dateFormatter.format(calendar.time))
        }
    }
}