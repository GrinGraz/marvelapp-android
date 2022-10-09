package cl.gringraz.corenetwork

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

sealed class RemoteError
data class ConnectionError(val message: String) : RemoteError()
data class UnauthorizedError(
    @SerializedName("error")
    @Expose
    val exceptionError: RemoteErrorResponse
) : RemoteError()

data class ApiError(
    @SerializedName("error") val exceptionError: RemoteErrorResponse
) : RemoteError()

data class UnknownError(val message: String) : RemoteError()
data class UnexpectedNullBodyError(val message: String) : RemoteError()

data class RemoteErrorResponse(
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("statusCode")
    @Expose
    val statusCode: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("message")
    @Expose
    val message: String? = null
)
