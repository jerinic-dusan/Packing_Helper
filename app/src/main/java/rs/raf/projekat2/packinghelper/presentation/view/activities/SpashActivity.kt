package rs.raf.projekat2.packinghelper.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rs.raf.projekat2.packinghelper.R

class SpashActivity : AppCompatActivity(R.layout.activity_spash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }
}
