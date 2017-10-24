package annekenl.nanobaking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import annekenl.nanobaking.recipedata.RecipeItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A 'navigation list' of 'buttons' that represents all the
 * parts of a recipe - starts with an ingredients list and then
 * each single recipe step. Pressing on a button creates the
 * respective fragment to display a recipe part. This fragment
 * works in conjunction with RecipeDetailNavFragment.
 */
public class RecipeDetailNavBtnsFragment extends RecipeDetailNavFragment
{
    @BindView(R.id.recipeIntroImgView) ImageView mImageView;
    @BindView(R.id.recipeIntroTextView) TextView mTextView;
    private Unbinder unbinder;

    private RecipeItem mItem;

    //private String droidChefUrl =
    // "http://cdn04.androidauthority.net/wp-content/uploads/2012/08/Android-chef.jpg"; //test

    public RecipeDetailNavBtnsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(RECIPE_ITEM_OBJ)) { //passed in from RecipeDetailActivity
            mItem = getArguments().getParcelable(RECIPE_ITEM_OBJ);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.recipe_intro_details, container, false);
        unbinder = ButterKnife.bind(this,rootView);


        if(mItem != null) {
            //main recipe image
            String imgUrl = mItem.getImageUrl();

            if(!imgUrl.isEmpty())
                Picasso.with(getContext()).load(imgUrl).noFade()  //test droidChefUrl
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .into(mImageView);
            else {
                mImageView.setVisibility(View.GONE);
            }

            mTextView.setText("View the necessary ingredients and steps to follow below. This recipe makes" + " " + mItem.getServings() + " servings.");
        }

        View btnsView = inflater.inflate(R.layout.recipe_detail_content, container, false);

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

            ((ViewGroup) btnsView).addView(recipePartBtn);
        }

        ((ViewGroup) rootView).addView(btnsView);

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setupNavigationButtons(NO_NAV_INDEX);
    }

    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
