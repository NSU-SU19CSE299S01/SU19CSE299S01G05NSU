package com.project.jhasan.soudagor


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Location
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.schibstedspain.leku.*
import com.schibstedspain.leku.tracker.LocationPickerTracker
import com.schibstedspain.leku.tracker.TrackEvents
import java.util.*



private const val MAP_BUTTON_REQUEST_CODE = 1
private const val MAP_POIS_BUTTON_REQUEST_CODE = 2

class ActivityMaps : AppCompatActivity() {

    private val lekuPois: List<LekuPoi>
        get() {
            val pois = ArrayList<LekuPoi>()

            val locationPoi1 = Location("leku")
            locationPoi1.latitude = 23.777176
            locationPoi1.longitude = 90.399452
            val poi1 = LekuPoi(UUID.randomUUID().toString(), "Los bellota", locationPoi1)
            pois.add(poi1)

            val locationPoi2 = Location("leku")
            locationPoi2.latitude = 23.777176
            locationPoi2.longitude = 90.399452
            val poi2 = LekuPoi(UUID.randomUUID().toString(), "Starbucks", locationPoi2)
            poi2.address = "Plaça de la Sagrada Família, 19, 08013 Barcelona"
            pois.add(poi2)

            return pois
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        setContentView(R.layout.activity_maps)
        val mapButton = findViewById<View>(R.id.map_button)
        mapButton.setOnClickListener {
            val locationPickerIntent = LocationPickerActivity.Builder()
                    .withLocation(23.777176, 90.399452)
                    //.withGeolocApiKey("<PUT API KEY HERE>")
                    .withSearchZone("bd")
                    //.withSearchZone(SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197)))
                    .withDefaultLocaleSearchZone()
                    //.shouldReturnOkOnBackPressed()
                    //.withStreetHidden()
                    //.withCityHidden()
                    //.withZipCodeHidden()
                    //.withSatelliteViewHidden()
                    //.withGooglePlacesEnabled()
                    .withGoogleTimeZoneEnabled()
                    //.withVoiceSearchHidden()
                    .withUnnamedRoadHidden()
                    .build(applicationContext)

            //this is optional if you want to return RESULT_OK if you don't set the latitude/longitude and click back button
            locationPickerIntent.putExtra("test", "this is a test")

            startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE)
        }

        val mapPoisButton = findViewById<View>(R.id.map_button_with_pois)
        mapPoisButton.setOnClickListener { _ ->
            val locationPickerIntent = LocationPickerActivity.Builder()
                    .withLocation(23.777176, 90.399452)
                    .withPois(lekuPois)
                    .build(applicationContext)

            startActivityForResult(locationPickerIntent, MAP_POIS_BUTTON_REQUEST_CODE)
        }

        val mapStyleButton = findViewById<View>(R.id.map_button_with_style)
        mapStyleButton.setOnClickListener {
            val locationPickerIntent = LocationPickerActivity.Builder()
                    .withLocation(23.777176, 90.399452)
                    .withMapStyle(R.raw.map_style_retro)
                    .build(applicationContext)
            startActivityForResult(locationPickerIntent, MAP_POIS_BUTTON_REQUEST_CODE)
        }

        initializeLocationPickerTracker()
    }


            } else if (requestCode == 2) {
                val latitude = data.getDoubleExtra(LATITUDE, 0.0)
                Log.d("LATITUDE****", latitude.toString())
                val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
                Log.d("LONGITUDE****", longitude.toString())
                val address = data.getStringExtra(LOCATION_ADDRESS)
                Log.d("ADDRESS****", address.toString())
                val lekuPoi = data.getParcelableExtra<LekuPoi>(LEKU_POI)
                Log.d("LekuPoi****", lekuPoi.toString())
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED")
        }


    }

    private fun initializeLocationPickerTracker() {
        LocationPicker.setTracker(MyPickerTracker(this))
    }

    private class MyPickerTracker(private val context: Context) : LocationPickerTracker {
        override fun onEventTracked(event: TrackEvents) {
            Toast.makeText(context, "Event: " + event.eventName, Toast.LENGTH_SHORT).show()
        }
    }
}