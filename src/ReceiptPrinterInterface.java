public class ReceiptPrinterInterface {
    public String productInfoReceipt = "";
    public void returnProductInfoReceipt(String[] foundInfoReceipt) {
        productInfoReceipt += foundInfoReceipt[0] + " " + foundInfoReceipt[1] + "\n";
        System.out.println("Receipt Printer:" + productInfoReceipt);
    }
}
