package uchet.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uchet.Main;

public class ChangeinfController {
	@FXML
	private TextField name;
	private File conf;
	private Stage stage;
	private Main main;
	
	@FXML
	private void initialize() {
		conf = new File("c:\\Uchet\\conf.txt");
		try {
			InputStream obj = new FileInputStream(conf);
			BufferedReader in = new BufferedReader(new InputStreamReader(obj));
			int k = 0;
			while(in.ready())
			{
				if(k == 0)
				{
					name.setText(in.readLine());
				}
				else
				{
					in.readLine();
				}
				k++;
			}
			in.close();
			obj.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void ok()
	{
		String[] configs = new String[2];
		try
		{
			InputStream obj = new FileInputStream(conf);
			BufferedReader in = new BufferedReader(new InputStreamReader(obj));
			int k = 0;
			while(in.ready())
			{
				configs[k] = in.readLine();
				k++;
			}
			in.close();
			obj.close();
			
			configs[0] = name.getText();
			main.setCompName(name.getText());
			
			OutputStream obj1 = new FileOutputStream(conf);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(obj1));
			for(int i = 0;i<2;i++)
			{
				out.write(configs[i] + "\n");
			}
			out.close();
			obj1.close();
		}
		catch(IOException e) {e.printStackTrace();}
		stage.close();
	}

	public void setDialogStage(Stage dialogStage) {
		stage = dialogStage;
	}
	public void setMain(Main main) {
		this.main = main;
	}
}
