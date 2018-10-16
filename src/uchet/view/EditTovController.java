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
			 String art = Art.getText().trim();
			 try
				{
					File tovm = new File(path+"\\"+art+"m.txt");
					File tovs = new File(path+"\\"+art+"s.txt");
					File tovn = new File(path+"\\"+art+"n.txt");
					File tovp = new File(path+"\\"+art+"p.txt");
					if(flag&&(tovp.exists()||tovn.exists()||tovs.exists()||tovm.exists()))
					{
						Alert alert = new Alert(AlertType.ERROR);
			            alert.initOwner(stage);
			            alert.setTitle("������");
			            alert.setHeaderText("����� � ����� ��������� ��� ����������");
	
			            alert.showAndWait();
		            }
					else if(tovp.exists()||tovn.exists()||tovs.exists()||tovm.exists())
					{
						OutputStream obj = new FileOutputStream(path + "\\" + art + "n.txt");
						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(obj));
						out.write(item.getName());
						out.close();
						obj.close();
						obj = new FileOutputStream(path + "\\" + art + "m.txt");
						out = new BufferedWriter(new OutputStreamWriter(obj));
						out.write(""+item.getMKol());
						out.close();
						obj.close();
						obj = new FileOutputStream(path + "\\" + art + "s.txt");
						out = new BufferedWriter(new OutputStreamWriter(obj));
						out.write(""+item.getSKol());
						out.close();
						obj.close();
						obj = new FileOutputStream(path + "\\" + art + "p.txt");
						out = new BufferedWriter(new OutputStreamWriter(obj));
						out.write(item.getPrice()+"");
						out.close();
						obj.close();
					}
					else
					{
						tovm.createNewFile();
						tovs.createNewFile();
						tovn.createNewFile();
						tovp.createNewFile();
						
						OutputStream obj = new FileOutputStream(path + "\\" + art + "n.txt");
						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(obj));
						out.write(item.getName());
						out.close();
						obj.close();
						obj = new FileOutputStream(path + "\\" + art + "m.txt");
						out = new BufferedWriter(new OutputStreamWriter(obj));
						out.write(""+item.getMKol());
						out.close();
						obj.close();
						obj = new FileOutputStream(path + "\\" + art + "s.txt");
						out = new BufferedWriter(new OutputStreamWriter(obj));
						out.write(""+item.getSKol());
						out.close();
						obj.close();
						obj = new FileOutputStream(path + "\\" + art + "p.txt");
						out = new BufferedWriter(new OutputStreamWriter(obj));
						out.write(item.getPrice()+"");
						out.close();
						obj.close();
						
				}
			}catch(Exception e) {e.printStackTrace();}
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
