package com.meta.metaway.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.multiClass.MultiClass;
import com.meta.metaway.user.model.User;
import com.meta.metaway.user.service.IUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
    private IUserService userService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private MultiClass multiClass;
	

    @GetMapping("/profile")
    public String getProfilePage(HttpServletRequest request, Model model) {
        String token = multiClass.getToken(request);

        if (token != null) {
            String username = jwtUtil.getUsername(token);
            User user = userService.getUserByUsername(username);

            if (user != null) {
                model.addAttribute("userProfile", user);
                return "user/profile";
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/update")
    public String getUpdateProfilePage(HttpServletRequest request, Model model) {
        String token = multiClass.getToken(request);
        String redirectUrl = "redirect:/login"; // 기본적으로 로그인 페이지로 리다이렉트

        if (token != null) {
            String username = jwtUtil.getUsername(token);
            User userProfile = userService.getUserByUsername(username);

            if (userProfile != null) {
                model.addAttribute("userProfile", userProfile);
                redirectUrl = "user/updateProfile";
            }
        }

        return redirectUrl;
    }
	
	    @PostMapping("/update")
	    public String updateUser(HttpServletRequest request, @ModelAttribute("userProfile") User updatedUser) {
	        String token = multiClass.getToken(request);
	
	        if (token != null) {
	            String username = jwtUtil.getUsername(token);
	            System.out.println("회원 수정에서 유저네임: " + username);
	            userService.updateUser(username, updatedUser);
	        }
	
	        return "redirect:/user/profile"; 
	    }
	    
	    @GetMapping("/delete")
	    public String getdeleteUser(HttpServletRequest request) {
	        String token = multiClass.getToken(request);
	        
	        if (token != null) {
		    	return "user/delete";

	        }
	    	return "user/delete";
	    }
	    
	    @PostMapping("/delete")
	    public String deleteUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("deleteUser") User deleteUser,
	    		@RequestParam("password") String password, Model model) {
	        String token = multiClass.getToken(request);
	        String redirectUrl = "redirect:/user/profile";
	        
	        if (token != null) {
	            Long id = jwtUtil.getId(token);
	            
	            boolean passwordMatches = userService.checkPassword(id, password);
	            if (passwordMatches) {
	                userService.deleteUserById(id);
	          
	                model.addAttribute("success", true);

	                Cookie deleteCookie = new Cookie("token", null);
	                deleteCookie.setMaxAge(0); 
	                deleteCookie.setHttpOnly(true); 
	                deleteCookie.setPath("/"); 
	                response.addCookie(deleteCookie);
	                
	    	        HttpSession session = request.getSession(false);
	    	        if (session != null) {
	    	            session.invalidate();
	    	        }
	    	        
	            } else {
	                model.addAttribute("failure", true);

	                redirectUrl = "redirect:/user/profile";
	            }
	        }
			return redirectUrl;
	    }
	    

}
