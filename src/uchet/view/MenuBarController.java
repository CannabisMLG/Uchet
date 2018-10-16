package uchet.view;

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
		main.showPrivFrame();
	}
	
	@FXML
	private void sell()
	{
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
