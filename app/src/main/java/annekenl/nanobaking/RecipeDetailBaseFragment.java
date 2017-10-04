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

import static annekenl.nanobaking.RecipeIngredsFragment.RECIPE_INGREDS;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailBaseFragment extends Fragment {

    public static final String RECIPE_ITEM_OBJ = "recipe_item";
    public static final int NO_NAV_INDEX = -1;

    private RecipeItem mItem;

    public static final ArrayList<Fragment> mFragmentNavigationList = new ArrayList<>(); //the list of 'sub recipe details' in order

  /*  @BindView(R.id.prevBtn) Button prevNavBtn;
    @BindView(R.id.nextBtn) Button nextNavBtn;
    private Unbinder unbinder;*/
    private View rootView;
    private Button prevNavBtn;
    private Button nextNavBtn;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailBaseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(RECIPE_ITEM_OBJ))
        {
            mItem = getArguments().getParcelable(RECIPE_ITEM_OBJ);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null && mItem != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }

        if(mItem != null)
        {
            /** INGREDIENTS **/
            //TextView ingredsBtn = (TextView) inflater.inflate(R.layout.recipe_card_button,null);
           // ingredsBtn.setText("Gather your Ingredients!");

            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(RECIPE_INGREDS, mItem.getIngredients());
            arguments.putString("frag_btn_title","Gather your Ingredients!");
            arguments.putInt("frag_nav_id",0);

            final RecipeIngredsFragment fragment = new RecipeIngredsFragment();
            fragment.setArguments(arguments);

            //addFragToNavigationList(fragment);
            mFragmentNavigationList.add(fragment);

           // ingredsBtn.setOnClickListener(new View.OnClickListener() {
                //@Override
                //public void onClick(View v) {
                    //transitionToSubDetailFragment(fragment,"recipe_ingreds");  //get frag at i
               // }
            //});

            /** STEPS **/
            ArrayList<StepItem> steps = mItem.getSteps();
            for(int i = 0; i < steps.size(); i++)
            {
                final StepItem aStepItem = steps.get(i);

                //TextView aStepBtn = (TextView) inflater.inflate(R.layout.recipe_card_button,null);
                //aStepBtn.setText("Step "+i+" - "+aStepItem.getShortDesc());

                Bundle arguments2 = new Bundle();
                arguments2.putParcelable(RecipeStepFragment.RECIPE_STEP, aStepItem);
                arguments2.putString("frag_btn_title","Step "+i+" - "+aStepItem.getShortDesc());
                arguments2.putInt("frag_nav_id",i+1);

                final RecipeStepFragment fragment2 = new RecipeStepFragment();
                fragment2.setArguments(arguments2);

                //addFragToNavigationList(fragment2);
                mFragmentNavigationList.add(fragment2);

               /* aStepBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        transitionToSubDetailFragment(fragment2,"recipe_step");
                    }
                });*/
            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.recipe_detail_overview, container, false);

        //unbinder = ButterKnife.bind(this,rootView);
        /*prevNavBtn = rootView.findViewById(R.id.prevBtn);
        nextNavBtn = rootView.findViewById(R.id.nextBtn);*/

        /** Navigation **/
       // setupNavigationButtons(NO_NAV_INDEX,rootView);

        if(mItem == null)
            return rootView;  //~no data

        Bundle arguments = new Bundle();
        //arguments.putParcelable(RecipeDetailBaseFragment.RECIPE_ITEM_OBJ, mItem);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(arguments);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_content, fragment) //, "recipe_nav_list"  //keep the base layout in recipe_detail_container
                //.addToBackStack("recipe_nav_list")
                .commit();

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        /*Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeDetailBaseFragment.RECIPE_ITEM_OBJ, mItem);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(arguments);

        transitionToSubDetailFragment(fragment,"recipe_details_list");*/
    }


    public void transitionToSubDetailFragment(Fragment fragment) //, String fragTitle
    {
        //getActivity().getSupportFragmentManager().popBackStack("recipe_buttons_list", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getActivity().getSupportFragmentManager().popBackStack();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_content, fragment)//, fragTitle)  //keep the base layout in recipe_detail_container
                //.addToBackStack("recipe_buttons_list") //no specific name
                .addToBackStack(null) //no specific name
                .commit();
    }

    public int getRecipePartsSize(){
        return mFragmentNavigationList.size();
    }

    public Fragment getRecipePart(int index) {
        return mFragmentNavigationList.get(index);
    }


    public void setupNavigationButtons(final int navid)
    {
        prevNavBtn = getActivity().findViewById(R.id.prevBtn);
        nextNavBtn = getActivity().findViewById(R.id.nextBtn);

        if(navid <= 0)
            prevNavBtn.setVisibility(View.GONE);
        else {
            prevNavBtn.setVisibility(View.VISIBLE);
            prevNavBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transitionToSubDetailFragment(mFragmentNavigationList.get(navid-1)); //not sure on the frag title here
                }
            });
        }



        if(navid >= 0 && navid < mFragmentNavigationList.size()-1) {
            nextNavBtn.setVisibility(View.VISIBLE);
            nextNavBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transitionToSubDetailFragment(mFragmentNavigationList.get(navid+1)); //not sure on the frag title here
                }
            });
        }
        else
            nextNavBtn.setVisibility(View.GONE);
    }


    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }
}
