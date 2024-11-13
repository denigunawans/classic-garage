package com.dng.classicgarage.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dng.classicgarage.R
import com.dng.classicgarage.adapter.GridCarAdapter
import com.dng.classicgarage.adapter.ListCarAdapter
import com.dng.classicgarage.databinding.ActivityMainBinding
import com.dng.classicgarage.model.Car

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listCar = ArrayList<Car>()

    private var rvLayoutManager = LinearLayoutManager(this)
    private var isGrid = false
    private var isSmallGrid = false

    private var isSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition{isSplash}
        Handler(Looper.getMainLooper()).postDelayed({isSplash = false}, 1200L)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null){
            isGrid = savedInstanceState.getBoolean(IS_GRID)
            isSmallGrid = savedInstanceState.getBoolean(IS_SMALL_GRID)
        }

        listCar.addAll(getAllCars())
        showRecyclerView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_GRID, isGrid)
        outState.putBoolean(IS_SMALL_GRID, isSmallGrid)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isGrid = savedInstanceState.getBoolean(IS_GRID)
        isSmallGrid = savedInstanceState.getBoolean(IS_SMALL_GRID)
    }

    private fun showRecyclerView(){
        val rvLayoutManager = if (isGrid){
            if (isSmallGrid){
                GridLayoutManager(this, 3)
            }else{
                GridLayoutManager(this, 2)
            }
        }else{
            LinearLayoutManager((this))
        }

        binding.rvCars.apply {
            setHasFixedSize(true)
            layoutManager = rvLayoutManager
            val carAdapter = if (isGrid) {
                GridCarAdapter(listCar)
            }else{
                ListCarAdapter(listCar)
            }
            adapter = carAdapter
        }
    }

    private fun getAllCars(): ArrayList<Car>{
        val carBrand = resources.getStringArray(R.array.data_list_brand)
        val carPhoto = resources.getStringArray(R.array.data_list_photo)
        val carCountry = resources.getStringArray(R.array.data_list_country)
        val carProduction = resources.getStringArray(R.array.data_list_production)
        val carDimension = resources.getStringArray(R.array.data_list_dimension)
        val carEngine = resources.getStringArray(R.array.data_list_engine)
        val carDesc = resources.getStringArray(R.array.data_list_description)

        val listCars = ArrayList<Car>()

        for (i in carBrand.indices){
            val car = Car(carBrand[i], carPhoto[i],carEngine[i], carProduction[i], carCountry[i], carDimension[i], carDesc[i] )
            listCars.add(car)
        }
        return listCars
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_aboutMe){
            val intent = Intent(this, AboutMeActivity::class.java)
            startActivity(intent)
        }else{
            when (item.itemId){
                R.id.menu_smal_grid -> {
                    rvLayoutManager = GridLayoutManager(this, 3)
                    isGrid = true
                    isSmallGrid = true
                }
                R.id.menu_medium_grid -> {
                    rvLayoutManager = GridLayoutManager(this, 2)
                    isGrid = true
                    isSmallGrid = false
                }
                R.id.menu_list -> {
                    rvLayoutManager = LinearLayoutManager(this)
                    isGrid = false
                    isSmallGrid =false
                }
            }
            showRecyclerView()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        private const val IS_GRID = "rv_layout"
        private const val IS_SMALL_GRID = "rv_layout_grid_small"
    }

}