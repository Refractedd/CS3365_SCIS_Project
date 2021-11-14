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
    private TextField productIDEntry;

    public String[] requestProductInfo(int productID) {
        CheckoutControl checkoutObj = new CheckoutControl();
        productInfo = checkoutObj.productInfo(productID);
        return productInfo;
    }

    public void start(Stage primaryStage) {
        Label promptProductID = new Label("ProductID: ");
        productIDEntry = new TextField();

        Button idButton = new Button("ITEM-ID");
        idButton.setOnAction(new itemIDButtonHandler());

        HBox hbox1 = new HBox(10, promptProductID, productIDEntry);
        VBox vbox1 = new VBox(10, hbox1, idButton);
        vbox1.setAlignment(Pos.CENTER);
        vbox1.setPadding(new Insets(15));
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
            for (String i: foundProductInfo) {
                System.out.println(i);
            }
        }
    }

    public static void main(String args[]) {
        launch(args);
    }
}
