package uchet;

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
import java.net.URISyntaxException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uchet.model.Item;
import uchet.model.ItemInTrash;
import uchet.view.ChangeinfController;
import uchet.view.ChooseBDController;
import uchet.view.ChooseTableController;
import uchet.view.EditTovController;
import uchet.view.KolController;
import uchet.view.MenuBarController;
import uchet.view.PrivController;
import uchet.view.SellTovController;
import uchet.view.TovListController;

public class Main extends Application {
	
	private Stage stage;
	private BorderPane menuBar;
	private ObservableList<Item> items = FXCollections.observableArrayList();
	private ObservableList<ItemInTrash> trash = FXCollections.observableArrayList();
	private ObservableList<Item> privoz = FXCollections.observableArrayList();
	private String path;
	private TableView<Item> itemTable;
	private String CompName;
	private File conf;
	private String path1;
	
	public Main() throws URISyntaxException
	{	
		try {
			File f = new File("c:\\Uchet");
			if(!f.exists())
				{
					f.mkdirs();
					f = new File("c:\\Uchet\\conf.txt");
					f.createNewFile();
					OutputStream obj1 = new FileOutputStream(f);
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(obj1));
					out.write("��� �������� \n");
					out.write("c:\\ \n");
					out.close();
					obj1.close();
				}
			f = new File("c:\\Uchet\\conf.txt");
			InputStream obj = new FileInputStream(f);
			BufferedReader in = new BufferedReader(new InputStreamReader(obj));
			CompName = in.readLine();
			in.close();
			obj.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refreshTable()
	{
		itemTable.refresh();
	}
	
	public String getPath()
	{
		return path;
	}
	//jija
	public ObservableList<Item> getItems()
	{
		return items;
	}

	@Override
	public void start(Stage primaryStage) {
		 this.stage = primaryStage;
	     this.stage.setTitle("���� by CannabisMLG");
	     
	     showChooseBD(primaryStage);
	}
	
	public void showChooseBD(Stage primaryStage)
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/ChooseBD.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			ChooseBDController controller = loader.getController();
			controller.setStage(primaryStage);
			controller.setMain(this);
			Scene scene = new Scene(page);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void initMenuBar()
	{
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MenuBar.fxml"));
			menuBar = (BorderPane) loader.load();
			MenuBarController controller = loader.getController();
			controller.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(menuBar);
		stage.setScene(scene);
		stage.show();
	}
	
	public void showTovList()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("TovList.fxml"));
			AnchorPane tovList = (AnchorPane) loader.load();
			menuBar.setCenter(tovList);
			TovListController controller = loader.getController();
			controller.setMainApp(this, path, trash);
			itemTable = controller.getTable(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean showEditDialog(Item item, boolean flag)
	{
		try {
            // ��������� fxml-���� � ������ ����� �����
            // ��� ������������ ����������� ����.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EditTov.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // ������ ���������� ���� Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("��������� ������");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EditTovController controller = loader.getController();
            controller.setFlag(flag);
            controller.setDialogStage(dialogStage);
            controller.setItem(item);
            controller.setPath(path);
            // ���������� ���������� ���� � ���, ���� ������������ ��� �� �������
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public int showKolDialog(String choose)
	{
		try {
            // ��������� fxml-���� � ������ ����� �����
            // ��� ������������ ����������� ����.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Kol.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // ������ ���������� ���� Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("������� ����������");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            KolController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setBase(choose);
            // ���������� ���������� ���� � ���, ���� ������������ ��� �� �������
            dialogStage.showAndWait();
            return controller.getKol();
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
	}
	
	public void showSellFrame()
	{
		try {
            // ��������� fxml-���� � ������ ����� �����
            // ��� ������������ ����������� ����.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/SellTov.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // ������ ���������� ���� Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("�������");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            SellTovController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setCompName(CompName);
            controller.setPath(path);
            controller.setTrash(trash);
            controller.setItems(items);
            double summ = 0;
            for(int i = 0;i < trash.size();i++)
            {
            	summ+=Double.parseDouble(trash.get(i).SummProperty().get());
            }
            controller.setItog(summ);
            // ���������� ���������� ���� � ���, ���� ������������ ��� �� �������
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showPrivFrame()
	{
		try
		{
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("view/Priv.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	
	        // ������ ���������� ���� Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("������");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(stage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	
	        PrivController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMain(this);
            controller.setPath(path);
            controller.setPriv(privoz);
            controller.setItems(items);
	        dialogStage.showAndWait();
		}catch(IOException e) {e.printStackTrace();}
	}
	
	public void showChooseTable()
	{
		try
		{
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("view/Choosetable.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	
	        // ������ ���������� ���� Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("������");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(stage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	
	        ChooseTableController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMain(this, path);
	        dialogStage.showAndWait();
		}catch(IOException e) {e.printStackTrace();}
	}
	
	public void showChangeinfDialog()
	{
		try
		{
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("view/ChangeInf.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	
	        // ������ ���������� ���� Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("���������");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(stage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	
	        ChangeinfController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMain(this);
	        dialogStage.showAndWait();
		}catch(IOException e) {e.printStackTrace();}
	}
	
	public ObservableList<ItemInTrash> getItemsInTrash()
	{
		return trash;
	}
	
	public ObservableList<Item> getPrivoz()
	{
		return privoz;
	}
	
	public void setCompName(String CompName)
	{
		this.CompName = CompName;
	}
	
	public Stage getStage()
	{
		return stage;
	}
	public void setPath(String path)
	{
		this.path = path;
		File workdir = new File(path);
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
	
	public static void main(String[] args) {
		launch(args);
	}
}