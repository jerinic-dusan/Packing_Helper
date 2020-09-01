package rs.raf.projekat2.packinghelper.presentation.view.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.occasion_item.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.packinghelper.R
import rs.raf.projekat2.packinghelper.data.models.SuitcaseSettings
import rs.raf.projekat2.packinghelper.presentation.contract.SuitcaseContract
import rs.raf.projekat2.packinghelper.presentation.viewmodel.SuitcaseViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.suitcase_item.view.*
import rs.raf.projekat2.packinghelper.presentation.view.recycler.adapter.SuitcaseAdapter
import rs.raf.projekat2.packinghelper.presentation.view.recycler.diff.SuitcaseDiffItemCallback
import rs.raf.projekat2.packinghelper.presentation.view.states.SuitcaseState
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule


class MapsActivity : AppCompatActivity(R.layout.activity_maps), OnMapReadyCallback {

    private val suitcaseViewModel: SuitcaseContract.ViewModel by viewModel<SuitcaseViewModel>()
    private lateinit var suitcaseAdapter: SuitcaseAdapter

    private lateinit var mMap: GoogleMap
    private val antipaxos = LatLng(39.1499994, 20.2333324)
    private val views = mutableListOf<View>()

    private lateinit var location: Address
    private lateinit var startDate: Date
    private lateinit var endDate: Date
    private lateinit var gender: String
    private lateinit var occasion: String
    private var counter = 0

    @SuppressLint("SimpleDateFormat")
    private val dateFormatter = SimpleDateFormat("dd MMM yyyy")
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()
    }

    private fun init() {
        initRecycler()
        initListeners()
        initFields()
        initObservers()
    }

    private fun initRecycler() {
        suitcase_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        suitcaseAdapter = SuitcaseAdapter(SuitcaseDiffItemCallback(),
            {

            },
            {

            })
        suitcase_recycler.adapter = suitcaseAdapter
    }

    private fun initObservers() {
        suitcaseViewModel.suitcaseState.observe(this, Observer {
            renderState(it)
        })
    }

    private fun renderState(state: SuitcaseState) {
        when(state){
            is SuitcaseState.Success -> {
                suitcaseAdapter.submitList(state.suitcases)
            }
            is SuitcaseState.Error -> {
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is SuitcaseState.Loading -> {
                Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n", "ResourceType")
    private fun initFields() {
        Timer("SettingUp", false).schedule(2000) { changeTitle() }

        for (i in 0..2){
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView: View = inflater.inflate(R.layout.occasion_item, null)
            parent_linear_layout!!.addView(rowView, parent_linear_layout!!.childCount)

            views.add(rowView)

            rowView.clicked.setOnClickListener {
                views.forEach {
                    it.checked.setBackgroundResource(android.R.color.transparent)
                }
                occasion = rowView.type.text.toString()
                rowView.checked.setBackgroundResource(R.drawable.checked)
                rowView.isPressed = false
                rowView.isPressed = true

            }

            when(i){
                0 -> {
                    rowView.type.text = "Business"
                    rowView.image.setImageResource(R.drawable.ic_business)
                }
                1 -> {
                    rowView.type.text = "Business Casual"
                    rowView.image.setImageResource(R.drawable.ic_businessman_casual)
                }
                2 -> {
                    rowView.type.text = "Casual"
                    rowView.image.setImageResource(R.drawable.ic_casual_man)
                }
            }
        }
    }

    private fun changeTitle() {
        val fade = AnimationUtils.loadAnimation(this, R.anim.fragment_fade_exit)
        fade.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                place.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26F)
                place.setHint(R.string.where_to)
                logo.setImageResource(android.R.color.transparent)
            }
            override fun onAnimationStart(animation: Animation?) {}
        })
        place.startAnimation(fade)
    }

    private fun initListeners() {
        place.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if(actionId == EditorInfo.IME_ACTION_DONE){
                handled = true
                searchLocation(place.text.toString().trim())
                place.clearFocus()
                place.hideKeyboard()
            }
            return@setOnEditorActionListener handled
        }
        radio_button_1.setOnClickListener {
            gender = "Male"
        }
        radio_button_2.setOnClickListener {
            gender = "Female"
        }
        new_suitcase.setOnClickListener {
            if (this::location.isInitialized && this::startDate.isInitialized && this::endDate.isInitialized && this::gender.isInitialized && this::occasion.isInitialized){
//                Toast.makeText(this, "Location: ${location.featureName}\nStart date: ${startDate.time}\nEnd date: ${endDate.time}\nGender: $gender\nOccasion: $occasion",Toast.LENGTH_LONG).show()
                suitcaseViewModel.setSuitcaseSettings(SuitcaseSettings(counter, location, startDate, endDate, gender, occasion))
                counter += 1
            }else{
                if (!this::location.isInitialized){
                    Toast.makeText(this, "Please specify travel destination.", Toast.LENGTH_SHORT).show()
                }else if(!this::startDate.isInitialized){
                    Toast.makeText(this, "Please specify start date.", Toast.LENGTH_SHORT).show()
                }else if(!this::endDate.isInitialized){
                    Toast.makeText(this, "Please specify end date.", Toast.LENGTH_SHORT).show()
                }else if(!this::gender.isInitialized){
                    Toast.makeText(this, "Please specify your gender.", Toast.LENGTH_SHORT).show()
                }else if(!this::occasion.isInitialized){
                    Toast.makeText(this, "Please specify travel occasion.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun searchLocation(location: String) {
         val addressList: List<Address>
        if (location != ""){
            addressList = Geocoder(this).getFromLocationName(location, 1)
            if (addressList.isNotEmpty()){
                changeMapLocation(addressList[0])
            }else{
                Toast.makeText(this, "No such location found...", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this, "No such location found...", Toast.LENGTH_LONG).show()
        }
    }

    private fun changeMapLocation(address: Address) {
        location = address
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(address.latitude, address.longitude), 5F))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_2_json))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(antipaxos, 0F))
    }

    fun fromDate(view: View) {
        DatePickerDialog(
            this, R.style.my_dialog_theme,
            OnDateSetListener { _, year, month, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, month, dayOfMonth)
                from_date.text = dateFormatter.format(newDate.time)
                startDate = newDate.time
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        ).show()
    }

    fun toDate(view: View) {
        DatePickerDialog(
            this, R.style.my_dialog_theme,
            OnDateSetListener { _, year, month, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, month, dayOfMonth)
                to_date.text = dateFormatter.format(newDate.time)
                endDate = newDate.time
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        ).show()
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
