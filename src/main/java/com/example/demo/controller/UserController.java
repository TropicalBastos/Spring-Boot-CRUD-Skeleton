package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.service.UserServiceImpl;

@Controller
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("/users")
	public String showAllUsers(Model model){
		List<User> users = (List<User>) userService.findAll();
		model.addAttribute("users", users);
		return "user/users";
	}
	
	@GetMapping("/user/edit")
	public String createUser(
			@RequestParam(value="id", required=false) Integer id, Model model) {
		User user;
		try {
			user = userService.findOne(id);
		}catch(Exception e) {
			user = null;
		}
		
		model.addAttribute("user", user);
		return "user/createuser";
	}
	
	@PostMapping("user/update")
	public String store(
			@RequestParam(value="id", required=false) Integer id,
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="email", required=true) String email,
			@RequestParam(value="password", required=true) String password,
			Model model) {
		User user;
		boolean updated = false;
		try {
			user = userService.findOne(id);
			if(user != null) {
				user.setEmail(email);
				user.setName(name);
				user.setPassword(password);
				userService.save(user);
				updated = true;
				model.addAttribute("updated", "Record updated!");
			}
		}catch(Exception e) {
			
		}
		
		if(!updated) {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setName(name);
			newUser.setPassword(password);
			userService.create(newUser);
			model.addAttribute("added", "Record added successfully");
		}
		
		//tell the view where the controller has come from
		model.addAttribute("fromupdate",true);
		
		return showAllUsers(model);
	}
	
	@PostMapping("/user/delete")
	public String deleteUser(@RequestParam(value="id", required=true) Integer id, Model model) {
		
		userService.delete(id);
		model.addAttribute("deleted", "Record successfully deleted");
		return showAllUsers(model);
		
	}

}
