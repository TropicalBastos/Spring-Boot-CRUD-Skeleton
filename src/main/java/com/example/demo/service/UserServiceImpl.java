package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.Role;
import com.example.demo.model.User;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	@Override
	public Collection<User> findAll() {
		Collection<User> users = userRepository.findAll();
		return users;
	}
	
	@Override
	public User findByEmail(String email) {
		List<User> users = userRepository.findAll();
		User user = null;
		for (User u : users) {
			if(u.getEmail().equals(email)) {
				user = u;
				break;
			}
		}
		return user;
	}

	@Override
	public User findOne(Integer id) {
		User user = userRepository.findOne(id);
		return user;
	}

	@Override
	public User create(User user) {
		
		//Ensure the entity does not already exist in repo
		if(user.getId() != null) {
			return null;
		}
		
		User savedUser = userRepository.save(user);
		return savedUser;
		
	}

	@Override
	public User update(User user) {
		
		//user to update has to exist in repo
		User userToUpdate = findOne(user.getId());
		if(userToUpdate == null) {
			//user hasn't been persisted
			return null;
		}
		
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setName(user.getName());
		User updatedUser = userRepository.save(userToUpdate);
		return updatedUser;
		
	}

	@Override
	public void delete(Integer id) {
		userRepository.delete(id);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = findByEmail(userName);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, authorities);
	}
	
	
}
