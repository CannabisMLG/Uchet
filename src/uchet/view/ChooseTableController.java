package uchet.view;

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
	private ObservableList<Item> titems = FXCollections.observableArrayList();
	
	private Main main;
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
		searchField.setText("");
		search();
		stage.close();
	}
	
	public void setMain(Main main, String path)
	{
		this.main = main;
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
    	if(searchField.getText().trim().equals(""))
    	{
    		items.addAll(titems);
    		privTable.setItems(items);
    		titems.clear();
    	}
    	else
    	{
    		items.addAll(titems);
    		titems.clear();
	    	for(int i = 0; i < items.size(); i++)
	    	{
	    		if(items.get(i).getName().lastIndexOf(searchField.getText().toLowerCase().trim()) != -1 || items.get(i).getArt().lastIndexOf(searchField.getText().toLowerCase().trim()) != -1) 
	    		{
	    			titems.add(items.get(i));
	    			items.remove(i);
	    		}
	    	}
	    	privTable.setItems(titems);
    	}
    }
}
