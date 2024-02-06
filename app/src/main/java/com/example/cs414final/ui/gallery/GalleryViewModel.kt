package com.example.cs414final.ui.gallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "24 HOUR FORECAST"
    }
    val text: LiveData<String> = _text

   /* var time000 = MutableLiveData<String>("default value")
    var time100 = MutableLiveData<String>("default value")
    var time200 = MutableLiveData<String>("default value")
    var time300 = MutableLiveData<String>("default value")
    var time400 = MutableLiveData<String>("default value")
    var time500 = MutableLiveData<String>("default value")
    var time600 = MutableLiveData<String>("default value")
    var time700 = MutableLiveData<String>("default value")
    var time800 = MutableLiveData<String>("default value")
    var time900 = MutableLiveData<String>("default value")
    var time1000 = MutableLiveData<String>("default value")
    var time1100 = MutableLiveData<String>("default value")
    var time1200 = MutableLiveData<String>("default value")
    var time1300 = MutableLiveData<String>("default value")
    var time1400 = MutableLiveData<String>("default value")
    var time1500 = MutableLiveData<String>("default value")
    var time1600 = MutableLiveData<String>("default value")
    var time1700 = MutableLiveData<String>("default value")
    var time1800 = MutableLiveData<String>("default value")
    var time1900 = MutableLiveData<String>("default value")
    var time2000 = MutableLiveData<String>("default value")
    var time2100 = MutableLiveData<String>("default value")
    var time2200 = MutableLiveData<String>("default value")
    var time2300 = MutableLiveData<String>("default value")


    var time000Val: LiveData<String> = time000
    var time100Val: LiveData<String> = time100
    var time200Val: LiveData<String> = time200
    var time300Val: LiveData<String> = time300
    var time400Val: LiveData<String> = time400
    var time500Val: LiveData<String> = time500
    var time600Val: LiveData<String> = time600
    var time700Val: LiveData<String> = time700
    var time800Val: LiveData<String> = time800
    var time900Val: LiveData<String> = time900
    var time1000Val: LiveData<String> = time1000
    var time1100Val: LiveData<String> = time1100
    var time1200Val: LiveData<String> = time1200
    var time1300Val: LiveData<String> = time1300
    var time1400Val: LiveData<String> = time1400
    var time1500Val: LiveData<String> = time1500
    var time1600Val: LiveData<String> = time1600
    var time1700Val: LiveData<String> = time1700
    var time1800Val: LiveData<String> = time1800
    var time1900Val: LiveData<String> = time1900
    var time2000Val: LiveData<String> = time2000
    var time2100Val: LiveData<String> = time2100
    var time2200Val: LiveData<String> = time2200
    var time2300Val: LiveData<String> = time2300

    var myStrings = arrayOf(
        time000Val, time100Val, time200Val, time300Val, time400Val, time500Val, time600Val, time700Val, time800Val, time900Val, time1000Val, time1100Val, time1200Val, time1300Val, time1400Val, time1500Val, time1600Val, time1700Val, time1800Val, time1900Val, time2000Val, time2100Val, time2200Val, time2300Val
    )

    var myStrings2 = arrayOf(
    time000, time100, time200, time300, time400, time500, time600, time700, time800, time900, time1000, time1100, time1200, time1300, time1400, time1500, time1600, time1700, time1800, time1900, time2000, time2100, time2200, time2300
    )

    private val myArrayLiveData = MutableLiveData<ArrayList<String>>()

    fun setMyArray(myArray: ArrayList<String>) {
        myArrayLiveData.value = myArray
        Log.d("list", myArrayLiveData.toString())
    }

    fun getMyArray(): LiveData<ArrayList<String>> {
        return myArrayLiveData
    }


    fun arraySize() {
        Log.d("size", myStrings.size.toString())
        Log.d("time2300",time2300.value.toString())
    }

    //fun getMyStrings(): Array<LiveData<String>> {
    //    Log.d("galleryStrings",myStrings.toString())
    //    return myStrings
    //}*/


}