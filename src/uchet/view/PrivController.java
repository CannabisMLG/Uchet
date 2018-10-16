package uchet.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import uchet.Main;
import uchet.model.Item;

public class PrivController {
	@FXML
	private TableView<Item> privTable;
	@FXML
	private TableColumn<Item, String> Name;
	@FXML
	private TableColumn<Item, String> Art;
	@FXML
	private TableColumn<Item, String> sKol;
	@FXML
	private TableColumn<Item, String> mKol;
	
	TableView<Item> itemTable;
	
	private Main main;
	private Stage stage;
	private String path;
	private ObservableList<Item> privoz = FXCollections.observableArrayList();
	private ObservableList<Item> items = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		Name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        Art.setCellValueFactory(cellData -> cellData.getValue().artProperty());
        sKol.setCellValueFactory(cellData -> cellData.getValue().sKolProperty());
        mKol.setCellValueFactory(cellData -> cellData.getValue().mKolProperty());
	}
	
	@FXML
	private void add()
	{
		main.showChooseTable();
		privTable.refresh();
	}
	
	@FXML
	private void delete()
	{
		int index = privTable.getSelectionModel().getSelectedIndex();
		if(privTable.getSelectionModel().getSelectedIndex()>=0) 
			{
				privoz.remove(index);
			}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getStage());
            alert.setTitle("Не выбрано");
            alert.setHeaderText("Не один из товаров не выбран");
            alert.setContentText("Пожалуйста, выберите товар в таблице.");
            alert.showAndWait();
		}
	}
	
	@FXML
	private void ok()
	{
		for(int i = 0;i<privoz.size();i++)
		{
			double sklad = 0, mag = 0;
			try
			{
				InputStream obj = new FileInputStream(path + "\\" + privoz.get(i).artProperty().get() + "m.txt");
				BufferedReader in = new BufferedReader(new InputStreamReader(obj));
				mag = Double.parseDouble(in.readLine());
				in.close();
				obj.close();
				obj = new FileInputStream(path + "\\" + privoz.get(i).artProperty().get() + "s.txt");
				in = new BufferedReader(new InputStreamReader(obj));
				sklad = Double.parseDouble(in.readLine());
				in.close();
				obj.close();
				OutputStream obj1 = new FileOutputStream(path + "\\" + privoz.get(i).artProperty().get() + "m.txt");
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(obj1));
				out.write(""+(mag + Double.parseDouble(privoz.get(i).mKolProperty().get())));
				out.close();
				obj1.close();
				obj1 = new FileOutputStream(path + "\\" + privoz.get(i).artProperty().get() + "s.txt");
				out = new BufferedWriter(new OutputStreamWriter(obj1));
				out.write(""+(sklad + Double.parseDouble(privoz.get(i).sKolProperty().get())));
				out.close();
				obj1.close();
			}
			catch(Exception e) {}
			for(int j = 0;j<items.size();j++)
			{
				if(items.get(j).artProperty().get().equals(privoz.get(i).artProperty().get())) 
					{
						items.get(j).setSKol(sklad + Double.parseDouble(privoz.get(i).sKolProperty().get()));
						items.get(j).setMKol(mag + Double.parseDouble(privoz.get(i).mKolProperty().get()));
					}
			}
		}
		clear();
		main.refreshTable();
		stage.close();
	}
	
	private void clear()
	{
		int size = privoz.size();
		for(int i = 0;i < size; i++)
		{
			privoz.remove(0);
		}
		stage.close();
	}
	
	public void setMain(Main main)
	{
		this.main = main;
        privTable.setItems(main.getPrivoz());
	}
	
	public void setDialogStage(Stage dialogStage) 
	{
        stage = dialogStage;
	}

	public void setTable(TableView<Item> itemTable) {
		this.itemTable = itemTable;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setItems(ObservableList<Item> items) {
		this.items = items;
	}

	public void setPriv(ObservableList<Item> privoz) {
		this.privoz = privoz;
	}
}
