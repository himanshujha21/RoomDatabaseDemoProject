package Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "userdata")
public class UserData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "profilePic")
    private String profilePic;

    @ColumnInfo(name = "firstname")
    private String firstname;

    @ColumnInfo(name = "lastname")
    private String lastname;

    @ColumnInfo(name = "email")
    private String email;


    @ColumnInfo(name = "mobileNumber")
    private String mobileNumber;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "alternateMobile")
    private String alternateMobile;

    @ColumnInfo(name = "highest_qualification")
    private String highestQualification;

    @ColumnInfo(name = "college")
    private String college;


    @ColumnInfo(name = "university")
    private String university;

    @ColumnInfo(name = "percentage")
    private String percentage;

    public UserData() {

    }

   /* public UserData(int uid, String profilePic, String firstname, String lastname, String email, String mobileNumber, String address, String alternateMobile, String highestQualification, String college, String university, String percentage) {
        this.uid = uid;
        this.profilePic = profilePic;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.alternateMobile = alternateMobile;
        this.highestQualification = highestQualification;
        this.college = college;
        this.university = university;
        this.percentage = percentage;
    }
*/
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}

