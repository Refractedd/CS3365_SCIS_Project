public class InventoryControl {
    String timerInput() {
        ProductInventory checkInventoryObj = new ProductInventory();
        String productsToOrder = checkInventoryObj.readInventoryMessages();
        if (productsToOrder.equals("") != true) {
            InventoryOrder createInventoryOrderObj = new InventoryOrder();
            SupplierInterface sendInventoryOrder = new SupplierInterface();
            createInventoryOrderObj.createOrder(productsToOrder);
            sendInventoryOrder.sendOrder(productsToOrder);
        }
        return productsToOrder;
    }
}
