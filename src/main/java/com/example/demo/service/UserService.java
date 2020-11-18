package com.example.demo.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
//@Transactional
public class UserService {
	@Autowired
	UserRepository userRepository;

	/*
	 * 増加
	 */
	public void userAdd(User user) {
		//		System.out.println(user.getId());
		userRepository.save(user);
	}

	/*
	 * IDで全てを取り出す
	 */
	public User findid(Integer id) {
		return userRepository.findbyid(id);
	}

	/*
	 * 全てを取り出す
	 */
	public List<User> findsall() {
		return userRepository.findAll();
	}
	/*
	 * ログイン時にIDとpasswordを取り出す
	 */

	public User findIdAndPass(String userId) {
		return userRepository.findbyuserId(userId);
	}

	/*
	 * IDで削除
	 */
	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}

	/*
	 * 更新
	 */
	public void usersedit(String userId, String tel, String email, String sex, String address, String photo,
			Timestamp dateModified, String passWord) {
		userRepository.useredit(userId, tel, email, sex, address, photo, dateModified, passWord);

	}

	/*
	 * 検索
	 */
	public List<User> selectuserId(String userId) {
		return userRepository.selectByuserId(userId);
	}

	public List<User> selectuserName(String userName) {
		return userRepository.selectByuserName(userName);
	}

	public List<User> selecttel(String tel) {
		return userRepository.selectBytel(tel);
	}

	public List<User> selectemail(String email) {
		return userRepository.selectByemail(email);
	}

	public List<User> selectsex(String sex) {
		return userRepository.selectBysex(sex);
	}

	public List<User> selectuserStatus(String userStatus) {
		return userRepository.selectByuserStatus(userStatus);
	}

	public List<User> selectaddress(String address) {
		return userRepository.selectByaddress(address);
	}

	public List<User> selectbirth(Date birth) {
		return userRepository.selectBybirth(birth);
	}
}
