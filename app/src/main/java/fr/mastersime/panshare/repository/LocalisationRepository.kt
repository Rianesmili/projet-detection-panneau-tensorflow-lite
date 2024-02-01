package fr.mastersime.panshare.repository

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalisationRepository @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {
    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Location? {
        return withContext(Dispatchers.IO) {
            try {
                val locationResult = fusedLocationProviderClient.lastLocation.await()
                locationResult
            } catch (e: Exception) {
                null
            }
        }
    }
}