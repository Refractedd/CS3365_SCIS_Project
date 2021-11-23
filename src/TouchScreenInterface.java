public class TouchScreenInterface{
    CheckoutControl checkoutObj = new CheckoutControl();
    String[] requestProductInfo(int productID, boolean loyaltyStatus, String currentProducts) {
        return checkoutObj.productInfo(productID, loyaltyStatus, currentProducts);
    }

    double[] calculateTotal() {
        return checkoutObj.calculateTotal();
    }
}
