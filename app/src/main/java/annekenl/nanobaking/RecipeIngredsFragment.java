package annekenl.nanobaking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import annekenl.nanobaking.recipedata.IngredientItem;

/**
 * A fragment representing a single Recipe list of ingredients.
 * This fragment is contained within the Recipe Detail layout.
 */
public class RecipeIngredsFragment extends RecipeDetailBaseFragment
{
    public static final String RECIPE_INGREDS = "recipe_ingreds";

    private ArrayList<IngredientItem> mIngreds;

    public RecipeIngredsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(RECIPE_INGREDS)) {
            mIngreds = getArguments().getParcelableArrayList(RECIPE_INGREDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.recipe_card_details_square, container, false);
       // View rootView = inflater.inflate(R.layout.recipe_detail_overview, container, false);

        //View ingredsView = inflater.inflate(R.layout.recipe_card_details_square, null);

        for(int i = 0; i < mIngreds.size(); i++)
        {
            LinearLayout currRow = (LinearLayout) inflater.inflate(R.layout.recipe_ingredient_row,null);
            IngredientItem currItem = mIngreds.get(i);

            ((TextView) currRow.findViewById(R.id.recipe_ingreds_name)).setText(currItem.getIngredient());
            ((TextView) currRow.findViewById(R.id.recipe_ingreds_measure)).setText(currItem.getQuantity()
                    + " " + currItem.getMeasure().toLowerCase());

            ((ViewGroup) rootView).addView(currRow);
        }

       // ((ViewGroup) rootView).addView(ingredsView);



        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setupNavigationButtons(0); //test 0
    }

}
