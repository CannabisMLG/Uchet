package uchet.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
	private StringProperty Name;
	private StringProperty Art;
	private IntegerProperty Price;
	private DoubleProperty mKol;
	private DoubleProperty sKol;
	private double skol, mkol;
	
	public Item()
	{
		this(null, null, 0, 0.0, 0.0);
	}
	
	public Item(String aName, String aArt,int aPrice, double amKol, double asKol)
	{
		skol = asKol;
		mkol = amKol;
		Name = new SimpleStringProperty(aName);
		Art = new SimpleStringProperty(aArt);
		Price = new SimpleIntegerProperty(aPrice);
		mKol = new SimpleDoubleProperty(amKol);
		sKol = new SimpleDoubleProperty(asKol);
		
	}
	public String getName()
	{
		return Name.get();
	}
	public String getArt()
	{
		return Art.get();
	}
	public double getMKol()
	{
		return mKol.get();
	}
	public int getPrice()
	{
		return Price.get();
	}
	public double getSKol()
	{
		return sKol.get();
	}
	public void setName(String s)
	{
		Name = new SimpleStringProperty(s);
	}
	public void setArt(String a)
	{
		Art = new SimpleStringProperty(a);
	}
	public void setPrice(int aPrice)
	{
		Price = new SimpleIntegerProperty(aPrice);
	}
	public void setMKol(double amKol)
	{
		mkol = amKol;
		mKol = new SimpleDoubleProperty(amKol);
	}
	public void setSKol(double asKol)
	{
		skol = asKol;
		sKol = new SimpleDoubleProperty(asKol);
	}
	public StringProperty nameProperty()
	{
		return Name;
	}
	public StringProperty artProperty()
	{
		return Art;
	}
	public SimpleStringProperty mKolProperty()
	{
		return new SimpleStringProperty(mKol.get()+"");
	}
	public SimpleStringProperty sKolProperty()
	{
		return new SimpleStringProperty(sKol.get()+"");
	}
	public SimpleIntegerProperty priceProperty()
	{
		return  (SimpleIntegerProperty) Price;
	}
	public StringProperty KolProperty()
	{
		String kol = (skol+mkol)+"";
		StringProperty Kol = new SimpleStringProperty(kol);
		return (StringProperty) Kol;
	}
}
