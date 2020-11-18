package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Code;
import com.example.demo.repository.CodeRepository;

@Service
@Transactional
public class CodeService {
	@Autowired
	CodeRepository codeRepository;

	/*
	 * データをinsert
	 */
	public String insertcode(Code code) {
		codeRepository.save(code);
		return "";
	}

	/*
	 * usernameで取り出す
	 */
	public List<Code> findall(String username) {
		return codeRepository.findByusername(username);

	}

	/*
	 * idで削除
	 */
	public String deletebyid(Integer id) {
		codeRepository.deleteById(id);
		return null;

	}

}
