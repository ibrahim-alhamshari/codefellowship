package com.example.codefellowship.repository;

import com.example.codefellowship.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepo extends CrudRepository<ApplicationUser, Integer> {

    public ApplicationUser findByUsername(String username);
}
