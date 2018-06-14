package com.can.controller;

import com.can.model.Customer;
import com.can.service.OrderService;
import com.can.service.ProductService;
import com.can.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    // INDEX
    @RequestMapping(value={"/", "/index", "/home"}, method = RequestMethod.GET)
    public String home(Model model){
        model.addAttribute("customerLoginMessage", "");
        model.addAttribute("ownerLoginMessage", "");
        return "index";
    }

    // OWNER MAPPINGS
    @RequestMapping(value = "/owner/dashboard", method = RequestMethod.GET)
    public String ownerHomepage(Model model){
        return "/owner/dashboard";
    }
    @RequestMapping(value = "/owner/product", method = RequestMethod.GET)
    public String ownerProductPage(Model model, HttpServletRequest request){
        model.addAttribute("products", productService.getAll());
        model.addAttribute("newProductAddedMessage", request.getSession().getAttribute("newProductAddedMessage"));
        model.addAttribute("productUpdatedMessage", request.getSession().getAttribute("productUpdatedMessage"));
        model.addAttribute("productDeletedMessage", request.getSession().getAttribute("productDeletedMessage"));
        return "/owner/product";
    }
    @RequestMapping(value = "/owner/customer", method = RequestMethod.GET)
    public String ownerCustomerPage(Model model, HttpServletRequest request){
        model.addAttribute("customers", userService.getCustomers());
        model.addAttribute("newCustomerAddedMessage", request.getSession().getAttribute("newCustomerAddedMessage"));
        model.addAttribute("customerUpdatedMessage", request.getSession().getAttribute("customerUpdatedMessage"));
        return "/owner/customer";
    }
    @RequestMapping(value = "/owner/order", method = RequestMethod.GET)
    public String ownerOrderPage(Model model, HttpServletRequest request){
        model.addAttribute("customers", userService.getCustomers());
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("products", productService.getAll());
        model.addAttribute("newOrderAddedMessage", request.getSession().getAttribute("newOrderAddedMessage"));
        model.addAttribute("orderUpdatedMessage", request.getSession().getAttribute("orderUpdatedMessage"));
        return "/owner/order";
    }



    // CUSTOMER MAPPINGS
    @RequestMapping(value = "/customer/dashboard", method = RequestMethod.GET)
    public String customerHomepage(Model model){
        return "/customer/dashboard";
    }
    @RequestMapping(value = "/customer/order", method = RequestMethod.GET)
    public String customerOrderPage(Model model, HttpServletRequest request){
        model.addAttribute("orders", orderService.getOrdersByUsername((String) request.getSession().getAttribute("customerUsername")));
        model.addAttribute("products", productService.getAll());
        model.addAttribute("newOrderAddedMessage", request.getSession().getAttribute("newOrderAddedMessage"));
        return "/customer/order";
    }
    @RequestMapping(value = "/customer/profile", method = RequestMethod.GET)
    public String customerProfilePage(Model model, HttpServletRequest request){
        Customer customer = userService.getCustomerByUsername((String) request.getSession().getAttribute("customerUsername"));
        request.getSession().setAttribute("profile", customer);
        model.addAttribute("customerUpdatedMessage", request.getSession().getAttribute("customerUpdatedMessage"));
        model.addAttribute("customerUpdatedPasswordMessage", request.getSession().getAttribute("customerUpdatedPasswordMessage"));
        return "/customer/profile";
    }
}
