package com.example.paketlengkap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.paketlengkap.model.Post;
import com.example.paketlengkap.service.PostService;

@Controller
public class PostController {
    @Autowired
	private PostService postService;

    @GetMapping("/dashboard")
    public String viewHomePage(Model model) {
		return "dashboard/home";	
	}
	
	// display list of post
	@GetMapping("/dashboard/posts")
	public String viewPostsPage(Model model) {
		return findPaginated(1, "id", "asc", model);		
	}
	
	@GetMapping("/dashboard/posts/create")
	public String showCreatePostForm(Model model) {
		// create model attribute to bind form data
		Post post = new Post();
		model.addAttribute("post", post);
		return "dashboard/create";
	}
	
	@PostMapping("/dashboard/posts/savePost")
	public String savePost(@ModelAttribute("post") Post post) {
		// save post to database
		postService.savePost(post);
		return "redirect:/dashboard/posts";
	}
	
	@GetMapping("/dashboard/posts/edit/{id}")
	public String showFormForEdit(@PathVariable ( value = "id") long id, Model model) {
		
		// get post from the service
		Post post = postService.getPostById(id);
		
		// set post as a model attribute to pre-populate the form
		model.addAttribute("post", post);
		return "dashboard/edit";
	}
	
	@GetMapping("/dashboard/posts/delete/{id}")
	public String deletePost(@PathVariable (value = "id") long id) {
		
		// call delete post method 
		this.postService.deletePostById(id);
		return "redirect:/dashboard/posts";
	}

	@GetMapping("/dashboard/posts/show/{id}")
	public String showPost (@PathVariable ( value = "id") long id, Model model) {
		
		// get post from the service
		Post post = postService.getPostById(id);
		
		// set post as a model attribute to pre-populate the form
		model.addAttribute("post", post);
		return "dashboard/show";
	}
	
	
	@GetMapping("/dashboard/posts/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Post> page = postService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Post> listPost = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listPost", listPost);
		return "dashboard/posts";
	}
}
