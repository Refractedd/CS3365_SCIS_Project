public class CheckoutControl {
    int productID = 0;

    public String[] productInfo(int productID) {
        ProductInventory inventoryObj = new ProductInventory();
        return inventoryObj.productInfo(productID);
    }
}
