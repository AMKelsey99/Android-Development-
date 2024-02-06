package com.example.cs414final

//import kotlinx.android.synthetic.main.activity_main.*

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cs414final.databinding.ActivityMainBinding
import com.example.cs414final.ui.gallery.GalleryViewModel
import com.example.cs414final.ui.home.HomeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var galleryViewModel: GalleryViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        /*if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }*/


        var homeList = ArrayList<String>()
        var currentWeather = ArrayList<String>()
        var hourlyUnit = ArrayList<String>()
        var hourlyTimes = ArrayList<String>()
        var hourlyTemps = ArrayList<String>()
        var hourlyRain = ArrayList<String>()
        var dailyTimes = ArrayList<String>()
        var dailyMin  = ArrayList<String>()
        var dailyMax = ArrayList<String>()
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val resources = applicationContext.resources

        var sharedPrefLat = sharedPreferences.getString("latitude","")
        var sharedPrefLong = sharedPreferences.getString("longitude","")
        var sharedPrefTemp = sharedPreferences.getString("currentTemp","")
        var sharedPrefTime = sharedPreferences.getString("currentTime","")
        var sharedPrefTZ = sharedPreferences.getString("timezone","")
        var sharedPrefWS = sharedPreferences.getString("windspeed","")
        var sharedPrefWD = sharedPreferences.getString("winddirection","")

        homeViewModel.homeLat.setValue(sharedPrefLat)
        homeViewModel.homeLong.setValue(sharedPrefLong)
        homeViewModel.homeTZ.setValue(sharedPrefTZ)

        homeViewModel.homeTemp.setValue(sharedPrefTemp)
        homeViewModel.homeTime.setValue(sharedPrefTime)
        homeViewModel.homeWS.setValue(sharedPrefWS)
        homeViewModel.homeWD.setValue(sharedPrefWD)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
             //   .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val refreshButton = findViewById<Button>(R.id.refreshButton)

        refreshButton.setOnClickListener {


            /*fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude*/

            val editText = findViewById<EditText>(R.id.latEdit)
            val latInput: Float = java.lang.Float.valueOf(editText.getText().toString())

            val editText2 = findViewById<EditText>(R.id.longEdit)
            val longInput: Float = java.lang.Float.valueOf(editText2.getText().toString())
            val currentDate = LocalDate.now()
            val currentDate7 = LocalDate.now().plus(7, ChronoUnit.DAYS)

            Log.d("LAT LONG", longInput.toString())
            Log.d("LAT LONG", latInput.toString())



            lifecycleScope.launchWhenCreated {
                val call = retrofitCall(latInput, longInput, currentDate, currentDate7)

                call.enqueue(object : Callback<ForecastResponse> {
                    override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                        val forecastResponse = response.body()
                        handleForecastResponse(forecastResponse, homeList, hourlyTimes, currentWeather, hourlyTemps, dailyTimes, dailyMin, dailyMax)
                        Log.d("test arrays after call", "$homeList \n $currentWeather \n $hourlyTimes \n $hourlyTemps \n $dailyTimes \n $dailyMin \n $dailyMax")

                        homeViewModel.homeLat.setValue(homeList[0])
                        homeViewModel.homeLong.setValue(homeList[1])
                        homeViewModel.homeTZ.setValue(homeList[2])

                        homeViewModel.homeTemp.setValue(currentWeather[0])
                        homeViewModel.homeTime.setValue(currentWeather[3])
                        homeViewModel.homeWS.setValue(currentWeather[1])
                        homeViewModel.homeWD.setValue(currentWeather[2])


                        //unable to attach ViewModel to views in the fragment as they use
                        //RecyclerViews created through an Adapter class
                        //In the future is it better to use a database?
                        //I did not have much time this week so I just used SharedPreferences
                        //But it is obviously horrifically redundant
                        editor.putString("time000",hourlyTimes[0])
                        editor.putString("time100",hourlyTimes[1])
                        editor.putString("time200",hourlyTimes[2])
                        editor.putString("time300",hourlyTimes[3])
                        editor.putString("time400",hourlyTimes[4])
                        editor.putString("time500",hourlyTimes[5])
                        editor.putString("time600",hourlyTimes[6])
                        editor.putString("time700",hourlyTimes[7])
                        editor.putString("time800",hourlyTimes[8])
                        editor.putString("time900",hourlyTimes[9])
                        editor.putString("time1000",hourlyTimes[10])
                        editor.putString("time1100",hourlyTimes[11])
                        editor.putString("time1200",hourlyTimes[12])
                        editor.putString("time1300",hourlyTimes[13])
                        editor.putString("time1400",hourlyTimes[14])
                        editor.putString("time1500",hourlyTimes[15])
                        editor.putString("time1600",hourlyTimes[16])
                        editor.putString("time1700",hourlyTimes[17])
                        editor.putString("time18000",hourlyTimes[18])
                        editor.putString("time1900",hourlyTimes[19])
                        editor.putString("time2000",hourlyTimes[20])
                        editor.putString("time2100",hourlyTimes[21])
                        editor.putString("time2200",hourlyTimes[22])
                        editor.putString("time2300",hourlyTimes[23])

                        editor.putString("temp000",hourlyTemps[0])
                        editor.putString("temp100",hourlyTemps[1])
                        editor.putString("temp200",hourlyTemps[2])
                        editor.putString("temp300",hourlyTemps[3])
                        editor.putString("temp400",hourlyTemps[4])
                        editor.putString("temp500",hourlyTemps[5])
                        editor.putString("temp600",hourlyTemps[6])
                        editor.putString("temp700",hourlyTemps[7])
                        editor.putString("temp800",hourlyTemps[8])
                        editor.putString("temp900",hourlyTemps[9])
                        editor.putString("temp1000",hourlyTemps[10])
                        editor.putString("temp1100",hourlyTemps[11])
                        editor.putString("temp1200",hourlyTemps[12])
                        editor.putString("temp1300",hourlyTemps[13])
                        editor.putString("temp1400",hourlyTemps[14])
                        editor.putString("temp1500",hourlyTemps[15])
                        editor.putString("temp1600",hourlyTemps[16])
                        editor.putString("temp1700",hourlyTemps[17])
                        editor.putString("temp1800",hourlyTemps[18])
                        editor.putString("temp1900",hourlyTemps[19])
                        editor.putString("temp2000",hourlyTemps[20])
                        editor.putString("temp2100",hourlyTemps[21])
                        editor.putString("temp2200",hourlyTemps[22])
                        editor.putString("temp2300",hourlyTemps[23])

                        editor.putString("day1",dailyTimes[1])
                        editor.putString("day2",dailyTimes[2])
                        editor.putString("day3",dailyTimes[3])
                        editor.putString("day4",dailyTimes[4])
                        editor.putString("day5",dailyTimes[5])
                        editor.putString("day6",dailyTimes[6])
                        editor.putString("day7",dailyTimes[7])

                        editor.putString("min1",dailyMin[1])
                        editor.putString("min2",dailyMin[2])
                        editor.putString("min3",dailyMin[3])
                        editor.putString("min4",dailyMin[4])
                        editor.putString("min5",dailyMin[5])
                        editor.putString("min6",dailyMin[6])
                        editor.putString("min7",dailyMin[7])

                        editor.putString("max1",dailyMax[1])
                        editor.putString("max2",dailyMax[2])
                        editor.putString("max3",dailyMax[3])
                        editor.putString("max4",dailyMax[4])
                        editor.putString("max5",dailyMax[5])
                        editor.putString("max6",dailyMax[6])
                        editor.putString("max7",dailyMax[7])



                        Log.d("timesList", hourlyTimes.toString())


                        editor.putString("latitude",homeList[0])
                        editor.putString("longitude",homeList[1])
                        editor.putString("timezone",homeList[2])
                        editor.putString("currentTemp",currentWeather[0])
                        editor.putString("currentTime",currentWeather[3])
                        editor.putString("windspeed",currentWeather[1])
                        editor.putString("winddirection",currentWeather[2])
                        editor.apply()
                    }

                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        Log.d("Fail Handler", "${t.message}")
                    }
                })
               // Log.d("test arrays after call", "$homeList \n $currentWeather \n $hourlyTimes \n $hourlyTemps \n $dailyTimes \n $dailyMin \n $dailyMax")
               // homeList = getWeather(latInput.toString(), longInput.toString(), currentDate, currentDate7, hourlyTimes, hourlyUnit, currentWeather, hourlyRain, hourlyTemps, dailyTimes, dailyMin, dailyMax)

            }
        }
    }

fun retrofitCall(latInput:Float, longInput:Float, currentDate:LocalDate, currentDate7:LocalDate): Call<ForecastResponse>  {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ForecastService::class.java)

    return service.getForecast(
        latitude = latInput,
        longitude = longInput,
        hourly = "temperature_2m,rain",
        daily = "temperature_2m_max,temperature_2m_min",
        currentWeather = true,
        forecastDays = 1,
        startDate = currentDate.toString(),
        endDate = currentDate7.toString(),
        timezone = "America/New_York"
    )

}

    private fun handleForecastResponse(forecastResponse: ForecastResponse?, home:ArrayList<String>, htime: ArrayList<String>, current: ArrayList<String>, temp: ArrayList<String>, dtime: ArrayList<String>, min: ArrayList<String>, max: ArrayList<String>) {
        if (forecastResponse != null) {

            val currentWeatherJson = forecastResponse.current_weather
            val temperature = currentWeatherJson["temperature"].toString()
            val windspeed = currentWeatherJson["windspeed"].toString()
            val winddirection = currentWeatherJson["winddirection"].toString()
            val time = LocalDateTime.parse(currentWeatherJson["time"].toString().substring(0, 16), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            //Log.d("RESPONSE", forecastResponse.toString())
            //Log.d("test", temperature.toString() + windspeed.toString() + winddirection.toString() + time.toString())
            current.add(temperature)
            current.add(windspeed)
            current.add(winddirection)
            current.add(time.toString())

            val homeLat = forecastResponse.latitude.toString()
            val homeLong = forecastResponse.longitude.toString()
            val homeTZ = forecastResponse.timezone
            home.add(homeLat)
            home.add(homeLong)
            home.add(homeTZ)

            val hourlyJson = forecastResponse.hourly
            val hourlyArray1 = hourlyJson["time"] as? ArrayList<String>
            if (hourlyArray1 != null) {
                htime.addAll(hourlyArray1)
            }
            val hourlyArray2 = hourlyJson["temperature_2m"]  as? ArrayList<Double>
            hourlyArray2?.forEach {
                temp.add(it.toString())
            }

            val dailyJson = forecastResponse.daily
            val dailyArray1 = dailyJson["time"] as? ArrayList<String>
            if (dailyArray1 != null) {
                dtime.addAll(dailyArray1)
            }

            val dailyArray2 = dailyJson["temperature_2m_min"] as? ArrayList<Double>
            dailyArray2?.forEach {
                min.add(it.toString())
            }

            val dailyArray3 = dailyJson["temperature_2m_max"] as? ArrayList<Double>
            dailyArray3?.forEach {
                max.add(it.toString())
            }

            //var listOfArrays:List<ArrayList<String>> = listOf(home, current, htime, temp, dtime, min, max)
            Log.d("test arrays", hourlyArray1.toString() + "\n " + hourlyArray2.toString())
            Log.d("test arrays", dailyArray1.toString() + "\n " + dailyArray2.toString() + "\n " + dailyArray3.toString())
            Log.d("test arraylists", "$home \n $current \n $temp \n $htime \n $dtime \n $min \n $max" )
            //return listOfArrays;
        }
        //return emptyList()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //OLD VANILLA KOTLIN API CALL
    /*suspend fun getWeather(latInput:String, longInput:String, currentDate:LocalDate, currentDate7:LocalDate, times: ArrayList<String>, units: ArrayList<String>, current: ArrayList<String>, rain: ArrayList<String>, temp: ArrayList<String>, dtime: ArrayList<String>, min: ArrayList<String>, max: ArrayList<String>): ArrayList<String> {
        var list = ArrayList<String>()
        var resultList = ArrayList<String>()
        var date = currentDate.toString()
        var date7 = currentDate7.toString()
        var URL =
            "https://api.open-meteo.com/v1/forecast?latitude=$latInput&longitude=$longInput&hourly=temperature_2m,rain&daily=temperature_2m_max,temperature_2m_min&current_weather=true&forecast_days=1&start_date=$date&end_date=$date7&timezone=America%2FNew_York"
        Log.d("URL", URL)
        /*"https://api.open-meteo.com/v1/forecast?latitude=41.93" + $latInput + "&longitude=-72.79&hourly=temperature_2m,rain&daily=temperature_2m_max,temperature_2m_min&current_weather=true&forecast_days=1&start_date=2023-04-23&end_date=2023-04-30&timezone=America%2FNew_York"*/
        try {
         val APIresult = GlobalScope.async { callOpenMeteo(URL)
            }.await()
            list = updateResult(APIresult)
            resultList = updateHourlyTime(APIresult)
            times.addAll(resultList)
            resultList = updateHourlyUnit(APIresult)
            units.addAll(resultList)
            resultList = updateCurrentWeather(APIresult)
            current.addAll(resultList)
            resultList = updateHourlyRain(APIresult)
            rain.addAll(resultList)
            resultList = updateHourlyTemp(APIresult)
            temp.addAll(resultList)
            resultList = updateDailyTime(APIresult)
            dtime.addAll(resultList)
            resultList = updateDailyMin(APIresult)
            min.addAll(resultList)
            resultList = updateDailyMax(APIresult)
            max.addAll(resultList)
            Log.d("times", times.toString())
            Log.d("units", units.toString())
            Log.d("current", current.toString())
            Log.d("rain", rain.toString())
            Log.d("temp", temp.toString())
            Log.d("geo info", list.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        delay(2000)
        return list
    }

    fun updateDailyTime(APIresult: String?):ArrayList<String> {
        var list = ArrayList<String>()
        val JSONresult = JSONObject(APIresult)
        val hourlyObject = JSONresult.getJSONObject("daily")
        val timeArray: JSONArray = hourlyObject.getJSONArray("time")
        for (i in 0 until timeArray.length()) {
            list.add(timeArray.getString(i))
        }
        return list
    }

    fun updateDailyMin(APIresult: String?):ArrayList<String> {
        var list = ArrayList<String>()
        val JSONresult = JSONObject(APIresult)
        val hourlyObject = JSONresult.getJSONObject("daily")
        val timeArray: JSONArray = hourlyObject.getJSONArray("temperature_2m_min")
        for (i in 0 until timeArray.length()) {
            list.add(timeArray.getString(i))
        }
        return list
    }

    fun updateDailyMax(APIresult: String?):ArrayList<String> {
        var list = ArrayList<String>()
        val JSONresult = JSONObject(APIresult)
        val hourlyObject = JSONresult.getJSONObject("daily")
        val timeArray: JSONArray = hourlyObject.getJSONArray("temperature_2m_max")
        for (i in 0 until timeArray.length()) {
            list.add(timeArray.getString(i))
        }
        return list
    }


    fun updateHourlyTime(APIresult: String?):ArrayList<String> {
        var list = ArrayList<String>()
        val JSONresult = JSONObject(APIresult)
        val hourlyObject = JSONresult.getJSONObject("hourly")
        val timeArray: JSONArray = hourlyObject.getJSONArray("time")
        for (i in 0 until timeArray.length()) {
            list.add(timeArray.getString(i))
        }
        return list
    }

    fun updateHourlyTemp(APIresult: String?):ArrayList<String> {
        var list = ArrayList<String>()
        val JSONresult = JSONObject(APIresult)
        val hourlyObject = JSONresult.getJSONObject("hourly")
        val tempArray: JSONArray = hourlyObject.getJSONArray("temperature_2m")
        for (i in 0 until tempArray.length()) {
            list.add(tempArray.getString(i))
        }
        return list
    }

    fun updateHourlyRain(APIresult: String?):ArrayList<String> {
        var list = ArrayList<String>()
        val JSONresult = JSONObject(APIresult)
        val hourlyObject = JSONresult.getJSONObject("hourly")
        val rainArray: JSONArray = hourlyObject.getJSONArray("rain")
        for (i in 0 until rainArray.length()) {
            list.add(rainArray.getString(i))
        }
        return list
    }

    fun updateHourlyUnit(APIresult: String?):ArrayList<String> {
        var list = ArrayList<String>()
        val JSONresult = JSONObject(APIresult)
        val hourlyObject = JSONresult.getJSONObject("hourly_units")
        list.add(hourlyObject.getString("time"))
        list.add(hourlyObject.getString("temperature_2m"))
        list.add(hourlyObject.getString("rain"))
        return list
    }

    fun updateCurrentWeather(APIresult: String?):ArrayList<String> {
        var list = ArrayList<String>()
        val JSONresult = JSONObject(APIresult)
        val currentObject = JSONresult.getJSONObject("current_weather")
        list.add(currentObject.getString("time"))
        list.add(currentObject.getString("temperature"))
        list.add(currentObject.getString("windspeed"))
        list.add(currentObject.getString("winddirection"))
        return list
    }

    fun updateResult(APIresult: String?): ArrayList<String> {
        var list = ArrayList<String>()
        val JSONresult = JSONObject(APIresult) //convert string object to JSON object
        var latitude = JSONresult.getString("latitude")
        var longitude = JSONresult.getString("longitude")
        var timezone = JSONresult.getString("timezone")
        list.add(latitude)
        list.add(longitude)
        list.add(timezone)
        return list
    }


    fun callOpenMeteo(APIURL:String):String? {
        var APIresult: String? = "" //initialize JSON return
        val url: URL;
        //Connect
        url = URL(APIURL)
        var connection = url.openConnection() as HttpURLConnection //API Call
        connection.setRequestProperty("Content-Type", "application/json")
        connection.requestMethod = "GET"
        connection.connect()

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            Log.d("http response code is ", connection.getResponseCode().toString());
        }

        val inputStream = connection.inputStream
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        val response = StringBuilder()
        var inputLine: String?
        while (bufferedReader.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }
        bufferedReader.close()

        val responseString = response.toString()
        //Log.d("responseString", responseString)
        return responseString;
    }*/
}


