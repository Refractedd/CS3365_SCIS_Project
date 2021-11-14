public class CheckoutControl {
    int productID = 0;

    public int productID(int productID) {
        ProductInventory inventoryObj = new ProductInventory();
        return inventoryObj.ProductID();
    }
}
