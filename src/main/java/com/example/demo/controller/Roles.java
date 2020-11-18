package com.example.demo.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;

@Controller
public class Roles {
	@Autowired
	RoleService roleService;

	/*
	 * 編集
	 * params = "edit1=編集"
	 */
	@RequestMapping(value = "/chedit", params = "edit1=編集")
	public String updateedit(Model model, @RequestParam(name = "charactername", required = false) String chname,
			@RequestParam(name = "status", required = false) String status) {
		Role r = roleService.findstatusbychname(chname);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if (!status.equals("選択...")) {
			roleService.updateRoleStatus(chname, status, timestamp);
		} else {
			roleService.updateRoleStatus(chname, r.getStatusbycharacter(), timestamp);
		}

		return "redirect:/roleall?charactername=root&characternames=manager";

	}

	/*
	 * 編集
	 * params = "edit2=編集"
	 */
	@RequestMapping(value = "/chedit", params = "edit2=編集")
	public String updateedits(Model model, @RequestParam(name = "characternames", required = false) String chname,
			@RequestParam(name = "statuss", required = false) String status) {
		Role rr = roleService.findstatusbychname(chname);
		System.out.println(status);
		System.out.println(chname);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if (!status.equals("選択...")) {
			roleService.updateRoleStatus(chname, status, timestamp);
		} else {
			roleService.updateRoleStatus(chname, rr.getStatusbycharacter(), timestamp);
		}

		return "redirect:/roleall?charactername=root&characternames=manager";

	}

	/*
	 * 一覧
	 */

	@RequestMapping("/roleall")
	public String all(Model model, @RequestParam(name = "charactername", required = false) String chname,
			@RequestParam(name = "characternames", required = false) String chnames) {

		Role role = roleService.findstatusbychname(chname);
		model.addAttribute("role", role);
		Role ro = roleService.findstatusbychname(chnames);
		model.addAttribute("ro", ro);
		return "/characterAll";

	}
}
