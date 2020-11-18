package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Control;
import com.example.demo.repository.ControlLoginRepository;

@Service
@Transactional
public class ControlLoginService {
	@Autowired
	ControlLoginRepository controlLoginRepository;

	/*
	 * controlName passWordを提出
	 */
	public List<Control> findControlLogin(String controlName, String passWord) {
		return controlLoginRepository.findBycontrolNameAndpassWord(controlName, passWord);
	}

	/*
	 * controlNameで取り出す、CharacterNameはmanagerではないかを判断
	 */
	public boolean findCharacterName(String controlName) {
		boolean bool = false;
		List<Control> list = controlLoginRepository.findByControlName(controlName);
		for (Control control : list) {
			System.out.println(control.getCharacterName());
			if (control.getCharacterName().equals("manager")) {
				return true;
			}
		}
		return bool;
	}

	/*
	 * controlNameを取り出し
	 */
	//提取controlName 控制层在control
	public Control mControlName(String controlName) {
		return controlLoginRepository.findBycontrolName(controlName);

	}

	public Control selectloginTimes(String controlName) {
		return controlLoginRepository.findBycontrolName(controlName);
	}

	/*
	 * 新規
	 */
	public String controlAdd(Control control) {
		controlLoginRepository.save(control);
		return "";

	}
}