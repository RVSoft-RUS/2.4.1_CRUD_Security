package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import web.model.User;
import web.service.UserService;

import javax.servlet.RequestDispatcher;
import java.util.*;

@Controller
@RequestMapping("/")
public class UsersController {
	private final UserService userService;

	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "hello", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC CRUD application");
		messages.add("5.2.0 version by may'20 ");
		model.addAttribute("messages", messages);
		return "hello";
	}

	@RequestMapping(value = "admin/users", method = RequestMethod.GET)
	public String printUsers(@RequestParam(value = "locale", required = false,
			defaultValue = "ru") String locale, ModelMap model) {
		List<User> userList = userService.getAllUsers();
		model.addAttribute("userList", userList);
		return "admin/users";
	}

	@RequestMapping(value = "user", method = RequestMethod.GET)
	public String userInfo(ModelMap model) {
		return "user/userInfo";
	}

	@RequestMapping(value = "admin/edit", method = RequestMethod.GET)
	public String editUserGet(@RequestParam(value = "id") String idStr, ModelMap model) {
		User user;
		try {
			long id = Long.parseLong(idStr);
			user = userService.getUserById(id);
			if (user == null) {
				model.addAttribute("errorText", "This user doesn't exist.");
				return "error";
			}
			model.addAttribute("userForEdit", user);
			return "admin/editUser";
		} catch (Exception e) {
			model.addAttribute("errorText", "Error while processing user.");
			return "error";
		}
	}

	@RequestMapping(value = "admin/edit", method = RequestMethod.POST)
	public String editUserPost(WebRequest webRequest, ModelMap model) {
		try {
			String name =  webRequest.getParameter("name");
			String surname =  webRequest.getParameter("surname");
			int age = Integer.parseInt(Objects.requireNonNull(webRequest.getParameter("age")));
			long id = Integer.parseInt(Objects.requireNonNull(webRequest.getParameter("id")));
			String login =  webRequest.getParameter("login");
			String password =  webRequest.getParameter("password");
			String role =  webRequest.getParameter("role");
			if (name.isEmpty() || surname.isEmpty() || age < 0 || age > 150) {
				model.addAttribute("errorText", "Incorrect user fields.");
				return "error";
			}
			User user = new User(id, login, password, role, name, surname, age);
			if (!userService.updateUser(user)) {
				model.addAttribute("errorText", "Error while processing user edit.");
				return "error";
			}
			return "redirect:users";
		} catch (Exception e) {
			model.addAttribute("errorText", "Error while processing user.");
			return "error";
		}
	}

	@RequestMapping(value = "admin/delete", method = RequestMethod.GET)
	public String deleteUserGet(@RequestParam(value = "id") String idStr, ModelMap model) {
		User user;
		try {
			long id = Long.parseLong(idStr);
			user = userService.getUserById(id);
			if (user == null) {
				model.addAttribute("errorText", "This user doesn't exist.");
				return "error";
			}
			model.addAttribute("userForDelete", user);
			return "admin/deleteUser";
		} catch (Exception e) {
			model.addAttribute("errorText", "Error while processing user.");
			return "error";
		}
	}

	@RequestMapping(value = "admin/delete", method = RequestMethod.POST)
	public String deleteUserPost(WebRequest webRequest, ModelMap model) {
		try {
			String surname =  webRequest.getParameter("surname");
			String name =  webRequest.getParameter("name");
			String login =  webRequest.getParameter("login");
			String password =  webRequest.getParameter("password");
			String role =  webRequest.getParameter("role");
			int age = Integer.parseInt(Objects.requireNonNull(webRequest.getParameter("age")));
			long id = Integer.parseInt(Objects.requireNonNull(webRequest.getParameter("id")));

			User user = new User(id, login, password, role, name, surname, age);
			if (!userService.deleteUser(user)) {
				model.addAttribute("errorText", "Error while processing user edit.");
				return "error";
			}
			return "redirect:users";
		} catch (Exception e) {
			model.addAttribute("errorText", "Error while processing user.");
			return "error";
		}
	}

	@RequestMapping(value = "admin/add", method = RequestMethod.GET)
	public String addUserGet() {
		return "admin/addUser";
	}

	@RequestMapping(value = "admin/add", method = RequestMethod.POST)
	public String addUserPost(WebRequest webRequest, ModelMap model) {
		try {
			String name =  webRequest.getParameter("name");
			String surname =  webRequest.getParameter("surname");
			int age = Integer.parseInt(Objects.requireNonNull(webRequest.getParameter("age")));
			if (name.isEmpty() || surname.isEmpty() || age < 0 || age > 150) {
				model.addAttribute("errorText", "Incorrect user fields.");
				return "error";
			}
			String login =  webRequest.getParameter("login");
			String password =  webRequest.getParameter("password");
			String role =  webRequest.getParameter("role");

			if (userService.isExistLogin(login)) {
				model.addAttribute("errorText", "User with same login already exist.");
				return "error";
			}
			User user = new User(login, password, role, name, surname, age);
			if (!userService.addUser(user)) {
				model.addAttribute("errorText", "Error while processing user edit.");
				return "error";
			}
			model.addAttribute("errorText", "User was added successfully!");
			return "error";
		} catch (Exception e) {
			model.addAttribute("errorText", "Error while processing user.");
			return "error";
		}
	}

	@GetMapping("/raw")
	@ResponseBody
	public String raw() {
		return "raw data";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}
}