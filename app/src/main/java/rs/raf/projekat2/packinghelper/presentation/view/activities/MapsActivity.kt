package rs.raf.projekat2.packinghelper.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import rs.raf.projekat2.packinghelper.R

class MapsActivity : AppCompatActivity(R.layout.activity_maps), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val antipaxos = LatLng(39.1499994, 20.2333324)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(antipaxos, 0F))
    }
}
