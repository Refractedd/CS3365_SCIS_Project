public class CheckoutControl {
    int productID = 0;
    public String[] foundProductInfo;
    CustomerDisplayInterface sendObj1 = new CustomerDisplayInterface();
    ReceiptPrinterInterface sendObj2 = new ReceiptPrinterInterface();

    public String[] productInfo(int productID) {
        ProductInventory inventoryObj = new ProductInventory();
        foundProductInfo = inventoryObj.productInfo(productID);
        sendObj1.returnProductInfoCustomer(foundProductInfo);
        sendObj2.returnProductInfoReceipt(foundProductInfo);
        return foundProductInfo;
    }
}
