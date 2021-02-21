package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class InfrastructureDepartment {

	
	public final String SAVE_PATH_FILE = "data/dataBill.gsb";
	public final String FILE_SEPARATOR = "\\|";
	public ArrayList<Billboard> billboards;
	
	public InfrastructureDepartment (){
		billboards = new ArrayList<Billboard>();
		
	}
	
	public void addBillboard (double width, double height, boolean use, String brand) throws IOException {
		billboards.add(new Billboard(width, height, use, brand));
		saveData();
	}
	
	public ArrayList<Billboard> getBillboard() {
		return billboards;
	}
	
	public void saveData() throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
		oos.writeObject(billboards);
		oos.close();
	}
	
	@SuppressWarnings("unchecked")
	public boolean loadData() throws IOException, ClassNotFoundException{
	    File f = new File(SAVE_PATH_FILE);
	    boolean loaded = false;
	    if(f.exists()){
	      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
	      billboards = (ArrayList<Billboard>) ois.readObject();
	      ois.close();
	      loaded = true;
	    }
	    return loaded;
	  }

	public void importData (String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while(line != null) {
			String [] parts = line.split(FILE_SEPARATOR);
			double width = Double.parseDouble(parts[0]);
			double height = Double.parseDouble(parts[1]);
			boolean use = Boolean.parseBoolean(parts[2]);
			String brand = parts[3];
			line = br.readLine();
			addBillboard(width, height, use, brand);
		}
		br.close();
	}
	
	public void exportData (String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);
		int x = 0;
		pw.println("--------------------------");
		pw.println("Dangerous Billboard Report");
		pw.println("--------------------------");
		pw.println("The dangerous billboard are:");
		for (int i = 0; i<billboards.size(); i++) {
			if(billboards.get(i).getArea() > 160000) {
				x++;
				pw.println(x+". Billboard "+billboards.get(i).getBrand()+" with area "+billboards.get(i).getArea());
			}
		}
		pw.close();
	}
}

