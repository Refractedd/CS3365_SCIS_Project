public class ProductInventory {
    private double price;
    private int amount;
    public int id;

    public ProductInventory(){
        this.price = 0.00;
        this.amount = 0;
        this.id = -1;
    }
    public ProductInventory(double p, int a, int id){
        this.price = p;
        this.amount = a;
        this.id = id;
    }
}
