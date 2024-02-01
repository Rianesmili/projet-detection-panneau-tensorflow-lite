package fr.mastersime.panshare.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NoPermissionScreen(
    onRequestCameraPermission: () -> Unit,
    onRequestLocationPermission: () -> Unit
) {
    NoPermissionContent(
        onRequestCameraPermission = onRequestCameraPermission,
        onRequestLocationPermission = onRequestLocationPermission
    )
}

@Composable
private fun NoPermissionContent(
    onRequestCameraPermission: () -> Unit,
    onRequestLocationPermission: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Please grant the permissions to use the core functionality of this app.")
        Button(onClick = onRequestCameraPermission) {
            Icon(imageVector = Icons.Default.Phone, contentDescription = "Camera")
            Text(text = "Grant Camera permission")
        }
        Button(onClick = onRequestLocationPermission) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location")
            Text(text = "Grant Location permission")
        }
    }
}