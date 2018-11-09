package uchet.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import uchet.Main;

public class MenuBarController {
	private Main main;
	
	public void setMain(Main aMain)
	{
		main = aMain;
	}
	
	@FXML
	private void priv()
	{
		main.recoverySearch();
		main.showPrivFrame();
	}
	
	@FXML
	private void showCash()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(main.getStage());
        alert.setTitle("Касса");
        alert.setHeaderText("Количество денег на данный момент");
        alert.setContentText("В кассе: " + main.getTCash() + " рублей.");
        alert.showAndWait();
	}
	
	@FXML
	private void showCheks()
	{
		System.out.println("кек");
		File l = new File(main.getPath() + "\\Чеки");
		Desktop desktop = null;
		if (Desktop.isDesktopSupported()) {
		    desktop = Desktop.getDesktop();
		}
		try {
		    desktop.open(l);
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
	}
	
	@FXML
	private void sell()
	{
		main.recoverySearch();
		main.showSellFrame();
	}
	
	@FXML
	private void changeKomp()
	{
		main.showChangeinfDialog();
	}
	public void setMainApp(Main main) {
        this.main = main;
    }
}
