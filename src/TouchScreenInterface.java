public class TouchScreenInterface{
    CheckoutControl checkoutObj = new CheckoutControl();
    PaymentControl paymentObj = new PaymentControl();
    String[] requestProductInfo(int productID, boolean loyaltyStatus, String currentProducts) {
        return checkoutObj.productInfo(productID, loyaltyStatus, currentProducts);
    }

    double[] calculateTotal(String[] memberAccountInfo, boolean loyaltyMember, double subTotal) {
        return checkoutObj.calculateTotal(memberAccountInfo, loyaltyMember, subTotal);
    }

    void returnPaymentTypeAmount(String paymentAmount, String paymentType) {
        paymentObj.returnPaymentTypeAmount(paymentAmount, paymentType);
    }
}
