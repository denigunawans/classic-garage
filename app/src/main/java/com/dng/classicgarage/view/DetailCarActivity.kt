package com.dng.classicgarage.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dng.classicgarage.R
import com.dng.classicgarage.databinding.ActivityAboutMeBinding
import com.dng.classicgarage.databinding.ActivityDetailCarBinding
import com.dng.classicgarage.databinding.ActivityMainBinding
import com.dng.classicgarage.model.Car

class DetailCarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.apply {
            title = "Detail Car"
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        val data = intent.getParcelableExtra<Car>("DATA")
        if (data != null){
            supportActionBar?.subtitle = data.brand
            binding.apply {
                Glide.with(this@DetailCarActivity)
                    .load(data.photo)
                    .fitCenter()
                    .into(imgCar)
                txBrand.text = data.brand
                txYearProduction.text = data.production
                txCountry.text = data.country
                txContentDesc.text = data.description
                txContentSpec.text = getString(R.string.spec_content, data.dimension, data.engine)
            }
            binding.btnShare.setOnClickListener { share(data) }
        }else{
            binding.btnShare.isClickable = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                this.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun share(dataCar: Car){
        val dataToShare = """
            Merk mobil : ${dataCar.brand}
            Asal, Tahun produksi : ${dataCar.country} ${dataCar.production}
            Dimensi : ${dataCar.dimension}
            Mesin : ${dataCar.engine}
            Foto : ${dataCar.photo}
            Review : ${dataCar.description}
        """.trimIndent()

        val intentShare = Intent(Intent.ACTION_SEND)
        intentShare.setType("text/plain")
        intentShare.putExtra(Intent.EXTRA_TEXT, dataToShare)
        startActivity(Intent.createChooser(intentShare, "Bagikan dengan"))
    }

}