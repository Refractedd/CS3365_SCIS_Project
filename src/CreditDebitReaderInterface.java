public class CreditDebitReaderInterface {
    boolean loyalMemberEntry(String phoneNum, String memberPIN) {
        CheckoutControl loyalMemberObj = new CheckoutControl();
        return loyalMemberObj.checkLoyalMember(phoneNum, memberPIN);
    }
}
