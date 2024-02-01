package fr.mastersime.panshare.feature.SummuryPhoto

import android.graphics.Bitmap
import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
// fun PhotoSummury(navController: NavController, photo: Bitmap, location: Location) {
fun PhotoSummury() {
    val viewModel: SummuryPhotoViewModel = hiltViewModel()

    Column(
        Modifier.fillMaxWidth()
    ){
        Text(
            "hello world"
        )
    }

    // Update the photoData StateFlow with the passed arguments
    // viewModel.updatePhotoData(photo, location)

}
