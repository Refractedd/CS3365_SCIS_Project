public class CheckoutControl {
    int productID = 0;
    String[] foundProductInfo;
    public String[] productInfo(int productID) {
        ProductInventory inventoryObj = new ProductInventory();
        foundProductInfo = inventoryObj.productInfo(productID);
        System.out.print(foundProductInfo[2]);
        if (foundProductInfo[2].equals("True")){
            String[] bulk = {"Bulk Item. Please Weight Item."};
            return bulk;
        }
        else {
            return foundProductInfo;
        }
    }
}
