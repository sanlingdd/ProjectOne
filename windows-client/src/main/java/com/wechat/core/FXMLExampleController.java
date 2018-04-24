package com.wechat.core;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FXMLExampleController {
	
	@FXML
	private Text actiontarget;
	
	@FXML
	private TextField userNameField;
	
	@FXML
	private PasswordField passwordField;

	@FXML protected void handleSubmitButtonAction(ActionEvent event) {
        
		if("yulia".equalsIgnoreCase(userNameField.getText()) && "pass".equalsIgnoreCase(passwordField.getText()))
		{
			
			//OKHttp login
			actiontarget.setText("Sign in button pressed");			
		}
        
    }
}