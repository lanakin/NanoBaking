package annekenl.nanobaking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A 'navigation list' of 'buttons' that represents all the
 * parts of a recipe - starts with an ingredients list and then
 * each single recipe step. Pressing on a button creates the
 * respective fragment to display a recipe part. This fragment
 * works in conjunction with RecipeDetailNavFragment.
 */
public class RecipeDetailNavBtnsFragment extends RecipeDetailNavFragment
{
    public RecipeDetailNavBtnsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.recipe_detail_content, container, false);

        int recipePartsSize = getRecipePartsSize();

        // Create a 'list' of Recipe Parts' Buttons (ingredients list btn or a recipe step btn)
        for(int i = 0; i < recipePartsSize; i++)
        {
            final Fragment currRecipePartDetailFrag = getRecipePart(i);

            TextView recipePartBtn = (TextView) inflater.inflate(R.layout.recipe_card_button,null);
            recipePartBtn.setText(currRecipePartDetailFrag.getArguments().getString(RecipeDetailNavFragment.RECIPE_PART_BTN_TITLE));

            recipePartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRecipePartFragment(currRecipePartDetailFrag);
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
