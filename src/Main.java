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

public class Main extends Application{
    private String productInfo = "";
    private String orderProductIDs = "";
    private TextField productIDEntry;
    private Label productInfoDisplayCashier;
    private Label productInfoDisplayCustomer;
    private TextField productWeightEntry;
    private String[] foundProductInfo;
    private Label loyalMemberResultCashier;
    private Label loyalMemberResultCustomer;
    private TextField phoneNumEntry;
    private TextField memberPINEntry;
    Button totalButton;
    VBox vboxCashierMain;
    VBox vboxCashierScale;
    VBox vboxCustomerMain;
    VBox vboxCustomerLoyalMember;

    TouchScreenInterface touchscreenObj = new TouchScreenInterface();
    CreditDebitReaderInterface loyalCheckObj = new CreditDebitReaderInterface();
    private boolean loyalMemberStatus = false;

    public void start(Stage primaryStage) {
        Label promptProductID = new Label("Product ID: ");
        productIDEntry = new TextField();
        Button idButton = new Button("ITEM-ID");
        idButton.setOnAction(new itemIDButtonHandler());

        Label promptProductScale = new Label("Product Weight: ");
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

        HBox hboxCashierMain = new HBox(10, promptProductID, productIDEntry);
        HBox hboxCashierScale = new HBox(10, promptProductScale, productWeightEntry);
        HBox hboxCustomerPhoneNum = new HBox(10, phoneNum, phoneNumEntry);
        HBox hboxCustomermemberPIN = new HBox(10, memberPIN, memberPINEntry);
        HBox hboxCustomerButtons = new HBox(10, loyalMemberSubmit, loyalMemberCancel);

        vboxCashierMain = new VBox(10, loyalMemberResultCashier, hboxCashierMain, idButton, totalButton, productInfoDisplayCashier);
        vboxCashierScale = new VBox(10, hboxCashierScale, scaleButton, scaleItem);
        vboxCustomerMain = new VBox(10, loyalMemberResultCustomer, productInfoDisplayCustomer);
        vboxCustomerLoyalMember = new VBox(10, loyalMember, hboxCustomerPhoneNum, hboxCustomermemberPIN, hboxCustomerButtons);

        vboxCashierScale.setVisible(false);
        vboxCustomerLoyalMember.setVisible(false);

        hboxCashierMain.setAlignment(Pos.CENTER);
        hboxCashierScale.setAlignment(Pos.CENTER);
        hboxCustomerPhoneNum.setAlignment(Pos.CENTER);
        hboxCustomermemberPIN.setAlignment(Pos.CENTER);
        hboxCustomerButtons.setAlignment(Pos.CENTER);

        vboxCashierMain.setAlignment(Pos.CENTER);
        vboxCashierMain.setPadding(new Insets(5));
        vboxCashierScale.setAlignment(Pos.CENTER);
        vboxCashierScale.setPadding(new Insets(5));
        vboxCustomerMain.setAlignment(Pos.CENTER);
        vboxCustomerMain.setPadding(new Insets(5));
        vboxCustomerLoyalMember.setAlignment(Pos.CENTER);
        vboxCustomerLoyalMember.setPadding(new Insets(5));

        SplitPane splitPane = new SplitPane();
        StackPane cashierDisplay = new StackPane(vboxCashierMain, vboxCashierScale);
        StackPane customerDisplay = new StackPane(vboxCustomerMain, vboxCustomerLoyalMember);
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
            totalButton.setVisible(true);
            loyalMemberResultCashier.setVisible(false);
            loyalMemberResultCustomer.setVisible(false);
            int productID = Integer.parseInt(productIDEntry.getText());
            orderProductIDs += Integer.toString(productID) + ", ";
            foundProductInfo = touchscreenObj.requestProductInfo(productID, loyalMemberStatus, orderProductIDs);
            if (loyalMemberStatus == false) {
                vboxCustomerLoyalMember.setVisible(true);
                vboxCustomerMain.setVisible(false);
                loyalMemberStatus = true;
            }
            if (foundProductInfo[2].equals("True")) {
                vboxCashierScale.setVisible(true);
                vboxCashierMain.setVisible(false);
            }
            else {
                productInfo += foundProductInfo[0] + " " + foundProductInfo[1] + "\n";
                productInfoDisplayCashier.setText(String.format(productInfo));
                productInfoDisplayCustomer.setText(String.format(productInfo));
            }
        }
    }

    class scaleButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            double weight = Double.parseDouble(productWeightEntry.getText());
            String calculatedPrice = String.valueOf(weight * Double.parseDouble(foundProductInfo[1]));
            productInfo += foundProductInfo[0] + " " + calculatedPrice + "\n";
            productInfoDisplayCashier.setText(String.format(productInfo));
            productInfoDisplayCustomer.setText(String.format(productInfo));
            vboxCashierScale.setVisible(false);
            vboxCashierMain.setVisible(true);
        }
    }

    class loyalMemberSubmitButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String memberPhoneNum = phoneNumEntry.getText();
            String loaylMemberPIN = memberPINEntry.getText();
            boolean loyal = loyalCheckObj.loyalMemberEntry(memberPhoneNum, loaylMemberPIN);
            if (loyal == true) {
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
            double[] test = touchscreenObj.calculateTotal();
            System.out.println("Subtotal:" + test[0]);
            System.out.println("Total:" + test[1]);
        }
    }
    
    public static void main(String args[]) {
        launch(args);
    }
}
