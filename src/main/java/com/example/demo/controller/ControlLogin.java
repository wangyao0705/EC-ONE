package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Control;
import com.example.demo.entity.Role;
import com.example.demo.service.ControlLoginService;
import com.example.demo.service.RoleService;

@Controller
public class ControlLogin {
	@RequestMapping(value = "/hello")
	public ModelAndView woc() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		return mav;

	}

	@Autowired
	ControlLoginService controlLoginService;
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/logins", method = RequestMethod.POST)
	public String cologin(
			Model model,
			@RequestParam(name = "userid", required = false) String id,
			@RequestParam(name = "password", required = false) String pass) {
		Control con = controlLoginService.mControlName(id);
		Role role = roleService.findstatusbychname(con.getCharacterName());
		boolean bo;
		bo = controlLoginService.findCharacterName(id);
		List<Control> listControls = controlLoginService.findControlLogin(id, pass);
		//時間を取る
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		//最後の登録時間と登録回数を変更
		Control control = new Control();
		control = controlLoginService.selectloginTimes(id);
		if (control.getStatusbycontrol().equals("退職")) {
			return "loginError";
		}

		if (control.getLoginTimes() == null) {
			control.setLoginTimes(1);
		} else {
			control.setLoginTimes(control.getLoginTimes() + 1);
		}
		//添加時間参数：timestamp
		control.setLastLogin(timestamp);

		controlLoginService.controlAdd(control);

		if (role.getStatusbycharacter().equals("使用中")) {

			if (listControls.size() == 0) {

				return "loginError";
				//判断characterName＝manager時
			} else if (bo) {
				//请求直接转发controlAllm， "redirect:/controlAllm?userId="为拼接格式，+id为需要传的参数
				//@RequestParam(name = "userId") String id
				return "redirect:/controlAllm?userId=" + id;

			} else {

				return "redirect:/controlAlls";

			}
		} else {
			return "loginError";
		}
	}

}
