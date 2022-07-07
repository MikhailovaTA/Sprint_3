package tests.serialization;

public class DateAcceptOrder {
    private int id;
    private int courierId;

    public DateAcceptOrder(int id, int courierId) {
        this.id = id;
        this.courierId = courierId;
    }

    public DateAcceptOrder() {}

    public int getId() {
        return id;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

}
