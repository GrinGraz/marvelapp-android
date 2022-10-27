package cl.gringraz.marvelcatalog.feature.characterslist

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import cl.gringraz.marvelcatalog.feature.characterslist.ui.MarvelCharactersListFragment
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions.typeTo
import com.adevinta.android.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.adevinta.android.barista.interaction.BaristaMenuClickInteractions
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import javax.inject.Named

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class MarvelCharactersListFragmentTest {

    @Test
    @Named("Given the characters list loaded When loading successful Then the fragment displayed ok")
    fun fragmentIsDisplayedOk() {

        val bundle = bundleOf(
            "listing_type" to "explore",
        )

        FragmentScenario.launchInContainer(
            themeResId = R.style.Theme_MarvelCatalog,
            fragmentClass = MarvelCharactersListFragment::class.java,
            fragmentArgs = bundle
        )

        // Loading characters
        assertDisplayed(R.id.progress_bar)
        sleep(2, TimeUnit.SECONDS) // Replace with Idle Resource
        // Loaded characters
        assertNotDisplayed(R.id.progress_bar)
        assertNotDisplayed(R.id.status_message)
        assertDisplayed(R.id.characters_recycler)
        assertListItemCount(R.id.characters_recycler, 20)
        assertDisplayedAtPosition(R.id.characters_recycler, 0, "3-D Man")
        // Navigate characters
        scrollListToPosition(R.id.characters_recycler, 8)
        assertDisplayedAtPosition(R.id.characters_recycler, 7, "Abyss")
        // Load more characters
        scrollListToPosition(R.id.characters_recycler, 19)
        assertDisplayed(R.id.progress_bar)
        sleep(2, TimeUnit.SECONDS)
        assertListItemCount(R.id.characters_recycler, 40)
        assertDisplayedAtPosition(R.id.characters_recycler, 20, "Ajak")
    }
}
