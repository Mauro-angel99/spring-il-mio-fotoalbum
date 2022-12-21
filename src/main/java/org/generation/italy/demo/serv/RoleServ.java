package org.generation.italy.demo.serv;

import java.util.List;

import org.generation.italy.demo.pojo.Role;
import org.generation.italy.demo.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServ {

	@Autowired
	private RoleRepo roleRepo;
	
	public void save(Role role) {
		
		roleRepo.save(role);
	}
	public void delete(Role role) {
		
		roleRepo.delete(role);
	}
	public Role findById(int id) {
		
		return roleRepo.findById(id).get();
	}
	public List<Role> findAll() {
		
		return roleRepo.findAll();
	}
}
