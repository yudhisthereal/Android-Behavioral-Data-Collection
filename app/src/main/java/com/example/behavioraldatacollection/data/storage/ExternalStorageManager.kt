package com.example.behavioraldatacollection.data.storage


import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ExternalStorageManager {

    // Function to save data to external storage using MediaStore
    fun saveDataToExternalStorage(context: Context, filename: String, data: String): Boolean {
        // Prepare the ContentValues for storing file details
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$filename.csv")
            put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }

        // Insert into MediaStore, and get the Uri to write to
        val uri = context.contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        return uri?.let {
            try {
                // Open the output stream and write data
                context.contentResolver.openOutputStream(it)?.use { outputStream ->
                    outputStream.write(data.toByteArray())
                }
                true // Success
            } catch (e: Exception) {
                e.printStackTrace()
                false // Failure
            }
        } ?: false
    }

    // Function to read data from external storage using MediaStore
    fun readDataFromExternalStorage(context: Context, filename: String): String {
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns._ID)
        val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf("$filename.csv")

        // Query the MediaStore for the file
        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                val fileUri = MediaStore.Files.getContentUri("external", it.getLong(idColumn))

                return try {
                    context.contentResolver.openInputStream(fileUri)?.use { inputStream ->
                        inputStream.bufferedReader().use { reader -> reader.readText() }
                    } ?: ""
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                }
            }
        }
        return ""
    }
}

class ExternalStoragePermissionManager {

    private val requestCode = 100

    // Function to request storage permission
    fun requestStoragePermission(activity: Activity) {
        // Check if the permission is granted
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                requestCode
            )
        } else {
            // Permission is already granted
            Toast.makeText(activity, "Storage Permission Already Granted", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle the result of the permission request
    fun handlePermissionResult(
        requestCode: Int,
        grantResults: IntArray,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        if (requestCode == this.requestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
}
