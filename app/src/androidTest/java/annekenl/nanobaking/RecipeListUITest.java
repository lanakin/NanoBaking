package annekenl.nanobaking;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static annekenl.nanobaking.R.id.main_toolbar;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

/**
 *  Model after example test in Udacity Lesson Project - 'Tea Time'
 *
 * Click on a item in the main Recipe List to choose a Recipe and test
 * that the chosen Recipe Details are shown.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListUITest
{
    public static final String RECIPE_NAME = "Nutella Pie";

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    /**
     * Clicks on a RecyclerView (list) item and checks that the correct Recipe Details are shown.
     *
     * (Regardless if a Recipe Detail Activity is launched on a phone - a Recipe Details fragment is created
     * and added to a 'recipe_detail_container'.)
     */
    @Test
    public void clickRecipeTitleItem()
    {
        //**not for a recycler view //onData(anything()).inAdapterView(withId(R.id.recipe_list)).atPosition(1).perform(click());

        /*RecyclerView works differently than AdapterView. In fact, RecyclerView is not an AdapterView anymore, hence it can't be used in combination with onData(Matcher).
        To use ViewActions in this class use onView(Matcher) with a Matcher that matches your RecyclerView, then perform a ViewAction from this class. */

        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the correct recipe details are shown - correct recipe title is shown on tablet or phone in app bar toolbar
        onView(withId(main_toolbar)).check(matches(isDisplayed()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.main_toolbar))))
                .check(matches(withText(RECIPE_NAME)));

    }

}
