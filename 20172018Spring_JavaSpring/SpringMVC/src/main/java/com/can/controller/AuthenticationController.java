package com.can.controller;

import com.can.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthenticationController {
    @Autowired
    UserService userService;

    // LOGINS
    @RequestMapping(value="/customer/login", method = RequestMethod.POST)
    public String customerLogin(@RequestParam(value="customerUsername")String username, @RequestParam(value="customerPassword")String password, HttpServletRequest request){
        if(this.userService.isCustomer(username, password)){
            request.getSession().setAttribute("isCustomerLoggedIn", "true");
            request.getSession().setAttribute("customerUsername", username);
            request.getSession().setAttribute("customerPassword", password);
            return "redirect:/customer/dashboard";
        } else{
            return "redirect:/badCustomerLogin";
        }
    }
    @RequestMapping(value="/owner/login", method = RequestMethod.POST)
    public String ownerLogin(@RequestParam(value="ownerUsername")String username, @RequestParam(value="ownerPassword")String password, HttpServletRequest request){
        if(this.userService.isOwner(username, password)){
            request.getSession().setAttribute("isOwnerLoggedIn", "true");
            request.getSession().setAttribute("ownerUsername", username);
            request.getSession().setAttribute("ownerPassword", password);
            // TODO:
            return "redirect:/owner/dashboard";
        } else{
            return "redirect:/badOwnerLogin";
        }
    }


    // BADLOGINS
    @RequestMapping(value="/badOwnerLogin", method = RequestMethod.GET)
    public String badOwnerLogin(Model model){
        model.addAttribute("customerLoginMessage", "");
        model.addAttribute("ownerLoginMessage", "Bad Login");
        return "index";
    }
    @RequestMapping(value="/badCustomerLogin", method = RequestMethod.GET)
    public String badCustomerLogin(Model model){
        model.addAttribute("customerLoginMessage", "Bad Login");
        model.addAttribute("ownerLoginMessage", "");
        return "index";
    }

    // logout
    @RequestMapping(value = "/logout")
    public String logout(Model model, HttpServletRequest request){
        request.getSession().setAttribute("isOwnerLoggedIn", "false");
        request.getSession().setAttribute("isCustomerLoggedIn", "false");
        return "logout";
    }



}
