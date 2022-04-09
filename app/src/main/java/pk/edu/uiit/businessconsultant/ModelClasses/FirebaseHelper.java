package pk.edu.uiit.businessconsultant.ModelClasses;

public class FirebaseHelper {
    String name;
    String email;
    String uid;
    String profileImage;
    String Field;
    String Password;
    String Phone;
    String CNIC;
    String Qualification;
    String Specification;
    String accountType;
    public FirebaseHelper() {

    }



    public FirebaseHelper(String uid , String Field, String name, String email, String Phone, String Password, String CNIC , String Qualificaion , String Specification, String profileImage, String accountType) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.Field=Field;
        this.Password=Password;
        this.Phone=Phone;
        this.CNIC=CNIC;
        this.Qualification=Qualificaion;
        this.Specification=Specification;
        this.accountType=accountType;
        this.profileImage = profileImage;
    }
    public FirebaseHelper(String uid , String Field, String name,String email){
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.Field=Field;
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
        return  uid;
    }

    public void setUid(String  uid) {
        this. uid =  uid;
    }

    public String getimageURL() {
        return profileImage;
    }

    public void setimageURL(String imageURL) {
        this.profileImage = imageURL;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
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

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
