package pack1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShopManagement {
//	static boolean check = true;

	// Hàm main
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		// Gọi 2 phương thức đọc dữ liệu của 2 danh sách để cập nhật lại danh sách sau
		// mỗi lần chạy chương trình
		FileObject.setCategories();
		FileObject.setProducts();
		// Gọi menu chính ra ngay khi chạy chương trình
		mainMenu(sc);
	}

	// Hàm menu chính
	public static void mainMenu(Scanner sc) {
		// 2 dòng do va try bên dưới đê xét ngoại lệ bắt lỗi nếu nhập yêu cầu sai và bắt
		// nhập lại (không nhập số nguyên mà nhập kiểu dữ liệu khác)
		boolean check = true;
		do {
			try {
				do {
					System.out.println("\n*************************MENU********************");
					System.out.println("1. Quản lý danh mục");
					System.out.println("2. Quản lý sản phẩm");
					System.out.println("3. Thoát");
					int choice1;
					System.out.println("Sự lựa chọn của bạn là: ");
					choice1 = Integer.parseInt(sc.nextLine());
					switch (choice1) {
					case 1:
						categoriesMenu(sc);
						break;
					case 2:
						// Hàm if dưới xét trường hợp chưa tồn tại danh mục nào nhưng lại chọn "Thêm sản
						// phẩm mới"
						if (Categories.listCate.size() == 0) {
							System.err.println(
									"Trước khi làm việc với menu QUẢN LÝ SẢN PHẨM bạn phải có ít nhất 1 danh mục đã tạo, hãy chọn 'Thêm danh mục' để tạo danh mục mới");
							categoriesMenu(sc);
						}
						productsMenu(sc);
						break;
					case 3:
						System.exit(0);
						break;
					default:
						System.err.println("Bạn đã chọn sai chức năng, hãy chọn lại!");
					}
				} while (true);
				// Đoạn catch bắt lỗi nhập yêu cầu chọn chức năng ban đầu
			} catch (Exception e) {
				System.err.println("Dữ liệu bạn nhập không phải một số nguyên, mời nhập lại:");
				check = false;
			}
		} while (!check);
	}

	// Hàm Menu QUẢN LÝ DANH MỤC
	public static void categoriesMenu(Scanner sc) {
		// 2 dòng do va try bên dưới đê xét ngoại lệ bắt lỗi nếu nhập yêu cầu sai và bắt
		// nhập lại (không nhập số nguyên mà nhập kiểu dữ liệu khác)
		boolean check = true;
		do {
			try {
				do {
					System.out.println("\n*************************QUẢN LÝ DANH MỤC********************");
					System.out.println("1. Danh sách danh mục");
					System.out.println("2. Thêm danh mục");
					System.out.println("3. Xóa danh mục");
					System.out.println("4. Tìm kiếm danh mục");
					System.out.println("5. Quay lại");
					System.out.println("Sự lựa chọn của bạn là: ");
					int choice2 = Integer.parseInt(sc.nextLine());
					switch (choice2) {

					case 1:
						// Hàm funtionalConditionsCategories(sc) để ra điều kiện tiên quyết là phải Thêm
						// danh mục trước khi sử dụng các chức năng khác
						funtionalConditionsCategories(sc);
						categoriesShowMenu(sc);
						break;

					case 2:
						System.out.println("Nhập số danh mục muốn thêm (nhập một số nguyên lớn hơn 0):");
						do {
							try {
								int addCates = Integer.parseInt(sc.nextLine());
								if (addCates <= 0) {
									throw new Exception("Bạn phải nhập một số nguyên lớn hơn 0, mời nhập lại:");
								}
								for (int i = 0; i < addCates; i++) {
									Categories cate = new Categories();
									cate.inputData();
									Categories.listCate.add(cate);
									check = true;
								}
							} catch (NumberFormatException e) {
								System.err.println(
										"Dữ liệu bạn vừa nhập không phải là một số nguyên dương, mời nhập lại:!");
								check = false;
							} catch (Exception e) {
								System.err.println(e.getMessage());
								check = false;
							}

						} while (!check);
						// Ghi lại danh sách sau khi thêm vào file categories.txt
						FileObject.saveCategories();
						break;

					case 3:
						funtionalConditionsCategories(sc);
						check = false;
						do {
							try {
								System.out.println(
										"Nhập mã danh mục của danh mục muốn xóa (lưu ý nếu xóa danh mục thì toàn bộ sản phẩm "
										+ "nằm trong danh mục ấy cũng sẽ mất theo và chỉ có thể xóa những danh mục "
										+ "không có danh mục con):");
								int cateIdRemove = Integer.parseInt(sc.nextLine());
								for (Categories cate : Categories.listCate) {
									if (cate.getCatalogId() == cateIdRemove) {
										for (Categories cate2 : Categories.listCate) {
											if (cate2.getParentId() == cate.getCatalogId()) {
												System.err.println(
														"Danh mục này có chứa danh mục con nên bạn không thể xóa."
																+ " Nếu muốn xóa, hãy xóa các danh mục con trước!");
												categoriesMenu(sc);
											}
										}
										break;
									}
								}
								List<Categories> removeCateList = new ArrayList<>();
								for (Categories cate : Categories.listCate) {
									if (cate.getCatalogId() == cateIdRemove) {
										removeCateList.add(cate);
										List<Product> removeProList = new ArrayList<>();
										for (Product pro : Product.listPro) {
											if (pro.getCatalog().getCatalogId() == cateIdRemove) {
												removeProList.add(pro);
											}
										}
										Product.listPro.removeAll(removeProList);
										check = true;
										break;
									}
								}
								if (check == true) {
									Categories.listCate.removeAll(removeCateList);
									System.out.println("Đã xóa danh mục có mã là: " + cateIdRemove);
								} else {
									System.err.println("Không có danh mục nào có mã như này, mời nhập lại:");
								}
							} catch (NumberFormatException e) {
								System.err.println(
										"Dữ liệu bạn vừa nhập không phải là một số nguyên dương, mời nhập lại:");
								check = false;
							}
						} while (!check);
						// Ghi lại danh sách sau khi xóa vào file categories.txt và file products
						FileObject.saveProducts();
						FileObject.saveCategories();
						break;

					case 4:
						funtionalConditionsCategories(sc);
						check = false;
						do {
							System.out.println("Nhập tên danh mục của danh mục cần tìm kiếm:");
							String searchCateName = sc.nextLine();
							for (Categories cate : Categories.listCate) {
								if (cate.getCatalogName().toLowerCase().equals(searchCateName.toLowerCase())) {
									cate.displayData();
									check = true;
									break;
								}
							}
							if (check == false) {
								System.err.println("Không có tên danh mục nào tương tự vậy, mời nhập lại:");
							}
						} while (!check);
						break;

					case 5:
						mainMenu(sc);

					default:
						System.err.println("Bạn đã chọn sai chức năng, hãy chọn lại!");
					}
				} while (true);
				// Đoạn catch bắt lỗi nhập yêu cầu chọn chức năng ban đầu
			} catch (Exception e) {
				System.err.println("Dữ liệu bạn nhập không phải một số nguyên, mời nhập lại:");
				check = false;
			}
		} while (!check);
	}

	// Hàm DANH SÁCH DANH MỤC
	public static void categoriesShowMenu(Scanner sc) {
		// 2 dòng do va try bên dưới đê xét ngoại lệ bắt lỗi nếu nhập yêu cầu sai và bắt
		// nhập lại (không nhập số nguyên mà nhập kiểu dữ liệu khác)
		boolean check = true;
		do {
			try {
				do {
					System.out.println("\n**************************DANH SÁCH DANH MỤC********************");
					System.out.println("1. Danh sách cây danh mục");
					System.out.println("2. Thông tin chi tiết danh mục");
					System.out.println("3. Quay lại");
					System.out.println("Sự lựa chọn của bạn là: ");
					int choice3 = Integer.parseInt(sc.nextLine());
					switch (choice3) {

					case 1:
						int level1Cate = 0;
						for (Categories cate : Categories.listCate) {
							if (cate.getParentId() == 0) {
								System.out.println((++level1Cate) + ". " + cate.getCatalogName());
								int level2Cate = 0;
								for (Categories _cate : Categories.listCate) {
									if (_cate.getParentId() == cate.getCatalogId()) {
										System.out.println("  " + level1Cate + "." + (++level2Cate) + ". "
												+ _cate.getCatalogName());
										int level3Cate = 0;
										for (Categories _cate_ : Categories.listCate) {
											if (_cate_.getParentId() == _cate.getCatalogId()) {
												System.out.println("    " + level1Cate + "." + level2Cate + "."
														+ (++level3Cate) + ". " + _cate_.getCatalogName());
											}
										}
									}
								}
							}
						}
						break;

					case 2:
						check = false;
						do {
							System.out.println("Nhập tên danh mục muốn xem thông tin chi tiết của nó:");
							String infoCate = sc.nextLine();
							for (Categories cate : Categories.listCate) {
								if (cate.getCatalogName().toLowerCase().contains(infoCate.toLowerCase())) {
									cate.displayData();
									check = true;
								}
							}
							if (check == false) {
								System.err.println("Tên danh mục này không tồn tại, mời nhập lại:");
							}
						} while (!check);
						break;
					case 3:
						return;
					default:
						System.err.println("Bạn đã chọn sai chức năng, hãy chọn lại!");
					}
				} while (true);
				// Đoạn catch bắt lỗi nhập yêu cầu chọn chức năng ban đầu
			} catch (Exception e) {
				System.err.println("Dữ liệu bạn nhập không phải một số nguyên, mời nhập lại:");
				check = false;
			}
		} while (!check);
	}

	// Hàm Menu QUẢN LÝ SẢN PHẨM
	public static void productsMenu(Scanner sc) {
		// 2 dòng do va try bên dưới đê xét ngoại lệ bắt lỗi nếu nhập yêu cầu sai và bắt
		// nhập lại (không nhập số nguyên mà nhập kiểu dữ liệu khác)
		boolean check = true;
		do {
			try {
				do {
					System.out.println("\n*************************QUẢN LÝ SẢN PHẨM********************");
					System.out.println("1. Thêm sản phẩm mới");
					System.out.println("2. Tính lợi nhuận sản phẩm");
					System.out.println("3. Hiển thị thông tin sản phẩm");
					System.out.println("4. Sắp xếp sản phẩm");
					System.out.println("5. Cập nhật thông tin sản phẩm");
					System.out.println("6. Cập nhật trạng thái sản phẩm");
					System.out.println("7. Quay lại");
					System.out.println("Sự lựa chọn của bạn là: ");
					int choice5 = Integer.parseInt(sc.nextLine());
					switch (choice5) {
					case 1:
						do {
							try {
								System.out.println("Nhập số sản phẩm muốn thêm:");
								int numPro = Integer.parseInt(sc.nextLine());
								if (numPro <= 0) {
									throw new Exception("Hãy nhập một số nguyên dương!");
								}
								for (int i = 0; i < numPro; i++) {
									Product pro = new Product();
									pro.inputData();
									Product.listPro.add(pro);
								}
								check = true;
							} catch (NumberFormatException e) {
								System.err.println(
										"Dữ liệu bạn vừa nhập không phải là một số nguyên dương, mời nhập lại:");
								check = false;
							} catch (Exception e) {
								System.err.println(e.getMessage());
								check = false;
							}
						} while (!check);
						// Ghi lại danh sách sau khi thêm vào file products.txt
						FileObject.saveProducts();
						break;
					case 2:
						funtionalConditionsProducts(sc);
						for (Product pro : Product.listPro) {
							pro.calProfit();
						}
						System.out.println("Đã tính xong lợi nhuận các các phẩm");
						break;
					case 3:
						funtionalConditionsProducts(sc);
						productsShowMenu(sc);
						break;
					case 4:
						funtionalConditionsProducts(sc);
						productsSortMenu(sc);
						break;
					case 5:
						funtionalConditionsProducts(sc);
						check = false;
						do {
							System.out.println("Nhập mã sản phẩm cần sửa đổi thông tin:");
							String editProId = sc.nextLine();
							for (Product pro : Product.listPro) {
								if (pro.getProductId().equals(editProId)) {
									System.out.println("Nhập thông tin mới của sản phẩm: ");
									Product replacePro = new Product();
									replacePro.updateinfo();
									pro.setProductName(replacePro.getProductName());
									pro.setTitle(replacePro.getTitle());
									pro.setImportPrice(replacePro.getImportPrice());
									pro.setExportPrice(replacePro.getExportPrice());
									pro.setDescriptions(replacePro.getDescriptions());
									pro.setProductStatus(replacePro.isProductStatus());
									pro.setCatalog(replacePro.getCatalog());
									System.out.println("Thông tin của sản phẩm có ID '" + editProId
											+ "' đã được đổi thành công!");
									check = true;
									break;
								}
							}
							if (check == false) {
								System.err.println("Không có mã sản phẩm nào như vậy, mời nhập lại:");
							}
						} while (!check);
						// Ghi lại danh sách sau khi sửa đổi thông tin vào file products.txt
						FileObject.saveProducts();
						break;
					case 6:
						funtionalConditionsProducts(sc);
						check = false;
						do {
							System.out.println("Nhập mã sản phẩm cần thay đổi trạng thái: ");
							String editProId = sc.nextLine();
							for (Product pro : Product.listPro) {
								if (pro.getProductId().equals(editProId)) {
									pro.setProductStatus(!pro.isProductStatus());;
									System.out.println("Đã thay đổi trạng thái sản phẩm '"
											+ pro.getProductName() + "' sang: "
											+ (pro.isProductStatus() ? "Hoạt động" : "Không hoạt động"));
									check = true;
								}
							}
							if (check == false) {
								System.err.println("Không có mã sản phẩm nào như vậy, mời nhập lại!");
							}
						} while (!check);
						// Ghi lại danh sách sau khi thay đổi trạng thái vào file products.txt
						FileObject.saveProducts();
						break;
					case 7:
						mainMenu(sc);
					default:
						System.err.println("Bạn đã chọn sai chức năng, mời chọn lại!");
					}
				} while (true);
				// Đoạn catch bắt lỗi nhập yêu cầu chọn chức năng ban đầu
			} catch (Exception e) {
				System.err.println("Dữ liệu bạn nhập không phải một số nguyên, mời nhập lại!");
				check = false;
			}
		} while (!check);
	}

	// Hàm hiển thị menu Thông tin sản phẩm
	public static void productsShowMenu(Scanner sc) {
		// 2 dòng do va try bên dưới đê xét ngoại lệ bắt lỗi nếu nhập yêu cầu sai và bắt
		// nhập lại (không nhập số nguyên mà nhập kiểu dữ liệu khác)
		boolean check = true;
		do {
			try {
				do {
					System.out.println("\n**************************THÔNG TIN SẢN PHẨM********************");
					System.out.println("1. Hiển thị sản phẩm theo danh mục");
					System.out.println("2. Hiển thị chi tiết sản phẩm");
					System.out.println("3. Quay lại");
					System.out.println("Sự lựa chọn của bạn là: ");
					int choice6 = Integer.parseInt(sc.nextLine());
					switch (choice6) {
					case 1:
						for (Categories cate : Categories.listCate) {
							System.out.println(cate.getCatalogName());
							for (Product pro : Product.listPro) {
								if (pro.getCatalog().getCatalogId() == cate.getCatalogId()) {
									pro.displayData();
								}
							}
						}
						break;
					case 2:
						check = false;
						do {
							System.out.println("Nhập tên sản phẩm cần tìm kiếm:");
							String searchProName = sc.nextLine();
							for (Product pro : Product.listPro) {
								if (pro.getProductName().toLowerCase().contains(searchProName.toLowerCase())) {
									pro.displayData();
									check = true;
								}
							}
							if (check == false) {
								System.err.println(
										"Không có tên sản phẩm nào tương tự dữ liệu bạn nhập vào, mời nhập lại:");
							}
						} while (!check);
						break;
					case 3:
						return;
					default:
						System.err.println("Bạn đã chọn sai chức năng, hãy chọn lại!");
					}
				} while (true);
				// Đoạn catch bắt lỗi nhập yêu cầu chọn chức năng ban đầu
			} catch (Exception e) {
				System.err.println("Dữ liệu bạn nhập không phải một số nguyên!");
				check = false;
			}
		} while (!check);
	}

	// Hàm hiển thị menu Sắp xếp sản phẩm
	public static void productsSortMenu(Scanner sc) {
		// 2 dòng do va try bên dưới đê xét ngoại lệ bắt lỗi nếu nhập yêu cầu sai và bắt
		// nhập lại (không nhập số nguyên mà nhập kiểu dữ liệu khác)
		boolean check = true;
		do {
			try {
				do {
					System.out.println("\n**************************SẮP XẾP SẢN PHẨM********************");
					System.out.println("1. Sắp xếp các sản phẩm theo giá bán tăng dần");
					System.out.println("2. Sắp xếp các sản phẩm theo lợi nhuận giảm dần");
					System.out.println("3. Quay lại");
					System.out.println("Sự lựa chọn của bạn là: ");
					int choice7 = Integer.parseInt(sc.nextLine());
					switch (choice7) {
					case 1:
						Product.listPro.sort((p1, p2) -> Float.compare(p1.getExportPrice(), p2.getExportPrice()));
						System.out.println(
								"Đã sắp xếp các sản phẩm theo giá bán tăng dần, danh sách sau khi sắp xếp như sau:\n");
						for (Product pro : Product.listPro) {
							pro.displayData();
						}
						break;

					case 2:
						// Xét điều kiện phải tính lợi nhuận xong mới sắp xếp được
						for (Product pro : Product.listPro) {
							if (pro.getProfit() == 0.0) {
								System.err.println(
										"Bạn chưa tính lợi nhuận cho tất cả các sản phẩm nên chưa thể sử dụng chức năng này, vui lòng nhấn chức năng số 2 trong menu QUẢN LÝ SẢN PHẨM để tính!");
								return;
							}
						}
						Product.listPro.sort((p1, p2) -> Float.compare(p2.getProfit(), p1.getProfit()));
						System.out.println(
								"Đã sắp xếp các sản phẩm theo lợi nhuận giảm dần, danh sách sau khi sắp xếp như sau:\n");
						for (Product pro : Product.listPro) {
							pro.displayData();
						}
						break;

					case 3:
						return;
					default:
						System.err.println("Bạn đã chọn sai chức năng, hãy chọn lại!");
					}
				} while (true);
				// Đoạn catch bắt lỗi nhập yêu cầu chọn chức năng ban đầu
			} catch (Exception e) {
				System.err.println("Dữ liệu bạn nhập không phải một số nguyên, mời nhập lại:");
				check = false;
			}
		} while (!check);
	}

	// Hàm xét điều kiện tiên quyết để sử dụng các chức năng đối tượng Categories
	public static void funtionalConditionsCategories(Scanner sc) {
		if (Categories.listCate.size() == 0) {
			System.err.println(
					"Chưa tồn tại danh mục nào để sử dụng chức năng này, vui lòng chọn chức năng 'Thêm danh mục' để thêm các danh mục vào danh sách!");
			categoriesMenu(sc);
		}
	}

	// Hàm xét điều kiện tiên quyết để sử dụng các chức năng đối tượng Product
	public static void funtionalConditionsProducts(Scanner sc) {
		if (Product.listPro.size() == 0) {
			System.err.println(
					"Chưa tồn tại sản phẩm nào để sử dụng chức năng này, vui lòng chọn chức năng 'Thêm sản phẩm mới' để thêm các sản phẩm vào danh sách!");
			productsMenu(sc);
		}
	}
}