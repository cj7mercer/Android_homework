package weatherrepoter.example.com.weatherrepoter;

/**
 * Created by ASUS on 2021/12/4.
 */

public class Province {
    private String city_name;
    private int id;

    public Province(String city_name, int id) {
        this.city_name = city_name;
        this.id = id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
