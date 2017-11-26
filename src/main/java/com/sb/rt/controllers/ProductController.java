/**
 * 
 */
package com.sb.rt.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.rt.dto.DtoRequestProduct;
import com.sb.rt.model.Product;
import com.sb.rt.objects.Response;
import com.sb.rt.service.ProductService;

/**
 * @author amahajan
 *
 */
@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ResponseEntity<?> getStatus() {
		Response response = new Response();
		response.setSuccess(true);
		response.setMessage("Service is running great.");
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<?> uploadProductData(@RequestParam MultipartFile file) throws IOException {
		Response response = new Response();
		String originalFileName = file.getOriginalFilename();
		List<Product> failed = null;
		if (originalFileName != null && (originalFileName.indexOf(".xlsx") > -1)) {
			failed = productService.uploadProductData(file);
		}
		response.setSuccess(true);
		response.setMessage("Products has been uploaded successfully. Failed are in data.");
		response.setData(failed);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/item/{epc}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductByEpc(@PathVariable String epc) {
		Response response = new Response();
		Product product = productService.getProductByEpc(epc);
		response.setSuccess(true);
		response.setMessage("Product details by EPC.");
		response.setData(product);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/screen/{epc}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllScreenData(@PathVariable String epc) {
		Response response = new Response();
		Map<String, Object> data = productService.getWholeDataByEpc(epc);
		response.setSuccess(true);
		response.setMessage("Screen Data.");
		response.setData(data);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/request", method = RequestMethod.POST)
	public ResponseEntity<?> requestForProducts(@RequestBody DtoRequestProduct body) {
		Response response = new Response();
		productService.requestForProducts(body);
		response.setSuccess(true);
		response.setMessage("Products are requested.");
		response.setData(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * This method is used to handle all the controller level exceptions.
	 * 
	 * @param request
	 * @param e
	 * @return response
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleError(HttpServletRequest request, Exception e) {
		Response response = new Response();
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}
}
