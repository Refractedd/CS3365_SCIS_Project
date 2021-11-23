import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    private String productInfo = "";
    private String orderProductIDs = "";
    private String memberPhoneNum;
    private String loyalMemberPIN;
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
    private Label subTotalCustomer;
    private Label taxCustomer;
    private Label finalTotalCustomer;
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

    TouchScreenInterface touchscreenObj = new TouchScreenInterface();
    CreditDebitReaderInterface loyalCheckObj = new CreditDebitReaderInterface();
    private boolean loyalMemberChecked = false;
    private boolean loyalMemberStatus = false;

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

        subTotalCustomer = new Label();
        taxCustomer = new Label();
        finalTotalCustomer = new Label();

        hboxCashierMain = new HBox(10, promptProductID, productIDEntry);
        HBox hboxCashierScale = new HBox(10, promptProductScale, productWeightEntry);
        HBox hboxCustomerPhoneNum = new HBox(10, phoneNum, phoneNumEntry);
        HBox hboxCustomerMemberPIN = new HBox(10, memberPIN, memberPINEntry);
        HBox hboxCustomerButtons = new HBox(10, loyalMemberSubmit, loyalMemberCancel);

        vboxCashierMain = new VBox(10, loyalMemberResultCashier, productInfoDisplayCashier);
        vboxCashierEntry = new VBox(10, hboxCashierMain, idButton, totalButton);
        vboxCashierScale = new VBox(10, hboxCashierScale, scaleButton, scaleItem);
        vboxCashierTotal = new VBox(10, subTotalCashier, taxCashier, finalTotalCashier);
        
        vboxCustomerMain = new VBox(10, loyalMemberResultCustomer, productInfoDisplayCustomer);
        vboxCustomerLoyalMember = new VBox(10, loyalMember, hboxCustomerPhoneNum, hboxCustomerMemberPIN, hboxCustomerButtons);
        vboxCustomerTotal = new VBox(10, subTotalCustomer, taxCustomer, finalTotalCustomer);

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

        /*Button creditDebitPaymentButton = new Button("Credit/Debit");
        Button cashPaymentButton = new Button("Cash");
        Button checkPaymentButton = new Button("Check");

        vboxCashierPaymentType = new VBox(10, creditDebitPaymentButton, cashPaymentButton, checkPaymentButton);*/

        SplitPane splitPane = new SplitPane();
        StackPane cashierDisplay = new StackPane(vboxCashierMain, vboxCashierEntry, vboxCashierScale, vboxCashierTotal);
        StackPane customerDisplay = new StackPane(vboxCustomerMain, vboxCustomerLoyalMember, vboxCustomerTotal);
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
                productInfoDisplayCashier.setText(String.format(productInfo));
                productInfoDisplayCustomer.setText(String.format(productInfo));
            }
        }
    }

    class scaleButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            double weight = Double.parseDouble(productWeightEntry.getText());
            String calculatedPrice = df.format(weight * Double.parseDouble(foundProductInfo[1]));
            productInfo += foundProductInfo[0] + " $" + calculatedPrice + " (" + weight + "lbs @ $" + df.format(Double.parseDouble(foundProductInfo[1])) + "/lb.)" + "\n";
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
            loyalCheckObj = new CreditDebitReaderInterface();
            loyalMemberStatus = loyalCheckObj.loyalMemberEntry(memberPhoneNum, loyalMemberPIN);
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
            loyalCheckObj.loyalMemberEntry("Cancel", "Cancel");
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
            double[] totalValues = touchscreenObj.calculateTotal(phoneNumMemberPin, loyalMemberStatus);
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
        }
    }
    
    public static void main(String args[]) {
        launch(args);
    }
}
