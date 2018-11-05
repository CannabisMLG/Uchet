package uchet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import uchet.model.Item;
import uchet.model.ItemList;

public class util {

	public static void main(String[] args) {
		ArrayList<Item> items = new ArrayList<>();
		String path = "c:\\BDmasterok";
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
		try {
			JAXBContext context = JAXBContext.newInstance(ItemList.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			(new File(path + "\\bd.xml")).createNewFile();
			ItemList list = new ItemList();
			System.out.println(items);
			list.setItems(items);
			
			m.marshal(list, new File(path + "\\bd.xml"));
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
