package cl.gringraz.marvelcatalog.feature.characterslist.presentation

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun LifecycleOwner.safeLifecycle(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

fun View.throttledClickListener(onClick: () -> Unit) {
    var job: Job? = null
    setOnClickListener {
        if (job?.isCompleted != false) {
            job = CoroutineScope(Dispatchers.Main).launch {
                onClick()
                delay(500)
            }
        }
    }
}
