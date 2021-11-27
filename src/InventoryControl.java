public class InventoryControl {
    void timerInput() {
        ProductInventory checkInventoryObj = new ProductInventory();
        String productsToOrder = checkInventoryObj.readInventoryMessages();
        if (productsToOrder.equals("") != true) {
            InventoryOrder createInventoryOrderObj = new InventoryOrder();
            SupplierInterface sendInventoryOrder = new SupplierInterface();
            createInventoryOrderObj.createAndSendOrder(productsToOrder);
            sendInventoryOrder.sendOrder(productsToOrder);
        }
    }
}
