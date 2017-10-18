package annekenl.nanobaking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import annekenl.nanobaking.recipedata.IngredientItem;
import annekenl.nanobaking.recipedata.RecipeItem;
import annekenl.nanobaking.recipedata.StepItem;

/**
 * Created by annekenl
 */

public class TestUtilities
{
    public static ArrayList<RecipeItem> mRecipes = new ArrayList<RecipeItem>();

    /* test JSON with a single Recipe */
    public static final String testJSONStr = "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"Nutella Pie\",\n" +
            "    \"ingredients\": [\n" +
            "      {\n" +
            "        \"quantity\": 2,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"Graham Cracker crumbs\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 6,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"unsalted butter, melted\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 0.5,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"granulated sugar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1.5,\n" +
            "        \"measure\": \"TSP\",\n" +
            "        \"ingredient\": \"salt\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 5,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"vanilla\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1,\n" +
            "        \"measure\": \"K\",\n" +
            "        \"ingredient\": \"Nutella or other chocolate-hazelnut spread\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 500,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"Mascapone Cheese(room temperature)\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"heavy cream(cold)\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 4,\n" +
            "        \"measure\": \"OZ\",\n" +
            "        \"ingredient\": \"cream cheese(softened)\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"steps\": [\n" +
            "      {\n" +
            "        \"id\": 0,\n" +
            "        \"shortDescription\": \"Recipe Introduction\",\n" +
            "        \"description\": \"Recipe Introduction\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1,\n" +
            "        \"shortDescription\": \"Starting prep\",\n" +
            "        \"description\": \"1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 2,\n" +
            "        \"shortDescription\": \"Prep the cookie crust.\",\n" +
            "        \"description\": \"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 3,\n" +
            "        \"shortDescription\": \"Press the crust into baking form.\",\n" +
            "        \"description\": \"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 4,\n" +
            "        \"shortDescription\": \"Start filling prep\",\n" +
            "        \"description\": \"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 5,\n" +
            "        \"shortDescription\": \"Finish filling prep\",\n" +
            "        \"description\": \"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 6,\n" +
            "        \"shortDescription\": \"Finishing Steps\",\n" +
            "        \"description\": \"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"servings\": 8,\n" +
            "    \"image\": \"\"\n" +
            "  }]";


    public static RecipeItem parseRecipeJson(String json)
    {
        RecipeItem emptyRecipeItem = new RecipeItem();

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

                mRecipes.add(recipeItem);  //main recipes list
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return emptyRecipeItem;
    }


    /**
     * Common helper method to return a test Recipe object
     * created from JSON data
     *
     * @return  a single RecipeItem
     */
    public static RecipeItem getARecipeItem()
    {
        parseRecipeJson(testJSONStr);

        return mRecipes.get(0);
    }

    /* to do - lengthen test json string to create more recipe items */
}
