package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.util.Role;

@SpringBootTest//SpringBootの機能を有効にする
@Transactional//テスト開始から終了までをTransaction化する
class UserDetailsServiceImplTest {
	
	@Autowired
	SiteUserRepository repository;
	
	@Autowired
	UserDetailsServiceImpl service;

	@Test
	@DisplayName("ユーザー名が存在する時、ユーザー詳細を取得することを期待します")
	void whenUsernameExists_expectToGetUserDetails() {
		
		//Arange(準備)
		SiteUser user = new SiteUser();
		user.setUsername("原田");
		user.setPassword("password");
		user.setEmail("harada@example.com");
		user.setRole(Role.USER.name());
		repository.save(user);
		
		//Act（実行）
		UserDetails actual = service.loadUserByUsername("原田");
		
		//Assert（検証）
		assertEquals(user.getUsername(), actual.getUsername());
	}
	
	@Test
	@DisplayName("ユーザー名が存在しない時、例外をスローします")
	void whenUsernameDoesNotExist_throwException() {
		//Act & Assert
		assertThrows(UsernameNotFoundException.class,
				() -> service.loadUserByUsername("竹田"));
	}

}
