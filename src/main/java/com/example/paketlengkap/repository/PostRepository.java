package com.example.paketlengkap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.paketlengkap.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
