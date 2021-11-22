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

public class Main extends Application{
    private String productInfo = "";
    private TextField productIDEntry;
    private Label productInfoDisplayCashier;
    private Label productInfoDisplayCustomer;
    private Label scaleItem;
    private Label promptProductScale;
    private TextField productWeightEntry;
    private Button scaleButton;
    private Label promptProductID;
    private Button idButton;
    private String[] foundProductInfo;
    
    public void start(Stage primaryStage) {
        promptProductID = new Label("Product ID: ");
        productIDEntry = new TextField();

        idButton = new Button("ITEM-ID");
        idButton.setOnAction(new itemIDButtonHandler());

        promptProductScale = new Label("Product Weight: ");
        productWeightEntry = new TextField();
        scaleButton = new Button("SCALE");
        scaleButton.setOnAction(new scaleButtonHandler());
        scaleItem = new Label("Bulk Item. Please Weight Item.");
        promptProductScale.setVisible(false);
        productWeightEntry.setVisible(false);
        scaleButton.setVisible(false);
        scaleItem.setVisible(false);
        

        productInfoDisplayCashier = new Label();
        productInfoDisplayCustomer = new Label();
        HBox hbox1 = new HBox(10, promptProductID, productIDEntry, promptProductScale, productWeightEntry);
        VBox vbox1 = new VBox(10, hbox1, idButton, scaleButton, scaleItem, productInfoDisplayCashier);
        VBox vboxCustomer = new VBox(10, productInfoDisplayCustomer);
        hbox1.setAlignment(Pos.CENTER);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setPadding(new Insets(5));
        vboxCustomer.setAlignment(Pos.CENTER);
        vboxCustomer.setPadding(new Insets(5));
        SplitPane splitPane = new SplitPane();

        StackPane cashierDisplay = new StackPane(vbox1);
        StackPane customerDisplay = new StackPane(vboxCustomer);

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
            TouchScreenInterface objCashier = new TouchScreenInterface();
            int productID = Integer.parseInt(productIDEntry.getText());
            foundProductInfo = objCashier.requestProductInfo(productID);
            if (foundProductInfo[2].equals("True")) {
                promptProductScale.setVisible(true);
                productWeightEntry.setVisible(true);
                scaleButton.setVisible(true);
                scaleItem.setVisible(true);
                promptProductID.setVisible(false);
                productIDEntry.setVisible(false);
                idButton.setVisible(false);
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
            promptProductScale.setVisible(false);
            productWeightEntry.setVisible(false);
            scaleButton.setVisible(false);
            scaleItem.setVisible(false);
            promptProductID.setVisible(true);
            productIDEntry.setVisible(true);
            idButton.setVisible(true);
        }
    }
    
    public static void main(String args[]) {
        launch(args);
    }
}
