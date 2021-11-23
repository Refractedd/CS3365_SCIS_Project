public class PaymentControl {
    Order paymentObj = new Order();
    ReceiptPrinterInterface sendObjReceipt = new ReceiptPrinterInterface();
    CustomerDisplayInterface sendObjCustomer = new CustomerDisplayInterface();
    void returnPaymentTypeAmount(String paymentAmount, String paymentType) {
        paymentObj.updatePaymentTypeAmount(paymentAmount, paymentType);
    }

    double[] creditDebitEntry(String cardNumber, String expDate, int CVV, int billingZipCode) {
        BankInterface authorizationObj = new BankInterface();
        sendObjReceipt.returnCardNumber(cardNumber);
        int authNum = authorizationObj.authorizeCreditDebit(cardNumber, expDate, CVV, billingZipCode);
        if (authNum != -1) {
            sendObjReceipt.returnAuthNumber(authNum);
            double changeAmount = paymentObj.updateAuthorizationNumCalculateChange(authNum);
            sendObjReceipt.returnChangeAmountAndPrint(changeAmount);
            sendObjCustomer.returnChangeAmount(changeAmount);
            double[] changeAmountAuthNum = {changeAmount, Double.valueOf(authNum)};
            return changeAmountAuthNum;
        }
        double[] denied = {-1.0,-1.0};
        return denied;
    }

    double cashOrCheckEntry() {
        return paymentObj.cashOrCheckChange();
    }
}
