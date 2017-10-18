package annekenl.nanobaking.widget;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import annekenl.nanobaking.recipedata.IngredientItem;
import annekenl.nanobaking.recipedata.RecipeItem;
import annekenl.nanobaking.recipedata.StepItem;

import static annekenl.nanobaking.RecipeListActivity.NANOBAKING_PREFS;
import static annekenl.nanobaking.RecipeListActivity.RECIPE_JSON_KEY;

/**
 * Created by annekenl1
 *
 * Helper class for widget data in place of a database solution for now.
 */

public class MyWidgetDataHelper
{
    private ArrayList<RecipeItem> mRecipesData = new ArrayList<>();


    public MyWidgetDataHelper(Context context)
    {
        if(mRecipesData.isEmpty()) {
            SharedPreferences prefs = context.getSharedPreferences(NANOBAKING_PREFS,0);
            String jsonData = prefs.getString(RECIPE_JSON_KEY,"");

            if(!jsonData.isEmpty())
                parseRecipesJson(jsonData);
            else {
                //right now shows empy view no data message
            }
        }
    }

    public ArrayList<RecipeItem> getRecipeItems() {
        return mRecipesData;
    }


    public static String getIngredientsListString(RecipeItem aRecipe)
    {
        ArrayList<IngredientItem> ingredientItems = aRecipe.getIngredients();

        String ingredients = "";

        for(int i = 0; i < ingredientItems.size(); i++)
        {
            IngredientItem currItem = ingredientItems.get(i);

            ingredients += currItem.getIngredient() + " - ";
            ingredients += currItem.getQuantity() +
                    currItem.getMeasure().toLowerCase() + "\n";
        }

        return ingredients;
    }


    //only interested in recipe name and ingredients for the widget
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

                if(currRecipe.has("steps"))
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

                if(currRecipe.has("servings"))
                    recipeItem.setServings(currRecipe.getString("servings"));
                if(currRecipe.has("image"))
                    recipeItem.setImageUrl(currRecipe.getString("image"));

                mRecipesData.add(recipeItem);  //main recipes list
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
