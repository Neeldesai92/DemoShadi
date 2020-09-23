
package in.neel.desai.Demo.Model;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Picture {
    @ColumnInfo(name = "large")
    @SerializedName("large")
    @Expose
    private String large;
    @ColumnInfo(name = "medium")
    @SerializedName("medium")
    @Expose
    private String medium;
    @ColumnInfo(name = "thumbnail")
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
