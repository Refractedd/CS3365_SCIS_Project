public class TouchScreenInterface{
    public String[] requestProductInfo(int productID) {
        CheckoutControl checkoutObj = new CheckoutControl();
        return checkoutObj.productInfo(productID);
    }
}
