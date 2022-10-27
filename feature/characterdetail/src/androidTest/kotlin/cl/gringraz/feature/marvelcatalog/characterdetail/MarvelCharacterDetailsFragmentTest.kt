package cl.gringraz.feature.marvelcatalog.characterdetail

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import cl.gringraz.marvelcatalog.feature.characterdetail.R
import cl.gringraz.marvelcatalog.feature.characterdetail.ui.MarvelCharacterDetailFragment
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import javax.inject.Named

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class MarvelCharacterDetailsFragmentTest {

    @Test
    @Named("Given the character loading When loaded successful Then the fragment displayed ok")
    fun fragmentIsDisplayedOk() {

        val bundle = bundleOf(
            "id" to 1011334,
            "url" to "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
            "name" to "3-D Man",
            "description" to "We don't have a character's description right now"
        )

        FragmentScenario.launchInContainer(
            themeResId = R.style.Theme_MarvelCatalog,
            fragmentClass = MarvelCharacterDetailFragment::class.java,
            fragmentArgs = bundle
        )

        // Loading character
        assertDisplayed(R.id.progress_bar)
        sleep(2, TimeUnit.SECONDS) // Replace with Idle Resource
        assertDisplayed("3-D Man")
        assertDisplayed(R.id.character_image)
        assertDisplayed(
            R.id.character_description,
            "We don't have a character's description right now"
        )
        // Loaded character
        assertNotDisplayed(R.id.progress_bar)
        assertNotDisplayed(R.id.status_message)
        assertDisplayedAtPosition(R.id.details_recycler, 0, "Comics")
        assertDisplayed(R.id.details_recycler)
        assertListItemCount(R.id.details_recycler, 4)
        assertDisplayedAtPosition(R.id.detail_recycler, 0, "Avengers: The Initiative (2007) #14")
        // Navigate character details
        scrollListToPosition(R.id.details_recycler, 3)
        assertDisplayedAtPosition(R.id.details_recycler, 3, "Events")
    }
}
