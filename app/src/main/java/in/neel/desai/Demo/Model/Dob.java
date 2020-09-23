
package in.neel.desai.Demo.Model;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dob {
    @ColumnInfo(name = "DobDate")
    @SerializedName("date")
    @Expose
    private String date;
    @ColumnInfo(name = "DobAge")
    @SerializedName("age")
    @Expose
    private Integer age;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
