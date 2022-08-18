package pack1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Product implements IProduct, Serializable {
	
	private static final long serialVersionUID = 1L;

	public static List<Product> listPro = new ArrayList<>();

	private String productId;
	private String productName;
	private String title;
	private float importPrice;
	private float exportPrice;
	private float profit;
	private String descriptions;
	private boolean productStatus;
	private Categories catalog;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(String productId, String productName, String title, float importPrice, float exportPrice,
			String descriptions, boolean productStatus, Categories catalog) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.title = title;
		this.importPrice = importPrice;
		this.exportPrice = exportPrice;
		this.descriptions = descriptions;
		this.productStatus = productStatus;
		this.catalog = catalog;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) throws Exception {
		if (productId.length() != 4) {
			throw new Exception("Mã sản phẩm phải bao gồm 4 ký tự!");
		}
		if (!productId.toUpperCase().startsWith("C")) {
			throw new Exception("Mã sản phẩm phải bắt đầu bằng ký tự 'C'!");
		}
		for (Product pro : listPro) {
			if (pro.getProductId().toUpperCase().equals(productId.toUpperCase())) {
				throw new Exception("Mã sản phẩm này đã tồn tại!");
			}
		}
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) throws Exception {
		if (productName.length() < 6 || productName.length() > 50) {
			throw new Exception("Tên sản phẩm phải bao gồm 6-50 ký tự!");
		}
		for (Product pro : listPro) {
			if (pro.getProductName().toLowerCase().equals(productName.toLowerCase())) {
				throw new Exception("Tên sản phẩm này đã tồn tại!");
			}
		}
		this.productName = productName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws Exception {
		if (title.length() < 6 || title.length() > 30) {
			throw new Exception("Tiêu đề sản phẩm phải bao gồm 6-30 ký tự!");
		}
		this.title = title;
	}

	public float getImportPrice() {
		return importPrice;
	}

	public void setImportPrice(float importPrice) throws Exception {
		if (importPrice <= 0) {
			throw new Exception("Giá nhập sản phẩm phải là số thực lớn hơn 0");
		}
		this.importPrice = importPrice;
	}

	public float getExportPrice() {
		return exportPrice;
	}

	public void setExportPrice(float exportPrice) throws Exception {
		if (exportPrice < (importPrice + importPrice * MIN_INTEREST_RATE)) {
			throw new Exception(
					"Giá bán sản phẩm là số thực và có giá trị lớn hơn giá nhập ít nhất là MIN_INTEREST_RATE lần (0.2 lần)");
		}
		this.exportPrice = exportPrice;
	}

	public float getProfit() {
		return profit;
	}

	public void setProfit(float profit) {
		this.profit = profit;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) throws Exception {
		if (descriptions.length() == 0) {
			throw new Exception("Mô tả sản phẩm không được để trống");
		}
		this.descriptions = descriptions;
	}

	public boolean isProductStatus() {
		return productStatus;
	}

	public void setProductStatus(boolean productStatus) {
		this.productStatus = productStatus;
	}

	public Categories getCatalog() {
		return catalog;
	}

	public void setCatalog(Categories catalog) {
		this.catalog = catalog;
	}

	@Override
	public void inputData() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean check = true;
		do {
			try {
				System.out.println("Nhập mã sản phẩm: ");
				this.setProductId(sc.nextLine());
				check = true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập tên sản phẩm: ");
				this.setProductName(sc.nextLine());
				check = true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập tiêu đề sản phẩm: ");
				this.setTitle(sc.nextLine());
				check = true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập giá nhập sản phẩm: ");
				this.setImportPrice(Float.parseFloat(sc.nextLine()));
				check = true;
			} catch (NumberFormatException e) {
				System.err.println("Dữ liệu bạn nhập không phải số, mời nhập lại!");
				check = false;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập giá bán sản phẩm: ");
				this.setExportPrice(Float.parseFloat(sc.nextLine()));
				check = true;
			} catch (NumberFormatException e) {
				System.err.println("Dữ liệu bạn nhập không phải số, mời nhập lại!");
				check = false;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập mô tả sản phẩm:");
				this.setDescriptions(sc.nextLine());
				check = true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			System.out.println("Nhập trạng thái sản phẩm: ");
			String sttPro = sc.nextLine();
			if (sttPro.toLowerCase().equals("true") || sttPro.toLowerCase().equals("false")) {
				this.productStatus = Boolean.parseBoolean(sttPro);
				check = true;
			} else {
				System.err.println("Trạng thái sản phẩm phải là 'true' hoặc 'false'");
				check = false;
			}
		} while (!check);
		
		check = false;
		do {
			try {
				System.out.println("Đưa sản phẩm này vào danh mục nào mời nhập ID danh mục đó: ");
				int searchId = Integer.parseInt(sc.nextLine());
				Categories ca = null;
				for (Categories cate : Categories.listCate) {
					if (cate.getCatalogId() == searchId) {
						ca = cate;
						this.catalog = ca;
						System.out.println("Đã đưa sản phẩm này vào danh mục "+searchId);
						check = true;
						break;
					}
				}
				if (check == false) {
					throw new Exception("ID không trùng với danh mục nào, mời nhập lại!");
				}
			} catch (NumberFormatException e) {
				System.err.println("Dữ liệu bạn vừa nhập không phải là một số nguyên dương!");
				check = false;
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} while (!check);

		System.out.println("\n==================================");
	}

	@Override
	public void displayData() {
		// TODO Auto-generated method stub
		System.out.println("\tMã sản phẩm: " + this.productId + " - Tên sản phẩm: " + this.productName);
		System.out.println("\tTiêu đề sản phẩm: " + this.title + " - Giá sản phẩm: " + this.importPrice);
		System.out.println("\tGiá bán sản phẩm: " + this.exportPrice + " - Mô tả sản phẩm: " + this.descriptions);
		System.out.println("\tTrạng thái sản phẩm: " + (this.productStatus ? "Hoạt động" : "Không hoạt động")
				+ " - Sản phẩm thuộc danh mục: " + this.catalog.getCatalogName());
		System.out.println("\n==================================");
	}

	@Override
	public void calProfit() {
		// TODO Auto-generated method stub
		this.profit = this.exportPrice - this.importPrice;
	}
	
	public void updateinfo() {
		Scanner sc = new Scanner(System.in);
		boolean check = true;
		do {
			try {
				System.out.println("Nhập tên sản phẩm: ");
				this.setProductName(sc.nextLine());
				check = true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập tiêu đề sản phẩm: ");
				this.setTitle(sc.nextLine());
				check = true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập giá nhập sản phẩm: ");
				this.setImportPrice(Float.parseFloat(sc.nextLine()));
				check = true;
			} catch (NumberFormatException e) {
				System.err.println("Dữ liệu bạn nhập không phải số, mời nhập lại!");
				check = false;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập giá bán sản phẩm: ");
				this.setExportPrice(Float.parseFloat(sc.nextLine()));
				check = true;
			} catch (NumberFormatException e) {
				System.err.println("Dữ liệu bạn nhập không phải số, mời nhập lại!");
				check = false;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập mô tả sản phẩm:");
				this.setDescriptions(sc.nextLine());
				check = true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			System.out.println("Nhập trạng thái sản phẩm: ");
			String sttPro = sc.nextLine();
			if (sttPro.toLowerCase().equals("true") || sttPro.toLowerCase().equals("false")) {
				this.productStatus = Boolean.parseBoolean(sttPro);
				check = true;
			} else {
				System.err.println("Trạng thái sản phẩm phải là 'true' hoặc 'false'");
				check = false;
			}
		} while (!check);
		
		check = false;
		do {
			try {
				System.out.println("Đưa sản phẩm này vào danh mục nào mời nhập ID danh mục đó: ");
				int searchId = Integer.parseInt(sc.nextLine());
				Categories ca = null;
				for (Categories cate : Categories.listCate) {
					if (cate.getCatalogId() == searchId) {
						ca = cate;
						this.catalog = ca;
						System.out.println("Đã đưa sản phẩm này vào danh mục "+searchId);
						check = true;
						break;
					}
				}
				if (check == false) {
					throw new Exception("ID không trùng với danh mục nào, mời nhập lại!");
				}
			} catch (NumberFormatException e) {
				System.err.println("Dữ liệu bạn vừa nhập không phải là một số nguyên dương!");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} while (!check);
		System.out.println("\n==================================");
	}
}
