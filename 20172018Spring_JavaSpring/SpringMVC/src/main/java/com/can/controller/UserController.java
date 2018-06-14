package com.can.controller;

import com.can.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/owner/customer/add", method = RequestMethod.POST)
    public String addCustomer(@RequestParam(value = "addNewCustomerName")String name,
                              @RequestParam(value = "addNewCustomerSurname")String surname,
                              @RequestParam(value = "addNewCustomerUsername")String username,
                              @RequestParam(value = "addNewCustomerPassword")String password,
                              @RequestParam(value = "addNewCustomerFloor")String floor,
                              @RequestParam(value = "addNewCustomerBuilding")String building,
                              @RequestParam(value = "addNewCustomerRoom")String room,
                              HttpServletRequest request){
        String message = userService.addCustomer(username, password, name, surname, floor, building, room);
        request.getSession().setAttribute("newCustomerAddedMessage", message);
        return "redirect:/owner/customer";
    }

    @RequestMapping(value = "/customer/profile/update", method = RequestMethod.POST)
    public String updateCustumer(@RequestParam(value = "updateCustomerID")String id,
                                 @RequestParam(value = "updateCustomerName")String name,
                                 @RequestParam(value = "updateCustomerSurname")String surname,
                                 @RequestParam(value = "updateCustomerFloor")String floor,
                                 @RequestParam(value = "updateCustomerBuilding")String building,
                                 @RequestParam(value = "updateCustomerRoom")String room,
                                 HttpServletRequest request){
        String message = userService.updateCustomer(Integer.parseInt(id.trim()), name, surname, floor, building, room);
        request.getSession().setAttribute("customerUpdatedMessage", message);
        return "redirect:/customer/profile";
    }


    @RequestMapping(value = "/owner/customer/update", method = RequestMethod.POST)
    public String addCustomer(@RequestParam(value = "updateCustomerID")String id,
                              @RequestParam(value = "updateCustomerName")String name,
                              @RequestParam(value = "updateCustomerSurname")String surname,
                              @RequestParam(value = "updateCustomerFloor")String floor,
                              @RequestParam(value = "updateCustomerBuilding")String building,
                              @RequestParam(value = "updateCustomerRoom")String room,
                              HttpServletRequest request){
        String message = userService.updateCustomer(Integer.parseInt(id.trim()), name, surname, floor, building, room);
        request.getSession().setAttribute("customerUpdatedMessage", message);
        return "redirect:/owner/customer";
    }

    @RequestMapping(value = "/customer/profile/updatePassword", method = RequestMethod.POST)
    public String updateCustumerPassword(@RequestParam(value = "updateCustomerOldPassword")String oldPassword,
                                         @RequestParam(value = "updateCustomerNewPassword")String newPassword,
                                         @RequestParam(value = "updateCustomerNewPasswordAgain")String newPasswordAgain,
                                         HttpServletRequest request){
        String message = userService.updateCustomerPassword((String) request.getSession().getAttribute("customerUsername"), oldPassword, newPassword, newPasswordAgain);
        request.getSession().setAttribute("customerUpdatedPasswordMessage", message);
        return "redirect:/customer/profile";
    }
}
