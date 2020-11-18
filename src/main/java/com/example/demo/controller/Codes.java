package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Code;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.CodeService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

@Controller
public class Codes {

	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	@Autowired
	CodeService codeService;
	@Autowired
	OrderService orderService;
	@Value("${imagesurl}")
	private String UPLOAD_DIR;
	@Value("${imagesdb}")
	private String longpath;

	/*
	 * 一覧oo
	 */
	@RequestMapping("/codeAll")
	public String codeall(Model model, @RequestParam(name = "userId") String userid) {
		User user = userService.findIdAndPass(userid);
		model.addAttribute("user", user);
		List<Product> product = productService.productfindAll();
		model.addAttribute("productList", product);
		return "code";
	}
	/*
	 * 情報変更スキップ
	 */

	@RequestMapping(value = "/userEdit")
	public String useredit(Model model, @RequestParam(name = "userID") String userid,
			@RequestParam(name = "Id") Integer id) {
		User user = userService.findIdAndPass(userid);
		model.addAttribute("user", user);
		return "userEdit";
	}

	/*
	 * 情報変更
	 */
	@RequestMapping(value = "/useredit", method = RequestMethod.GET)
	public String userAdd(Model model,
			//照片类型特殊
			@RequestParam(name = "photo", required = false) MultipartFile photo,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "username", required = false) String userid,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "sex", required = false) String sex,
			@RequestParam(name = "address", required = false) String address,
			@RequestParam(name = "tel", required = false) String tel,
			@RequestParam(name = "id", required = false) Integer id) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		User user = userService.findid(id);
		System.out.println(id);
		if (photo != null) {
			//从本地文件中取的照片的名字如11.jpg
			String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
			//拼接=照片的名字+在application中定义的长路径名字 @Value("${imagesdb}") private String longpath;
			Path path = Paths.get(longpath + fileName);
			//存入DB只需要短路径的名字+照片名字（存入DB）
			user.setPhoto(UPLOAD_DIR + fileName);
			try {
				//读取本地路径存入项目中 station下面的img文件（存入本地项目）
				Files.copy(photo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!email.isEmpty()) {
			user.setEmail(email);
		}
		if (!password.isEmpty()) {
			user.setPassWord(password);
		}
		if (sex.isEmpty()) {
			user.setSex(user.getSex());
		}
		if (!address.isEmpty()) {
			user.setAddress(address);
		}
		if (!tel.isEmpty()) {
			user.setTel(tel);
		}
		user.setDateModified(timestamp);
		userService.userAdd(user);
		return "redirect:/codeAll?userId=" + user.getUserId();
	}

	/*
	 * return
	 */
	@RequestMapping(value = "/return")
	public String returnLogin(Model model) {
		return "userLogin";
	}

	/*
	 * 检索
	 */
	@RequestMapping(value = "/selects")
	public String selectProtype(Model model, @RequestParam(name = "text") String text,
			@RequestParam(name = "userID") String userid) {
		User user = userService.findIdAndPass(userid);
		model.addAttribute("user", user);
		List<Product> products = productService.selectproductType(text);
		model.addAttribute("productList", products);
		return "code";

	}

	/*
	 * All検索
	 */
	@RequestMapping(value = "/Allselect")
	public String selectAll(Model model, @RequestParam(name = "userId") String userid) {
		//		User user = userService.findIdAndPass(userid);
		//		model.addAttribute("user", user);
		System.out.println(userid);
		return "redirect:/codeAll?userId=" + userid;
	}

	/*
	 * カート入り
	 */
	@RequestMapping(value = "/codes", method = RequestMethod.GET)
	public String codeni(Model model, @RequestParam(name = "proId") Integer proid,
			@RequestParam(name = "userID", required = false) String userid,
			@RequestParam(name = "protype", required = false) String protype,
			@RequestParam(name = "proinfo", required = false) String proinfo,
			@RequestParam(name = "sales", required = false) Double sales) {
		Code code = new Code();
		code.setProductId(proid);
		code.setUsername(userid);
		code.setProductInfo(proinfo);
		code.setProductType(protype);
		code.setSales(sales);
		codeService.insertcode(code);
		return "redirect:/codeAlls?userId=" + userid;

	}
	/*
	 * カート一覧
	 */

	@RequestMapping("/codeAlls")
	public String codes(Model model, @RequestParam(name = "userId") String userid) {
		User user = userService.findIdAndPass(userid);
		model.addAttribute("user", user);
		List<Code> code = codeService.findall(userid);
		model.addAttribute("code", code);
		return "codes";

	}
	/*
	 * カート削除
	 */

	@RequestMapping(value = "/deletecode")
	public String deletebyid(Model model, @RequestParam(name = "id") Integer id,
			@RequestParam(name = "userId") String userid) {
		codeService.deletebyid(id);
		return "redirect:/codeAlls?userId=" + userid;

	}

	/*
	 * カート戻る
	 */
	@RequestMapping(value = "/returns")
	public String returncode(Model model, @RequestParam(name = "userId") String userid) {
		return "redirect:/codeAll?userId=" + userid;

	}
	/*
	 *カードボタン
	 */

	@RequestMapping(value = "/codebutton")
	public String codebuttons(Model model, @RequestParam(name = "userId") String userid) {
		return "redirect:/codeAlls?userId=" + userid;
	}
	/*
	 *カード中購入
	 */

	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public String buycode(Model model, @RequestParam(name = "number", required = false) Integer number,
			@RequestParam(name = "proid", required = false) Integer proid,
			@RequestParam(name = "userId", required = false) String userid,
			@RequestParam(name = "id", required = false) Integer id) {
		User user = userService.findIdAndPass(userid);
		Product product = productService.findproid(proid);
		if (product.getStock() - number < 0 || number == 0) {
			return "buynumber";
		}

		productService.updatestock(proid, product.getStock() - number);
		Order order = new Order();
		order.setOrderId(id + 10000);
		order.setProductId(proid);
		order.setUserName(user.getUserName());
		order.setProductInfo(product.getProductType() + ":" + product.getProductInfo());
		order.setStatusByOrder("注文完了");
		order.setStatusByBuy("支払完了");
		order.setAddress(user.getAddress());
		order.setOrderNumber(number);
		order.setTotal(product.getCost() * number);
		order.setPayment(product.getSales() * number);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		order.setPayTime(timestamp);
		orderService.findorderAll(order);
		codeService.deletebyid(id);

		return "redirect:/codeAlls?userId=" + userid;

	}
	/*
	 *すぐ購入
	 */

	@RequestMapping(value = "/buys", method = RequestMethod.GET)
	public String codeNi(Model model, @RequestParam(name = "proid") Integer proid,
			@RequestParam(name = "number") Integer number,
			@RequestParam(name = "userID", required = false) String userid) {
		Product product = productService.findproid(proid);
		if (product.getStock() - number < 0 || number == 0) {
			return "buynumber";
		}

		User user = userService.findIdAndPass(userid);
		System.out.println(userid);
		System.out.println(proid);
		System.out.println(product.getStock());
		System.out.println(number);
		productService.updatestock(proid, product.getStock() - number);
		Order order = new Order();
		//ランドムrandom 乱数を作る
		Random rand = new Random();
		int a = rand.nextInt(100) + 1000;
		String str = String.valueOf(a);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		order.setOrderId(Integer.parseInt(proid.toString() + "0" + str));
		order.setProductId(proid);
		order.setUserName(user.getUserName());
		order.setProductInfo(product.getProductType() + ":" + product.getProductInfo());
		order.setStatusByOrder("注文完了");
		order.setStatusByBuy("支払完了");
		order.setAddress(user.getAddress());
		order.setOrderNumber(number);
		order.setTotal(product.getCost() * number);
		order.setPayment(product.getSales() * number);
		order.setPayTime(timestamp);
		orderService.findorderAll(order);

		return "codeOder";

	}

}
