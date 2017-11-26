/**
 * 
 */
package com.sb.rt.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sb.rt.dto.DtoRequestProduct;
import com.sb.rt.model.Product;

/**
 * @author ankur.mahajan
 *
 */
public interface ProductService {

	List<Product> uploadProductData(MultipartFile file) throws IOException;

	Product getProductByEpc(String epc);

	Map<String, Object> getWholeDataByEpc(String epc);

	void requestForProducts(DtoRequestProduct body);

}
