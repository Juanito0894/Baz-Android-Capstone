package com.javg.cryptocurrencies.data.repository

import android.content.Context
import com.javg.cryptocurrencies.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
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
                is HttpException -> onError(context.getString(R.string.cry_bad_request_error))
                else -> onError(context.getString(R.string.cry_general_error))
            }
        }
    }
}
