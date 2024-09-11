package frontend_book_market_app.polytechnic.client.profile.network;

import frontend_book_market_app.polytechnic.client.profile.model.AddressModel;

public class SharedService {
    private static SharedService instance;
    private AddressModel defaultAddress;

    private SharedService() {}

    public static synchronized SharedService getInstance() {
        if (instance == null) {
            instance = new SharedService();
        }
        return instance;
    }

    public AddressModel getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(AddressModel defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
