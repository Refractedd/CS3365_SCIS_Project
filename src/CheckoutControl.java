public class CheckoutControl {
    int productID = 0;
    public String[] foundProductInfo;
    ReceiptPrinterInterface sendObj2 = new ReceiptPrinterInterface();
    CustomerDisplayInterface sendObj1 = new CustomerDisplayInterface();

    public String[] productInfo(int productID) {
        ProductInventory inventoryObj = new ProductInventory();
        foundProductInfo = inventoryObj.productInfo(productID);
        if (foundProductInfo[2].equals("True")){
            String[] bulk = {"Bulk Item. Please Weight Item."};
            return bulk;
        }
        else {
            sendObj1.returnProductInfoCustomer(foundProductInfo);
            sendObj2.returnProductInfoReceipt(foundProductInfo);
            return foundProductInfo;
        }
    }


}
