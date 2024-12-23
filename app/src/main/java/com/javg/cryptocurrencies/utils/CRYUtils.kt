package com.javg.cryptocurrencies.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.javg.cryptocurrencies.data.model.CRYAskOrBids
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*


object CRYUtils {

    /**
     * Checks if the device is connected to a network source,
     * be it mobile or wifi and returns a true or false
     * depending on whether or not it has a connection
     */
    fun isInternetAvailable(context: Context, onChangeState: (isConnected: Boolean) -> Unit): Boolean {
        val TAG = "isInternetAvailable"
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        Log.e(TAG, "Llega a validar de nuevo la conexion de red")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        onChangeState.invoke(true)
                        Log.e(TAG, "====================== The default network is now: " + network)
                    }

                    override fun onLost(network: Network) {
                        onChangeState.invoke(false)
                        Log.e(TAG, "====================== The application no longer has a default network. The last default network was " + network)
                    }

                    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                        Log.e(TAG, "====================== The default network changed capabilities: " + networkCapabilities)
                    }

                    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                        Log.e(TAG, "====================== The default network changed link properties: " + linkProperties)
                    }
                })
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }

        return result
    }

    /**
     * Gets device time with mexico time zone setting and returns it
     */
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDay(): String {
        val myTimeZone = TimeZone.getTimeZone("America/Mexico_City")
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        simpleDateFormat.timeZone = myTimeZone
        return simpleDateFormat.format(Date())
    }

    /**
     * Converts a string to a list of type CRYAskOrBids
     */
    fun convertersJsonToList(askOrBids: String) = Gson().fromJson(askOrBids, Array<CRYAskOrBids>::class.java).toList()

    fun listToJson(list: List<Any>): String = Gson().toJson(list)

    fun <T> jsonStringToList(s: String?, clazz: Class<Array<T>>?): List<T> = Gson().fromJson(s, clazz).toList()

    inline fun <reified T> jsonToList(jsonString: String): T{
        val typeToken = object : TypeToken<T>(){}.type
        return Gson().fromJson(jsonString, typeToken)
    }

    fun formatAmount(amount: String): String {
        val symbols = DecimalFormatSymbols(Locale.US)
        val formatter = DecimalFormat("###,###,###", symbols)
        val convertAmount = formatter.format(amount.toDouble())
        return String.format(Locale.US, "$%s", convertAmount)
    }
}
