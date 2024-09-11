package frontend_book_market_app.polytechnic.client.profile.model;

public class UpdateAddressModel {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String address_type;
    private boolean is_default;


    public UpdateAddressModel(int id, String name, String phone, String address, String address_type, boolean is_default) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.address_type = address_type;
        this.is_default = is_default;
    }
    public static UpdateAddressModel fromAddressModel(AddressModel addressModel) {
        return new UpdateAddressModel(
                addressModel.getAddress_id(),
                addressModel.getName(),
                addressModel.getPhone(),
                addressModel.getAddress(),
                addressModel.getAddress_type(),
                addressModel.isIs_default()
        );
    }
    // Default constructor
    public UpdateAddressModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }
}
