package pk.edu.uiit.businessconsultant.ModelClasses;

public class Portfolio {
    String UID ,EducationalInstitute,Awards,Working_org,Experience,AreaOfInterest,Languages,Goals,Nationality,No_Of_Awards;

    public Portfolio() {
    }

    public Portfolio(String uid,String educationalInstitute, String awards, String working_org, String experience, String areaOfInterest, String languages, String goals, String nationality,String No_Of_Award) {
        UID= uid;
        EducationalInstitute = educationalInstitute;
        Awards = awards;
        Working_org = working_org;
        Experience = experience;
        AreaOfInterest = areaOfInterest;
        Languages = languages;
        Goals = goals;
        Nationality = nationality;
        No_Of_Awards=No_Of_Award;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEducationalInstitute() {
        return EducationalInstitute;
    }
    public void setEducationalInstitute(String educationalInstitute) {
        EducationalInstitute = educationalInstitute;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public String getWorking_org() {
        return Working_org;
    }

    public void setWorking_org(String working_org) {
        Working_org = working_org;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getAreaOfInterest() {
        return AreaOfInterest;
    }

    public void setAreaOfInterest(String areaOfInterest) {
        AreaOfInterest = areaOfInterest;
    }

    public String getLanguages() {
        return Languages;
    }

    public void setLanguages(String languages) {
        Languages = languages;
    }

    public String getGoals() {
        return Goals;
    }

    public void setGoals(String goals) {
        Goals = goals;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getNo_Of_Awards() {
        return No_Of_Awards;
    }

    public void setNo_Of_Awards(String no_Of_Awards) {
        No_Of_Awards = no_Of_Awards;
    }
}
