package uchet.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class KolController {
	
	@FXML
	Label label;
	
	int kol = 0;
	
	@FXML
	TextField Kol;
	
	Stage stage;
	
	@FXML
	 private void initialize() {
	 }
	
	@FXML
	private void ok()
	{
		kol = Integer.parseInt(Kol.getText());
		stage.close();
	}
	
	public int getKol()
	{
		return kol;
	}
	
	public void setBase(String choose)
	{
		if(choose.equals("m")) label.setText("������� ���������� �� ��������:");
		else if(choose.equals("s")) label.setText("������� ���������� �� ������:");
		else if(choose.equals("mp")) label.setText("������� ���������� � �������:");
		else label.setText("������� ���������� �� �����:");
	}
	
	 public void setDialogStage(Stage dialogStage) {
	        stage = dialogStage;
	 }

}
