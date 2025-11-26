package com.iesfernandoaguilar.solsonafuentes.appmobile.network

import kotlinx.coroutines.*
import java.io.*
import java.net.Socket

object SocketConnection {
    private const val SERVER_IP = "192.168.0.16"
    private const val SERVER_PORT = 8081

    private var socket: Socket? = null
    private var output: ObjectOutputStream? = null
    private var input: ObjectInputStream? = null

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
        output?.writeObject(data)
        output?.flush()
    }

    suspend fun receive(): Any? = withContext(Dispatchers.IO) {
        input?.readObject()
    }

    fun disconnect() {
        socket?.close()
    }
}