package tr.org.linux.kamp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tr.org.linux.kamp.thymeleaf.annotation.NoLayout;

@Controller
@RequestMapping("/")
public class IndexController {
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("title", "Dashboard");
		return "index";
	}
	
	@NoLayout
	@RequestMapping("/nolayout")
	public String nolayout() {
		return "nolayout";
	}
	
	@NoLayout
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}
