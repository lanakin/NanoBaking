package annekenl.nanobaking.recipedata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by annekenl
 */

public class IngredientItem implements Parcelable
{
    private String quantity = "";
    private String measure = "";
    private String ingredient = "";

    public void setQuantity(String q) {
        quantity = q;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setMeasure(String m) {
        measure = m;
    }

    public String getMeasure() {
        return measure;
    }

    //name
    public void setIngredient(String i) {
        ingredient = i;
    }

    public String getIngredient() {
        return ingredient;
    }

    /** BELOW is Parcel Implementation Code; pass an object into another Activity easily **/
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(quantity);
        out.writeString(measure);
        out.writeString(ingredient);
    }

    public static final Creator<IngredientItem> CREATOR
            = new Creator<IngredientItem>() {
        public IngredientItem createFromParcel(Parcel in) {
            return new IngredientItem(in);
        }

        public IngredientItem[] newArray(int size) {
            return new IngredientItem[size];
        }
    };

    private IngredientItem(Parcel in)
    {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }
}
