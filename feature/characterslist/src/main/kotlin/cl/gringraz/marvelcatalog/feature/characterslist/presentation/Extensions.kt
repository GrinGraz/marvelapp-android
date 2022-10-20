package cl.gringraz.marvelcatalog.feature.characterslist.presentation

import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
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

inline fun View.throttledClickListener(crossinline onClick: () -> Unit) {
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

inline fun SearchView.debounceQueryTextListener(
    crossinline onQueryTextChange: (String?) -> Unit
) {
    var job: Job? = null
    setOnQueryTextListener(object : OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean = false

        override fun onQueryTextChange(newText: String?): Boolean {
            job?.cancel()
            job = CoroutineScope(Dispatchers.Main).launch {
                delay(500)
                onQueryTextChange(newText)
            }
            return false
        }
    })
}
