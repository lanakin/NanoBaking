package annekenl.nanobaking.recipedata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by annekenl
 */

public class StepItem implements Parcelable
{
    private int id = -1;
    private String  shortDesc = "";
    private String description = "";
    private String videoUrl = "";
    private String thumbnailUrl = "";

    public StepItem() {}

    public void setId(int i) {
        id = i;
    }

    public int getId() {
        return id;
    }

    public void setShortDesc(String sd) {
        shortDesc = sd;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setDescription(String d) {
        description = d;
    }

    public String getDescription() {
        return description;
    }

    public void setVideoUrl(String v) {
        videoUrl = v;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setThumbnailUrl(String t) {
        thumbnailUrl = t;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /** BELOW is Parcel Implementation Code; pass an object into another Activity easily **/
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(id);
        out.writeString(shortDesc);
        out.writeString(description);
        out.writeString(videoUrl);
        out.writeString(thumbnailUrl);
    }

    public static final Creator<StepItem> CREATOR
            = new Creator<StepItem>() {
        public StepItem createFromParcel(Parcel in) {
            return new StepItem(in);
        }

        public StepItem[] newArray(int size) {
            return new StepItem[size];
        }
    };

    private StepItem(Parcel in)
    {
        id = in.readInt();
        shortDesc = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }
}
