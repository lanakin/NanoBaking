package annekenl.nanobaking.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import annekenl.nanobaking.R;
import annekenl.nanobaking.recipedata.IngredientItem;
import annekenl.nanobaking.recipedata.RecipeItem;
import annekenl.nanobaking.recipedata.StepItem;

import android.content.SharedPreferences;

import static annekenl.nanobaking.RecipeListActivity.NANOBAKING_PREFS;
import static annekenl.nanobaking.RecipeListActivity.RECIPE_JSON_KEY;


/**
 * Created by annekenl1
 * References: https://www.sitepoint.com/killer-way-to-show-a-list-of-items-in-android-collection-widget/
 * Udacity Android Nanodegree widget lessons
 * http://www.vogella.com/tutorials/AndroidWidgets/article.html
 * https://laaptu.wordpress.com/2013/07/24/populate-appwidget-listview-with-remote-datadata-from-web/
 */

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
    private Context mContext;
    private ArrayList<RecipeItem> mWidgetRecipes;

    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    //called when the appwidget is created for the first time.
    @Override
    public void onCreate() {
        mWidgetRecipes = new ArrayList<RecipeItem>();

        SharedPreferences prefs = mContext.getSharedPreferences(NANOBAKING_PREFS,0);
        String jsonData = prefs.getString(RECIPE_JSON_KEY,"");

        if(!jsonData.isEmpty())
            parseRecipesJson(jsonData);
        else {
            //open recipe list activity... ?
        }
    }

    //called whenever the appwidget is updated.
    @Override
    public void onDataSetChanged() {
        //to do
    }

    @Override
    public void onDestroy() { }

    @Override
    public int getCount() {
        return mWidgetRecipes.size();
    }


    //processes & returns a RemoteViews object - here a single list item.
    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.my_widget_list_row);
        rv.setTextViewText(R.id.myWidgetListItemTV, mWidgetRecipes.get(position).getName()); //recipe names then ingreds...

        //Intent fillInIntent = new Intent();
        //fillInIntent.putExtra(CollectionAppWidgetProvider.EXTRA_LABEL, mCursor.getString(1));
        //rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    //only interested in recipe name and ingredients for the widget
    protected void parseRecipesJson(String json)
    {
        try
        {
            JSONArray recipes = new JSONArray(json);

            for(int i = 0; i < recipes.length(); i++)
            {
                JSONObject currRecipe = recipes.getJSONObject(i);
                RecipeItem recipeItem = new RecipeItem();

                //if(currRecipe.has("id"))
                   // recipeItem.setId(currRecipe.getInt("id"));
                if(currRecipe.has("name"))
                    recipeItem.setName(currRecipe.getString("name"));

                if(currRecipe.has("ingredients"))
                {       //create ingredients list
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

               /* if(currRecipe.has("steps"))
                {       //create steps list
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
            */
                //if(currRecipe.has("servings"))
                   // recipeItem.setServings(currRecipe.getString("servings"));
                //if(currRecipe.has("image"))
                    //recipeItem.setImageUrl(currRecipe.getString("image"));

                mWidgetRecipes.add(recipeItem);  //main recipes list
            }

            //mRecylerViewAdapter.notifyDataSetChanged();
            onDataSetChanged(); //?
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
