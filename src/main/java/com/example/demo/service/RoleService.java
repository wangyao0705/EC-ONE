package com.example.demo.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;

@Service
@Transactional
public class RoleService {
	@Autowired
	RoleRepository roleRepository;

	/*
	 * characterNameで職位状態を取り出し
	 */
	public Role findstatusbychname(String characterName) {
		return roleRepository.findByCharacterNames(characterName);

	}

	/*
	 * 職位状態と更新時間を更新
	 */
	public void updateRoleStatus(String characterName, String statusbycharacter, Timestamp dateModifie) {
		roleRepository.updateRole(characterName, statusbycharacter, dateModifie);
	}

	/*
	 * 全てを取り出す
	 */
	public List<Role> findall() {
		return roleRepository.findAll();
	}
}
