package cl.gringraz.corenetwork

import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class UnexpectedEmptyBodyException(message: String) : Exception(message)

fun Throwable.processRemoteException(): RemoteError = when (this) {
    is HttpException -> response().processHttpException()
    is IOException -> ConnectionError(message ?: "Connection terminated")
    is UnexpectedEmptyBodyException -> UnexpectedNullBodyError(message ?: "Empty Body")
    else -> UnknownError(message ?: "Unhandled unknown exception")
}

fun <T> Response<T>.processRemoteResponse(): Either<RemoteError, Response<T>> {
    return when {
        this.isSuccessful -> Either.Success(this)
        else -> throw HttpException(this)
    }
}

private fun Response<*>?.processHttpException(): RemoteError {
    val errorBody: String = this?.errorBody()?.string() ?: ""
    return when {
        this == null -> UnknownError("Unknown error")
        this.hasError(errorBody) && this.isAuthError -> errorBody.parseUnauthorizedError()
        this.hasError(errorBody) -> errorBody.parseApiError()
        else -> UnknownError("Unhandled unknown error")
    }
}

private fun String.parseUnauthorizedError(): UnauthorizedError =
    Gson().fromJson(this, UnauthorizedError::class.java)

private fun String.parseApiError(): RemoteError {
    return try {
        Gson().fromJson(this, ApiError::class.java)
    } catch (exception: Exception) {
        UnknownError("Unknown error")
    }
}

private val <T> Response<T>.isAuthError: Boolean
    get() = code() == 401

private fun <T> Response<T>.hasError(body: String): Boolean =
    errorBody() != null && body.isNotBlank()
