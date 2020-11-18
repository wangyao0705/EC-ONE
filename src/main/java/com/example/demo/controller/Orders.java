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

import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;

@Controller
public class Orders {
	@Autowired
	OrderService orderService;
	@Autowired
	ProductService productService;

	/*
	 * Orders一覧
	 */
	@RequestMapping("/OrderAlls")
	public String orderall(Model model) {
		List<Order> order = orderService.findall();
		model.addAttribute("order", order);
		return "orderAll";
	}

	/*
	 * 削除
	 */
	@RequestMapping(value = "/AllOrder", params = "delect=削除", method = RequestMethod.GET)
	public String deleteorderbyid(Model model, @RequestParam(name = "checkbox-value") Integer id) {
		orderService.deleteid(id);
		return "redirect:/OrderAlls";
	}

	/*
	 *出荷時間を変更
	 */
	@RequestMapping(value = "/AllOrder", params = "check=出荷確認", method = RequestMethod.GET)
	public String updatetimes(Model model, @RequestParam(name = "checkbox-value") Integer id) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Order order = orderService.findids(id);
		if (!order.getStatusByBuy().equals("支払完了(check済)")) {
			return "loginError";
		}
		orderService.updatetime(id, timestamp);
		return "redirect:/OrderAlls";
	}
	/*
	 *检索
	 */

	@RequestMapping(value = "/AllOrder", params = "select=検索", method = RequestMethod.GET)
	public String selectAll(Model model, @RequestParam(name = "orderid", required = false) Integer orderid,
			@RequestParam(name = "pid", required = false) Integer pid,
			@RequestParam(name = "pname", required = false) String pname,
			@RequestParam(name = "uname", required = false) String uname,
			@RequestParam(name = "authority", required = false) String authority,
			@RequestParam(name = "authoritys", required = false) String authoritys,
			@RequestParam(name = "total", required = false) Double total,
			@RequestParam(name = "payment", required = false) Double payment,
			@RequestParam(name = "address", required = false) String address,
			@RequestParam(name = "numberbyorder", required = false) Integer numberbyorder) {
		List<Order> orders = new ArrayList<>();
		System.out.println(authority);
		System.out.println(authoritys);
		if (orderid != null) {
			orders = orderService.selectorderId(orderid);
		} else if (pid != null) {
			orders = orderService.selectproductId(pid);
		} else if (!pname.isEmpty()) {
			orders = orderService.selectproductInfo(pname);
		} else if (!uname.isEmpty()) {
			orders = orderService.selectuserName(uname);
		} else if (total != null) {
			orders = orderService.selecttotal(total);
		} else if (payment != null) {
			orders = orderService.selectpayment(payment);
		} else if (!address.isEmpty()) {
			orders = orderService.selectaddress(address);
		} else if (numberbyorder != null) {
			orders = orderService.selectorderNumber(numberbyorder);
		} else if (authority != null) {
			orders = orderService.selectstatusByOrder(authority);
		} else if (authoritys != null) {
			orders = orderService.selectstatusByBuy(authoritys);
		}
		model.addAttribute("order", orders);
		return "orderAll";
	}
	/*
	 * 編集
	 */

	@RequestMapping(value = "/AllOrder", params = "edit=編集", method = RequestMethod.GET)
	public String updateorderall(Model model, @RequestParam(name = "orderid", required = false) Integer orderid,
			@RequestParam(name = "checkbox-value") Integer id,
			@RequestParam(name = "pid", required = false) Integer pid,
			@RequestParam(name = "pname", required = false) String pname,
			@RequestParam(name = "uname", required = false) String uname,
			@RequestParam(name = "authority", required = false) String authority,
			@RequestParam(name = "authoritys", required = false) String authoritys,
			@RequestParam(name = "total", required = false) Double total,
			@RequestParam(name = "payment", required = false) Double payment,
			@RequestParam(name = "address", required = false) String address,
			@RequestParam(name = "numberbyorder", required = false) Integer numberbyorder) {
		Order order = orderService.orderidByid(id);
		Product product = productService.findproid(pid);
		if (product.getStock() - numberbyorder + order.getOrderNumber() < 0) {
			System.out.println(product.getStock());
			return "loginError";
		} else {
			productService.updatestock(pid, product.getStock() - numberbyorder + order.getOrderNumber());

		}
		if (authority == null) {
			order.setStatusByOrder(order.getStatusByOrder());
		} else {
			order.setStatusByOrder(authority);
		}
		if (authoritys == null) {
			order.setStatusByBuy(order.getStatusByBuy());
		} else {
			order.setStatusByBuy(authoritys);
		}
		if (numberbyorder != null) {
			order.setTotal(order.getTotal() / order.getOrderNumber() * numberbyorder);
			order.setPayment(order.getPayment() / order.getOrderNumber() * numberbyorder);
			order.setOrderNumber(numberbyorder);
		} else {
			order.setOrderNumber(order.getOrderNumber());
			order.setTotal(order.getTotal());
			order.setPayment(order.getPayment());
		}
		orderService.updateOrderAll(id, order.getOrderId(), order.getProductId(), order.getProductInfo(), uname,
				order.getStatusByOrder(), order.getStatusByBuy(), order.getTotal(), order.getPayment(), address,
				order.getOrderNumber());
		return "redirect:/OrderAlls";

	}
}
