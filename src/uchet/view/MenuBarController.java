package uchet.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
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
	private void showCheks()
	{
		System.out.println("���");
		File l = new File(main.getPath() + "\\����");
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
