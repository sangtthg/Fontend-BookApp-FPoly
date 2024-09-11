package frontend_book_market_app.polytechnic.client.profile.model;

import java.util.List;

public class Province {
    private String Id;
    private String Name;
    private List<District> Districts;

    public Province(String id, String name, List<District> districts) {
        Id = id;
        Name = name;
        Districts = districts;
    }
    public Province() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<District> getDistricts() {
        return Districts;
    }

    public void setDistricts(List<District> districts) {
        Districts = districts;
    }

}
