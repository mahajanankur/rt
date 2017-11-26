/**
 * 
 */
package com.sb.rt.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sb.rt.dao.ProductDao;
import com.sb.rt.dto.DtoRequestProduct;
import com.sb.rt.model.Product;
import com.sb.rt.service.ProductService;
import com.sb.rt.utils.MailSenderUtil;

/**
 * @author ankur.mahajan
 *
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

	@Autowired
	MailSenderUtil mailUtil;

	@Value("${email.to}")
	private String emailTO;

	@Value("${email.subject}")
	private String subject;

	@Value("${email.template.table.start}")
	private String templateStart;

	@Value("${email.template.table.end}")
	private String templateEnd;

	@Value("${email.template.table.middle}")
	private String templateMiddle;

	@Override
	public List<Product> uploadProductData(MultipartFile file) throws IOException {
		Set<Product> set = new HashSet<>();
		List<Product> notUploaded = new ArrayList<>();
		File newFile = convertMultipartToFile(file);
		List<Product> products = readExcelFile(newFile);
		set.addAll(products);
		for (Product item : set) {
			try {
				//replace quotes from SKU. To tackle excel problem.
				String newSku = item.getSku().replace('"', ' ').trim();
				item.setSku(newSku);
				productDao.uploadProductData(item);
			}
			catch (Exception e) {
				notUploaded.add(item);
				continue;

			}

		}
		return notUploaded;
	}

	/**
	 * This method is used to convert the multi-part file into file.
	 * 
	 * @param file
	 * @return convFile
	 * @throws IOException
	 */
	private File convertMultipartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	/**
	 * This method is used to read the data from excel file and insert into the
	 * list.
	 * 
	 * @param file
	 * @return ProductList
	 */
	private List<Product> readExcelFile(File file) {
		List<Product> products = new ArrayList<>();
		try {
			FileInputStream inputStream = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			boolean skipHeader = false;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (!skipHeader) {
					skipHeader = true;
					continue;
				}
				else {
					Iterator<Cell> cellIterator = row.cellIterator();
					int counter = 0;
					Product product = new Product();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String cellVal = null;
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							cellVal = cell.getNumericCellValue() + "";
							break;
						case Cell.CELL_TYPE_STRING:
							cellVal = cell.getStringCellValue() + "";
							break;

						case Cell.CELL_TYPE_BLANK:
							cellVal = null;
							break;
						}
						switch (counter) {
						case 0:
							product.setEpc(cellVal);
							break;
						case 1:
							product.setSku(cellVal);
							break;
						case 2:
							product.setSkuName(cellVal);
							break;
						case 3:
							product.setItemName(cellVal);
							break;
						case 4:
							product.setImage(cellVal);
							break;
						case 5:
							product.setSize(cellVal);
							break;
						case 6:
							product.setColor(cellVal);
							break;
						case 7:
							Double styleC = Double.parseDouble(cellVal);
							int styleCode = styleC.intValue();
							product.setStyleCode(styleCode);
							break;
						case 8:
							product.setZone(cellVal);
							break;
						case 9:
							product.setDescription(cellVal);
							break;
						}
						counter++;
					}
					products.add(product);
				}
				inputStream.close();
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public Product getProductByEpc(String epc) {
		Product product = productDao.getProductByEpc(epc);
		return product;
	}

	@Override
	public Map<String, Object> getWholeDataByEpc(String epc) {
		Map<String, Object> response = new HashMap<>();
		Product product = productDao.getProductByEpc(epc);
		//Get other colors and sizes available.
		int styleCode = product.getStyleCode();
		List<Product> productsByStyle = productDao.getProductsByStyleCode(styleCode);
		Set<String> colorsAvailable = new HashSet<>();
		Set<String> sizesAvailable = new HashSet<>();
		for (Product item : productsByStyle) {
			colorsAvailable.add(item.getColor());
			sizesAvailable.add(item.getSize());
		}
		//Get suggestions for similar product.
		String sku = product.getSku();
		List<Product> suggestions = productDao.getSuggestionsBySku(sku);

		response.put("actual", product);
		response.put("colors", colorsAvailable);
		response.put("sizes", sizesAvailable);
		response.put("suggestions", suggestions);

		return response;
	}

	@Override
	public void requestForProducts(DtoRequestProduct body) {
		List<String> epcs = body.getEpcs();
		List<String> colors = body.getColors();
		List<String> sizes = body.getSizes();
		StringBuilder message = new StringBuilder();
		int counter = 1;
		for (String epc : epcs) {
			Product product = getProductByEpc(epc);
			if (null != product) {
				if (counter == 1) {
					message.append(templateStart);
					message.append("<p>Products are requested in fitting room <b>" + body.getFittingRoomId() + "</b></p>");
					message.append(templateMiddle);
				}
				message.append("<tr>");
				message.append("<td>" + counter + "</td>");
				message.append("<td>" + product.getEpc() + "</td>");
				if (null != colors) {
					message.append("<td>" + colors.toString().replace('[', ' ').replace(']', ' ').trim() + "</td>");
				}
				else {
					message.append("<td>" + product.getColor() + "</td>");
				}
				if (null != sizes) {
					message.append("<td>" + sizes.toString().replace('[', ' ').replace(']', ' ').trim() + "</td>");
				}
				else {
					message.append("<td>" + product.getSize() + "</td>");
				}
				message.append("<td>" + product.getZone() + "</td>");
				counter++;
			}
		}
		if (null != message && !message.equals("")) {
			message.append(templateEnd);
		}
		mailUtil.sendMail(emailTO, subject, message.toString());
	}

}
