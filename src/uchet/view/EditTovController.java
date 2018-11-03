package uchet.view;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uchet.Main;
import uchet.model.Item;

public class EditTovController {
	
	@FXML
	private TextField Name;
	@FXML
	private TextField Art;
	@FXML
	private TextField Price;
	@FXML
	private TextField mKol;
	@FXML
	private TextField sKol;
	
	private Main main;
	private Stage stage;
	private Item item;
	private boolean okClicked = false, flag;
	private String path = "";
	
	 @FXML
	 private void initialize() {
	 }
	 
	 public void setDialogStage(Stage dialogStage) {
	        stage = dialogStage;
	 }
	 
	 public void setMain(Main main)
	 {
		 this.main = main;
	 }
	 
	 public void setFlag(boolean aFlag)
	 {
		 flag = aFlag;
	 }
	 /**
	  * �����, ���������� ������ �� ����� ������ �������
	  * @param aItem - ������ �� ������ �������
	  */
	 public void setItem(Item aItem)
	 {
		 item = aItem;
		 Name.setText(aItem.getName());
		 Art.setText(aItem.getArt());
		 Art.setEditable(flag);
		 Price.setText("");
		 mKol.setText("");
		 sKol.setText("");
	 }
	 
	
	 
	 public boolean isOkClicked() {
	        return okClicked;
	 }
	 
	 /**
	  * ��� ������� �� ������ ok ����������� � ������� ������ ���� ���� ������� ���� ��������������
	  * � ���� ������ � �����/�������������� ������ ������ 
	  */
	 @FXML
	 private void okCliced()
	 {
		 if(isInputValid())
		 {
			 item.setName(Name.getText());
			 item.setArt(Art.getText());
			 item.setPrice(Integer.parseInt(Price.getText()));
			 item.setMKol(Double.parseDouble(mKol.getText()));
			 item.setSKol(Double.parseDouble(sKol.getText()));
			 okClicked = true;
			 stage.close();
		 }
	 }
	 
	 public void setPath(String path)
	 {
		 this.path = path;
	 }
	 
	 /**
	  * �����, ����������� ��� �� ���� �������
	  * @return true - ��� ���� �������, false - �����-�� �� ����� �� �������
	  */
	 public boolean isInputValid()
	 {
		 String errorMessage = "";

	        if (Name.getText() == null || Name.getText().length() == 0) {
	            errorMessage += "�� ������� ��� ������!\n"; 
	        }
	        if (Art.getText() == null || Art.getText().length() == 0) {
	            errorMessage += "�� ������ �������!\n"; 
	        }
	        if (Price.getText() == null || Price.getText().length() == 0) {
	            errorMessage += "�� ������� ����!\n"; 
	        }

	        if (mKol.getText() == null || mKol.getText().length() == 0) {
	            errorMessage += "�� ������� ���-�� � ��������!\n"; 
	        }

	        if (sKol.getText() == null || sKol.getText().length() == 0) {
	            errorMessage += "�� ������� ���-�� �� ������!\n"; 
	        }

	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // ���������� ��������� �� ������.
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(stage);
	            alert.setTitle("����������� ��������");
	            alert.setHeaderText("����������, ������� ��� ��������");
	            alert.setContentText(errorMessage);

	            alert.showAndWait();

	            return false;
	        }
	 }
	 /**
	  * �����, ������������� ��� ������� �� ������ "������"
	  */
	 @FXML
	 private void handleCancel() {
	      stage.close();
	 }
	 
}
