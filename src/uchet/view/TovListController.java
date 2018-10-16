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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import uchet.Main;
import uchet.model.*;

public class TovListController {
	@FXML
	private TableView<Item> itemTable;
	@FXML
	private TableColumn<Item, String> NameColumn;
	@FXML
	private TableColumn<Item, String> ArtColumn;
	@FXML
	private TableColumn<Item, String> KolColumn;
	
	@FXML
	TextField searchField;
	
	@FXML
	private Label NameLabel;
	@FXML
	private Label ArtLabel;
	@FXML
	private Label PriceLabel;
	@FXML
	private Label mKolLabel;
	@FXML
	private Label sKolLabel;
	
	private ObservableList<ItemInTrash> trash = FXCollections.observableArrayList();
	
	Main main;
	private String path;

	public TovListController() {}
	
	 /**
     * ������������� ������-�����������. ���� ����� ���������� �������������
     * ����� ����, ��� fxml-���� ����� ��������.
     */
    @FXML
    private void initialize() {
        // ������������� ������� ��������� � ����� ���������.
        NameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ArtColumn.setCellValueFactory(cellData -> cellData.getValue().artProperty());
        KolColumn.setCellValueFactory(cellData -> cellData.getValue().KolProperty());
     // ������� �������������� ���������� �� ��������.
        showItemDetails(null);

        // ������� ��������� ������, � ��� ��������� ����������
        // �������������� ���������� �� ��������.
        itemTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showItemDetails(newValue));
    }
    /**
     * ��������� ��� ��������� ����, ��������� ����������� � ������.
     * ���� ��������� ����� = null, �� ��� ��������� ���� ���������.
     * 
     * @param item � ����� ���� Item ��� null
     */
    private void showItemDetails(Item item) {
        if (item != null) {
            NameLabel.setText(item.getName());
            ArtLabel.setText(item.getArt());
            PriceLabel.setText(item.getPrice()+"");
            mKolLabel.setText(item.getMKol()+"");
            sKolLabel.setText(item.getSKol()+"");
        } else {
            // ���� Item = null, �� ������� ���� �����.
        	 NameLabel.setText("");
             ArtLabel.setText("");
             PriceLabel.setText("");
             mKolLabel.setText("");
             sKolLabel.setText("");
        }
    }
    /**
     * ���������� ������� �����������, ������� ��� �� ���� ������.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main, String path, ObservableList<ItemInTrash> trash) {
        this.main = main;
        this.path = path;
        this.trash = trash;

        // ���������� � ������� ������ �� ������������ ������
        itemTable.setItems(main.getItems());
    }
    
    /**
     * �����, ����������� ����� � �������
     */
    @FXML
    private void addToTrash()
    {
    	
    	if(itemTable.getSelectionModel().getSelectedIndex()>=0)trash.add(new ItemInTrash(itemTable.getSelectionModel().getSelectedItem().getName(),itemTable.getSelectionModel().getSelectedItem().getArt(),itemTable.getSelectionModel().getSelectedItem().getPrice(),main.showKolDialog("m"),main.showKolDialog("s")));
    	else
    	{
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getStage());
            alert.setTitle("�� �������");
            alert.setHeaderText("�� ���� �� ������� �� ������");
            alert.setContentText("����������, �������� ����� � �������.");
            alert.showAndWait();
    	}
    }
    
    /**
     * ����� ��� �������� ������ �� ����
     */
    @FXML
    private void deleteItem()
    {
    	int selectionIndex = itemTable.getSelectionModel().getSelectedIndex();
    	if(selectionIndex>=0)
    	{
    		File fName = new File(path, ArtLabel.getText()+"n.txt");
			File fPrice = new File(path, ArtLabel.getText()+"p.txt");
			File fmKol = new File(path, ArtLabel.getText()+"m.txt");
			File fsKol = new File(path, ArtLabel.getText()+"s.txt");
			fName.delete();
			fPrice.delete();
			fmKol.delete();
			fsKol.delete();
			itemTable.getItems().remove(selectionIndex);
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getStage());
            alert.setTitle("�� �������");
            alert.setHeaderText("�� ���� �� ������� �� ������");
            alert.setContentText("����������, �������� ����� � �������.");

            alert.showAndWait();
    	}
    }
    
    /**
     * ����� ��� �������� ������
     */
    @FXML
    private void newItem()
    {
    	Item tempItem = new Item();
    	if(main.showEditDialog(tempItem, true))
    	{
    		main.getItems().add(tempItem);
    	}
    }
    
    /**
     * ����� ��� ��������� ������ �� ����
     */
    @FXML
    private void editItem()
    {
    	Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            boolean okClicked = main.showEditDialog(selectedItem, false);
            if (okClicked) {
                showItemDetails(selectedItem);
                itemTable.refresh();
            }
    	}
    	else
    	{
    		 Alert alert = new Alert(AlertType.WARNING);
             alert.initOwner(main.getStage());
             alert.setTitle("�� �������");
             alert.setHeaderText("�� ������ �����");
             alert.setContentText("����������, �������� ����� � �������.");

             alert.showAndWait();
    	}
    }
    
    @FXML
    private void copyItem()
    {
    	Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
    	selectedItem = new Item(selectedItem.getName(), "", 0,0,0);
        if (selectedItem != null) {
            boolean okClicked = main.showEditDialog(selectedItem, true);
            if (okClicked) {
                showItemDetails(selectedItem);
                itemTable.refresh();
            }
        }
    }
    
    public TableView<Item> getTable()
    {
    	return itemTable;
    }
    
    /**
     * �����, ����������� ����� �� ��������� ������
     */
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
