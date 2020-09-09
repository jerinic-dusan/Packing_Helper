package rs.raf.projekat2.packinghelper.presentation.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_suitcase.*
import rs.raf.projekat2.packinghelper.R
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems

class SuitcaseActivity : AppCompatActivity(R.layout.activity_suitcase), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var suitcaseWithItems: SuitcaseWithItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        suitcaseWithItems = intent.getParcelableExtra(MapsActivity.MESSAGE_KEY_SUITCASE)!!
        init()
    }

    private fun init() {
        initMap()
        initFields()
        initListeners()
    }

    private fun initListeners() {
        cancel_edit.setOnClickListener { finish() }
        save_edit.setOnClickListener { finish() }
    }

    @SuppressLint("SetTextI18n")
    private fun initFields() {
        suitcase_title.setText(suitcaseWithItems.suitcase.name)
        suitcase_title.requestFocus()
        location_text.text = suitcaseWithItems.suitcase.name
        occasion_text.text = suitcaseWithItems.suitcase.occasion
        duration_text.text = suitcaseWithItems.suitcase.days.toString() + "days"
        temperature_text.text = suitcaseWithItems.suitcase.temp
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_2_json))
        mMap.apply { addMarker(MarkerOptions().position(LatLng(suitcaseWithItems.suitcase.lat, suitcaseWithItems.suitcase.lng)).icon(BitmapDescriptorFactory.defaultMarker(224F))) }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(suitcaseWithItems.suitcase.lat, suitcaseWithItems.suitcase.lng - 20), 0F))
    }

}