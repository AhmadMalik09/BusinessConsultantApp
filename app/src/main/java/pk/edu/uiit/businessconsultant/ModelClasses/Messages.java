package pk.edu.uiit.businessconsultant.ModelClasses;

public class Messages {
    String message;
    String senderID;

    long timeStamp;

    public Messages() {
    }

    public String getMessage() {
        return message;
    }

    public Messages(String message, String senderID,long timeStamp) {
        this.message = message;
        this.senderID = senderID;
        this.timeStamp = timeStamp;

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }


}
