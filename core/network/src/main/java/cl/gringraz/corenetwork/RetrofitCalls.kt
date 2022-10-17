package cl.gringraz.corenetwork

import arrow.core.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend inline fun <T> apiCall(
    dispatcher: CoroutineDispatcher,
    crossinline retrofitCall: suspend () -> Response<T>
): Either<RemoteError, Response<T>> {
    return withContext(dispatcher) {
        try {
            retrofitCall().processRemoteResponse()
        } catch (e: Exception) {
            return@withContext Either.Left(e.processRemoteException())
        }
    }
}
