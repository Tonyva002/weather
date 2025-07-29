package com.pangea.weather.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.pangea.weather.R
import com.pangea.weather.adapters.CityAdapter
import com.pangea.weather.interfaces.ClickListener
import com.pangea.weather.models.City

class CitiesActivity : AppCompatActivity() {

    private val TAG = "com.pangea.weather.CITY"
    private var toolbar: Toolbar? = null
    private var recyclerView: RecyclerView? = null
    private var cities = ArrayList<City>()
    private var layoutManager: LayoutManager? = null
    private var adapter: CityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cities)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setToolbar()

        cities = ArrayList()
        cities.add(City("Mexico City", null, null))
        cities.add(City("Berlin, DE", null, null))
        cities.add(City("Santo Domingo, RD", null, null))
        cities.add(City("Madrid, Spain", null, null))
        cities.add(City("New York, USA", null, null))


        recyclerView = findViewById(R.id.recycler)
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager

        adapter = CityAdapter(cities, object: ClickListener{
            override fun onClick(view: View, index: Int) {

                if (index == 0){
                    goToDetail("3530597")
                }
                if (index == 1){
                    goToDetail("2950159")
                }
                if (index == 2){
                    goToDetail("3492908")
                }
                if (index == 3){
                    goToDetail("3117735")
                }
                if (index == 4){
                    goToDetail("5128581")
                }
            }

        })

        recyclerView?.adapter = adapter

    }

    private fun goToDetail(key: String){
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(TAG, key)
        startActivity(intent)
    }

    private fun setToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(getString(R.string.clima))
        setSupportActionBar(toolbar)
    }
}