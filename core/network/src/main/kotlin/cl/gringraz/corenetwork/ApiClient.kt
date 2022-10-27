package cl.gringraz.corenetwork

/**
 * ApiClient is heavily based in Retrofit, although it could be used along with others type-safe
 * HTTP clients honouring the parameter type.
 *
 * ApiClient receives a parametrized type `T`, it should be the service interface that holds the
 * retrofit API endpoints definition.
 *
 * The `enpoints` implementation should return the implementation returned by Retrofit through
 * `public <T> T create(final Class<T> service)`.
 *
 * Refers to `cl.gringraz.RetrofitClient<T>` implementation.
*/
interface ApiClient<T> {
    val endpoints: T
}
