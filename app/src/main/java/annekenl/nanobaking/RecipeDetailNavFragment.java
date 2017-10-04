package annekenl.nanobaking;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;

import annekenl.nanobaking.recipedata.RecipeItem;
import annekenl.nanobaking.recipedata.StepItem;

/**
 * Parses out the recipe detail information from
 * the passed in Recipe Item and orders it into
 * the ingredients list and subsequent recipe steps.
 * It handles the navigation and display of the various
 * parts of the recipe.
 */
public class RecipeDetailNavFragment extends Fragment
{
    public static final String RECIPE_ITEM_OBJ = "recipe_item";
    public static final String RECIPE_PART_BTN_TITLE = "rp_btn_title";
    public static final String RECIPE_PART_NAV_ID = "rp_nav_id";
    public static final int NO_NAV_INDEX = -1;

    private RecipeItem mItem;
    public static ArrayList<Fragment> mNavigationList;//store 'recipe parts' - ingredients or
        //a single recipe step as their respective fragments, in order, for user to easily navigate back and forth through.

    private Button prevNavBtn;
    private Button nextNavBtn;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailNavFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(savedInstanceState==null) {
            try {
                if (getArguments().containsKey(RECIPE_ITEM_OBJ)) { //passed in from RecipeDetailActivity
                    mItem = getArguments().getParcelable(RECIPE_ITEM_OBJ);

                    Activity activity = this.getActivity();
                    CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
                    if (appBarLayout != null && mItem != null) {
                        appBarLayout.setTitle(mItem.getName());
                    }

                    mNavigationList = new ArrayList<>();
                }

                if (mItem != null) {
                    /** INGREDIENTS **/
                    Bundle arguments = new Bundle();
                    arguments.putParcelableArrayList(RecipeIngredsFragment.RECIPE_INGREDS, mItem.getIngredients());
                    arguments.putString(RECIPE_PART_BTN_TITLE, "Gather your Ingredients!");
                    arguments.putInt(RECIPE_PART_NAV_ID, 0);

                    RecipeIngredsFragment fragment = new RecipeIngredsFragment();
                    fragment.setArguments(arguments);

                    mNavigationList.add(fragment);

                    /** STEPS **/
                    ArrayList<StepItem> steps = mItem.getSteps();
                    for (int i = 0; i < steps.size(); i++) {
                        StepItem aStepItem = steps.get(i);

                        Bundle arguments2 = new Bundle();
                        arguments2.putParcelable(RecipeStepFragment.RECIPE_STEP, aStepItem);
                        arguments2.putString(RECIPE_PART_BTN_TITLE, "Step " + aStepItem.getId() + " - " + aStepItem.getShortDesc());
                        arguments2.putInt(RECIPE_PART_NAV_ID, i + 1);

                        RecipeStepFragment fragment2 = new RecipeStepFragment();
                        fragment2.setArguments(arguments2);

                        mNavigationList.add(fragment2);
                    }
                }
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.recipe_detail_overview, container, false);

        if(mItem == null)
            return rootView;  //~no data

        RecipeDetailNavBtnsFragment fragment = new RecipeDetailNavBtnsFragment(); //"recipe_nav_list"
        Bundle arguments = new Bundle();
        fragment.setArguments(arguments);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_base_content, fragment)
                .commit();

        return rootView;
    }


    public void showRecipePartFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().popBackStack(); //system back button will go to the main list of "navigation buttons"

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_base_content, fragment)   //contains either the navigation list, ingredients list, or a recipe step
                .addToBackStack(null) //no specific name
                .commit();
    }

    //Navigation List Helpers
    public int getRecipePartsSize() {
        return mNavigationList.size();
    }

    public Fragment getRecipePart(int index) {
        return mNavigationList.get(index);
    }

    public void setupNavigationButtons(final int navID)
    {
        prevNavBtn = getActivity().findViewById(R.id.prevBtn);
        nextNavBtn = getActivity().findViewById(R.id.nextBtn);

        if(navID <= 0)
            prevNavBtn.setVisibility(View.GONE);
        else {
            prevNavBtn.setVisibility(View.VISIBLE);
            prevNavBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRecipePartFragment(mNavigationList.get(navID-1));
                }
            });
        }

        if(navID >= 0 && navID < mNavigationList.size()-1) {
            nextNavBtn.setVisibility(View.VISIBLE);
            nextNavBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRecipePartFragment(mNavigationList.get(navID+1));
                }
            });
        }
        else
            nextNavBtn.setVisibility(View.GONE);
    }

}
