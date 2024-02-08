package fr.mastersime.panshare.feature.CameraScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import fr.mastersime.PanShare.ml.Model
import fr.mastersime.panshare.Setup.Screen
import fr.mastersime.panshare.Setup.Screen.PHOTO_SUMMURY_VIEW_ROUTE
import fr.mastersime.panshare.feature.SummuryPhoto.SummuryPhotoViewModel
import fr.mastersime.panshare.model.PhotoData
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


@Composable
fun CameraContent(
    onPhotoCaptured: (Bitmap) -> Unit,
    lastCapturedPhoto: Bitmap? = null,
    navController: NavController,
    photoData: PhotoData,
    summuryPhotoViewModel: SummuryPhotoViewModel
) {

    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text(text = "Détécter Le panneau") },
            onClick = {
                val bitmap = lastCapturedPhoto
                val model = Model.newInstance(context)
                Log.d("CameraContent", "Hello From onCLick de Camera centent")

                // Convert the bitmap to a ByteBuffer
                val byteBuffer = ByteBuffer.allocateDirect(4 * 200 * 200 * 3)
                byteBuffer.order(ByteOrder.nativeOrder())
                val intValues = IntArray(200 * 200)
                bitmap?.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
                var pixel = 0
                for (i in 0 until 200) {
                    for (j in 0 until 200) {
                        val value = intValues[pixel++]
                        byteBuffer.putFloat(((value shr 16 and 0xFF) - 127.5f) / 127.5f)
                        byteBuffer.putFloat(((value shr 8 and 0xFF) - 127.5f) / 127.5f)
                        byteBuffer.putFloat(((value and 0xFF) - 127.5f) / 127.5f)
                    }
                }

                // Creates inputs for reference.
                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 200, 200, 3), DataType.FLOAT32)
                inputFeature0.loadBuffer(byteBuffer)

                // Runs model inference and gets result.
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                // Get the index of the class with the highest confidence
                val maxIndex =
                    outputFeature0.floatArray.indices.maxByOrNull { outputFeature0.floatArray[it] }
                        ?: -1

                Log.d("CameraContent", "Hello From outputFeature0: ${outputFeature0.floatArray.contentToString()}")
                // Define the classes
                val classes = arrayOf("limitation", "danger", "obligation")

                // Get the class name with the highest confidence
                val className = classes[maxIndex]

                photoData.type = className

                summuryPhotoViewModel.updatePhotoData(photoData)

                // Display a toast with the class name
                Toast.makeText(context, className, Toast.LENGTH_SHORT).show()
                Log.d("CameraContent", "Hello From class name: $className")

                // Releases model resources if no longer used.
                model.close()
                navController.navigate("$PHOTO_SUMMURY_VIEW_ROUTE/$className")
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
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(0.7f)
                    .padding(paddingValues),
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setBackgroundColor(Color.BLACK)
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        previewView.controller = cameraController
                        cameraController.bindToLifecycle(lifecycleOwner)
                    }
                })


        }
    }
}