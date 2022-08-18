package pack1;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileObject {

//	//CÁCH 1: CÁCH THƯỜNG
//	// GHI FILE OBJECT
//
//	public static void saveCategories() {
//		try {
//			File f = new File("D:\\Code Eclipse\\categories.txt");
//			FileOutputStream fos = new FileOutputStream(f);
//			ObjectOutputStream oos = new ObjectOutputStream(fos);
//			oos.writeObject(Categories.listCate);
//			fos.close();
//			oos.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public static void saveProducts() {
//		try {
//			File f = new File("D:\\Code Eclipse\\products.txt");
//			FileOutputStream fos = new FileOutputStream(f);
//			ObjectOutputStream oos = new ObjectOutputStream(fos);
//			oos.writeObject(Product.listPro);
//			fos.close();
//			oos.close();
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//
//	// ĐỌC FILE OBJECT
//
//	public static void setCategories() {
//		try {
//			File f = new File("D:\\Code Eclipse\\categories.txt");
//			FileInputStream fis = new FileInputStream(f);
//			ObjectInputStream ois = new ObjectInputStream(fis);
//			
//			Categories.listCate = (List<Categories>) ois.readObject();
//			
//			fis.close();
//			ois.close();
//			
//		} catch (EOFException e) {
//			// TODO: handle exception
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//
//	public static void setProducts() {
//		try {
//			File f = new File("D:\\Code Eclipse\\products.txt");
//			FileInputStream fis = new FileInputStream(f);
//			ObjectInputStream ois = new ObjectInputStream(fis);
//			
//			Product.listPro = (List<Product>) ois.readObject();
//			
//			fis.close();
//			ois.close();
//			
//		} catch (EOFException e) {
//			// TODO: handle exception
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
	
	//CÁCH 2: SỬ DỤNG GENERIC

	// GHI FILE OBJECT

		public static void saveCategories() {
			writeGenericObject(Categories.listCate, "D:\\Code Eclipse\\categories.txt");
		}

		public static void saveProducts() {
			writeGenericObject(Product.listPro, "D:\\Code Eclipse\\products.txt");
		}

		// ĐỌC FILE OBJECT

		public static void setCategories() {
			Categories.listCate = readGenericObject("D:\\Code Eclipse\\categories.txt", Categories.class);
		}

		public static void setProducts() {
			Product.listPro = readGenericObject("D:\\Code Eclipse\\products.txt", Product.class);
		}
		
		public static <T> void writeGenericObject(List <T> data, String path) {
			try {
				File f = new File(path);
				FileOutputStream fos = new FileOutputStream(f);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(data);
				fos.close();
				oos.close();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		public static <T> List<T> readGenericObject(String path, Class <T> source) {
			try {
				File f = new File(path);
				FileInputStream fis = new FileInputStream(f);
				@SuppressWarnings("resource")
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				@SuppressWarnings("unchecked")
				List<T> data = (List<T>) ois.readObject();
				return data;
				
			} catch (EOFException e) {
				// TODO: handle exception
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return new ArrayList<>();
		}
}
