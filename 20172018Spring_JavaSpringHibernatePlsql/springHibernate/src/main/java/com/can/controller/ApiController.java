package com.can.controller;

import com.can.model.Customer;
import com.sun.jndi.cosnaming.IiopUrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ApiController {

    @RequestMapping(value = "/restful/get", method = RequestMethod.GET)
    public Customer addCustomer(HttpServletRequest request){
        Customer customer = new Customer();
        customer.setName("Eben");
        customer.setRoom("Eben");
        customer.setBuilding("Eben");
        customer.setUsername("Eben");
        customer.setPassword("Eben");
        customer.setId(12312312);
        return customer;
    }
}
