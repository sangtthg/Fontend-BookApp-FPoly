package frontend_book_market_app.polytechnic.client.profile.model;

import java.util.List;

public class District {
    private String Id;
    private String Name;
    private List<Ward> Wards;

    public District() {
    }

    public District(String id, String name, List<Ward> wards) {
        Id = id;
        Name = name;
        Wards = wards;
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

    public List<Ward> getWards() {
        return Wards;
    }

    public void setWards(List<Ward> wards) {
        Wards = wards;
    }
}
