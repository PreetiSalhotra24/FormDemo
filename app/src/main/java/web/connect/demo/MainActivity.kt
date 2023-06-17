package web.connect.demo

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import web.connect.demo.database.DBHandler
import web.connect.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var dbHandler: DBHandler
    lateinit var receiver: ConnectivityChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHandler = DBHandler(this)
        onClicks()

    }

    override fun onResume() {
        super.onResume()
        receiver = ConnectivityChangeReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(receiver, filter)
    }

    private fun onClicks() {

        binding.apply {

            btnSubmit.setOnClickListener {

                etCountry.clearFocus()
                etCountry.isCursorVisible = false

                val etFirstName = etFirstName.text.toString()
                val etLastName = etLastName.text.toString()
                val etMobile = etMobile.text.toString()
                val etBloodGroup = etBloodGroup.text.toString()
                val etAddress = etAddress.text.toString()
                val etCity = etCity.text.toString()
                val etState = etState.text.toString()
                val etCountry = etCountry.text.toString()

                if (!etFirstName.isNullOrEmpty() && !etLastName.isNullOrEmpty() && !etMobile.isNullOrEmpty()
                    && !etBloodGroup.isNullOrEmpty() && !etAddress.isNullOrEmpty() && !etCity.isNullOrEmpty()
                    && !etState.isNullOrEmpty() && !etCountry.isNullOrEmpty()){
                    if(isNetworkAvailable()){
                        dbHandler.addNewForm(etFirstName,etLastName,etMobile,etBloodGroup,etAddress,etCity,etState,etCountry)
                        Toast.makeText(applicationContext, "Submit Successfully...", Toast.LENGTH_SHORT).show()
                        clearFormData()
                    } else {
                        dbHandler.addNewForm(etFirstName,etLastName,etMobile,etBloodGroup,etAddress,etCity,etState,etCountry)
                        Toast.makeText(applicationContext, "Submit Successfully...", Toast.LENGTH_SHORT).show()
                        clearFormData()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "All Fields are Required", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    private fun clearFormData() {
        binding.etFirstName.setText("")
        binding.etLastName.setText("")
        binding.etMobile.setText("")
        binding.etBloodGroup.setText("")
        binding.etAddress.setText("")
        binding.etCity.setText("")
        binding.etState.setText("")
        binding.etCountry.setText("")
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onStop() {
        super.onStop()
        try{
           unregisterReceiver(receiver)
        }
        catch (e: Exception){
           e.printStackTrace()
        }
    }

}