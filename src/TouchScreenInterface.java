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
public class TouchScreenInterface extends Application{
    public String[] productInfo;
    public String productInfoCashier = "";
    private TextField productIDEntry;
    private Label productInfoDisplay;
    private Label scaleItem;

    public String[] requestProductInfo(int productID) {
        CheckoutControl checkoutObj = new CheckoutControl();
        productInfo = checkoutObj.productInfo(productID);
        return productInfo;
    }

    public void start(Stage primaryStage) {
        Label promptProductID = new Label("Product ID: ");
        productIDEntry = new TextField();

        Button idButton = new Button("ITEM-ID");
        idButton.setOnAction(new itemIDButtonHandler());

        Button scaleButton = new Button("SCALE");
        // scaleButton.setOnAction(new itemIDButtonHandler());
        scaleItem = new Label();
        productInfoDisplay = new Label();

        HBox hbox1 = new HBox(100, promptProductID, productIDEntry);
        VBox vbox1 = new VBox(100, hbox1, idButton, scaleButton, scaleItem, productInfoDisplay);
        hbox1.setAlignment(Pos.CENTER);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setPadding(new Insets(5));
        Scene scene = new Scene(vbox1);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cashier Display");
        primaryStage.show();
    }
    class itemIDButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            TouchScreenInterface obj = new TouchScreenInterface();
            int productID = Integer.parseInt(productIDEntry.getText());
            String[] foundProductInfo = obj.requestProductInfo(productID);
            if (foundProductInfo[0].equals("Bulk Item. Please Weight Item.")) {
                scaleItem.setText(String.format("Bulk Item. Please Weight Item."));
            }
            else {
                productInfoCashier += foundProductInfo[0] + " " + foundProductInfo[1] + "\n";
                productInfoDisplay.setText(String.format(productInfoCashier));
                scaleItem.setText(String.format(""));
            }
        }
    }

    public static void main(String args[]) {
        launch(args);
    }
}
