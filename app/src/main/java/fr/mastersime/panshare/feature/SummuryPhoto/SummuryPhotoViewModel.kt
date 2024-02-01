package fr.mastersime.panshare.feature.SummuryPhoto


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mastersime.panshare.model.Location
import fr.mastersime.panshare.model.PhotoData
import fr.mastersime.panshare.repository.LocalisationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummuryPhotoViewModel @Inject constructor(
    private val localisationRepository: LocalisationRepository
) : ViewModel() {


    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> get() = _location

    private val _photoData = MutableStateFlow<PhotoData?>(null)
    val photoData: StateFlow<PhotoData?> get() = _photoData



    init {
        fetchLocation()
    }

    fun fetchLocation() {
        viewModelScope.launch {
            val androidLocation = localisationRepository.getLastKnownLocation()
            _location.value = androidLocation?.let {
               Location(it.latitude, it.longitude)
            }
        }
    }

    fun updatePhotoData(newPhotoData: PhotoData) {
        _photoData.value = newPhotoData
    }

}
