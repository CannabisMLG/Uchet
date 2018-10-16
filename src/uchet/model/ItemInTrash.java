package uchet.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ItemInTrash {
	private StringProperty Name;
	private StringProperty Art;
	private IntegerProperty Price;
	private DoubleProperty mKol;
	private DoubleProperty sKol;
	
	public ItemInTrash(String name, String art, int price, int mkol, int skol)
	{
		Name = new SimpleStringProperty(name);
		Art = new SimpleStringProperty(art);
		Price = new SimpleIntegerProperty(price);
		mKol = new SimpleDoubleProperty(mkol);
		sKol = new SimpleDoubleProperty(skol);
	}
	
	public StringProperty nameProperty()
	{
		return Name;
	}
	public StringProperty artProperty()
	{
		return Art;
	}
	public StringProperty priceProperty()
	{
		return  new SimpleStringProperty(Price.get()+"");
	}
	public StringProperty sKolProperty()
	{
		return new SimpleStringProperty(sKol.get()+"");
	}
	public StringProperty mKolProperty()
	{
		return new SimpleStringProperty(mKol.get()+"");
	}
	public StringProperty SummProperty()
	{
		double Kol = sKol.get() + mKol.get();
		return new SimpleStringProperty((Kol*Price.get())+"");
	}
	public void setsKol(int kol)
	{
		sKol = new SimpleDoubleProperty(kol);
	}
	public void setmKol(int kol)
	{
		mKol = new SimpleDoubleProperty(kol);
	}
	public String getName()
	{
		return Name.get();
	}
	public String getArt()
	{
		return Art.get();
	}
	public int getPrice()
	{
		return Price.get();
	}
	public double getsKol()
	{
		return sKol.get();
	}
	public double getmKol()
	{
		return mKol.get();
	}
}
