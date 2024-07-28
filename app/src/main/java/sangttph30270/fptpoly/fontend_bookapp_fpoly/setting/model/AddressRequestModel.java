package sangttph30270.fptpoly.fontend_bookapp_fpoly.setting.model;

public class AddressRequestModel {
    private String name;
    private String phone;
    private String address;
    private String address_type;
    private boolean is_default;

    public AddressRequestModel(String name, String phone, String address, String address_type, boolean is_default) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.address_type = address_type;
        this.is_default = is_default;
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

    public String getAddressType() {
        return address_type;
    }

    public void setAddressType(String address_type) {
        this.address_type = address_type;
    }

    public boolean isDefault() {
        return is_default;
    }

    public void setDefault(boolean is_default) {
        this.is_default = is_default;
    }
}
