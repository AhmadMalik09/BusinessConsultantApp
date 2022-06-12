package pk.edu.uiit.businessconsultant.ModelClasses;

public class Users {
    public String name;
    String email;
    String uid;
    public String profileImage;
    String Password;
    String confirmPassword;
    String Phone;
    String accountType;

    public Users() {
    }

    public Users(String uid,String name, String email,String password,String confirmPassword,String phone, String accountType,String profileImage) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.profileImage = profileImage;
        Password = password;
        this.confirmPassword=confirmPassword;
        Phone = phone;
        this.accountType = accountType;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
