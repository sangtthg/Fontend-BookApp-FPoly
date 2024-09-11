package frontend_book_market_app.polytechnic.client.profile.model;

public class Ward {
    private String Id;
    private String Name;
    private String Level;

    public Ward() {
    }

    public Ward(String id, String name, String level) {
        Id = id;
        Name = name;
        Level = level;
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

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
}
