package com.example.mobv.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobv.adapter.PubAdapter
import com.example.mobv.database.fromDBtoSingleton
import com.example.mobv.database.fromSingletonToDB
import com.example.mobv.databinding.FragmentListPubBinding
import com.example.mobv.helper.Injection
import com.example.mobv.helper.PreferenceData
import com.example.mobv.model.BarsSingleton
import com.example.mobv.model.Coords
import com.example.mobv.viewmodel.BarsViewModel
import com.example.sqlbasics.AppDatabase
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_list_pub.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//TODO AFTER LOGIN TIMEOUT REDIRECT TO LOGIN
class ListPub : Fragment() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(requireContext())}
    private var _binding: FragmentListPubBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel: BarsViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(BarsViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPubBinding.inflate(inflater, container, false)
        val view = binding.root

        GlobalScope.launch {
//            BarsSingleton.bars =
            fromDBtoSingleton(database.californiaParkDao().getAllPubs())
            viewmodel.bars.postValue("Success")
        }

        if (checkPermissions()) {
//            getGpsLocation()
        } else {
            Toast.makeText(context,"Please grant location permission!", Toast.LENGTH_LONG).show()

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listPubButtonSort.setOnClickListener{
            BarsSingleton.toggleSortByName()
            findNavController().navigate(
                ListPubDirections.actionListPubToListPub()
            )
        }

        binding.listPubButtonSortUsers.setOnClickListener{
            BarsSingleton.toggleSortByUsers()
            if (binding.ListPubRecyclerView.adapter != null){
                val adapter = binding.ListPubRecyclerView.adapter!! as PubAdapter
                adapter.update(BarsSingleton.bars!!)
            } else {
                Toast.makeText(activity,"Bars loading, please try again later.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.listPubButtonSort.setOnClickListener{
            BarsSingleton.toggleSortByName()
            if (binding.ListPubRecyclerView.adapter != null){
                val adapter = binding.ListPubRecyclerView.adapter!! as PubAdapter
                adapter.update(BarsSingleton.bars!!)
            } else {
                Toast.makeText(activity,"Bars loading, please try again later.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.listPubButtonSortDistance.setOnClickListener{
            BarsSingleton.toggleSortByDistance()
            if (binding.ListPubRecyclerView.adapter != null){
                val adapter = binding.ListPubRecyclerView.adapter!! as PubAdapter
                adapter.update(BarsSingleton.bars!!)
            } else {
                Toast.makeText(activity,"Bars loading, please try again later.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.listPubButtonSignOut.setOnClickListener{
            PreferenceData.getInstance().clearData(requireContext())
            findNavController().navigate(
                ListPubDirections.actionListPubToSignIn()
            )
        }

        binding.listPubButtonFriendsList.setOnClickListener{
            findNavController().navigate(
                ListPubDirections.actionListPubToFriendList()
            )
        }

        binding.listPubButtonCheckIntoBar.setOnClickListener{
            findNavController().navigate(
                ListPubDirections.actionListPubToCheckIntoBar()
            )
        }

        swiperefresh.setOnRefreshListener {
            if (checkPermissions()) {
                getGpsLocation()
            } else {
                Toast.makeText(context,"Please grant location permission!", Toast.LENGTH_LONG).show()
                swiperefresh.isRefreshing = false
            }
        }

        viewmodel.coords.observe(viewLifecycleOwner){
            it?.let {
                if (it.lat != null && it.lon != null){
                    viewmodel.refreshData()
                }
            }
        }

        viewmodel.bars.observe(viewLifecycleOwner) {
            if (it != null && it.equals("Success")) {
                if (viewmodel.coords.value?.lat != null && viewmodel.coords.value?.lon != null) {
                    BarsSingleton.initDistance(viewmodel.coords.value?.lat!!, viewmodel.coords.value?.lon!!)

                }
                if (binding.ListPubRecyclerView.adapter != null){
                    val adapter = binding.ListPubRecyclerView.adapter!! as PubAdapter
                    adapter.update(BarsSingleton.bars!!)
                } else{
                    binding.ListPubRecyclerView.adapter = PubAdapter(requireContext(), BarsSingleton.bars!!,findNavController())
                    binding.ListPubRecyclerView.setHasFixedSize(true)
                }
                GlobalScope.launch {
                    database.californiaParkDao().insertAllPubsDB(fromSingletonToDB())
                }
                swiperefresh.isRefreshing = false
            }
            else if (it != null && it.equals("Failure")){
                Toast.makeText(activity,"Error loading data!", Toast.LENGTH_SHORT).show()
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

    @SuppressLint("MissingPermission")
    private fun getGpsLocation() {
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
                swiperefresh.isRefreshing = false
            }
        }
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

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}