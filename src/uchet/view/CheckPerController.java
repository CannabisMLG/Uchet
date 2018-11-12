package uchet.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.GregorianCalendar;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import uchet.Main;

public class CheckPerController {
	
	@FXML
	private DatePicker Date1, Date2;
	@FXML
	private Label Cash;
	
	private Stage stage;
	private Main main;
	
	@FXML
	private void initialize(){
		Date1.setValue(LocalDate.now());
		Date2.setValue(LocalDate.now());
	}
	
	public void setDialogStage(Stage dialogStage) 
	{
        stage = dialogStage;
	}
 
	/**
	 * метод получающий ссылку на main
	 * @param main
	 */
	public void setMain(Main main)
	{
		this.main = main;
	}
	
	@FXML
	private void showCash()
	{
		double ccash = 0;
		GregorianCalendar cal = new GregorianCalendar();
		File[] days = (new File(main.getPath() + "\\Выручка")).listFiles();
		LocalDate start = Date1.getValue();
		LocalDate end = Date2.getValue();
		if(start == null||end == null)
		{
			 Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(stage);
	            alert.setTitle("Некорректный ввод");
	            alert.setHeaderText("Пожалуйста, введите все значения корректно");
	            alert.setContentText("Оба поля должны быть заполнены");
	            alert.showAndWait();
		}
		else
		{
			for(File day:days)
			{
				String date = day.getName();
				int year = Integer.parseInt(date.substring(0, 4));
				int month = Integer.parseInt(date.substring(5, 7));
				int dday = Integer.parseInt(date.substring(8, 10));
				if(year>=start.getYear() && year<=end.getYear() && month>=start.getMonthValue() && 
						month<=end.getMonthValue() && dday>=start.getDayOfMonth() && dday<=end.getDayOfMonth())
				{
					try
					{
						InputStream obj = new FileInputStream(day);
			 			BufferedReader in = new BufferedReader(new InputStreamReader(obj));
			 			ccash += Double.parseDouble(in.readLine());
			 			in.close();
			 			obj.close();
					}catch(Exception e) {}
				}
			}
			Cash.setText(ccash + " ₽");
		}
	}
}
