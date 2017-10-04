package annekenl.nanobaking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import annekenl.nanobaking.recipedata.RecipeItem;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends RecipeDetailBaseFragment
{
    private RecipeItem mItem;

    private ArrayList<TextView> recipeParts = new ArrayList<>();

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

       //if (getArguments().containsKey(RECIPE_ITEM_OBJ)) {
            //mItem = getArguments().getParcelable(RECIPE_ITEM_OBJ);
       // }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.recipe_detail_sub_content, container, false);


        /** Navigation **/
        //etupNavigationButtons(NO_NAV_INDEX);

        int recipePartsSize = getRecipePartsSize();
        if(recipePartsSize == 0)
            return rootView;  //~no data


        // Create a 'list' of Recipe Parts' Buttons (ingredients list btn or a recipe step btn)
        for(int i = 0; i < recipePartsSize; i++)
        {
            final Fragment currRecipePartDetailFrag = getRecipePart(i);

            TextView recipePartBtn = (TextView) inflater.inflate(R.layout.recipe_card_button,null);
            recipePartBtn.setText(currRecipePartDetailFrag.getArguments().getString("frag_btn_title"));

            recipePartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transitionToSubDetailFragment(currRecipePartDetailFrag); //,currRecipePartDetailFrag.getArguments().getString("frag_btn_title"));  //get frag at i
                }
            });

            ((ViewGroup) rootView).addView(recipePartBtn);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setupNavigationButtons(NO_NAV_INDEX);
    }
}
