package annekenl.nanobaking;

import android.graphics.Color;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

/**
 *  Check for basic display setup when on tablets (w900dp)
 */

@RunWith(AndroidJUnit4.class)

public class TabletUITest
{
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

   // private static ArrayList<RecipeItem> mRecipesList = new ArrayList<>();

    //@BeforeClass
    //public static void setUpClass() {
        //executed only once, before the first test
    //}

    /**
     * When Tablet View is launch Recipe Detail Container fragment area is visible and should have a default fragment with
     * a android cook image displayed before any recipe is chosen.
     */
    @Test
    public void startupOnTablet_DetailSpace_notBlank()
    {
        //both are present on screen when in tablet mode
        onView(withId(R.id.recipe_list)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_detail_container)).check(matches(isDisplayed()));

        onView(withId(R.id.startup_default_img)).check(matches(isDisplayed()));
    }

    @Test
    /*
     * To do - get a list of expected test recipe names
     *
     * Check expected app bar title and highlighted recipe list item reflect
     * a recipe name item that was chosen/clicked.
     */
    public void clickOnRecipeListItem()
    {
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the correct recipe details are shown - correct recipe title is shown
        onView(withId(main_toolbar)).check(matches(isDisplayed()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(main_toolbar))))
                .check(matches(withText("Nutella Pie")));

        //check that the chosen item 0 is highlighted in list (also other items are not)
        chosenRecipeIsHighlighted(0);

        /**click another item - app bar title and highlighted list item should change**/
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Checks that the correct recipe details are shown - correct recipe title is shown
        onView(withId(main_toolbar)).check(matches(isDisplayed()));
        onView(allOf(instanceOf(TextView.class), withParent(withId(main_toolbar))))
                .check(matches(withText("Brownies")));

        //check that the chosen item 1 is highlighted in list (also other items are not)
        chosenRecipeIsHighlighted(1);
    }

    /* Sub test that each recipe name item in recipe list is the expected color for selected or
    * non-selected item.
    */
    protected void chosenRecipeIsHighlighted(int selectedPosition)
    {
       RecyclerView mRecipeList = mActivityTestRule.getActivity().findViewById(R.id.recipe_list);

        for(int i = 0; i < mRecipeList.getChildCount(); i++)
        {
            int color = ((TextView) mRecipeList.getChildAt(i)).getCurrentTextColor();
            int selectedColor = ContextCompat.getColor(mActivityTestRule.getActivity(), R.color.selected_color); //** getColor() deprecated

            if(i == selectedPosition)
                assertEquals("Error Not Selected Item Color", selectedColor, color);
            else
                assertEquals("Error Not Default Item Color", Color.BLACK, color);
        }
    }

}
