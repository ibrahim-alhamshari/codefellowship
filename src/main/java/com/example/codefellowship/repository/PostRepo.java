package com.example.codefellowship.repository;

import com.example.codefellowship.entities.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Integer> {
}
