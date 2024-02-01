package fr.mastersime.panshare.feature.SummuryPhoto

import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SummuryPhotoViewModel @Inject constructor() : ViewModel() {

    // Add a StateFlow to store the captured photo and location
    private val _photoData = MutableStateFlow<Pair<Bitmap, Location>?>(null)
    val photoData: StateFlow<Pair<Bitmap, Location>?> = _photoData.asStateFlow()

    fun updatePhotoData(photo: Bitmap, location: Location) {
        _photoData.value = Pair(photo, location)
    }
}
