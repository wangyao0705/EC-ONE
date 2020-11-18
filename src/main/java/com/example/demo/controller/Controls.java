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
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Control;
import com.example.demo.service.ControlLoginService;
import com.example.demo.service.ControlService;

@Controller
public class Controls {
	@Autowired
	private ControlService controlService;
	@Autowired
	private ControlLoginService controlLoginService;

	/*
	 * チェックDB中に重複の管理者nameがあるかどうか（controlname）
	 * 増加
	 */
	//html type为按键都是get <a th:href="@{coAdd}"> @{coAdd} 为固定语法<input type="button" value="新規追加"></a>
	@RequestMapping(value = "/coAdd", method = RequestMethod.GET)
	public ModelAndView caddAndView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controlAdd");
		return mav;

	}

	@RequestMapping(value = "/controlAdds", method = RequestMethod.POST)
	public String controlAdds(
			Model model,
			//name是html中的name，String为重新设置接收数据的name
			@RequestParam(name = "controlname", required = false) String controlname,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "authority", required = false) String post,
			@RequestParam(name = "sex", required = false) String sex,
			@RequestParam(name = "tel", required = false) String tel) {
		//		RedirectView redirectTarget = new RedirectView();
		//チェックDB中に重複の管理者nameがあるかどうか（controlname）
		boolean bool;
		bool = controlService.controlcname(controlname);
		if (bool) {
			return "AddError";

		} else {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			Control control = new Control();
			control.setControlName(controlname);
			control.setSex(sex);
			control.setPassWord(controlname);
			control.setStatusbycontrol(status);
			control.setCharacterName(post);
			control.setTel(tel);
			control.setDateCreated(timestamp);
			controlService.controlAdd(control);
			return "redirect:/controlAlls";
		}
	}

	/*
	 * 一覧root
	 */
	@RequestMapping("/controlAlls")
	public String rall(Model model) {
		List<Control> controlList = controlService.findAllControlDate();
		//指定された管理者nameをdelete
		for (int i = 0; i < controlList.size(); i++) {
			if ("smanager".equals(controlList.get(i).getControlName())) {
				controlList.remove(i);
			}
		}
		model.addAttribute("controlList", controlList);
		//html
		return "controlAll";

	}
	/*
	 *一覧manager
	 */

	@RequestMapping("/controlAllm")
	public String mall(Model model,
			@RequestParam(name = "userId") String id) {
		System.out.println(id);
		List<Control> controlList = controlService.findAllControlDate();
		for (int i = 0; i < controlList.size(); i++) {
			if ("root".equals(controlList.get(i).getCharacterName())) {
				controlList.remove(i);
			}
		}
		//从service带参数id（登录时的controlname）接收对象为control对象
		Control control = controlLoginService.mControlName(id);
		//与html页面传值 <td th:text="${control.controlName}">
		model.addAttribute("control", control);
		model.addAttribute("controlList", controlList);
		//html
		return "managerAll";

	}
	/*
	 * 削除
	 */

	//<input type="submit" value="削除" name="delete" >params用在from的action的判断 Alls为from的action的名字
	@RequestMapping(value = "/Alls", method = RequestMethod.GET, params = "delete=削除")
	public String deletes(Model model, @RequestParam(name = "radio-value", required = false) Integer id) {
		if (id == 1) {
			//			model.addAttribute("error", "権限なし");
			return "loginError";
		} else {
			controlService.deleteControlById(id);
		}

		return "redirect:/controlAlls";

	}

	/*
	 * 編集
	 */
	@RequestMapping(value = "/Alls", method = RequestMethod.GET, params = "edit=編集")
	public String edit(Model model, @RequestParam(name = "radio-value", required = false) Integer id,
			@RequestParam(name = "controlname", required = false) String cname,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "authority", required = false) String chname,
			@RequestParam(name = "sex", required = false) String sex,
			@RequestParam(name = "tel", required = false) String tel) {
		Control control = controlService.updateControl(id);
		if (status.equals("選択...")) {
			control.setStatusbycontrol(control.getStatusbycontrol());

		} else if (!status.equals("選択...")) {
			if (!status.equals(control.getStatusbycontrol())) {
				control.setStatusbycontrol(status);
			} else if (status.equals(control.getStatusbycontrol())) {
				control.setStatusbycontrol(control.getStatusbycontrol());
			}
		}
		if (chname.equals("選択...")) {
			control.setCharacterName(control.getCharacterName());
		} else if (!chname.equals("選択...")) {
			if (!chname.equals(control.getCharacterName())) {
				control.setCharacterName(chname);

			} else if (chname.equals(control.getCharacterName())) {
				control.setCharacterName(control.getCharacterName());
			}
		}
		if (sex.equals("選択...")) {
			control.setSex(control.getSex());
		} else if (!sex.equals("選択...")) {
			if (!sex.equals(control.getSex())) {
				control.setSex(sex);
			} else if (sex.equals(control.getSex())) {
				control.setSex(control.getSex());
			}
		}

		control.setControlName(cname);
		control.setTel(tel);
		controlService.updateControlById(id, control.getControlName(), control.getCharacterName(),
				control.getStatusbycontrol(), control.getSex(), control.getTel());
		return "redirect:/controlAlls";

	}

	/*
	 * ALL检索
	 */
	@RequestMapping(value = "/coAll", method = RequestMethod.GET)
	public String selectAll(Model model) {
		return "redirect:/controlAlls";
	}
	/*
	 * rootページ検索
	 */

	@RequestMapping(value = "/Alls", method = RequestMethod.GET, params = "select=検索")
	public String select(Model model, @RequestParam(name = "controlname", required = false) String cname,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "authority", required = false) String chname,
			@RequestParam(name = "sex", required = false) String sex,
			@RequestParam(name = "tel", required = false) String tel,
			@RequestParam(name = "login_times", required = false) String logintime) {
		List<Control> control = new ArrayList<>();
		if (!cname.isEmpty()) {
			control = controlService.selectControlNames(cname);
		} else if (!status.equals("選択...")) {
			control = controlService.selectStatuss(status);
		} else if (!chname.equals("選択...")) {
			control = controlService.selectCharacterNames(chname);

		} else if (!sex.equals("選択...")) {
			control = controlService.selectSexs(sex);

		} else if (!tel.isEmpty()) {
			control = controlService.selectTels(tel);

		} else if (!logintime.isEmpty()) {
			control = controlService.selectLoginTimes(logintime);

		}
		System.out.println(control.toString());
		model.addAttribute("controlList", control);

		return "controlAll";

	}

	/*
	 * マネージャーページ検索
	 */
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String mselect(Model model, @RequestParam(name = "controlname", required = false) String cname,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "authority", required = false) String chname,
			@RequestParam(name = "sex", required = false) String sex,
			@RequestParam(name = "tel", required = false) String tel,
			@RequestParam(name = "login_times", required = false) String logintime,
			@RequestParam(name = "Mcname", required = false) String mname) {
		Control con = controlLoginService.mControlName(mname);
		model.addAttribute("control", con);
		List<Control> control = new ArrayList<>();
		if (!cname.isEmpty()) {
			control = controlService.selectControlNames(cname);
		} else if (!status.equals("選択...")) {
			control = controlService.selectStatuss(status);
		} else if (!chname.equals("選択...")) {
			control = controlService.selectCharacterNames(chname);

		} else if (!sex.equals("選択...")) {
			control = controlService.selectSexs(sex);

		} else if (!tel.isEmpty()) {
			control = controlService.selectTels(tel);

		} else if (!logintime.isEmpty()) {
			control = controlService.selectLoginTimes(logintime);

		}
		System.out.println(control.toString());
		model.addAttribute("controlList", control);

		return "managerAll";

	}

	/*
	 * managerのパスワード変更画面をスキップ
	 */
	//<a>都为GET
	@RequestMapping(value = "/pass", method = RequestMethod.GET)
	public String pass(Model model, @RequestParam(name = "Mcontrolname") String mname) {
		Control control = controlLoginService.mControlName(mname);
		model.addAttribute("control", control);
		return "password";
	}

	/*
	 * manager password变更
	 */
	@RequestMapping(value = "/passedit", method = RequestMethod.GET)
	public String passEdit(Model model, @RequestParam(name = "coname") String mname,
			@RequestParam(name = "password1") String passone,
			@RequestParam(name = "password2") String passtwo) {
		if (passone.equals(passtwo)) {
			controlService.updatempass(mname, passtwo);
		} else {
			return "loginError";
		}
		return "redirect:/controlAllm?userId=" + mname;

	}

}
