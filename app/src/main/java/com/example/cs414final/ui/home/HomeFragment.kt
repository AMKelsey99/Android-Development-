package com.example.cs414final.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cs414final.MainActivity
import com.example.cs414final.databinding.FragmentHomeBinding
import com.example.cs414final.ui.gallery.GalleryViewModel
import com.example.cs414final.ui.gallery.MyAdapter
import com.example.cs414final.ui.gallery.RowItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter

    private val galleryViewModel: GalleryViewModel by viewModels()
    var hourlyArray = ArrayList<RowItem>()
    lateinit var hourlyTimes: ArrayList<String>
    lateinit var hourlyTemps: ArrayList<String>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val homeViewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)





        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val title: TextView = binding.textHome
        val lat = binding.currentLat
        val long = binding.longitude
        var currentTemp = binding.currentTemp
        var timeCurrent = binding.timeCurrent
        var timezone = binding.timezone
        var windspeedCurrent = binding.windspeedCurrent
        var winddirectionCurrent = binding.winddirectionCurrent


        homeViewModel.text.observe(viewLifecycleOwner) {
            title.text = it
        }

        homeViewModel.homeLatVal.observe(viewLifecycleOwner) {
            lat.text = it
        }

        homeViewModel.homeLongVal.observe(viewLifecycleOwner) {
            long.text = it
        }

        homeViewModel.homeTZVal.observe(viewLifecycleOwner) {
            timezone.text = it
        }

        homeViewModel.homeTimeVal.observe(viewLifecycleOwner) {
            timeCurrent.text = it
        }

        homeViewModel.homeTempVal.observe(viewLifecycleOwner) {
            currentTemp.text = it
        }

        homeViewModel.homeWSVal.observe(viewLifecycleOwner) {
            windspeedCurrent.text = it
        }

        homeViewModel.homeWDVal.observe(viewLifecycleOwner) {
            winddirectionCurrent.text = it
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}