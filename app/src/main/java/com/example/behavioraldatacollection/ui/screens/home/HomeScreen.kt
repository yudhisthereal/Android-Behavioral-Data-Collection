package com.example.behavioraldatacollection.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.behavioraldatacollection.data.Globals
import com.example.behavioraldatacollection.data.repository.GestureRepository
import com.example.behavioraldatacollection.data.repository.HandwritingRepository
import com.example.behavioraldatacollection.data.repository.KeystrokeRepository
import java.util.UUID
import com.example.behavioraldatacollection.data.storage.ExternalStorageManager

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val storageManager = ExternalStorageManager()
    var isMonitoring by remember { mutableStateOf(Globals.isMonitoring) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            isMonitoring = !isMonitoring
            Globals.isMonitoring = isMonitoring

            if (!isMonitoring) {
                val dataToSave = buildString {
                    append("Keystroke Data: ${KeystrokeRepository.keystrokeDataList}\n")
                    append("Gesture Data: ${GestureRepository.gestureDataList}\n")
                    append("Handwriting Data: ${HandwritingRepository.handwritingDataList}\n")
                }
                val success = storageManager.saveDataToExternalStorage(context,"Data ${UUID.randomUUID()}", dataToSave)

                Toast.makeText(context, "Data saved: $success", Toast.LENGTH_SHORT).show()

                // Clear the data and get ready for the next data collection session
                KeystrokeRepository.keystrokeDataList.clear()
                GestureRepository.gestureDataList.clear()
                HandwritingRepository.handwritingDataList.clear()
            }
        }) {
            Text(if (isMonitoring) "Stop" else "Start")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.navigate("typing") }) {
            Text("Typing Activity")
        }

        Button(onClick = { navController.navigate("gestures") }) {
            Text("Gesture Activity")
        }

        Button(onClick = { navController.navigate("handwriting") }) {
            Text("Handwriting Activity")
        }
    }
}