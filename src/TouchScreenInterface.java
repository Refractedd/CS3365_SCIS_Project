public class TouchScreenInterface{
    public String[] requestProductInfo(int productID, boolean loyaltyStatus, String currentProducts) {
        CheckoutControl checkoutObj = new CheckoutControl();
        return checkoutObj.productInfo(productID, loyaltyStatus, currentProducts);
    }
}
