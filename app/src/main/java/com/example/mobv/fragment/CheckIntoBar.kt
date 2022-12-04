package com.example.mobv.fragment
//Parts of code taken from: https://github.com/marosc/mobv2022
//Geofence related code: https://www.section.io/engineering-education/geofencing-in-android-with-kotlin/

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobv.GeofenceBroadcastReceiver
import com.example.mobv.adapter.NearbyBarAdapter
import com.example.mobv.databinding.FragmentCheckIntoBarBinding
import com.example.mobv.helper.Injection
import com.example.mobv.model.CloseBarsSingleton
import com.example.mobv.model.Coords
import com.example.mobv.viewmodel.CheckIntoBarViewModel
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_list_pub.*

class CheckIntoBar : Fragment() {

    private var _binding: FragmentCheckIntoBarBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel: CheckIntoBarViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geofencingClient: GeofencingClient

    private val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 3 // random unique value
    private val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 4
    private val REQUEST_TURN_DEVICE_LOCATION_ON = 5

    @RequiresApi(Build.VERSION_CODES.N)
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false) -> {
                // Precise location access granted.
            }
            else -> {
                Log.d("geofenceTesting: ", "background permission denied")
                // No location access granted.
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(CheckIntoBarViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckIntoBarBinding.inflate(inflater, container, false)
        val view = binding.root

            if (checkPermissions()) {
                loadData()
            } else {
                Toast.makeText(context,"Please grant location permission!", Toast.LENGTH_LONG).show()
                findNavController().navigate(
                    CheckIntoBarDirections.actionCheckIntoBarToListPub()
                )
            }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swiperefresh.setOnRefreshListener {
            loadData()
        }

        binding.checkIntoBarButtonCheckIn.setOnClickListener {
            Log.d("testingOut: ", "check in initiated")
            Log.d("testingOut: ", "viewmodel coords value: ${viewmodel.coords.value}")

            if (checkBackgroundPermissions()) {
                Log.d("geofenceTesting: ", "background permission granted")

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    permissionDialog()
                }
            }

            viewmodel.coords.value?.let {
                Log.d("testingOut: ", "coords not null")
                viewmodel.checkInBar(requireContext())
                val (lat: Double?, lon: Double?) = viewmodel.coords.value!!
                if (lon != null && lat != null) {
                    Log.d("testingOut: ", "creating fence")
                    createFence(lat, lon)
                }
            }
        }

        viewmodel.coords.observe(viewLifecycleOwner){
            it?.let {
                if (it.lat != null && it.lon != null){
                    viewmodel.refreshData(it.lat, it.lon)
                }
            }
            Log.d("testingOut: ", "viewmodel coords CHANGED!: ${viewmodel.coords.value}")
        }


        viewmodel.bars.observe(viewLifecycleOwner){
            Log.d("testingOut: ", "viewmodel bars CHANGED!")
            it?.let{
                if (it.elements != null){
                    CloseBarsSingleton.bars = it.elements
                    CloseBarsSingleton.purge()
                    if (viewmodel.coords.value?.lat != null && viewmodel.coords.value?.lon != null){
                        CloseBarsSingleton.sortByDistance(viewmodel.coords.value!!.lat!!, viewmodel.coords.value!!.lon!!)
                    }

                    if (binding.CheckIntoBarRecyclerView.adapter != null){
                        Log.d("testingOut: ", "bind adapter is not null!")
                        val adapter = binding.CheckIntoBarRecyclerView.adapter!! as NearbyBarAdapter
                        adapter.selectedItemPos = 0
                        adapter.lastItemSelectedPos = 0
                        adapter.update(CloseBarsSingleton.bars!!)
                    }
                    else {
                        Log.d("testingOut: ", "bind adapter is null!")
                        val tmp = NearbyBarAdapter(requireContext(), CloseBarsSingleton.bars!! ,findNavController(), viewmodel)
                        binding.CheckIntoBarAnimationView.visibility = View.GONE
                        binding.CheckIntoBarRecyclerView.adapter = tmp

                        // Use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        binding.CheckIntoBarRecyclerView.setHasFixedSize(true)
                    }
                    swiperefresh.isRefreshing = false
                }
                else{
                    Log.d("testingOut: ", "viewmodel bars is null!")
                    binding.CheckIntoBarRecyclerView.adapter = null
                }
            }
        }
        askLocationPermission()
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        CloseBarsSingleton.bars = null
//        _binding = null
//    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun permissionDialog() {
        val alertDialog: AlertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("Background location needed")
                setMessage("Allow background location (All times) for detecting when you leave bar.")
                setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        locationPermissionRequest.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION
                            )
                        )
                    })
                setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            // Create the AlertDialog
            builder.create()
        }
        alertDialog.show()
    }

    private fun checkBackgroundPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    @SuppressLint("MissingPermission")
    private fun loadData() {
        if (checkPermissions()) {
//            viewmodel.loading.postValue(true)
            if (isGpsEnabled()) {
                fusedLocationClient.getCurrentLocation(
                    CurrentLocationRequest.Builder().setDurationMillis(30000)
                        .setMaxUpdateAgeMillis(60000).build(), null
                ).addOnSuccessListener {
                    Log.d("testingOut: ", "your location: ${it.latitude} ${it.longitude}")
                                    it?.let {
                                        viewmodel.coords.postValue(Coords(it.latitude, it.longitude))
                                    }
                }
            } else {
                Toast.makeText(context,"Please turn on your GPS!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isGpsEnabled(): Boolean{
        // What happens when button is clicked
        // Calling Location Manager
        val mLocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Checking GPS is enabled
        val mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        // Display the message into the string
        return mGPS
    }

    private val gadgetQ = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    @TargetApi(29)
    private fun approveForegroundAndBackgroundLocation(): Boolean {
        val foregroundLocationApproved = (
                PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ))
        val backgroundPermissionApproved =
            if (gadgetQ) {
                PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            } else {
                true
            }
        return foregroundLocationApproved && backgroundPermissionApproved
    }

    private fun authorizedLocation(): Boolean {
        val formalizeForeground = (
                PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ))
        val formalizeBackground =
            if (gadgetQ) {
                PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            } else {
                true
            }
        return formalizeForeground && formalizeBackground
    }

    private fun askLocationPermission() {
        if (authorizedLocation())
            return
        var grantingPermission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val customResult = when {
            gadgetQ -> {
                grantingPermission += Manifest.permission.ACCESS_BACKGROUND_LOCATION
                REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
            }
            else -> REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        }
        Log.d("testing", "askLocationPermission")

        ActivityCompat.requestPermissions(
            requireActivity(), grantingPermission, customResult
        )
    }

    @SuppressLint("MissingPermission")
    private fun createFence(lat: Double, lon: Double) {
        Log.d("geofenceTesting: ", "creating fence")

        if (!checkPermissions()) {
            Log.d("geofenceTesting: ", "dont have permission")

            Toast.makeText(requireContext(), "Geofence failed, permissions not granted.", Toast.LENGTH_SHORT).show()
        }
        val geofenceIntent = PendingIntent.getBroadcast(
            requireContext(), 0,
            Intent(requireContext(), GeofenceBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        Log.d("geofenceTesting: ", "fence intent created")


        val request = GeofencingRequest.Builder().apply {
            addGeofence(
                Geofence.Builder()
                    .setRequestId("mygeofence")
                    .setCircularRegion(lat, lon, 300F)
                    .setExpirationDuration(1000L * 60 * 60 * 24)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build()
            )
        }.build()
        Log.d("geofenceTesting: ", "fence request built")


        geofencingClient.addGeofences(request, geofenceIntent).run {
            addOnSuccessListener {
                Toast.makeText(requireContext(), "Geofence created successfully.", Toast.LENGTH_LONG).show()
                Log.d("geofenceTesting: ", "Geofence created successfully.")

                findNavController().navigate(
                    CheckIntoBarDirections.actionCheckIntoBarToListPub()
                )
            }
            addOnFailureListener {
                Toast.makeText(requireContext(), "Geofence failed to create.", Toast.LENGTH_SHORT).show()

                it.printStackTrace()
            }
        }
    }
}


