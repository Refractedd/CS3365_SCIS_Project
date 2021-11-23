import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.text.DecimalFormat;

public class Main extends Application{
    DecimalFormat df = new DecimalFormat("#.##");
    private String cardNum;
    private double subTotal = 0;
    private double bulkWeight;
    private double calculatedChangeCashCheck;
    private double[] calculatedChangeAuthNum;
    private double[] totalValues;
    private String productInfo = "";
    private String orderProductIDs = "";
    private String memberPhoneNum;
    private String loyalMemberPIN;
    private boolean loyalMemberChecked = false;
    private boolean loyalMemberStatus = false;
    private TextField productIDEntry;
    private Label productInfoDisplayCashier;
    private Label productInfoDisplayCustomer;
    private TextField productWeightEntry;
    private String[] foundProductInfo;
    private Label loyalMemberResultCashier;
    private Label loyalMemberResultCustomer;
    private TextField phoneNumEntry;
    private TextField memberPINEntry;
    private Label subTotalCashier;
    private Label taxCashier;
    private Label finalTotalCashier;
    private Label changeAmountCashier;
    private Label subTotalCustomer;
    private Label taxCustomer;
    private Label finalTotalCustomer;
    private Label changeAmountCustomer;
    private TextField paymentAmountEntry;
    private TextField cardNumberEntry;
    private TextField expDateEntry;
    private TextField cvvEntry;
    private TextField zipCodeEntry;
    private Label openTill;
    private Button closeTillButton;
    private Label receiptProductInfo;
    private Label receiptCardNumber;
    private Label receiptAuthNumber;
    private Label receiptTotalInformation;
    private Label cardDenied;
    Button cancelOrderButton;
    Button totalButton;
    Button idButton;
    HBox hboxCashierMain;
    VBox vboxCashierMain;
    VBox vboxCashierScale;
    VBox vboxCashierTotal;
    VBox vboxCashierEntry;
    VBox vboxCustomerMain;
    VBox vboxCustomerLoyalMember;
    VBox vboxCustomerTotal;
    VBox vboxCashierPaymentType;
    VBox vboxCustomerCreditDebitPayment;
    VBox vboxEndCheckout;
    VBox vboxCustomerReceipt;

    TouchScreenInterface touchscreenObj = new TouchScreenInterface();
    CreditDebitReaderInterface creditDebitReaderObj = new CreditDebitReaderInterface();
    

    public void start(Stage primaryStage) {
        Label promptProductID = new Label("Product ID: ");
        productIDEntry = new TextField();
        idButton = new Button("ITEM-ID");
        idButton.setOnAction(new itemIDButtonHandler());

        Label promptProductScale = new Label("Product Weight (lbs): ");
        productWeightEntry = new TextField();
        Button scaleButton = new Button("SCALE");
        scaleButton.setOnAction(new scaleButtonHandler());
        Label scaleItem = new Label("Bulk Item. Please Weight Item.");

        Label loyalMember = new Label("Please Enter Loyalty Membership Information.");
        Label phoneNum = new Label("Phone Number: ");
        Label memberPIN = new Label("Member PIN: ");
        phoneNumEntry = new TextField();
        memberPINEntry = new TextField();
        Button loyalMemberSubmit = new Button("SUBMIT");
        loyalMemberSubmit.setOnAction(new loyalMemberSubmitButtonHandler());
        Button loyalMemberCancel = new Button("CANCEL");
        loyalMemberCancel.setOnAction(new loyalMemberCancelButtonHandler());
        loyalMemberResultCashier = new Label();
        loyalMemberResultCashier.setVisible(false);
        loyalMemberResultCustomer = new Label();
        loyalMemberResultCustomer.setVisible(false);
        
        productInfoDisplayCashier = new Label();
        productInfoDisplayCustomer = new Label();

        totalButton = new Button("TOTAL");
        totalButton.setOnAction(new calculateTotalButtonHandler());
        totalButton.setVisible(false);

        subTotalCashier = new Label();
        taxCashier = new Label();
        finalTotalCashier = new Label();
        changeAmountCashier = new Label();
        changeAmountCashier.setVisible(false);

        subTotalCustomer = new Label();
        taxCustomer = new Label();
        finalTotalCustomer = new Label();
        changeAmountCustomer = new Label();
        changeAmountCustomer.setVisible(false);

        hboxCashierMain = new HBox(10, promptProductID, productIDEntry);
        HBox hboxCashierScale = new HBox(10, promptProductScale, productWeightEntry);
        HBox hboxCustomerPhoneNum = new HBox(10, phoneNum, phoneNumEntry);
        HBox hboxCustomerMemberPIN = new HBox(10, memberPIN, memberPINEntry);
        HBox hboxCustomerButtons = new HBox(10, loyalMemberSubmit, loyalMemberCancel);

        vboxCashierMain = new VBox(10, loyalMemberResultCashier, productInfoDisplayCashier);
        vboxCashierEntry = new VBox(10, hboxCashierMain, idButton, totalButton);
        vboxCashierScale = new VBox(10, hboxCashierScale, scaleButton, scaleItem);
        vboxCashierTotal = new VBox(10, subTotalCashier, taxCashier, finalTotalCashier, changeAmountCashier);
        
        vboxCustomerMain = new VBox(10, loyalMemberResultCustomer, productInfoDisplayCustomer);
        vboxCustomerLoyalMember = new VBox(10, loyalMember, hboxCustomerPhoneNum, hboxCustomerMemberPIN, hboxCustomerButtons);
        vboxCustomerTotal = new VBox(10, subTotalCustomer, taxCustomer, finalTotalCustomer, changeAmountCustomer);

        vboxCashierScale.setVisible(false);
        vboxCashierTotal.setVisible(false);
        vboxCustomerLoyalMember.setVisible(false);
        vboxCustomerTotal.setVisible(false);

        hboxCashierMain.setAlignment(Pos.CENTER);
        hboxCashierScale.setAlignment(Pos.CENTER);
        hboxCustomerPhoneNum.setAlignment(Pos.CENTER);
        hboxCustomerMemberPIN.setAlignment(Pos.CENTER);
        hboxCustomerButtons.setAlignment(Pos.CENTER);

        vboxCashierMain.setAlignment(Pos.TOP_CENTER);
        vboxCashierMain.setPadding(new Insets(50,20,50,20));
        vboxCashierEntry.setAlignment(Pos.CENTER);
        vboxCashierEntry.setPadding(new Insets(50,20,50,20));
        vboxCashierScale.setAlignment(Pos.CENTER);
        vboxCashierScale.setPadding(new Insets(50,20,50,20));
        vboxCashierTotal.setAlignment(Pos.BOTTOM_CENTER);
        vboxCashierTotal.setPadding(new Insets(50,20,50,20));
        vboxCustomerMain.setAlignment(Pos.TOP_CENTER);
        vboxCustomerMain.setPadding(new Insets(50,20,50,20));
        vboxCustomerLoyalMember.setAlignment(Pos.CENTER);
        vboxCustomerLoyalMember.setPadding(new Insets(50,20,50,20));
        vboxCustomerTotal.setAlignment(Pos.BOTTOM_CENTER);
        vboxCustomerTotal.setPadding(new Insets(50,20,50,20));

        Label promptPaymentAmount = new Label("Payment Amount: ");
        paymentAmountEntry = new TextField();
        Button creditDebitPaymentButton = new Button("CREDIT/DEBIT");
        creditDebitPaymentButton.setOnAction(new creditDebitButtonHandler());
        Button cashPaymentButton = new Button("CASH");
        cashPaymentButton.setOnAction(new cashButtonHandler());
        Button checkPaymentButton = new Button("CHECK");
        checkPaymentButton.setOnAction(new checkButtonHandler());
        cardDenied = new Label("Card Denied. Try Again Or Cancel.");
        cancelOrderButton = new Button("CANCEL ORDER");
        cancelOrderButton.setOnAction(new cancelOrderButtonHandler());
        cardDenied.setVisible(false);
        cancelOrderButton.setVisible(false);

        Label promptCardNumber = new Label("Card Number: ");
        cardNumberEntry = new TextField();
        Label promptExpDate = new Label("Expiration Date: ");
        expDateEntry = new TextField();
        Label promptCVV = new Label("CVV: ");
        cvvEntry = new TextField();
        Label promptZipCode = new Label("Zip Code: ");
        zipCodeEntry = new TextField();
        Button submitCardInfoButton = new Button("SUBMIT");
        submitCardInfoButton.setOnAction(new submitCardInfoButtonHandler());

        openTill = new Label("Till is Open. ");
        openTill.setVisible(false);

        closeTillButton = new Button("Order Complete. Close Till.");
        closeTillButton.setOnAction(new closeTillButtonHandler());

        Label receipt = new Label("Order Receipt");
        receiptProductInfo = new Label();
        receiptAuthNumber = new Label();
        receiptCardNumber = new Label();
        receiptTotalInformation = new Label();
        
        HBox hboxPaymentAmount = new HBox(10, promptPaymentAmount, paymentAmountEntry);
        HBox hboxCardNum = new HBox(10, promptCardNumber, cardNumberEntry);
        HBox hboxExpDate = new HBox(10, promptExpDate, expDateEntry);
        HBox hboxCVV = new HBox(10, promptCVV, cvvEntry);
        HBox hboxZipCode = new HBox(10, promptZipCode, zipCodeEntry);

        vboxCashierPaymentType = new VBox(10, cardDenied, openTill, hboxPaymentAmount, creditDebitPaymentButton, cashPaymentButton, checkPaymentButton, cancelOrderButton);
        vboxCustomerCreditDebitPayment = new VBox(10, hboxCardNum, hboxExpDate, hboxCVV, hboxZipCode, submitCardInfoButton);
        vboxEndCheckout = new VBox(10, closeTillButton);
        vboxCustomerReceipt = new VBox(10, receipt, receiptProductInfo, receiptAuthNumber, receiptCardNumber, receiptTotalInformation);

        hboxPaymentAmount.setAlignment(Pos.CENTER);
        hboxCardNum.setAlignment(Pos.CENTER);
        hboxExpDate.setAlignment(Pos.CENTER);
        hboxCVV.setAlignment(Pos.CENTER);
        hboxZipCode.setAlignment(Pos.CENTER);

        vboxCashierPaymentType.setAlignment(Pos.CENTER);
        vboxCashierPaymentType.setPadding(new Insets(50,20,50,20));
        vboxCashierPaymentType.setVisible(false);

        vboxCustomerCreditDebitPayment.setAlignment(Pos.CENTER);
        vboxCustomerCreditDebitPayment.setPadding(new Insets(50,20,50,20));
        vboxCustomerCreditDebitPayment.setVisible(false);

        vboxEndCheckout.setAlignment(Pos.CENTER);
        vboxEndCheckout.setPadding(new Insets(50,20,50,20));
        vboxEndCheckout.setVisible(false);

        vboxCustomerReceipt.setAlignment(Pos.CENTER);
        vboxCustomerReceipt.setPadding(new Insets(50,20,50,20));
        vboxCustomerReceipt.setVisible(false);

        SplitPane splitPane = new SplitPane();
        StackPane cashierDisplay = new StackPane(vboxCashierMain, vboxCashierEntry, vboxCashierScale, vboxCashierTotal, vboxCashierPaymentType, vboxEndCheckout);
        StackPane customerDisplay = new StackPane(vboxCustomerMain, vboxCustomerLoyalMember, vboxCustomerTotal, vboxCustomerCreditDebitPayment, vboxCustomerReceipt);
        splitPane.getItems().addAll(cashierDisplay, customerDisplay);
        

        AnchorPane pane = new AnchorPane();
        AnchorPane.setTopAnchor(splitPane, 15.0);
        AnchorPane.setRightAnchor(splitPane, 15.0);
        AnchorPane.setBottomAnchor(splitPane, 15.0);
        AnchorPane.setLeftAnchor(splitPane, 15.0);
        pane.getChildren().addAll(splitPane);
        pane.setStyle("-fx-background-color: GREY");

        Scene scene = new Scene(pane, 595, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Supermarket Checkout");
        primaryStage.show();
    }

    class itemIDButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            loyalMemberResultCashier.setVisible(false);
            loyalMemberResultCustomer.setVisible(false);
            int productID = Integer.parseInt(productIDEntry.getText());
            orderProductIDs += Integer.toString(productID) + ", ";
            foundProductInfo = touchscreenObj.requestProductInfo(productID, loyalMemberChecked, orderProductIDs);
            if (loyalMemberChecked == false) {
                vboxCustomerLoyalMember.setVisible(true);
                vboxCustomerMain.setVisible(false);
                loyalMemberChecked = true;
            }
            if (foundProductInfo[2].equals("True")) {
                vboxCashierScale.setVisible(true);
                vboxCashierEntry.setVisible(false);
            }
            else {
                productInfo += foundProductInfo[0] + " $" + foundProductInfo[1] + "\n";
                subTotal += Double.parseDouble(foundProductInfo[1]);
                productInfoDisplayCashier.setText(String.format(productInfo));
                productInfoDisplayCustomer.setText(String.format(productInfo));
            }
        }
    }

    class scaleButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            bulkWeight = Double.parseDouble(productWeightEntry.getText());
            String calculatedPrice = df.format(bulkWeight * Double.parseDouble(foundProductInfo[1]));
            subTotal += Double.parseDouble(calculatedPrice);
            productInfo += foundProductInfo[0] + " $" + calculatedPrice + " (" + bulkWeight + "lbs @ $" + df.format(Double.parseDouble(foundProductInfo[1])) + "/lb.)" + "\n";
            productInfoDisplayCashier.setText(String.format(productInfo));
            productInfoDisplayCustomer.setText(String.format(productInfo));
            vboxCashierScale.setVisible(false);
            vboxCashierEntry.setVisible(true);
        }
    }

    class loyalMemberSubmitButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            totalButton.setVisible(true);
            memberPhoneNum = phoneNumEntry.getText();
            loyalMemberPIN = memberPINEntry.getText();
            creditDebitReaderObj = new CreditDebitReaderInterface();
            loyalMemberStatus = creditDebitReaderObj.loyalMemberEntry(memberPhoneNum, loyalMemberPIN);
            if (loyalMemberStatus == true) {
                loyalMemberResultCashier.setText("Loyalty Member Account Verified.");
                loyalMemberResultCustomer.setText("Loyalty Member Account Verified.");
            }
            else {
                loyalMemberResultCashier.setText("Loyalty Member Account Denied.");
                loyalMemberResultCustomer.setText("Loyalty Member Account Denied.");
            }
            loyalMemberResultCashier.setVisible(true);
            loyalMemberResultCustomer.setVisible(true);
            vboxCustomerLoyalMember.setVisible(false);
            vboxCustomerMain.setVisible(true);
        }
    }

    class loyalMemberCancelButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            totalButton.setVisible(true);
            creditDebitReaderObj.loyalMemberEntry("Cancel", "Cancel");
            loyalMemberResultCashier.setText("Loyalty Member Account Cancelled.");
            loyalMemberResultCustomer.setText("Loyalty Member Account Cancelled.");
            loyalMemberResultCashier.setVisible(true);
            loyalMemberResultCustomer.setVisible(true);
            vboxCustomerLoyalMember.setVisible(false);
            vboxCustomerMain.setVisible(true);
        }
    }

    class calculateTotalButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String[] phoneNumMemberPin = {memberPhoneNum, loyalMemberPIN};
            totalValues = touchscreenObj.calculateTotal(phoneNumMemberPin, loyalMemberStatus, subTotal);
            subTotalCashier.setText("Subtotal: $" + df.format(totalValues[0]));
            taxCashier.setText("Sales Tax (6.25%): $" + df.format(totalValues[1] - totalValues[0]));
            finalTotalCashier.setText("Total: $"+ df.format(totalValues[1]));
            subTotalCustomer.setText("Subtotal: $" + df.format(totalValues[0]));
            taxCustomer.setText("Sales Tax (6.25%): $" + df.format(totalValues[1] - totalValues[0]));
            finalTotalCustomer.setText("Total: $"+ df.format(totalValues[1]));
            idButton.setVisible(false);
            totalButton.setVisible(false);
            vboxCashierEntry.setVisible(false);
            vboxCashierTotal.setVisible(true);
            vboxCustomerTotal.setVisible(true);
            vboxCashierPaymentType.setVisible(true);
            openTill.setVisible(true);
        }
    }

    class creditDebitButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String paymentAmount = paymentAmountEntry.getText();
            String paymentType = "Credit/Debit";
            touchscreenObj.returnPaymentTypeAmount(paymentAmount, paymentType);
            vboxCustomerCreditDebitPayment.setVisible(true);
            //vboxCashierPaymentType.setVisible(false);
        }
    }

    class submitCardInfoButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            cardNum = cardNumberEntry.getText();
            calculatedChangeAuthNum = creditDebitReaderObj.creditDebitEntry(cardNumberEntry.getText(), expDateEntry.getText(), Integer.parseInt(cvvEntry.getText()), Integer.parseInt(zipCodeEntry.getText()));
            if (calculatedChangeAuthNum[0] == -1.0 & calculatedChangeAuthNum[1] == -1.0) {
                vboxCustomerCreditDebitPayment.setVisible(false);
                cardDenied.setVisible(true);
                cancelOrderButton.setVisible(true);
            }
            else {
                changeAmountCashier.setVisible(true);
                changeAmountCashier.setText("Change Amount: $" + df.format(calculatedChangeAuthNum[0]));
                receiptProductInfo.setText(productInfo);
                receiptAuthNumber.setText("Authorization Number: " + String.valueOf((int)calculatedChangeAuthNum[1]));
                receiptCardNumber.setText("Card Number: " + cardNum);
                receiptTotalInformation.setText("Subtotal: $" + df.format(totalValues[0]) + "\n" + "Sales Tax (6.25%): $" + df.format(totalValues[1] - totalValues[0]) + "\n" + "Total: $"+ df.format(totalValues[1]) + "\n" + "Change Amount: $" + df.format(calculatedChangeAuthNum[0]));
                vboxCustomerCreditDebitPayment.setVisible(false);
                vboxEndCheckout.setVisible(true);
                vboxCustomerReceipt.setVisible(true);
                vboxCustomerTotal.setVisible(false);
                vboxCustomerMain.setVisible(false);
                vboxCashierMain.setVisible(false);
                vboxCashierPaymentType.setVisible(false);
            }
        }
    }

    class closeTillButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

        }
    }

    class cashButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String paymentAmount = paymentAmountEntry.getText();
            String paymentType = "Cash";
            touchscreenObj.returnPaymentTypeAmount(paymentAmount, paymentType);
            calculatedChangeCashCheck = creditDebitReaderObj.cashOrChangeEntry();
            changeAmountCashier.setVisible(true);
            changeAmountCashier.setText("Change Amount: $" + df.format(calculatedChangeCashCheck));
            receiptProductInfo.setText(productInfo);
            receiptTotalInformation.setText("Cash Payment \nSubtotal: $" + df.format(totalValues[0]) + "\n" + "Sales Tax (6.25%): $" + df.format(totalValues[1] - totalValues[0]) + "\n" + "Total: $"+ df.format(totalValues[1]) + "\n" + "Change Amount: $" + df.format(calculatedChangeCashCheck));
            vboxCustomerCreditDebitPayment.setVisible(false);
            vboxEndCheckout.setVisible(true);
            vboxCustomerReceipt.setVisible(true);
            vboxCustomerTotal.setVisible(false);
            vboxCustomerMain.setVisible(false);
            vboxCashierMain.setVisible(false);
            vboxCashierPaymentType.setVisible(false);
            receiptAuthNumber.setVisible(false);
            receiptCardNumber.setVisible(false);
        }
    }

    class checkButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String paymentAmount = paymentAmountEntry.getText();
            String paymentType = "Check";
            touchscreenObj.returnPaymentTypeAmount(paymentAmount, paymentType);
            calculatedChangeCashCheck = creditDebitReaderObj.cashOrChangeEntry();
            changeAmountCashier.setVisible(true);
            changeAmountCashier.setText("Change Amount: $" + df.format(calculatedChangeCashCheck));
            receiptProductInfo.setText(productInfo);
            receiptTotalInformation.setText("Check Payment \n Subtotal: $" + df.format(totalValues[0]) + "\n" + "Sales Tax (6.25%): $" + df.format(totalValues[1] - totalValues[0]) + "\n" + "Total: $"+ df.format(totalValues[1]) + "\n" + "Change Amount: $" + df.format(calculatedChangeCashCheck));
            vboxCustomerCreditDebitPayment.setVisible(false);
            vboxEndCheckout.setVisible(true);
            vboxCustomerReceipt.setVisible(true);
            vboxCustomerTotal.setVisible(false);
            vboxCustomerMain.setVisible(false);
            vboxCashierMain.setVisible(false);
            vboxCashierPaymentType.setVisible(false);
            receiptAuthNumber.setVisible(false);
            receiptCardNumber.setVisible(false);
        }
    }
    
    class cancelOrderButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

        }
    }

    public static void main(String args[]) {
        launch(args);
    }
}
