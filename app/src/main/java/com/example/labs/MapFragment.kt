package com.example.labs

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

/**
 * Лабораторная работа 16: Работа с геолокацией и картами (OSMDroid).
 * Этот вариант не требует API ключей Google.
 */
class MapFragment : Fragment() {

    private lateinit var map: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Настройка OSMDroid (нужно вызвать до инфлейта макета)
        Configuration.getInstance().userAgentValue = requireContext().packageName

        val view = inflater.inflate(R.layout.fragment_map, container, false)
        
        map = view.findViewById(R.id.mapview)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        view.findViewById<Button>(R.id.btnBackFromMap).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        checkPermissionsAndShowLocation()
        
        return view
    }

    private fun checkPermissionsAndShowLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        showUserLocation()
    }

    private fun showUserLocation() {
        // Добавляем слой "Мое местоположение"
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), map)
        locationOverlay.enableMyLocation()
        map.overlays.add(locationOverlay)

        // Получаем точные координаты для маркера и зума
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val startPoint = GeoPoint(location.latitude, location.longitude)
                val mapController = map.controller
                mapController.setZoom(15.0)
                mapController.setCenter(startPoint)

                // Добавляем маркер
                val startMarker = Marker(map)
                startMarker.position = startPoint
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                startMarker.title = "Вы здесь"
                map.overlays.add(startMarker)
            } else {
                Toast.makeText(context, "Ожидание GPS...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showUserLocation()
            } else {
                Toast.makeText(context, "Разрешение отклонено", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}
