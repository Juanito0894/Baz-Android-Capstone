package com.javg.cryptocurrencies.data.repository

import android.content.Context
import com.javg.cryptocurrencies.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketException

open class CRYGenericRepository {

    suspend inline fun <T>getResponseV2(
        context: Context,
        crossinline callFunction: suspend () -> T,
        onSuccess: (response: T) -> Unit,
        onError: (message: String) -> Unit
    ) {
        try {
            val responseAux = withContext(Dispatchers.IO){ callFunction.invoke()}
            onSuccess(responseAux)
        } catch (e: Exception) {
            when(e){
                is SocketException -> onError(context.getString(R.string.cry_internet_error))
            }
        }
    }

    suspend fun <T>getResponse(
        callFunction: suspend () -> T,
    ) {
        try {
            callFunction.invoke()
        } catch (e: Exception) {
            println("exception getResponse -> ${e.message}")
        }
    }
}
