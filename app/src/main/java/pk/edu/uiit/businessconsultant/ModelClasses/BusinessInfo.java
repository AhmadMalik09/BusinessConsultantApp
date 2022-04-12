package pk.edu.uiit.businessconsultant.ModelClasses;

public class BusinessInfo {
    String Question,Answers;



    public BusinessInfo(String question, String answers) {
        Question = question;
        Answers = answers;


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

}
