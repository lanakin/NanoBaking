package annekenl.nanobaking;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import annekenl.nanobaking.recipedata.RecipeItem;
import annekenl.nanobaking.recipedata.StepItem;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    //public static final String ARG_ITEM_ID = "item_id";
    public static final String RECIPE_ITEM_OBJ = "recipe_item";

    /**
     * The dummy content this fragment is presenting.
     */
    //private DummyContent.DummyItem mItem;
    private RecipeItem mItem;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

        if (getArguments().containsKey(RECIPE_ITEM_OBJ))
        {
           // mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            mItem = getArguments().getParcelable(RECIPE_ITEM_OBJ);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        /** INGREDIENTS **/
        TextView ingredsBtn = (TextView) inflater.inflate(R.layout.recipe_card_button,null);
        ingredsBtn.setText("Gather your Ingredients!");

        ingredsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                arguments.putParcelableArrayList(RecipeIngredsFragment.RECIPE_INGREDS, mItem.getIngredients());
                RecipeIngredsFragment fragment = new RecipeIngredsFragment();
                fragment.setArguments(arguments);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment, "recipe_ingreds")
                        .addToBackStack("recipe_ingreds")
                        .commit();
            }
        });

        ((ViewGroup) rootView).addView(ingredsBtn);

        /** STEPS **/
        ArrayList<StepItem> steps = mItem.getSteps();
        for(int i = 0; i < steps.size(); i++)
        {
            final StepItem aStepItem = steps.get(i);

            TextView aStepBtn = (TextView) inflater.inflate(R.layout.recipe_card_button,null);
            aStepBtn.setText("Step "+i+" - "+aStepItem.getShortDesc());

            aStepBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(RecipeStepFragment.RECIPE_STEP, aStepItem);
                    RecipeStepFragment fragment = new RecipeStepFragment();
                    fragment.setArguments(arguments);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment, "recipe_step")
                            .addToBackStack("recipe_step")
                            .commit();
                }
            });

            ((ViewGroup) rootView).addView(aStepBtn);
        }

        return rootView;
    }
}
