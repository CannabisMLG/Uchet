package uchet.view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uchet.Main;
import uchet.model.Item;
import uchet.model.ItemInTrash;

public class SellTovController {
	private Stage stage;
	
	@FXML
	private TableView<ItemInTrash> sellTable;
	@FXML
	private TableColumn<ItemInTrash, String> nameColumn;
	@FXML
	private TableColumn<ItemInTrash, String> artColumn;
	@FXML
	private TableColumn<ItemInTrash, String> priceColumn;
	@FXML
	private TableColumn<ItemInTrash, String> mkolColumn;
	@FXML
	private TableColumn<ItemInTrash, String> skolColumn;
	@FXML
	private TableColumn<ItemInTrash, String> summColumn;
	@FXML
	private Label itog;
	private GregorianCalendar cal;
	private double summ;
	
	private String path;
	private String fileName;
	private Main main;
	
	private String CompName;
	
	
	private ObservableList<ItemInTrash> trash = FXCollections.observableArrayList();
	private ObservableList<Item> items = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        artColumn.setCellValueFactory(cellData -> cellData.getValue().artProperty());
        skolColumn.setCellValueFactory(cellData -> cellData.getValue().sKolProperty());
        mkolColumn.setCellValueFactory(cellData -> cellData.getValue().mKolProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        summColumn.setCellValueFactory(cellData -> cellData.getValue().SummProperty());
		cal = new GregorianCalendar();
	}
	/**
	 * Метод, устанавливающий итог в Label
	 * @param summa - итог, который будет записан
	 */
	public void setItog(double summa)
	{
		summa*=100;
		int temp = (int)summa;
		summa = temp/100;
		summ = summa;
		itog.setText("Итог: "+summa);
	}
	
	/**
	 * Метод, убирающий скидку
	 */
	@FXML 
	private void nullpercent()
	{
		double summ = 0;
        for(int i = 0;i < trash.size();i++)
        {
        	summ+=Double.parseDouble(trash.get(i).SummProperty().get());
        }
        setItog(summ);
		sellTable.refresh();
	}
	/**
	 * Метод, скидка 2%
	 */
	@FXML 
	private void twopercent()
	{
		double summ = 0;
        for(int i = 0;i < trash.size();i++)
        {
        	summ+=Double.parseDouble(trash.get(i).SummProperty().get());
        }
        setItog(summ*0.98);
		sellTable.refresh();
	}
	/**
	 * Метод, скидка 3%
	 */
	@FXML 
	private void threepercent()
	{
		double summ = 0;
        for(int i = 0;i < trash.size();i++)
        {
        	summ+=Double.parseDouble(trash.get(i).SummProperty().get());
        }
        setItog(summ*0.97);
		sellTable.refresh();
	}
	/**
	 * Метод, скидка 4%
	 */
	@FXML 
	private void fourpercent()
	{
		double summ = 0;
        for(int i = 0;i < trash.size();i++)
        {
        	summ+=Double.parseDouble(trash.get(i).SummProperty().get());
        }
        setItog(summ*0.96);
		sellTable.refresh();
	}
	/**
	 * Метод, скидка 5%
	 */
	@FXML 
	private void fivepercent()
	{
		double summ = 0;
        for(int i = 0;i < trash.size();i++)
        {
        	summ+=Double.parseDouble(trash.get(i).SummProperty().get());
        }
        setItog(summ*0.95);
		sellTable.refresh();
	}
	
	/**
	 * Метод, очищающий корзину
	 */
	@FXML
	private void clear()
	{
		int size = trash.size();
		for(int i = 0;i < size; i++)
		{
			trash.remove(0);
		}
		stage.close();
	}
	
	public void setItems(ObservableList<Item> items)
	{
		this.items = items;
	}
	
	/**
	 * Метод, удаляющий выбранный товар из корзины
	 */
	@FXML
	private void deleteItem()
	{
		int index = sellTable.getSelectionModel().getSelectedIndex();
		if(sellTable.getSelectionModel().getSelectedIndex()>=0) 
			{
				trash.remove(index);
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
	/**
	 * Метод, изменяющий количество товара в корзине
	 */
	@FXML
	private void editKol()
	{
		if(sellTable.getSelectionModel().getSelectedIndex()>=0) 
		{
			trash.get(sellTable.getSelectionModel().getSelectedIndex()).setmKol(main.showKolDialog("m"));
			trash.get(sellTable.getSelectionModel().getSelectedIndex()).setsKol(main.showKolDialog("s"));
			double summ = 0;
            for(int i = 0;i < trash.size();i++)
            {
            	summ+=Double.parseDouble(trash.get(i).SummProperty().get());
            }
            setItog(summ);
			sellTable.refresh();
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
	
	/**
	 * Метод, подтвержающий продажу и обновляющий кол-во товаров
	 */
	@FXML
	private void ok()
	{
		for(int i = 0;i<trash.size();i++)
		{
			for(int j = 0;j<items.size();j++)
			{
				if(items.get(j).artProperty().get().equals(trash.get(i).artProperty().get())) 
					{
						items.get(j).setSKol(items.get(j).getSKol() - Double.parseDouble(trash.get(i).sKolProperty().get()));
						items.get(j).setMKol(items.get(j).getMKol() - Double.parseDouble(trash.get(i).mKolProperty().get()));
					}
			}
		}
		saveCheck();
		clear();
		main.plusTCash(Double.parseDouble(itog.getText().substring(6, itog.getText().length()).toString()));
		main.saveItemsInFile();
		main.refreshTable();
		stage.close();
	}
	
	public void setMain(Main main)
	{
		this.main = main;
		sellTable.setItems(main.getItemsInTrash());
	}
	
	public void setTrash(ObservableList<ItemInTrash> trash)
	{
		this.trash = trash;
	}
	/**
	 * Метод устанавливающий путь для сохранения чека и обновления данных в файлах
	 * @param path - путь к базе
	 */
	public void setPath(String path)
	{
		this.path = path;
		fileName = path + "\\Чеки\\" + cal.get(Calendar.YEAR) + "\\" + (cal.get(Calendar.MONTH)+1) + 
				"\\" + cal.get(Calendar.DATE) + "\\" + cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)+"-"
				+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.HOUR)+"-"+cal.get(Calendar.MINUTE)+"-"+cal.get(Calendar.SECOND) +
				".xls";
	}

	public void setDialogStage(Stage dialogStage) {
        stage = dialogStage;
	}
	
	/**
	 * Метод, выполняющий печать чека
	 */
	@FXML
	private void print()
	{
		saveCheck();
		try {
			Desktop.getDesktop().print(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		clear();
		main.refreshTable();
		stage.close();
	}
	
	/**
	 * Метод, сохраняющий чек в виде excel таблицы
	 */
	private void saveCheck()
	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = createCellStyleForTable(workbook);
		HSSFSheet sheet = workbook.createSheet("List 1");
		Row rowShapka = sheet.createRow(0);
		rowShapka.createCell(0).setCellValue("Товарный чек");
		rowShapka = sheet.createRow(1);
		rowShapka.createCell(0).setCellValue(CompName);
		Row row = sheet.createRow(3);
		sheet.setColumnWidth(0, 9000);
		sheet.setColumnWidth(3, 2000);
		sheet.setColumnWidth(4, 2000);
		row.createCell(0).setCellValue("Наименование");
		row.createCell(1).setCellValue("Артикул");
		row.createCell(2).setCellValue("Цена");
		row.createCell(3).setCellValue("К-во м.");
		row.createCell(4).setCellValue("К-во ск.");
		row.createCell(5).setCellValue("Сумма");
		row.getCell(0).setCellStyle(style);
		row.getCell(1).setCellStyle(style);
		row.getCell(2).setCellStyle(style);
		row.getCell(3).setCellStyle(style);
		row.getCell(4).setCellStyle(style);
		row.getCell(5).setCellStyle(style);
		for(int i = 4;i<=trash.size()+3;i++)
		{
			Row row1 = sheet.createRow(i);
			row1.createCell(0).setCellValue(trash.get(i-4).getName());
			row1.createCell(1).setCellValue(trash.get(i-4).getArt());
			row1.createCell(2).setCellValue(trash.get(i-4).getPrice());
			row1.createCell(3).setCellValue(trash.get(i-4).getmKol());
			row1.createCell(4).setCellValue(trash.get(i-4).getsKol());
			row1.createCell(5).setCellValue(summColumn.getCellData(i-4));
			row1.getCell(0).setCellStyle(style);
			row1.getCell(1).setCellStyle(style);
			row1.getCell(2).setCellStyle(style);
			row1.getCell(3).setCellStyle(style);
			row1.getCell(4).setCellStyle(style);
			row1.getCell(5).setCellStyle(style);
		}
		Row row1 = sheet.createRow(trash.size() + 4);
		row1.createCell(4).setCellValue("Итог:");
		row1.createCell(5).setCellValue(summ);
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setAlignment(HorizontalAlignment.RIGHT);
		row1.getCell(4).setCellStyle(style1);
		style1.setAlignment(HorizontalAlignment.LEFT);
		row1.getCell(5).setCellStyle(style1);
		row1 = sheet.createRow(trash.size() + 6);
		row1.createCell(3).setCellValue("Подпись________________");
		row1.createCell(0).setCellValue(cal.get(Calendar.DATE)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));
		File file = new File(path + "\\Чеки\\" + cal.get(Calendar.YEAR));
		if(!file.exists())
		{
			file.mkdirs();
		}
		file = new File(path + "\\Чеки\\" + cal.get(Calendar.YEAR) + "\\" + (cal.get(Calendar.MONTH)+1));
		if(!file.exists())
		{
			file.mkdirs();
		}
		file = new File(path + "\\Чеки\\" + cal.get(Calendar.YEAR) + "\\" + (cal.get(Calendar.MONTH)+1) + "\\" + cal.get(Calendar.DATE));
		if(!file.exists())
		{
			file.mkdirs();
		}
		try (FileOutputStream out = new FileOutputStream(new File(fileName))) {
            workbook.write(out);
            System.out.println("yay");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 
	 * @param book
	 * @return
	 */
	private HSSFCellStyle createCellStyleForTable(HSSFWorkbook book)
	{
	    BorderStyle thin  = BorderStyle.THIN;
	    short       black = IndexedColors.BLACK.getIndex();

	   HSSFCellStyle style = book.createCellStyle();
	    style.setBorderTop        (thin);
	    style.setBorderBottom     (thin);
	    style.setBorderRight      (thin);
	    style.setBorderLeft       (thin);

	    style.setTopBorderColor   (black);
	    style.setRightBorderColor (black);
	    style.setBottomBorderColor(black);
	    style.setLeftBorderColor  (black);

	    return style;
	}
	public void setCompName(String CompName) {
		this.CompName = CompName;
	}
}
