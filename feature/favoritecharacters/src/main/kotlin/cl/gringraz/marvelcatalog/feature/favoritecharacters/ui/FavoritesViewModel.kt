package cl.gringraz.marvelcatalog.feature.favoritecharacters.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoritesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Comming soon!"
    }
    val text: LiveData<String> = _text
}
