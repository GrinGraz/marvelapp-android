package cl.gringraz.marvelcatalog

import android.app.Application
import cl.gringraz.flagboard_android.Flagboard

class MarvelCatalogApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val flags = mapOf("favorites" to false)
        Flagboard.loadFlags(flags)
    }
}