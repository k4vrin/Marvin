package com.kavrin.marvin.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkListener(private val context: Context) : ConnectivityManager.NetworkCallback() {

    private val _isNetworkAvailable = MutableStateFlow(false)

    fun checkNetworkAvailability(): StateFlow<Boolean> {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        var isConnected = false

        connectivityManager.activeNetwork?.let { network ->
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network)
            networkCapabilities?.let { capabilities ->
                isConnected = when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> false
                }
            }
        }
        _isNetworkAvailable.value = isConnected
        return _isNetworkAvailable.asStateFlow()
    }

    override fun onAvailable(network: Network) {
        _isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        _isNetworkAvailable.value = false
    }
}

@Composable
fun connectivityState(): State<Boolean> {
    val context = LocalContext.current

    // Creates a State<> with current connectivity state as initial value
    return NetworkListener(context).checkNetworkAvailability().collectAsState()
}