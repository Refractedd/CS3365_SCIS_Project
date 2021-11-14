public class TouchScreenInterface {
    public int productID = 0;
    public int requestProductID(int productID) {
        CheckoutControl checkoutObj = new CheckoutControl();
        this.productID = checkoutObj.productID(productID);
        return this.productID;
    };
    public static void main(String args[]) {
        TouchScreenInterface obj = new TouchScreenInterface();
        System.out.println(obj.requestProductID(7));
    }
}
