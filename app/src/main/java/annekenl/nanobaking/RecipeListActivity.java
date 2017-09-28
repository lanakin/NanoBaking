package annekenl.nanobaking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import annekenl.nanobaking.recipedata.IngredientItem;
import annekenl.nanobaking.recipedata.RecipeItem;
import annekenl.nanobaking.recipedata.StepItem;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity
{
    private ArrayList<RecipeItem> mRecipes = new ArrayList<RecipeItem>();
    private SimpleItemRecyclerViewAdapter mRecylerViewAdapter;

    //Whether or not the activity is in two-pane mode, i.e. running on a tablet
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        getRecipeData(); //**

        Toolbar toolbar = (Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        //LAYOUT
        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void getRecipeData() { new FetchRecipesTask().execute(); }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
        mRecylerViewAdapter = new SimpleItemRecyclerViewAdapter(mRecipes);
        recyclerView.setAdapter(mRecylerViewAdapter);
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>
    {

        //private final List<DummyContent.DummyItem> mValues;
        private final List<RecipeItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<RecipeItem> items) {
            //mValues = items;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.nameView.setText(holder.mItem.getName());
            //holder.mIdView.setText(mValues.get(position).id);
            //holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        RecipeDetailFragment fragment = new RecipeDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_detail_container, fragment)
                                .commit();
                    } else {*/
                        Context context = v.getContext();
                        Intent intent = new Intent(context, RecipeDetailActivity.class);
                        intent.putExtra(RecipeDetailFragment.RECIPE_ITEM_OBJ, holder.mItem);

                        context.startActivity(intent);
                    //}
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public final View mView;
            public final TextView nameView;
            //public final TextView mIdView;
            //public final TextView mContentView;
            //public DummyContent.DummyItem mItem;
            public RecipeItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                nameView = (TextView) view.findViewById(R.id.recipelist_name);
               // mIdView = (TextView) view.findViewById(R.id.id);
               // mContentView = (TextView) view.findViewById(R.id.content);
                mItem = new RecipeItem();
            }

            @Override
            public String toString() {
                return super.toString() + " '" + nameView.getText() + "'";
            }
        }
    }


    //Typical AsyncTask for network query. Modified from example Sunshine app in Udacity Android Nanodegree
    private class FetchRecipesTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params)
        {
            HttpsURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String recipesJsonStr = null;

            //if (params.length == 0)
            //return null;

            try {
                // Construct the URL for The Movie Database API
                final String TMDB_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
                //final String API_PARAM = "api_key";
                //final String PAGE_PARAM = "page";


                Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                        //.appendQueryParameter(API_PARAM, MOVIE_DB_API_KEY)
                        //optional
                        //.appendQueryParameter(PAGE_PARAM, "1")
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                recipesJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("Error ", e.toString());
                // If the code didn't successfully get the data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Error closing stream", e.toString());
                    }
                }
            }

            Log.d(FetchRecipesTask.class.getSimpleName(), recipesJsonStr);

            return recipesJsonStr;
        }

        @Override
        protected void onPostExecute(String result)
        {
            if(result != null) {

                parseRecipesJson(result);
            }
        }

    }


    private void parseRecipesJson(String json)
    {
        try
        {
            JSONArray recipes = new JSONArray(json);

            for(int i = 0; i < recipes.length(); i++)
            {
                JSONObject currRecipe = recipes.getJSONObject(i);
                RecipeItem recipeItem = new RecipeItem();

                if(currRecipe.has("id"))
                    recipeItem.setId(currRecipe.getInt("id"));
                if(currRecipe.has("name"))
                    recipeItem.setName(currRecipe.getString("name"));

                if(currRecipe.has("ingredients"))
                {
                    //create ingredients list
                    JSONArray ingredients = currRecipe.getJSONArray("ingredients");
                    ArrayList<IngredientItem> ingredientItems = new ArrayList<>();

                    for(int j = 0; j < ingredients.length(); j++)
                    {
                        JSONObject currIngredient = ingredients.getJSONObject(j);
                        IngredientItem ingredientItem = new IngredientItem();

                        if(currIngredient.has("quantity"))
                            ingredientItem.setQuantity(currIngredient.getString("quantity"));
                        if(currIngredient.has("measure"))
                            ingredientItem.setMeasure(currIngredient.getString("measure"));
                        if(currIngredient.has("ingredient"))
                            ingredientItem.setIngredient(currIngredient.getString("ingredient")); //name

                        ingredientItems.add(ingredientItem);
                    }

                    recipeItem.setIngredients(ingredientItems);
                }

                if(currRecipe.has("steps"))
                {
                    //create steps list
                    JSONArray steps = currRecipe.getJSONArray("steps");
                    ArrayList<StepItem> stepItems = new ArrayList<>();

                    for(int k = 0; k < steps.length(); k++)
                    {
                        JSONObject currStep = steps.getJSONObject(k);
                        StepItem stepItem = new StepItem();

                        if(currStep.has("id"))
                            stepItem.setId(currStep.getInt("id"));
                        if(currStep.has("shortDescription"))
                            stepItem.setShortDesc(currStep.getString("shortDescription"));
                        if(currStep.has("description"))
                            stepItem.setDescription(currStep.getString("description"));
                        if(currStep.has("videoURL"))
                            stepItem.setVideoUrl(currStep.getString("videoURL"));
                        if(currStep.has("thumbnailURL"))
                            stepItem.setThumbnailUrl(currStep.getString("thumbnailURL"));

                        stepItems.add(stepItem);
                    }

                    recipeItem.setSteps(stepItems);
                }

                if(currRecipe.has("servings"))
                    recipeItem.setServings(currRecipe.getString("servings"));
                if(currRecipe.has("image"))
                    recipeItem.setImageUrl(currRecipe.getString("image"));

                mRecipes.add(recipeItem);  //main recipes list
            }

            //mMovieAdapter.notifyDataSetChanged();
            mRecylerViewAdapter.notifyDataSetChanged();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
