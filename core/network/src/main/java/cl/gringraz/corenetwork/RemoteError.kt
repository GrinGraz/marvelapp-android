package cl.gringraz.corenetwork

import com.google.gson.annotations.SerializedName

sealed class RemoteError(open val message: String)
object ConnectionError : RemoteError(message = "Connection error")
object UnknownError : RemoteError(message = "Unknown error")
data class ApiError(override val message: String) : RemoteError(message = message)

data class RemoteErrorResponse(
    @SerializedName("code") val code: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("message") val message: String?
)
