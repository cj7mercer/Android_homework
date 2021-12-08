package diary.example.com.diary;

/**
 * Created by ASUS on 2021/11/16.
 */

public class Diary {
    private int id;
    private int month;
    private int day;
    private String name;

    Diary(){
    }

    public Diary(int id, int month, int day, String name) {
        this.id = id;
        this.month = month;
        this.day = day;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setName(String name) {
        this.name = name;
    }
}
