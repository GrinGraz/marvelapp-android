package cl.gringraz.corenetwork

sealed class Either<out L, out R> {

    data class Error<out L>(val error: L) : Either<L, Nothing>()

    data class Success<out R>(val success: R) : Either<Nothing, R>()

    val isSuccess get() = this is Success<R>
    val isError get() = this is Error<L>

    /**
     * This method will send the Failure if is [Error].
     * But, if is [Success] then it will invoke the [transform]
     * block and return the transformation result.
     *
     * @return [Either.Success<T>] or the expected [Either.Error]
     * @see [https://github.com/arrow-kt/arrow-core/blob/master/arrow-core-data/src/main/kotlin/arrow/core/Either.kt]
     */
    inline fun <T> mapSuccess(
        crossinline transform: (R) -> T
    ): Either<L, T> {
        return when (this) {
            is Success -> Success(transform(success))
            is Error -> this
        }
    }

    suspend inline fun <T> coMapSuccess(
        crossinline transform: suspend (R) -> T
    ): Either<L, T> {
        return when (this) {
            is Success -> Success(transform(success))
            is Error -> this
        }
    }

    suspend inline fun <T> coMapError(
        crossinline transform: suspend (L) -> T
    ): Either<T, R> {
        return when (this) {
            is Success -> this
            is Error -> Error(transform(error))
        }
    }

    inline fun <T> mapError(
        crossinline transform: (L) -> T
    ): Either<T, R> {
        return when (this) {
            is Success -> this
            is Error -> Error(transform(error))
        }
    }

    fun <T> fold(fnL: (L) -> T, fnR: (R) -> T): T {
        return when (this) {
            is Error -> fnL(error)
            is Success -> fnR(success)
        }
    }

    suspend fun <T> coFold(fnL: (L) -> T, fnR: suspend (R) -> T): T {
        return when (this) {
            is Error -> fnL(error)
            is Success -> fnR(success)
        }
    }

    inline fun <C, D> bimap(
        crossinline leftOperation: (L) -> C,
        crossinline rightOperation: (R) -> D
    ): Either<C, D> =
        fold({ Error(leftOperation(it)) }, { Success(rightOperation(it)) })

    fun either(fnL: (L) -> Unit, fnR: (R) -> Unit): Any =
        when (this) {
            is Error -> fnL(error)
            is Success -> fnR(success)
        }

    suspend fun coEither(fnL: (L) -> Unit, fnR: suspend (R) -> Unit): Any =
        when (this) {
            is Error -> fnL(error)
            is Success -> fnR(success)
        }

    suspend fun coEitherSuspend(fnL: suspend (L) -> Unit, fnR: suspend (R) -> Unit): Any =
        when (this) {
            is Error -> fnL(error)
            is Success -> fnR(success)
        }

    fun getSuccessOrNull(): R? = if (this is Success<R>) {
        this.success
    } else {
        null
    }

    fun getFailureOrNull(): L? = if (this is Error<L>) {
        this.error
    } else {
        null
    }
}

inline fun <A, B, C> Either<A, B>.flatMap(f: (B) -> Either<A, C>): Either<A, C> =
    when (this) {
        is Either.Success -> f(this.success)
        is Either.Error -> this
    }
