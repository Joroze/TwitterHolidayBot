package json;

/**
 * Created by jorda on 5/15/2016.
 */
public class CheckidayAPI {

    private String error;

    private String lastUpdate;

    private Holidays[] holidays;

    private String number;

    private String date;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Holidays[] getHolidays() {
        return holidays;
    }

    public void setHolidays(Holidays[] holidays) {
        this.holidays = holidays;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ClassPojo [error = " + error + ", lastUpdate = " + lastUpdate + ", holidays = " + holidays + ", number = " + number + ", date = " + date + "]";
    }
}
