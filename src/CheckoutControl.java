public class CheckoutControl {
    public String[] foundProductInfo;

    ProductInventory inventoryObj = new ProductInventory();
    Order updateLoyaltyObj = new Order();
    LoyaltyMemberAccount loyalMemberObj = new LoyaltyMemberAccount();

    CustomerDisplayInterface sendObj1 = new CustomerDisplayInterface();
    ReceiptPrinterInterface sendObj2 = new ReceiptPrinterInterface();
    Order sendObj3 = new Order();

    public String[] productInfo(int productID, boolean loyaltyStatus, String currentProducts) {
        foundProductInfo = inventoryObj.productInfo(productID);
        sendObj1.returnProductInfoCustomer(foundProductInfo);
        sendObj2.returnProductInfoReceipt(foundProductInfo);
        sendObj3.returnProductInfoOrder(productID, loyaltyStatus, currentProducts);
        /*if (loyaltyStatus == false) {
            int size = foundProductInfo.length;
            String[] newFoundProductInfo = new String[size + 1];
            newFoundProductInfo[0] = foundProductInfo[0];
            newFoundProductInfo[1] = foundProductInfo[1];
            newFoundProductInfo[2] = foundProductInfo[2];
            newFoundProductInfo[3] = "NotChecked";
            return newFoundProductInfo;
        }*/
        return foundProductInfo;
    }

    boolean checkLoyalMember(String phoneNum, String memberPIN) {
        if (phoneNum.equals("Cancel")) {
            updateLoyaltyObj.updatedLoyaltyChecked(false);
            return false;
        }
        else {
            boolean tempCheck = loyalMemberObj.checkLoyalMember(phoneNum, memberPIN);
            updateLoyaltyObj.updatedLoyaltyChecked(tempCheck);
            return tempCheck;
        }
    }
}
