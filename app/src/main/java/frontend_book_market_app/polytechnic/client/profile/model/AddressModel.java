package frontend_book_market_app.polytechnic.client.profile.model;

public class AddressModel {
    private int address_id;
    private String name;
    private String phone;
    private String address;
    private String address_type;
    private boolean is_default;
    // New fields


    public AddressModel(int address_id, String name, String phone, String address, String address_type, boolean is_default) {
        this.address_id = address_id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.address_type = address_type;
        this.is_default = is_default;

    }

    public AddressModel(String name, String phone, String address, String address_type, boolean is_default) {
        this.address_id = 0;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.address_type = address_type;
        this.is_default = is_default;
    }

    public static AddressModel fromUpdateAddressModel(UpdateAddressModel updateAddressModel) {
        return new AddressModel(
                updateAddressModel.getId(),
                updateAddressModel.getName(),
                updateAddressModel.getPhone(),
                updateAddressModel.getAddress(),
                updateAddressModel.getAddress_type(),
                updateAddressModel.isIs_default()
        );
    }

    @Override
    public String toString() {
        return "AddressModel{" +
                "address_id=" + address_id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", address_type='" + address_type + '\'' +
                ", is_default=" + is_default +
                '}';
    }

    public AddressModel() {
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

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
