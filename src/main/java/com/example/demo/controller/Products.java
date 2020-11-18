package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

@Controller
public class Products {

	@Autowired
	ProductService productService;
	/*
	 * 新規
	 */

	@RequestMapping(value = "/proAdd", method = RequestMethod.GET)
	public String Add(Model model) {
		return "productAdd";

	}

	@RequestMapping(value = "/productAdd")
	public String productAdds(Model model,
			@RequestParam(name = "producttype") String protype,
			@RequestParam(name = "product_intro") String proinfo,
			@RequestParam(name = "proid") Integer proid,
			@RequestParam(name = "sales") Double sales,
			@RequestParam(name = "cost") Double cost,
			@RequestParam(name = "stock") Integer stock) {
		Product product = new Product();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		List<Product> products = productService.productfindAll();
		for (Product pro : products) {
			if (pro.getProductId().equals(proid)) {
				return "loginError";
			}
		}
		product.setProductId(proid);
		product.setProductType(protype);
		product.setProductInfo(proinfo);
		product.setSales(sales);
		product.setCost(cost);
		product.setStock(stock);
		product.setDateCreated(timestamp);
		productService.productAdd(product);

		return "redirect:/proAll";

	}

	/*
	 * 一覧
	 */
	@RequestMapping("/proAll")
	public String productAll(Model model) {
		List<Product> product = productService.productfindAll();
		model.addAttribute("pro", product);
		return "productAll";

	}

	/*
	 * 削除
	 */
	@RequestMapping(value = "/proalls", method = RequestMethod.GET, params = "delete=削除")
	public String productdrop(Model model, @RequestParam(name = "radio-value") Integer id) {
		productService.dropProduct(id);
		return "redirect:/proAll";

	}

	/*
	 * 編集
	 */

	@RequestMapping(value = "/proalls", method = RequestMethod.GET, params = "edit=編集")
	public String productedit(Model model, @RequestParam(name = "radio-value") Integer id,
			@RequestParam(name = "productid") Integer proid,
			@RequestParam(name = "producttype") String protype,
			@RequestParam(name = "product_intro") String proinfo,
			@RequestParam(name = "sales") Double sales,
			@RequestParam(name = "cost") Double cost,
			@RequestParam(name = "stock") Integer stock) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Product product = productService.productById(id);
		//		if (!product.getProductType().equals(protype)) {
		//			return "loginError";
		//		} else {

		productService.editProduct(id, proid, proinfo, sales, cost, stock, product.getProductType(), timestamp);
		//		}
		//		product.setProductId(proid);
		//		product.setProductInfo(proinfo);
		//		product.setSales(sales);
		//		product.setStock(stock);
		//		product.setCost(cost);

		return "redirect:/proAll";

	}
	/*
	 * 検索 数字は暧昧检索できない
	 */

	@RequestMapping(value = "/proalls", method = RequestMethod.GET, params = "select=検索")
	public String productselect(Model model, @RequestParam(name = "productid") Integer proid,
			@RequestParam(name = "producttype") String protype,
			@RequestParam(name = "product_intro") String proinfo,
			@RequestParam(name = "sales") Double sales,
			@RequestParam(name = "cost") Double cost,
			@RequestParam(name = "stock") Integer stock,
			@RequestParam(name = "Access_number") Integer acnumber) {
		List<Product> products = new ArrayList<>();
		if (proid != null) {
			products = productService.selectproductId(proid);
			System.out.println(products);
		} else if (!protype.equals("選択")) {
			products = productService.selectproductType(protype);
		} else if (!proinfo.isEmpty()) {
			products = productService.selectproductInfo(proinfo);
		} else if (sales != null) {
			products = productService.selectsales(sales);
		} else if (cost != null) {
			products = productService.selectcost(cost);
		} else if (stock != null) {
			products = productService.selectstock(stock);
		} else if (acnumber != null) {
			products = productService.selectaccessNumber(acnumber);
		}
		model.addAttribute("pro", products);

		return "productAll";

	}
}
