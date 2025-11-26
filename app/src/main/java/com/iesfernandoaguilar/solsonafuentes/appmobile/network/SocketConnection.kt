package com.iesfernandoaguilar.solsonafuentes.appmobile.network

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.*
import java.io.*
import java.net.Socket

object SocketConnection {
    private const val SERVER_IP = "192.168.0.16"
    private const val SERVER_PORT = 8081

    private var socket: Socket? = null
    var output: ObjectOutputStream? = null
    var input: ObjectInputStream? = null

    fun isConnected(): Boolean = socket?.isConnected == true

    suspend fun connect(): Boolean = withContext(Dispatchers.IO) {
        try {
            socket = Socket(SERVER_IP, SERVER_PORT)
            output = ObjectOutputStream(socket?.outputStream)
            input = ObjectInputStream(socket?.inputStream)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun send(data: Any) = withContext(Dispatchers.IO) {
        try {
            output?.writeObject(data)
            output?.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun receive(): Any? = withContext(Dispatchers.IO) {
        try {
            input?.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun disconnect() {
        try {
            socket?.close()
            output = null
            input = null
            socket = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}