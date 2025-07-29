package com.pangea.weather.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.pangea.weather.R
import com.pangea.weather.models.City
import com.pangea.weather.network.Network

class DetailsActivity : AppCompatActivity() {

    var tvCity:TextView? = null
    var tvGrade:TextView? = null
    var tvStatus:TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvCity = findViewById(R.id.tvCity)
        tvGrade = findViewById(R.id.tvGrade)
        tvStatus = findViewById(R.id.tvStatus)

        val cityId = intent.getStringExtra("com.pangea.weather.CITY")
        if (cityId.isNullOrEmpty()) {
            Toast.makeText(this, "ID de ciudad no válido", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        if (Network.hayRed(this)){
            //Ejecutar solicitud http
            getWeatherWithVolley(
                "https://api.openweathermap.org/data/2.5/weather?id=$cityId&appid=fe470636a5a4d7d5b221d8e8810429a8&units=metric&lang=es"
            )
        }else{
            //Mostrar un mensaje de error
            Toast.makeText(this, "No hay red", Toast.LENGTH_LONG).show()
        }

    }

    //Solicitud http con volley y gson
    private fun  getWeatherWithVolley(url: String) {
        try {
            val queue = Volley.newRequestQueue(this)
            val solicitud = StringRequest(
                Request.Method.GET,
                url,
                { response ->
                    Log.d("Volley", response)
                    try {
                        val gson = Gson()
                        val city = gson.fromJson(response, City::class.java)
                        tvCity?.text = city.name
                        tvGrade?.text = buildString {
                            append(city.main?.temp.toString())
                            append("°")
                        }
                        tvStatus?.text = city.weather?.get(0)?.description

                    } catch (e: Exception) {
                        Log.e("GSON", "Error al parsear JSON: ${e.message}", e)
                    }
                },
                { error ->
                    Log.e("Volley", "Volley Error: ${error.message}", error)
                }
            )
            queue.add(solicitud)
        } catch (e: Exception) {
            Log.e("Volley", "Excepción al realizar la solicitud: ${e.message}", e)
        }
    }


}