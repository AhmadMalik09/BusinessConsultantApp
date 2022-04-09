package pk.edu.uiit.businessconsultant.ModelClasses;

public class Messages {
    String message;
    public  static String senderID;
    String receiverID;
    long timeStamp;

    public Messages() {
    }

    public String getMessage() {
        return message;
    }

    public Messages(String message, String senderID, String receiverID, long timeStamp) {
        this.message = message;
        this.senderID = senderID;
        this.timeStamp = timeStamp;
        this.receiverID=receiverID;
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

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }
}
