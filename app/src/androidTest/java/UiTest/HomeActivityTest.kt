package UiTest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun testAddCourseHome() {
        onView(withId(R.id.action_add)).perform(click())

        onView(withId(R.id.add_course)).check(matches(isDisplayed()))
    }
    
}