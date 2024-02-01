package fr.mastersime.panshare.feature.CameraScreen


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import fr.mastersime.panshare.feature.NoPermissionScreen


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(navController: NavController) {
    val viewModel: CameraViewModel = hiltViewModel()

    val cameraState: CameraState by viewModel.state.collectAsStateWithLifecycle()

    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val locationPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)

    when {
        cameraPermissionState.status.isGranted && locationPermissionState.status.isGranted -> {
            CameraContent(
                onPhotoCaptured = viewModel::storePhotoInGallery,
                lastCapturedPhoto = cameraState.capturedImage,
                navController
            )
        }

        cameraPermissionState.status.shouldShowRationale || locationPermissionState.status.shouldShowRationale -> {
            NoPermissionScreen(
                onRequestCameraPermission = { cameraPermissionState.launchPermissionRequest() },
                onRequestLocationPermission = { locationPermissionState.launchPermissionRequest() }
            )
        }

        else -> {
            NoPermissionScreen(
                onRequestCameraPermission = { cameraPermissionState.launchPermissionRequest() },
                onRequestLocationPermission = { locationPermissionState.launchPermissionRequest() }
            )
        }
    }
}
