package com.rubens.conectamedicina.data.storage

import android.net.Uri

interface FileStorage {
    suspend fun uploadImageToStorage(
        uri: Uri,
        userUsername: String,
        imgLink: (String)->Unit
    )
}


//todo implementar os metodos dessa interface

//todo fazer os metodos la no server para colocar o link da nova imagem no database