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

public class GUI extends Application
{
   // Define javafx variables
   private TextField actualValue;
   private Label resultLabelAssessmentVal;
   private Label resultLabelPropertyTax;
   
   public static void main(String[] args)
   {
       // Launch the application.
       launch(args);
   }
   
   @Override
   public void start(Stage primaryStage) {
       // Create label and text field for actual property value.
       Label promptLabelActual = new Label("Actual Value of Property: ($)");
       actualValue = new TextField();           

       // Create calculate button and event handler.
       Button calcButton = new Button("Calculate");
       calcButton.setOnAction(new PropertyCalcButton());

       // Create result labels to display the results.
       resultLabelAssessmentVal = new Label();
       resultLabelPropertyTax = new Label();           
       
       // Set up GUI 
       HBox hbox1 = new HBox(20, promptLabelActual, actualValue);          
       VBox vbox = new VBox(20, hbox1, calcButton, resultLabelAssessmentVal, resultLabelPropertyTax);                
       vbox.setAlignment(Pos.CENTER);
       vbox.setPadding(new Insets(25));           
       Scene scene = new Scene(vbox);
       primaryStage.setScene(scene);             
       primaryStage.setTitle("Assessment Value and Property Tax Calculator");                
       primaryStage.show();   
   }

   // Define class PropertyCalcButton for button click event.
   class PropertyCalcButton implements EventHandler<ActionEvent>
   {
      @Override
      public void handle(ActionEvent event)
      {
        // Define variables and calculate assessment value and property tax. 
        Double value = Double.parseDouble(actualValue.getText());
        Double assessmentVal = value * .6;
        Double propertyTax = (assessmentVal / 100) * .64;
         
        // Display the results.
        resultLabelAssessmentVal.setText(String.format("Assessment Value: $%.2f", assessmentVal));
        resultLabelPropertyTax.setText(String.format("Property Tax: $%.2f", propertyTax));
      }
   }
}
