public class CheckoutControl {
    private String[] foundProductInfo;
    private double[] calculatedOrderTotals;
    private boolean loyaltyMemberStatus;

    ProductInventory inventoryObj = new ProductInventory();
    Order updateLoyaltyObj = new Order();
    Order totalObj = new Order();
    LoyaltyMemberAccount loyalMemberObj = new LoyaltyMemberAccount();

    CustomerDisplayInterface sendObj1 = new CustomerDisplayInterface();
    ReceiptPrinterInterface sendObj2 = new ReceiptPrinterInterface();
    Order sendObj3 = new Order();

    public String[] productInfo(int productID, boolean loyaltyStatus, String currentProducts) {
        foundProductInfo = inventoryObj.productInfo(productID);
        sendObj1.returnProductInfoCustomer(foundProductInfo);
        sendObj2.returnProductInfoReceipt(foundProductInfo);
        sendObj3.returnProductInfoOrder(productID, loyaltyStatus, currentProducts);
        return foundProductInfo;
    }

    boolean checkLoyalMember(String phoneNum, String memberPIN) {
        if (phoneNum.equals("Cancel")) {
            updateLoyaltyObj.updatedLoyaltyChecked(false);
            return false;
        }
        else {
            loyaltyMemberStatus = loyalMemberObj.checkLoyalMember(phoneNum, memberPIN);
            updateLoyaltyObj.updatedLoyaltyChecked(loyaltyMemberStatus);
            return loyaltyMemberStatus;
        }
    }

    double[] calculateTotal(String[] memberAccountInfo, boolean loyalMember) {
        calculatedOrderTotals = totalObj.calculateTotalPrice();
        sendObj1.returnOrderTotal(calculatedOrderTotals);
        sendObj2.returnOrderTotal(calculatedOrderTotals);
        sendObj3.updateOrderTotal(calculatedOrderTotals);
        loyalMemberObj.updateLoyaltyCreditPoints(calculatedOrderTotals[1], loyalMember, memberAccountInfo);
        return calculatedOrderTotals;
    }
}
