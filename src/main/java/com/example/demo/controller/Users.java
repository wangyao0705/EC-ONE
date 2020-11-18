package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class Users {
	@Autowired
	UserService userService;
	//在application定义的照片路径 imagesurl为所定义的路径名字 如 imagesurl=/img/
	@Value("${imagesurl}")
	private String UPLOAD_DIR;
	@Value("${imagesdb}")
	private String longpath;

	/*
	 * userログイン画面
	 */
	@RequestMapping(value = "/userhello")
	public String userlogin(Model model) {
		return "userLogin";
	}

	@RequestMapping(value = "/userlogin")
	public String userslogin(Model model, @RequestParam(name = "userid") String userId,
			@RequestParam(name = "password") String password) {

		User user = userService.findIdAndPass(userId);

		if (!user.getPassWord().equals(password)) {
			return "loginError";
		}
		model.addAttribute("user", user);

		return "redirect:/codeAll?userId=" + userId;

	}

	/*
	 * 新規
	 */
	@RequestMapping(value = "/usersadd", method = RequestMethod.GET)
	public String useradd(Model model) {
		return "userAdd";

	}

	@RequestMapping(value = "/usersadd", method = RequestMethod.POST)
	public String userAdd(Model model,
			//照片类型特殊MultipartFile
			@RequestParam(name = "photo", required = false) MultipartFile photo,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "sex", required = false) String sex,
			@RequestParam(name = "address", required = false) String address,
			//创建object时 birth为Date类型，下面set时需要转换 user.setBirth(Date.valueOf(birth));
			@RequestParam(name = "birth", required = false) String birth,
			@RequestParam(name = "name1", required = false) String na,
			@RequestParam(name = "name2", required = false) String me,
			@RequestParam(name = "tel", required = false) String tel,
			@RequestParam(name = "furigana1", required = false) String furi,
			@RequestParam(name = "furigana2", required = false) String gana) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		//		List<User> users = userService.findsall();
		User use = new User();
		System.out.println(username);
		if (email.isEmpty() || username.isEmpty() || password.isEmpty() || sex.isEmpty() || address.isEmpty()
				|| birth.isEmpty() || tel.isEmpty() ||
				na.isEmpty() || me.isEmpty() || furi.isEmpty() || gana.isEmpty() || photo.isEmpty()) {
			return "loginError";
		}
		if (!email.isEmpty()) {
			use.setEmail(email);
		}
		if (!username.isEmpty()) {
			use.setUserId(username);
		}
		if (!password.isEmpty()) {
			use.setPassWord(password);
		}
		if (!sex.isEmpty()) {
			use.setSex(sex);
		}
		if (!address.isEmpty()) {
			use.setAddress(address);
		}
		if (!birth.isEmpty()) {
			use.setBirth(Date.valueOf(birth));
		}
		if (!tel.isEmpty()) {
			use.setTel(tel);
		}
		if (!na.isEmpty() || !me.isEmpty() || !furi.isEmpty() || !gana.isEmpty()) {
			use.setUserName(na + " " + me + "(" + furi + " " + gana + ")");
		}
		if (!photo.isEmpty()) {
			//从本地文件中取的照片的名字如11.jpg
			String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
			//拼接=照片的名字+在application中定义的长路径名字 @Value("${imagesdb}") private String longpath;
			Path path = Paths.get(longpath + fileName);
			//存入DB只需要短路径的名字+照片名字（存入DB）
			use.setPhoto(UPLOAD_DIR + fileName);
			try {
				//读取本地路径存入项目中 station下面的img文件（存入本地项目）
				Files.copy(photo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		use.setUserStatus("普通");
		use.setDateCreated(timestamp);
		userService.userAdd(use);
		//		}
		return "userLogin";

	}
	/*
	 * 一覧
	 */

	@RequestMapping("/usersAll")
	public String userall(Model model) {
		List<User> userlist = userService.findsall();
		model.addAttribute("userlist", userlist);
		return "userAll";
	}

	/*
	 * 削除
	 */
	@RequestMapping(value = "/userAction", params = "delect=削除", method = RequestMethod.GET)
	public String delectByid(Model model, @RequestParam(name = "checkbox-value", required = false) Integer id) {
		userService.deleteById(id);
		return "redirect:/usersAll";

	}
	/*
	 *检索
	 */

	@RequestMapping(value = "/userAction", params = "select=検索", method = RequestMethod.GET)
	public String selectAll(Model model, @RequestParam(name = "userid") String userid,
			@RequestParam(name = "username") String username,
			@RequestParam(name = "tel") String tel,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "sex") String sex,
			@RequestParam(name = "status") String status,
			@RequestParam(name = "address") String address) {

		List<User> users = new ArrayList<>();
		if (!userid.isEmpty()) {
			users = userService.selectuserId(userid);
		} else if (!username.isEmpty()) {
			users = userService.selectuserName(username);
		} else if (!tel.isEmpty()) {
			users = userService.selecttel(tel);
		} else if (!email.isEmpty()) {
			users = userService.selectemail(email);
		} else if (!sex.equals("選択...")) {
			users = userService.selectsex(sex);
		} else if (!status.equals("選択...")) {
			users = userService.selectuserStatus(status);
		} else if (!address.isEmpty()) {
			users = userService.selectaddress(address);
		}

		model.addAttribute("userlist", users);
		return "userAll";

	}
}
