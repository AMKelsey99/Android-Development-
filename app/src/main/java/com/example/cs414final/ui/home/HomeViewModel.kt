package com.example.cs414final.ui.home

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var sharedpreferences: SharedPreferences = application.getSharedPreferences("preference_key", Context.MODE_PRIVATE)

    var titleText = MutableLiveData<String>("Current Forecast")
    var homeLat = MutableLiveData<String>("default value")
    var homeLong = MutableLiveData<String>("default value")
    var homeTZ = MutableLiveData<String>("default value")
    var homeWS = MutableLiveData<String>("default")
    var homeWD = MutableLiveData<String>("default")
    var homeTime = MutableLiveData<String>("default")
    var homeTemp = MutableLiveData<String>("default")

    var text: LiveData<String> = titleText
    var homeLatVal: LiveData<String> = homeLat
    var homeLongVal: LiveData<String> = homeLong
    var homeTZVal: LiveData<String> = homeTZ
    var homeWSVal: LiveData<String> = homeWS
    var homeWDVal: LiveData<String> = homeWD
    var homeTimeVal: LiveData<String> = homeTime
    var homeTempVal: LiveData<String> = homeTemp
}