package web.connect.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import web.connect.demo.database.DBHandler

class ConnectivityChangeReceiver : BroadcastReceiver() {

    private lateinit var dbHandler: DBHandler

    override fun onReceive(context: Context, intent: Intent) {

        dbHandler = DBHandler(context)

        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            val isConnected = networkInfo != null && networkInfo.isConnected

            if (isConnected) {
                showToast(context, "Network is connected")
                // Perform actions when the device is connected to the network
                val data = dbHandler.getAllForms()
                if (data.isNotEmpty()) {
                    val message = "Form Data : $data"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            } else {
                showToast(context, "Network is disconnected")
                // Perform actions when the device is disconnected from the network
            }
        }
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
