public class CustomerDisplayInterface {
    public String productInfoCustomer = "";
    public void returnProductInfoCustomer(String[] foundInfoCustomer) {
        productInfoCustomer += foundInfoCustomer[0] + " " + foundInfoCustomer[1] + "\n";
        System.out.println("Customer Display:" + productInfoCustomer);
    }
}
