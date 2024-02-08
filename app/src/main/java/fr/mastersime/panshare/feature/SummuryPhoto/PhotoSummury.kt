package fr.mastersime.panshare.feature.SummuryPhoto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.mastersime.panshare.model.Location
import fr.mastersime.panshare.model.PhotoData

@Composable
fun PhotoSummury(type: String?) {
    val viewModel: SummuryPhotoViewModel = hiltViewModel()
    var locationModel by remember { mutableStateOf<Location?>(null) }
    var photoData by remember { mutableStateOf<PhotoData?>(null) }


    LaunchedEffect(key1 = viewModel) {
        viewModel.location.collect { location ->
            locationModel = location
        }
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.photoData.collect { data ->
            photoData = data
        }
    }

    Column(
        Modifier.fillMaxWidth()
    ) {
        photoData?.image?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f) // 2/3 de l'espace
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = "Latitude: ${locationModel?.latitude}",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Longitude: ${locationModel?.longitude}",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Type de Panneau: $type",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
