package com.example.paketlengkap.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.paketlengkap.model.Post;

public interface PostService {
	List<Post> getAllPosts();
	void savePost(Post post);
	Post getPostById(long id);
	void deletePostById(long id);
	Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
