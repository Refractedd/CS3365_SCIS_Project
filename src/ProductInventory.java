public class ProductInventory extends CheckoutControl {
    private double price;
    public int amount;
    public int id;

    public int ProductID(){
        this.price = 0.00;
        amount = 1;
        this.id = -1;
        return amount;
    }
}
