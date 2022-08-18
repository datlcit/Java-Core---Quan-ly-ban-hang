package pack1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Categories implements ICategories, Serializable {
	
	private static final long serialVersionUID = 1L;

	public static List<Categories> listCate = new ArrayList<>();

	private int catalogId;
	private String catalogName;
	private String descriptions;
	private boolean catalogStatus;
	private int parentId;

	public Categories() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Categories(int catalogId, String catalogName, String descriptions, boolean catalogStatus, int parentId) {
		super();
		this.catalogId = catalogId;
		this.catalogName = catalogName;
		this.descriptions = descriptions;
		this.catalogStatus = catalogStatus;
		this.parentId = parentId;
	}

	public int getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(int catalogId) throws Exception {
		if (catalogId <= 0) {
			throw new Exception("Mã danh mục phải là 1 số nguyên lớn hơn 0");
		}
		for (Categories cate : listCate) {
			if (cate.getCatalogId() == catalogId) {
				throw new Exception("Mã danh mục đã tồn tại");
			}
		}
		this.catalogId = catalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) throws Exception {
		if (catalogName.length() < 6 || catalogName.length() > 30) {
			throw new Exception("Tên danh mục phải gồm từ 6-30 ký tự!");
		}
		this.catalogName = catalogName;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) throws Exception {
		if (descriptions.length() == 0) {
			throw new Exception("Phần mô tả không được để trống!");
		}
		this.descriptions = descriptions;
	}

	public boolean isCatalogStatus() {
		return catalogStatus;
	}

	public void setCatalogStatus(boolean catalogStatus) {
		this.catalogStatus = catalogStatus;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) throws Exception {

		// Xét điều kiện nếu nhập trùng parentId và catalogId hoặc không tồn tại parentId này và bắt buộc phải nhập parentId=0
		int count = 0;
		for (Categories cate : listCate) {
			if (cate.getCatalogId() == parentId) {
				count = 1;
				//Thỏa mãn tồn tại 1 catalogId để đặt làm parentId
			}
		}
		if (count == 0 && parentId != 0) {
			throw new Exception("1. Danh mục cha của danh mục này không được trùng với mã danh mục của chính nó"
					+ "\n2. Không tồn tại mã danh mục này nên không thể đặt nó làm danh mục cha."
					+ "\nHãy nhập mã danh mục cha khác hoặc nhập '0' để chọn đây là danh mục gốc ");
		}

		//Xét nếu đã đủ 3 cấp danh mục thì phải chọn parentId khác
		for (Categories cate : listCate) {
			if (cate.getCatalogId() == parentId && cate.getParentId() != 0) {
				for (Categories _cate : listCate) {
					if (cate.getParentId() == _cate.getCatalogId() && _cate.getParentId() != 0) {
						throw new Exception("Đã chứa tối đa 3 cấp danh mục, vui lòng chọn mã danh mục cha khác");
					}
				}
			}
		}

		this.parentId = parentId;
	}

	@Override
	public void inputData() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);

		boolean check = true;

		do {
			try {
				System.out.println("Nhập mã danh mục: ");
				this.setCatalogId(Integer.parseInt(sc.nextLine()));
				check = true;
			} catch (NumberFormatException e) {
				System.err.println("Dữ liệu bạn nhập không phải một số nguyên dương!");
				check = false;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập tên danh mục: ");
				this.setCatalogName(sc.nextLine());
				check = true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập mô tả danh mục: ");
				this.setDescriptions(sc.nextLine());
				check = true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);

		do {
			System.out.println("Nhập Trạng thái danh mục: ");
			String stt = sc.nextLine();
			if (stt.toLowerCase().equals("true") || stt.toLowerCase().equals("false")) {
				this.setCatalogStatus(Boolean.parseBoolean(stt));
				check = true;
			} else {
				System.err.println("Trạng thái phải là \"true\" hoặc \"fasle\"");
				check = false;
			}
		} while (!check);

		do {
			try {
				System.out.println("Nhập mã danh mục cha: ");
				this.setParentId(Integer.parseInt(sc.nextLine()));
				check = true;
			} catch (NumberFormatException e) {
				System.err.println("Bạn phải nhập một số nguyên dương lớn hơn hoặc bằng 0!");
				check = false;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				check = false;
			}
		} while (!check);
		System.out.println("\n==========================");
	}

	@Override
	public void displayData() {
		// TODO Auto-generated method stub
		System.out.println("\tMã danh mục: " + this.catalogId +" - Tên danh mục: " + this.catalogName);
		System.out.println("\tMô tả danh mục: " + this.descriptions);
		System.out.println("\tTrạng thái danh mục: " + (this.catalogStatus ? "Hoạt động" : "Không hoạt động") + " - Mã danh mục cha: " + this.parentId);
		System.out.println("\n==========================");
	}

}
