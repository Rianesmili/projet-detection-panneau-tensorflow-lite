package fr.mastersime.panshare.feature.CameraScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import fr.mastersime.panshare.Setup.Screen
import fr.mastersime.panshare.core.utilis.rotateBitmap
import fr.mastersime.panshare.feature.SummuryPhoto.SummuryPhotoViewModel
import fr.mastersime.panshare.model.PhotoData
import java.util.concurrent.Executor


@Composable
fun CameraContent(
    onPhotoCaptured: (Bitmap) -> Unit, lastCapturedPhoto: Bitmap? = null,
    navController: NavController
) {

    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }
    val viewModel: SummuryPhotoViewModel =
        hiltViewModel() // Ajoutez cette ligne pour obtenir une instance de votre ViewModel


    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text(text = "Détécter Le panneau") },
            onClick = {
                navController.navigate(Screen.CAPTURE_PHOTO_VIEW_ROUTE)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Camera capture icon"
                )
            },
        )
    }) { paddingValues: PaddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(modifier = Modifier
                .fillMaxSize(0.7f)
                .padding(paddingValues),
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setBackgroundColor(Color.BLACK)
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        previewView.controller = cameraController
                        cameraController.bindToLifecycle(lifecycleOwner)
                    }
                })

            if (lastCapturedPhoto != null) {
                LastPhotoPreview(
                    modifier = Modifier.align(alignment = Alignment.BottomStart),
                    lastCapturedPhoto = lastCapturedPhoto
                )
            }
        }
    }

    fun capturePhoto() {
        val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

        cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val correctedBitmap: Bitmap =
                    image.toBitmap().rotateBitmap(image.imageInfo.rotationDegrees)

                onPhotoCaptured(correctedBitmap)
                image.close()

                // Créez un nouvel objet PhotoData avec la photo prise par l'utilisateur
                val newPhotoData = PhotoData(
                    image = correctedBitmap,
                    location = null, // Vous pouvez remplir cette valeur avec la localisation si vous l'avez
                    type = null // Vous pouvez remplir cette valeur avec le type si vous l'avez
                )

                // Mettez à jour _photoData dans le ViewModel avec le nouvel objet PhotoData
                viewModel.updatePhotoData(newPhotoData)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraContent", "Error capturing image", exception)
            }
        })
    }
}

@Composable
private fun LastPhotoPreview(
    modifier: Modifier = Modifier, lastCapturedPhoto: Bitmap
) {

    val capturedPhoto: ImageBitmap =
        remember(lastCapturedPhoto.hashCode()) { lastCapturedPhoto.asImageBitmap() }

    Card(
        modifier = modifier
            .size(128.dp)
            .padding(16.dp),
        // elevation = 8.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Image(
            bitmap = capturedPhoto,
            contentDescription = "Last captured photo",
            contentScale = ContentScale.Crop
        )
    }
}