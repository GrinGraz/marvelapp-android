package cl.gringraz.corenetwork

data class ApiConfig(
    val baseUrl: String,
    val authToken: String,
    val timeout: Long = 15L,
    val isProd: Boolean = true
)
