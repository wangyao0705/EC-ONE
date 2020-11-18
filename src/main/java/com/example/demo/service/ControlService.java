package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Control;
import com.example.demo.repository.ControlRepository;

@Service
@Transactional
public class ControlService {
	@Autowired
	ControlRepository controlRepository;

	/*
	 * controlNameを取り出し
	 */
	public boolean controlcname(String controlName) {
		boolean boo = false;
		if (!controlRepository.findByControlName(controlName).isEmpty()) {
			boo = true;
		}
		;
		return boo;

	}

	/*
	 * 新規
	 */
	public String controlAdd(Control control) {
		controlRepository.save(control);
		return "";

	}

	/*
	 * 全てのデータを取り出し
	 */
	public List<Control> findAllControlDate() {

		return controlRepository.findAll();

	}

	/*
	 * IDで削除
	 */
	public Control deleteControlById(Integer id) {
		controlRepository.deleteById(id);
		return null;

	}
	/*
	 *更新
	 */

	public Control updateControlById(Integer id, String controlName, String characterName, String statusbycontrol,
			String sex, String tel) {
		controlRepository.updateCotrolByid(id, controlName, characterName, statusbycontrol, sex, tel);

		return null;

	}
	/*
	 * idで取り出す
	 */

	public Control updateControl(Integer id) {
		return controlRepository.findcontrolId(id);
	}

	/*
	 * 更新password
	 */
	public void updatempass(String controlName, String passWord) {
		controlRepository.updatePassWord(controlName, passWord);
	}
	/*
	 * 検索
	 */

	public List<Control> selectControlNames(String controlName) {
		return controlRepository.selectControlName(controlName);
	}

	public List<Control> selectCharacterNames(String characterName) {
		return controlRepository.selectCharacterName(characterName);
	}

	public List<Control> selectSexs(String sex) {
		return controlRepository.selectSex(sex);
	}

	public List<Control> selectTels(String tel) {
		return controlRepository.selectTel(tel);
	}

	public List<Control> selectStatuss(String statusbycontrol) {
		return controlRepository.selectStatus(statusbycontrol);
	}

	public List<Control> selectLoginTimes(String loginTimes) {
		return controlRepository.selectLoginTime(loginTimes);
	}

}
