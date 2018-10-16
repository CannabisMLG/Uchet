package uchet.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uchet.Main;
import uchet.model.Item;

public class ChooseTableController {
	@FXML
	private TableView<Item> privTable;
	@FXML
	private TableColumn<Item, String> Name;
	@FXML
	private TableColumn<Item, String> Art;
	@FXML
	private TextField searchField;
	private ObservableList<Item> privoz = FXCollections.observableArrayList();
	
	private Main main;
	private String path;
	private Stage stage;
	
	@FXML
	private void initialize() {
		Name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        Art.setCellValueFactory(cellData -> cellData.getValue().artProperty());
	}
	
	@FXML
	private void add()
	{
		privoz.add(new Item(privTable.getSelectionModel().getSelectedItem().getName(),privTable.getSelectionModel().getSelectedItem().getArt(),0 ,main.showKolDialog("mp"),main.showKolDialog("sp")));
		stage.close();
	}
	
	public void setMain(Main main, String path)
	{
		this.main = main;
		this.path = path;
		privTable.setItems(main.getItems());
		privoz = main.getPrivoz();
	}
	
	public void setDialogStage(Stage dialogStage) 
	{
        stage = dialogStage;
	}
	
	@FXML
    private void search()
    {
    	ObservableList<Item> items = main.getItems();
    	if("".equals(searchField.getText()))
    	{
    		int size = items.size();
	    	for(int i = 0;i < size;i++)
	    	{
	    		items.remove(0);
	    	}
	    	File workdir = new File(main.getPath());
	    	File[] files = workdir.listFiles();
	    	for(int i = 0;i < files.length;i++)
	    	{
	    		if(files[i].isFile()&&files[i].getName().charAt(files[i].getName().length()-5)=='p')
	    		{
	    			String art = files[i].getName().substring(0, files[i].getName().length()-5);
	    			String name = "qwe";
	    			int price = 0;
	   				Double mkol = 0.0, skol = 0.0;
	  				try
	  				{
	    				InputStream obj = new FileInputStream(path + "\\" + art + "n.txt");
	    				BufferedReader in = new BufferedReader(new InputStreamReader(obj));
	    				name = in.readLine().trim();
	    				in.close();
	    				obj.close();
	   					obj = new FileInputStream(path + "\\" + art + "p.txt");
	    				in = new BufferedReader(new InputStreamReader(obj));
	    				price = Integer.parseInt(in.readLine().trim());
	    				in.close();
	    				obj.close();
	    				obj = new FileInputStream(path + "\\" + art + "m.txt");
	    				in = new BufferedReader(new InputStreamReader(obj));
	    				mkol = Double.parseDouble(in.readLine().trim());
	    				in.close();
	    				obj.close();
	    				obj = new FileInputStream(path + "\\" + art + "s.txt");
	    				in = new BufferedReader(new InputStreamReader(obj));
	    				skol = Double.parseDouble(in.readLine().trim());
	    				in.close();
	    				obj.close();
	    			}
	   				catch(IOException e) {}
	    			items.add(new Item(name, art, price, mkol, skol));
	    		}
	    	}
	    }
    	else
    	{
    		int size = items.size();
	    	for(int i = 0;i < size;i++)
	    	{
	    		items.remove(0);
	    	}
	    	File workdir = new File(main.getPath());
	    	File[] files = workdir.listFiles();
	    	for(int i = 0;i < files.length;i++)
	    	{
	    		if(files[i].isFile()&&files[i].getName().charAt(files[i].getName().length()-5)=='p')
	    		{
	    			String art = files[i].getName().substring(0, files[i].getName().length()-5);
	    			String name = "qwe";
	    			int price = 0;
	   				Double mkol = 0.0, skol = 0.0;
	  				try
	  				{
	    				InputStream obj = new FileInputStream(path + "\\" + art + "n.txt");
	    				BufferedReader in = new BufferedReader(new InputStreamReader(obj));
	    				name = in.readLine().trim();
	    				in.close();
	    				obj.close();
	   					obj = new FileInputStream(path + "\\" + art + "p.txt");
	    				in = new BufferedReader(new InputStreamReader(obj));
	    				price = Integer.parseInt(in.readLine().trim());
	    				in.close();
	    				obj.close();
	    				obj = new FileInputStream(path + "\\" + art + "m.txt");
	    				in = new BufferedReader(new InputStreamReader(obj));
	    				mkol = Double.parseDouble(in.readLine().trim());
	    				in.close();
	    				obj.close();
	    				obj = new FileInputStream(path + "\\" + art + "s.txt");
	    				in = new BufferedReader(new InputStreamReader(obj));
	    				skol = Double.parseDouble(in.readLine().trim());
	    				in.close();
	    				obj.close();
	    			}
	   				catch(IOException e) {}
	    			if(art.toLowerCase().indexOf(searchField.getText().toLowerCase())>-1||name.toLowerCase().indexOf(searchField.getText().toLowerCase())>-1)items.add(new Item(name, art, price, mkol, skol));
	    		}
	    	}
    	}
    }
}
