package online.shop.util

import android.util.Log
import kotlin.coroutines.cancellation.CancellationException

suspend fun <R> runSuspendCatching(
    action: suspend () -> R,
    onSuccess: (R) -> Unit,
    onError: () -> Unit,
) {
    runCatching { action.invoke() }
        .onSuccess(onSuccess)
        .onFailure {
            if (it is CancellationException) {
                throw it
            } else {
                Log.println(Log.ERROR, "Internal catching", it.message!!)
                onError.invoke()
            }
        }
}