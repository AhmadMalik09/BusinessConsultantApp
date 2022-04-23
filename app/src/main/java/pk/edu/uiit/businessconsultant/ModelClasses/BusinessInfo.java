package pk.edu.uiit.businessconsultant.ModelClasses;

public class BusinessInfo {
    String Question,Answers;
    String Field;




    public BusinessInfo(String question, String answers,String Field) {
        this.Question = question;
        this.Answers = answers;
        this.Field=Field;



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

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }
}
