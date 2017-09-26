package annekenl.nanobaking.recipedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by annekenl1
 */

public class RecipeItem implements Parcelable
{
    private int id = -1;
    private String name = "";
    private ArrayList<IngredientItem>  ingredients;
    private ArrayList<StepItem> steps;
    private String servings = "";
    private String imageUrl = ""; //if provided, so far no recipes have this*

    public RecipeItem() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<IngredientItem> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientItem> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<StepItem> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepItem> steps) {
        this.steps = steps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /** BELOW is Parcel Implementation Code; pass an object into another Activity easily **/
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(id);
        out.writeString(name);
        out.writeTypedList(ingredients);
        out.writeTypedList(steps);
        out.writeString(servings);
        out.writeString(imageUrl);
    }

    public static final Creator<RecipeItem> CREATOR
            = new Creator<RecipeItem>() {
        public RecipeItem createFromParcel(Parcel in) {
            return new RecipeItem(in);
        }

        public RecipeItem[] newArray(int size) {
            return new RecipeItem[size];
        }
    };

    private RecipeItem(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
        in.readTypedList(ingredients,IngredientItem.CREATOR);
        in.readTypedList(steps,StepItem.CREATOR);
        servings = in.readString();
        imageUrl = in.readString();
    }
}
