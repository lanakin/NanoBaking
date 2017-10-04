package annekenl.nanobaking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import annekenl.nanobaking.recipedata.StepItem;

/**
 * Created by annekenl1
 */

public class RecipeStepFragment extends RecipeDetailBaseFragment
{
    public static final String RECIPE_STEP = "recipe_step";

    private StepItem mRecipeStep;

    public RecipeStepFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(RECIPE_STEP)) {
            mRecipeStep = getArguments().getParcelable(RECIPE_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.recipe_card_details_square, container, false);

        //View rootView = inflater.inflate(R.layout.recipe_detail_overview, container, false);
        //View fragView = rootView.findViewById(R.id.recipe_detail_sub_frag_area);

        //View stepView = inflater.inflate(R.layout.recipe_card_details_square, null);

        /* let's see step data to start */
        TextView genericTV = new TextView(getActivity());

        genericTV.setText(mRecipeStep.getId()+ "\n"
                + mRecipeStep.getShortDesc() + "\n"
                + mRecipeStep.getDescription() + "\n"
                + mRecipeStep.getVideoUrl() + "\n"
                + mRecipeStep.getThumbnailUrl());

        //((ViewGroup) stepView).addView(genericTV);
        //((ViewGroup) fragView).addView(stepView);

        ((ViewGroup) rootView).addView(genericTV);

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setupNavigationButtons(getArguments().getInt("frag_nav_id")); //test
    }

}
