package com.example.cs414final.ui.gallery

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs414final.databinding.FragmentGalleryBinding


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter

    private val galleryViewModel: GalleryViewModel by viewModels()
    var hourlyArray = ArrayList<RowItem>()
    lateinit var hourlyTimes: ArrayList<String>
    lateinit var hourlyTemps: ArrayList<String>



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)
        hourlyTimes = ArrayList<String>()
        hourlyTemps = ArrayList<String>()
        context?.let { super.onAttach(it) }
        val sp = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        var time000 = sp?.getString("time000", "")
        var time100 = sp?.getString("time100", "")
        var time200 = sp?.getString("time200", "")
        var time300 = sp?.getString("time300", "")
        var time400 = sp?.getString("time400", "")
        var time500 = sp?.getString("time500", "")
        var time600 = sp?.getString("time600", "")
        var time700 = sp?.getString("time700", "")
        var time800 = sp?.getString("time800", "")
        var time900 = sp?.getString("time900", "")
        var time1000 = sp?.getString("time1000", "")
        var time1100 = sp?.getString("time1100", "")
        var time1200 = sp?.getString("time1200", "")
        var time1300 = sp?.getString("time1300", "")
        var time1400 = sp?.getString("time1400", "")
        var time1500 = sp?.getString("time1500", "")
        var time1600 = sp?.getString("time1600", "")
        var time1700 = sp?.getString("time1700", "")
        var time1800 = sp?.getString("time1800", "")
        var time1900 = sp?.getString("time1900", "")
        var time2000 = sp?.getString("time2000", "")
        var time2100 = sp?.getString("time2100", "")
        var time2200 = sp?.getString("time2200", "")
        var time2300 = sp?.getString("time2300", "")

        var myStrings1 = arrayOf(time000,time100,time200,time300,time400,time500,time600,time700,time800,time900,time1000,time1100,time1200,time1300,time1400,time1500,time1600,time1700,time1800,time1900,time2000,time2100,time2200,time2300)
        for (i in myStrings1) {
            var nonNullableString = i ?: ""
            hourlyTimes.add(nonNullableString)
        }
        Log.d("times",hourlyTimes.toString())

        var temp000 = sp?.getString("temp000", "")
        var temp100 = sp?.getString("temp100", "")
        var temp200 = sp?.getString("temp200", "")
        var temp300 = sp?.getString("temp300", "")
        var temp400 = sp?.getString("temp400", "")
        var temp500 = sp?.getString("temp500", "")
        var temp600 = sp?.getString("temp600", "")
        var temp700 = sp?.getString("temp700", "")
        var temp800 = sp?.getString("temp800", "")
        var temp900 = sp?.getString("temp900", "")
        var temp1000 = sp?.getString("temp1000", "")
        var temp1100 = sp?.getString("temp1100", "")
        var temp1200 = sp?.getString("temp1200", "")
        var temp1300 = sp?.getString("temp1300", "")
        var temp1400 = sp?.getString("temp1400", "")
        var temp1500 = sp?.getString("temp1500", "")
        var temp1600 = sp?.getString("temp1600", "")
        var temp1700 = sp?.getString("temp1700", "")
        var temp1800 = sp?.getString("temp1800", "")
        var temp1900 = sp?.getString("temp1900", "")
        var temp2000 = sp?.getString("temp2000", "")
        var temp2100 = sp?.getString("temp2100", "")
        var temp2200 = sp?.getString("temp2200", "")
        var temp2300 = sp?.getString("temp2300", "")

        var myStrings2 = arrayOf(temp000,temp100,temp200,temp300,temp400,temp500,temp600,temp700,temp800,temp900,temp1000,temp1100,temp1200,temp1300,temp1400,temp1500,temp1600,temp1700,temp1800,temp1900,temp2000,temp2100,temp2200,temp2300)
        for (i in myStrings2) {
            var nonNullableString = i ?: ""
            hourlyTemps.add(nonNullableString)
        }
        Log.d("temps",hourlyTemps.toString())

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        dataInitialize(hourlyTimes, hourlyTemps)
        recyclerView = binding.recyclerView2
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = MyAdapter(hourlyArray)
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(context,
            DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dataInitialize(myStrings: ArrayList<String>, myStrings2: ArrayList<String>) {
        hourlyArray = arrayListOf<RowItem>()
        repeat(24) {
            hourlyArray.add(RowItem("",""))
        }


        for (i in 0 until 24) {
            //Log.d("mystrings",myStrings[i].toString())
            var item = RowItem(myStrings[i],myStrings2[i])
            hourlyArray[i] = item
            //Log.d("hourlyArray", hourlyArray.toString())
        }
    }

}

