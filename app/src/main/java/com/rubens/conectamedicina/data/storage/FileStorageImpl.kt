package com.rubens.conectamedicina.data.storage

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class FileStorageImpl: FileStorage {


    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val TAG = "FileStorageImpl"



    override suspend fun uploadImageToStorage(uri: Uri, userUsername: String, imgLink: (String)->Unit) {
        val storageRef = storage.reference.child("usersProfilePics/${userUsername}.jpg")

        val uploadTask = storageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                link->
                val imageUrl = link.toString()
                imgLink(imageUrl)



            }
        }.addOnFailureListener {
            Log.d(TAG, "Error in uploadImageToStorage: ${it.message}")

        }

    }


}