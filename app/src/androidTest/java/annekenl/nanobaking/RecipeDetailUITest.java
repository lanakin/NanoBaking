package annekenl.nanobaking;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import annekenl.nanobaking.recipedata.IngredientItem;
import annekenl.nanobaking.recipedata.RecipeItem;
import annekenl.nanobaking.recipedata.StepItem;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static annekenl.nanobaking.RecipeDetailNavFragment.RECIPE_ITEM_OBJ;
import static annekenl.nanobaking.TestUtilities.getARecipeItem;

/**
 *  Reference Information: example test in Udacity Lesson Project - 'Tea Time'
 * and stack overflow question: https://stackoverflow.com/questions/31752303/espresso-startactivity-that-depends-on-intent
 */

@RunWith(AndroidJUnit4.class)

public class RecipeDetailUITest
{
    private RecipeItem mTestRecipeObj = new RecipeItem();

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class){

                @Override
                protected Intent getActivityIntent() {

                    /* create the test Recipe item */
                    mTestRecipeObj = getARecipeItem();

                    Intent intent = new Intent();
                    intent.putExtra(RECIPE_ITEM_OBJ, mTestRecipeObj);
                    return intent;
                }
            };


    /**
     * Clicks on a recipe detail button - particularly
     * the first button with text "Gather your Ingredients!"
     * and see a list of ingredients displayed.
     *
     */
    @Test
    public void clickRecipeDetailButton_Ingredients()
    {
        onView(withText(R.string.detail_ingredients_button)).perform(click());

        //ingredients are displayed
        ArrayList<IngredientItem> mIngreds = mTestRecipeObj.getIngredients();
        for(int i = 0; i < mIngreds.size(); i++)
        {
            IngredientItem currItem = mIngreds.get(i);

            onView(withText(currItem.getIngredient())).check(matches(isDisplayed())); //name
            onView(withText(currItem.getQuantity()
                    + " " + currItem.getMeasure().toLowerCase())).check(matches(isDisplayed())); //measure amt
        }
    }


    /**
     * Clicks on a recipe detail button - one with a recipe step
     * title and check that the basic display for a recipe step's
     * details is shown.
     */
    @Test
    public void clickRecipeDetailButton_AStep()
    {
        StepItem aStepItem = mTestRecipeObj.getSteps().get(0);

        onView(withText("Step " + aStepItem.getId() + " - " + aStepItem.getShortDesc())).perform(click());

        //Recipe Step Basic Display parts are present
        onView(withId(R.id.recipe_step_media)).check(matches(isDisplayed())); //recipe image or video

        if(!aStepItem.getVideoUrl().isEmpty())
            onView(withId(R.id.stepPlayerView)).check(matches(isDisplayed()));
        else
            onView(withId(R.id.stepImageView)).check(matches(isDisplayed()));

        onView(withText(aStepItem.getShortDesc() + "\n\n" +
                aStepItem.getDescription() + "\n")).check(matches(isDisplayed())); //recipe details text

    }


    /** Navigation Check **/

    /**
     * The first "recipe sub part" shown in details for a single recipe is the ingredients.
     * The only available navigation button is the next button to proceed to the first step.
     * (the system back button will go back to the overall button list of recipe sub parts)
     */
    @Test
    public void testFirstRecipePartNav()
    {
        onView(withText(R.string.detail_ingredients_button)).perform(click());

        onView(withId(R.id.nextBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.prevBtn)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.nextBtn)).perform(click());

        StepItem aStepItem = mTestRecipeObj.getSteps().get(0);

        onView(withText(aStepItem.getShortDesc() + "\n\n" +
                aStepItem.getDescription() + "\n")).check(matches(isDisplayed())); //showing the next part (step 0)
    }


    /**
     * The last "recipe sub part" shown in details for a single recipe is the last recipe step.
     * The only available navigation button is the previous button to go back to the second-to-last step.
     * (the system back button will go back to the overall button list of recipe sub parts)
     */
    @Test
    public void testLastRecipePartNav()
    {
        int stepsSize = mTestRecipeObj.getSteps().size();
        StepItem aStepItem = mTestRecipeObj.getSteps().get(stepsSize-1);

        onView(withText("Step " + aStepItem.getId() + " - " + aStepItem.getShortDesc())).perform(click());

        onView(withId(R.id.prevBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.nextBtn)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.prevBtn)).perform(click());

        aStepItem = mTestRecipeObj.getSteps().get(stepsSize-2);

        onView(withText(aStepItem.getShortDesc() + "\n\n" +
                aStepItem.getDescription() + "\n")).check(matches(isDisplayed())); //showing the second-to-last step
    }


    /**
     * In-between the ingredients list and the last recipe step - a middle recipe step has a previous and
     * next button for going back and forward among the steps in order.
     * (the system back button will go back to the overall button list of recipe sub parts)
     */
    @Test
    public void testMiddleRecipePartNav()
    {
        int stepsSize = mTestRecipeObj.getSteps().size();
        StepItem aStepItem = mTestRecipeObj.getSteps().get(1); //starting at step 1

        onView(withText("Step " + aStepItem.getId() + " - " + aStepItem.getShortDesc())).perform(click());

        onView(withId(R.id.prevBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.nextBtn)).check(matches(isDisplayed()));

        onView(withId(R.id.prevBtn)).perform(click());  //MOVE BACK from step 1 to step 0

        aStepItem = mTestRecipeObj.getSteps().get(0);

        onView(withText(aStepItem.getShortDesc() + "\n\n" +
                aStepItem.getDescription() + "\n")).check(matches(isDisplayed())); //confirm we're at step 0

       //click next button at step 0 to go to step 1
        onView(withId(R.id.nextBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.nextBtn)).perform(click());    //GO FORWARD to step 1

        aStepItem = mTestRecipeObj.getSteps().get(1);

        onView(withText(aStepItem.getShortDesc() + "\n\n" +
                aStepItem.getDescription() + "\n")).check(matches(isDisplayed())); //confirm we're at step 1

        //click next button at step 1 to go to step 2
        onView(withId(R.id.nextBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.nextBtn)).perform(click());    //GO FORWARD again to step 2

        aStepItem = mTestRecipeObj.getSteps().get(2);

        onView(withText(aStepItem.getShortDesc() + "\n\n" +
                aStepItem.getDescription() + "\n")).check(matches(isDisplayed())); //confirm we're at step 2
    }

}
