public class CreditDebitReaderInterface {
    boolean loyalMemberEntry(String phoneNum, String memberPIN) {
        CheckoutControl loyalMemberObj = new CheckoutControl();
        return loyalMemberObj.checkLoyalMember(phoneNum, memberPIN);
    }
    
    double[] creditDebitEntry(String cardNumber, String expDate, int CVV, int billingZipCode) {
        PaymentControl creditDebitObj = new PaymentControl();
        return creditDebitObj.creditDebitEntry(cardNumber, expDate, CVV, billingZipCode);
    }

    double cashOrCheckEntry() {
        PaymentControl cashOrCheckObj = new PaymentControl();
        return cashOrCheckObj.cashOrCheckEntry();
    }
}
