package tj.behruz.simpleqrscanner

import android.graphics.Rect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tj.behruz.simpleqrscanner.ui.theme.SimpleQrScannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleQrScannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   QRScannerScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleQrScannerTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScannerScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Camera Preview
        CameraPreview(modifier = Modifier.fillMaxSize())

        // Toolbar
        TopAppBar(
            title = { Text(text = "Skaner") },
            navigationIcon = {
                IconButton(onClick = { /* Handle back action */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Handle flashlight toggle */ }) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Flashlight")
                }
            },
        )

        // QR Scanner Overlay
        QRScannerOverlay(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )

        // Text Below Scanner
        Text(
            text = "QR kodni skanerlang",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 300.dp)
        )

        // Bottom Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { /* Handle Skaner */ }) {
                Text(text = "Skaner")
            }
            Button(onClick = { /* Handle Мой QR */ }) {
                Text(text = "Мой QR")
            }
        }
    }
}

@Composable
fun QRScannerOverlay(modifier: Modifier = Modifier) {
    Box(modifier = modifier.height(300.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Background dimming
            drawRect(color = Color.Black.copy(alpha = 0.6f))

            // Transparent scanner area
            val scannerRect = androidx.compose.ui.geometry.Rect(
                left = size.width * 0.15f,
                top = size.height * 0.25f,
                right = size.width * 0.85f,
                bottom = size.height * 0.75f
            )
            drawRect(
                color = Color.Transparent,
                topLeft = Offset(scannerRect.left, scannerRect.top),
                size = Size(scannerRect.width, scannerRect.height),
                blendMode = BlendMode.Clear
            )

            // Scanner border
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(scannerRect.left, scannerRect.top),
                size = Size(scannerRect.width, scannerRect.height),
                cornerRadius = CornerRadius(16.dp.toPx()),
                style = Stroke(width = 4.dp.toPx())
            )
        }

        // Scanning line
        val infiniteTransition = rememberInfiniteTransition(label = "")
        val offsetY by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.Green)
                .align(Alignment.TopCenter)
                .offset(y = (300.dp * offsetY))
        )
    }
}

@Composable
fun CameraPreview(modifier: Modifier = Modifier) {
    // Replace with actual Camera Preview implementation
    Box(modifier = modifier.background(Color.Gray))
}