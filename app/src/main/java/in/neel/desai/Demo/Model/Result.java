
package in.neel.desai.Demo.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Result {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "PersonId")
    @SerializedName("PersonId")
    @Expose
    int PersonId;

    @ColumnInfo(name = "gender")
    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("name")
    @Expose
    @Embedded
    private Name name;

    @SerializedName("location")
    @Expose
    @Embedded
    private Location location;
    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    private String email;
    @SerializedName("login")
    @Expose
    @Embedded
    private Login login;
    @SerializedName("dob")
    @Expose
    @Embedded
    private Dob dob;
    @SerializedName("registered")
    @Expose
    @Embedded
    private Registered registered;
    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    @Expose
    private String phone;
    @ColumnInfo(name = "cell")
    @SerializedName("cell")
    @Expose
    private String cell;

    @Expose

    @Embedded
    @NonNull
    private Id id;
    @SerializedName("picture")
    @Expose
    @Embedded
    private Picture picture;
    @SerializedName("nat")
    @Expose
    private String nat;

    @ColumnInfo(name = "RequestFlg")
    @SerializedName("RequestFlg")
    @Expose
    private String RequestFlg = "";

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Dob getDob() {
        return dob;
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public String getRequestFlg() {
        return RequestFlg;
    }

    public void setRequestFlg(String requestFlg) {
        RequestFlg = requestFlg;
    }

    public int getPersonId() {
        return PersonId;
    }

    public void setPersonId(int personId) {
        PersonId = personId;
    }
}
