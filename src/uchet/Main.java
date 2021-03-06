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
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uchet.model.*;
import uchet.view.ChangeinfController;
import uchet.view.CheckPerController;
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
	private ObservableList<Item> items = FXCollections.observableArrayList(), itemsclone = FXCollections.observableArrayList();
	private ObservableList<ItemInTrash> trash = FXCollections.observableArrayList();
	private ObservableList<Item> privoz = FXCollections.observableArrayList();
	private String path;
	private TableView<Item> itemTable;
	private String CompName;
	private TovListController tlc;
	private double tCash = 0;
	
	/**
	 * � ���� ������ ����������� ���������� �� ���� conf.txt � ���� �� �����������, �� �� ��������� � ����������� 
	 * ���������� �� ���������
	 * @throws URISyntaxException
	 */
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
					out.write("��� ����������� \n");
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
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		 stage = primaryStage;
		 stage.setResizable(false);
	     stage.setTitle("���� by CannabisMLG");
	     
	     showChooseBD(primaryStage);
	}
	
	/**
	 * �����, ���������� �� �������� � ����������� ���� ������ ��
	 * @param primaryStage
	 */
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
	
	/**
	 * �����, ���������������� � ����������� Menubar
	 */
	public void initMenuBar()
	{
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MenuBar.fxml"));
			menuBar = (BorderPane) loader.load();
	        stage.setResizable(true);
			MenuBarController controller = loader.getController();
			controller.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(menuBar);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * �����, ������������ ��� ������ �� ������ ������ � ��������� ������ �������
	 */
	public void recoverySearch()
	{
		tlc.getSS().setText("");
		tlc.search();
	}
	
	public void showTovList()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/TovList.fxml"));
			AnchorPane tovList = (AnchorPane) loader.load();
			menuBar.setCenter(tovList);
			TovListController controller = loader.getController();
			controller.setMainApp(this, path, trash);
			tlc = controller.get();
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
	        dialogStage.setResizable(false);
            dialogStage.setTitle("��������� ������");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EditTovController controller = loader.getController();
            controller.setFlag(flag);
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setItem(item);
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
	        dialogStage.setResizable(false);
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
	        dialogStage.setResizable(false);
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
	        dialogStage.setResizable(false);
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
	
	public void showCheckPerDialog()
	{
		try
		{
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("view/CheckPer.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	
	        // ������ ���������� ���� Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setResizable(false);
	        dialogStage.setTitle("�������� ������� �� ������");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(stage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	
	        CheckPerController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMain(this);
	        dialogStage.showAndWait();
		}catch(IOException e) {e.printStackTrace();}
	}
	
	/**
	 * ����� ��� ��������� ������� �� �������
	 * @return
	 */
	public ObservableList<ItemInTrash> getItemsInTrash()
	{
		return trash;
	}
	/**
	 * ����� ��� ��������� ������� �� ������
	 * @return
	 */
	public ObservableList<Item> getPrivoz()
	{
		return privoz;
	}
	
	/**
	 * ����� ��� ��������� ����� ��������
	 * @param CompName - ����� ���
	 */
	public void setCompName(String CompName)
	{
		this.CompName = CompName;
	}
	
	/**
	 * �����, ������������ �������� ����
	 * @return
	 */
	public Stage getStage()
	{
		return stage;
	}
	
	/**
	 * �����, ��������������� ���� � �����, ���������� ��,
	 * ��������� xml-���� ��� ������������� � ����������� �����
	 * ��� ���������� ������� �� xml-�����
	 * @param path
	 */
	public void setPath(String path)
	{
		this.path = path;
		File bd = new File(path + "\\bd.xml");
		if(!bd.exists())
		{
			try {
				bd.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		File file = new File(path + "\\�������");
		if(!file.exists())
		{
			file.mkdirs();
		}
		File tcash = new File(path + "\\�������\\" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + 
					"-" + cal.get(Calendar.DATE) + ".txt");
		if(!tcash.exists())
		{
			try {
				tcash.createNewFile();
				OutputStream obj = new FileOutputStream(tcash);
	 			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(obj));
	 			out.write("0");
	 			out.close();
	 			obj.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		loadItemsFromFile();
	}
	
	/**
	 * ����� ��� ������ ������� �� xml-�����
	 */
	public void loadItemsFromFile()
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(
					ItemList.class);
			Unmarshaller um = context.createUnmarshaller();
			
			ItemList list = (ItemList) um.unmarshal(new File(path + "\\bd.xml"));
			System.out.println(list.getItems());
			items.addAll(list.getItems());
			
			list = (ItemList) um.unmarshal(new File(path + "\\bd.xml"));
			itemsclone.addAll(list.getItems());
			
 			GregorianCalendar cal = new GregorianCalendar();
			InputStream obj = new FileInputStream(path + "\\�������\\" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + 
 					"-" + cal.get(Calendar.DATE) + ".txt");
 			BufferedReader in = new BufferedReader(new InputStreamReader(obj));
 			tCash = Double.parseDouble(in.readLine());
 			in.close();
 			obj.close();
			
		}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * ����� ��� ������ ������� � xml-�����
	 */
	public void saveItemsInFile()
	{
		try {
			JAXBContext context = JAXBContext.newInstance(ItemList.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			ItemList list = new ItemList();
			System.out.println(items);
			list.setItems(items);
			m.marshal(list, new File(path + "\\bd.xml"));
			GregorianCalendar cal = new GregorianCalendar();
			OutputStream obj = new FileOutputStream(path + "\\�������\\" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + 
 					"-" + cal.get(Calendar.DATE) + ".txt");
 			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(obj));
 			out.write(getTCash() + "");
 			out.close();
 			obj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����, ����������� �������
	 */
	public void refreshTable()
	{
		itemTable.refresh();
	}
	
	/**
	 * ����� ��� ��������� ���� � ��
	 * @return ���� ��
	 */
	public String getPath()
	{
		return path;
	}
	
	/**
	 * ���������� ��� �������� �� �������
	 * @return
	 */
	public ObservableList<Item> getItems()
	{
		return items;
	}
	
	public ObservableList<Item> get�Items()
	{
		return itemsclone;
	}
	
	public void plusTCash(double cash)
	{
		tCash+=cash;
	}
	
	public double getTCash()
	{
		return tCash;
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
