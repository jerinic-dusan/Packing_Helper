package rs.raf.projekat2.packinghelper.presentation.view.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_suitcase.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.packinghelper.R
import rs.raf.projekat2.packinghelper.data.models.SuitcaseGroup
import rs.raf.projekat2.packinghelper.data.models.SuitcaseItem
import rs.raf.projekat2.packinghelper.data.models.SuitcaseWithItems
import rs.raf.projekat2.packinghelper.presentation.contract.SuitcaseContract
import rs.raf.projekat2.packinghelper.presentation.view.recycler.adapter.SuitcaseGroupAdapter
import rs.raf.projekat2.packinghelper.presentation.view.recycler.diff.SuitcaseGroupDiffItemCallback
import rs.raf.projekat2.packinghelper.presentation.viewmodel.SuitcaseViewModel

class SuitcaseActivity : AppCompatActivity(R.layout.activity_suitcase), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var suitcaseWithItems: SuitcaseWithItems
    private val groupSet = mutableSetOf<String>()
    private lateinit var suitcaseGroupAdapter: SuitcaseGroupAdapter
    private var counter = 0
    private val suitcaseGroups = mutableListOf<SuitcaseGroup>()
    private val suitcaseViewModel: SuitcaseContract.ViewModel by viewModel<SuitcaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        suitcaseWithItems = intent.getParcelableExtra(MapsActivity.MESSAGE_KEY_SUITCASE)!!
        init()
    }

    private fun init() {
        initMap()
        initRecycler()
        initFields()
        initListeners()
    }

    private fun initRecycler() {
        recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        suitcaseGroupAdapter = SuitcaseGroupAdapter(SuitcaseGroupDiffItemCallback(),
            {
                //REMOVE GROUP
                suitcaseGroups.remove(it)
                suitcaseGroupAdapter.submitList(suitcaseGroups.toList())

                val layoutManager = recycler.layoutManager as StaggeredGridLayoutManager
                layoutManager.getChildAt(2)?.let {
                    if(layoutManager.isViewPartiallyVisible(layoutManager.getChildAt(2)!!, true, false) && !cancel_edit.isShown && !save_edit.isShown){
                        save_edit.show(false)
                        cancel_edit.show(true)
                    }
                }
            },
            {
                //ADD GROUP
                suitcaseGroups.add(suitcaseGroups.size - 1, SuitcaseGroup(counter, "Name", mutableListOf()))
                counter += 1
                suitcaseGroupAdapter.submitList(suitcaseGroups.toList())
            },
            {
                //EDIT GROUPS
                val index = suitcaseGroups.indexOf(suitcaseGroups.find { sg -> sg.id == it.id })
                suitcaseGroups.removeAt(index)
                suitcaseGroups.add(index, it)
            }
        )
        recycler.adapter = suitcaseGroupAdapter
    }

    private fun initListeners() {
        cancel_edit.setOnClickListener { finish() }
        save_edit.setOnClickListener {
            val items = mutableListOf<SuitcaseItem>()

            suitcaseGroups.forEachIndexed{ index, group ->
                if(index == suitcaseGroups.size - 1){ return@forEachIndexed }
                group.items.forEach { item ->
                    items.add(SuitcaseItem(0, suitcaseWithItems.suitcase.id, item.name, item.group, item.amount))
                }
            }

            suitcaseWithItems.suitcase.name = suitcase_title.text.toString().trim()
            suitcaseViewModel.update(SuitcaseWithItems(suitcaseWithItems.suitcase, items.toList()))
            finish()
        }
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(dy > 0){
                    cancel_edit.hide(false)
                    save_edit.hide(true)
                }else if(dy < 0){
                    save_edit.show(false)
                    cancel_edit.show(true)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun initFields() {
        suitcase_title.setText(suitcaseWithItems.suitcase.name)
        suitcase_title.requestFocus()
        location_text.text = suitcaseWithItems.suitcase.location
        occasion_text.text = suitcaseWithItems.suitcase.occasion
        duration_text.text = suitcaseWithItems.suitcase.days.toString() + "days"
        temperature_text.text = suitcaseWithItems.suitcase.temp

        suitcaseWithItems.suitcaseItems.forEach { groupSet.add(it.group) }
        prepareData(groupSet.toList())
    }

    private fun prepareData(list: List<String>) {
        suitcaseGroups.forEach { suitcaseGroups.remove(it) }
        list.forEach { group ->
            val items = mutableListOf<SuitcaseItem>()
            suitcaseWithItems.suitcaseItems.forEach {
                if(it.group == group){
                    items.add(it)
                }
            }
            suitcaseGroups.add(SuitcaseGroup(counter, group, items))
            counter += 1
        }
        suitcaseGroups.add(SuitcaseGroup(-1, "Add", mutableListOf()))
        suitcaseGroupAdapter.submitList(suitcaseGroups.toList())
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