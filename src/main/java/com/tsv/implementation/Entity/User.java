package com.tsv.implementation.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	private String email;

	private String password;


	private int sem;

	private String branch;

	private  String collage_id;

	public int getSem() {
		return sem;
	}

	public void setSem(String sem) {
		this.sem = Integer.parseInt(sem);
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCollage_id() {
		return collage_id;
	}

	public void setCollage_id(String collage_id) {
		this.collage_id = collage_id;
	}


	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Role role) {
		this.roles.add(role);
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinTable(name = "users_role", joinColumns = @JoinColumn(name = "cust_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") )
	Set<Role> roles = new HashSet<Role>();

	private int otp;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public String getRole() {
//		return role;
//	}

//	public Set<Role> getRole() {
//		return roles;
//	}
//
//	public void setRole(Role role) {
//		this.roles.add(role);
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}


}
