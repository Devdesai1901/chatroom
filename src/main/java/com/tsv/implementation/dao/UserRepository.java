package com.tsv.implementation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tsv.implementation.Entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value = "select u from User u where u.email = ?1")
	User findByEmail(String emailId);
}
