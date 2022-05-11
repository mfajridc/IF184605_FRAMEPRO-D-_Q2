package com.example.paketlengkap.controller;

import java.util.List;

import com.example.paketlengkap.model.User;
import com.example.paketlengkap.repository.UserRepository;
import com.example.paketlengkap.model.Post;
import com.example.paketlengkap.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PostService postService;

	@GetMapping("")
	public String viewHomePage(Model model) {
		model.addAttribute("title", "Bag & Shoe Care");
		model.addAttribute("user", new User());

		return "main/home";
	}

	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepo.save(user);

		return "main/home";
	}

	// display list of post
	@GetMapping("/blog")
	public String viewBlogPage(Model model) {
		model.addAttribute("title", "Blog - Bag & Shoe Care");
		model.addAttribute("user", new User());
		// return "main/blog";
		return findPaginated(1, "id", "asc", model);
	}

	@GetMapping("/blog/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("title", "Blog - Bag & Shoe Care");

		int pageSize = 6;

		Page<Post> page = postService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Post> listPost = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listPost", listPost);
		return "main/blog";
	}

	@GetMapping("/blog/show/{id}")
	public String showPost (@PathVariable ( value = "id") long id, Model model) {
		model.addAttribute("title", "Post - Bag & Shoe Care");
		model.addAttribute("user", new User());
		
		// get post from the service
		Post post = postService.getPostById(id);
		
		// set post as a model attribute to pre-populate the form
		model.addAttribute("post", post);
		return "main/post";
	}
}
