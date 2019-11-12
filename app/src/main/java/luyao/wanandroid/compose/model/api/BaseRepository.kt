package luyao.wanandroid.compose.model.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import luyao.wanandroid.compose.model.bean.Result
import luyao.wanandroid.compose.model.bean.WanResponse
import retrofit2.Response
import java.io.IOException

/**
 * Created by luyao
 * on 2019/4/10 9:41
 */
open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> WanResponse<T>): WanResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            Result.Error(IOException(errorMessage, e))
        }
    }

    suspend fun <T : Any> executeResponse(response: WanResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): Result<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                Result.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let { it() }
                Result.Success(response.data)
            }
        }
    }

    suspend fun <T : Any> executeResponse(response: Response<WanResponse<T>>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): Result<T> {
        return coroutineScope {

            if (response.isSuccessful){
                val result = response.body()
                if (result?.errorCode == -1) {
                    errorBlock?.let { it() }
                    Result.Error(IOException(result.errorMsg))
                } else {
                    successBlock?.let { it() }
                    Result.Success(result!!.data)
                }
            }else {
                Result.Error(IOException(response.message()))
            }

        }
    }


}