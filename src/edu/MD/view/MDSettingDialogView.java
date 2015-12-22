package edu.MD.view;

import edu.MD.control.MDConfiguration;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MDSettingDialogView {
	@FXML
    private TextField liquidFilmThicknessField;
    @FXML
    private TextField liquidFilmSizeField;
    @FXML
    private TextField initialTemperatureField;
    @FXML
    private TextField timeStepSizeField;
    @FXML
    private TextField fluidField;


    private Stage dialogStage;
    private MDConfiguration configuration;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	if (configuration==null) configuration = new MDConfiguration();
    	
    	StringConverter<Number> intConverter =new StringConverter<Number>() {
    		@Override
    		public String toString(Number number){
    			return number.toString();
    		}
    		
    		@Override
    		public Number fromString(String string){
    			return Integer.parseInt(string);
    		}
		};
		
		StringConverter<Number> doubleConverterNormal =new StringConverter<Number>() {
    		@Override
    		public String toString(Number number){
    			Double doubleNumber = (Double) number;
    			return String.format("%.2f", doubleNumber);
    		}
    		
    		@Override
    		public Number fromString(String string){
    			return Double.parseDouble(string);
    		}
		};
		
		StringConverter<Number> doubleConverterSmall =new StringConverter<Number>() {
    		@Override
    		public String toString(Number number){
    			Double doubleNumber = (Double) number;
    			return String.format("%.2e", doubleNumber);
    		}
    		
    		@Override
    		public Number fromString(String string){
    			return Double.parseDouble(string);
    		}
		};
		
    	Bindings.bindBidirectional(liquidFilmThicknessField.textProperty(), configuration.filmThickness(), intConverter);
    	Bindings.bindBidirectional(liquidFilmSizeField.textProperty(), configuration.filmSize(), intConverter);
    	Bindings.bindBidirectional(initialTemperatureField.textProperty(), configuration.temperature(), doubleConverterNormal);
    	Bindings.bindBidirectional(timeStepSizeField.textProperty(), configuration.timeStep(), doubleConverterSmall);
    	Bindings.bindBidirectional(fluidField.textProperty(), configuration.fluid());
    	
    }
    
   

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }



    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n"; 
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n"; 
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n"; 
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
