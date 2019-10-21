package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.AppUserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import java.awt.*;


@RestController
@RequestMapping("/api/user")
public class AppUserController {

	private static final Logger log = LoggerFactory.getLogger(AppUserController.class);

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<AppUser> findById(@PathVariable Long id) {
		return ResponseEntity.of(appUserRepository.findById(id));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<AppUser> findByUserName(@PathVariable String username) {
		AppUser user = appUserRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<AppUser> createUser(@RequestBody CreateUserRequest createUserRequest) {
		log.info("Creating user {}", createUserRequest.getUsername());
		AppUser user = new AppUser();
		user.setUsername(createUserRequest.getUsername());
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		if(createUserRequest.getPassword().length() < 7 ||
			!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
			log.error("Error with user password. Cannot create user {}", createUserRequest.getUsername());
			return ResponseEntity.badRequest().build();
		}
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		appUserRepository.save(user);
		return ResponseEntity.ok(user);
	}
	
}
