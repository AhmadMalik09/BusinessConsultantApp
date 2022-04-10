package pk.edu.uiit.businessconsultant.ModelClasses;

public class BusinessInfo {
    String Question,Answers,UID;



    public BusinessInfo(String uid,String question, String answers) {
        Question = question;
        Answers = answers;
        UID=uid;

    }

    public BusinessInfo() {
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswers() {
        return Answers;
    }

    public void setAnswers(String answers) {
        Answers = answers;
    }
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
