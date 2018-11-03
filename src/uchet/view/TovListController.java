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
	private TextField searchField;
	
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
	private ObservableList<Item> titems = FXCollections.observableArrayList();
	
	Main main;
	private String path;

	public TovListController() {}
	
	 /**
     * ������������� ������-�����������. ���� ����� ���������� �������������
     * ����� ����, ��� fxml-���� ����� ��������.
     */
    @FXML
    private void initialize() {
        // ������������� ������� ������� � ����� ���������.
        NameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ArtColumn.setCellValueFactory(cellData -> cellData.getValue().artProperty());
        KolColumn.setCellValueFactory(cellData -> cellData.getValue().KolProperty());
     // ������� �������������� ���������� � ������.
        showItemDetails(null);

        // ������� ��������� ������, � ��� ��������� ����������
        // �������������� ���������� � ������.
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
    public TextField getSS()
    {
    	return searchField;
    }
    public TovListController get()
    {
    	return this;
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
    	
    	if(itemTable.getSelectionModel().getSelectedIndex()>=0) trash.add(new ItemInTrash(itemTable.getSelectionModel().getSelectedItem().getName(),itemTable.getSelectionModel().getSelectedItem().getArt(),itemTable.getSelectionModel().getSelectedItem().getPrice(),main.showKolDialog("m"),main.showKolDialog("s")));
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
    		main.getItems().remove(selectionIndex);
    		main.saveItemsInFile();
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
    		main.saveItemsInFile();
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
   			 	main.saveItemsInFile();
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
    public void search()
    {
    	ObservableList<Item> items = main.getItems();
    	if(searchField.getText().trim().equals(""))
    	{
    		System.out.println(items);
    		for(int i = 0; i  < titems.size(); i++) items.add(titems.get(i));
    		System.out.println(items);
    		itemTable.setItems(items);
    	}
    	else
    	{
    		titems.clear();
	    	for(int i = 0; i < items.size(); i++)
	    	{
	    		if(items.get(i).getName().lastIndexOf(searchField.getText().toLowerCase().trim()) != -1 || items.get(i).getArt().lastIndexOf(searchField.getText().toLowerCase().trim()) != -1) 
	    		{
	    			titems.add(items.get(i));
	    			items.remove(i);
	    		}
	    	}
	    	itemTable.setItems(titems);
    	}
    }
}
