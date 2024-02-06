package com.example.cs414final.ui.slideshow

import android.content.Context
import android.os.Bundle
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
import com.example.cs414final.databinding.FragmentSlideshowBinding
import com.example.cs414final.ui.gallery.GalleryViewModel
import com.example.cs414final.ui.gallery.MyAdapter
import com.example.cs414final.ui.gallery.RowItem

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter2

    private val galleryViewModel: GalleryViewModel by viewModels()
    var dailyArray = ArrayList<RowItem2>()
    lateinit var dailyTimes: ArrayList<String>
    lateinit var dailyMins: ArrayList<String>
    lateinit var dailyMaxs: ArrayList<String>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)
        dailyTimes = ArrayList<String>()
        dailyMins = ArrayList<String>()
        dailyMaxs = ArrayList<String>()
        context?.let { super.onAttach(it) }
        val sp = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        var day1 = sp?.getString("day1","")
        var day2 = sp?.getString("day2","")
        var day3 = sp?.getString("day3","")
        var day4 = sp?.getString("day4","")
        var day5 = sp?.getString("day5","")
        var day6 = sp?.getString("day6","")
        var day7 = sp?.getString("day7","")
        var myStrings1 = arrayOf(day1,day2,day3,day4,day5,day6,day7)
        for (i in myStrings1) {
            var nonNullableString = i ?: ""
            dailyTimes.add(nonNullableString)
        }

        var min1 = sp?.getString("min1","")
        var min2 = sp?.getString("min2","")
        var min3 = sp?.getString("min3","")
        var min4 = sp?.getString("min4","")
        var min5 = sp?.getString("min5","")
        var min6 = sp?.getString("min6","")
        var min7 = sp?.getString("min7","")
        var myStrings2 = arrayOf(min1,min2,min3,min4,min5,min6,min7)
        for (i in myStrings2) {
            var nonNullableString = i ?: ""
            dailyMins.add(nonNullableString)
        }

        var max1 = sp?.getString("max1","")
        var max2 = sp?.getString("max2","")
        var max3 = sp?.getString("max3","")
        var max4 = sp?.getString("max4","")
        var max5 = sp?.getString("max5","")
        var max6 = sp?.getString("max6","")
        var max7 = sp?.getString("max7","")
        var myStrings3 = arrayOf(max1,max2,max3,max4,max5,max6,max7)
        for (i in myStrings3) {
            var nonNullableString = i ?: ""
            dailyMaxs.add(nonNullableString)
        }


        dataInitialize(dailyTimes,dailyMins,dailyMaxs)
        recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = MyAdapter2(dailyArray)
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

    private fun dataInitialize(myStrings: ArrayList<String>, myStrings2: ArrayList<String>, myStrings3: ArrayList<String>) {
        dailyArray = arrayListOf<RowItem2>()
        repeat(7) {
            dailyArray.add(RowItem2("","",""))
        }


        for (i in 0 until 7) {
            //Log.d("mystrings",myStrings[i].toString())
            var item = RowItem2(myStrings[i],myStrings2[i],myStrings3[i])
            dailyArray[i] = item
            //Log.d("hourlyArray", hourlyArray.toString())
        }
    }
}