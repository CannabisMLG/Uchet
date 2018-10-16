package uchet.view;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import uchet.Main;

public class ChooseBDController {
	@FXML
	private TextField pathField;
	private Main main;
	private Stage stage;
	private File conf;
	
	@FXML
	private void initialize() throws IOException{
		conf = new File("c:\\Uchet\\conf.txt");
		InputStream obj = new FileInputStream(conf);
		BufferedReader in = new BufferedReader(new InputStreamReader(obj));
		String path = null;
		in.readLine();
		path = in.readLine();
		in.close();
		obj.close();
		pathField.setText(path);
	}
	
	@FXML
	private void chooseFolder()
	{
		final DirectoryChooser p = new DirectoryChooser();
		p.setTitle("Выберите папку");
		pathField.setText(p.showDialog(stage).getAbsolutePath());
	}
	
	@FXML
	private void ok()
	{
		main.setPath(pathField.getText());
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
			
			configs[1] = pathField.getText();
			
			OutputStream obj1 = new FileOutputStream(conf);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(obj1));
			for(int i = 0;i<2;i++)
			{
				out.write(configs[i] + "\n");
			}
			System.err.println("ok");
			out.close();
			obj1.close();
		}
		catch(Exception e) {}
		main.initMenuBar();
		main.showTovList();
	}
	public void setMain(Main main)
	{
		this.main = main;
	}
	
	public void setStage(Stage stage)
	{
		this.stage = stage;
	}
	
}
