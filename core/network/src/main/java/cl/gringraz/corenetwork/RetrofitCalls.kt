package cl.gringraz.corenetwork

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend inline fun <T> executeCall(
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline retrofitCall: suspend () -> Response<T>
): Either<RemoteError, Response<T>> {
    return withContext(ioDispatcher) {
        try {
            retrofitCall().processRemoteResponse()
        } catch (e: Exception) {
            return@withContext Either.Error(e.processRemoteException())
        }
    }
}
