package frontend_book_market_app.polytechnic.client.utils;

public class CurrencyFormatter {

    public static String toVND(String price) {
        try {
            double priceValue = Double.parseDouble(price);
            String formattedPrice = String.format("%,.0f", priceValue);
            return formattedPrice + " VNĐ";
        } catch (NumberFormatException e) {
            return price;
        }
    }

    public static String toVNDWithSymbol(String price) {
        try {
            double priceValue = Double.parseDouble(price);
            String formattedPrice = String.format("%,.0f", priceValue);
            return "₫" + formattedPrice;
        } catch (NumberFormatException e) {
            return price;
        }
    }
}
