package cl.gringraz.marvelcatalog.feature.characterdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cl.gringraz.marvelcatalog.feature.characterdetail.databinding.ActivityStubBinding

class StubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityStubBinding.inflate(layoutInflater).root)
    }
}
