package tests.deserialization;

public class ResponceServer {

    private boolean ok;
    private String message;

    public String getMessage() {
        return message;
    }

    public boolean getOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
