package com.can.controller;

import com.can.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/owner/product/add", method = RequestMethod.POST)
    public String addProduct(@RequestParam(value = "productNameAdd")String productName, HttpServletRequest request){
        String message = productService.addProduct(productName);
        request.getSession().setAttribute("newProductAddedMessage", message);
        return "redirect:/owner/product";
    }


    @RequestMapping(value = "/owner/product/update", method = RequestMethod.POST)
    public String updateProduct(@RequestParam(value = "productNameUpdate")String newProductName, @RequestParam(value = "productIDUpdate")String productID, HttpServletRequest request){
        String message = productService.updateProduct(Integer.parseInt(productID), newProductName);
        request.getSession().setAttribute("productUpdatedMessage", message);
        return "redirect:/owner/product";
    }

    @RequestMapping(value = "/owner/product/delete", method = RequestMethod.POST)
    public String deleteProduct(@RequestParam(value = "productIDDelete")String productID, HttpServletRequest request){
        String message = productService.deleteProduct(Integer.parseInt(productID));
        request.getSession().setAttribute("productDeletedMessage", message);
        return "redirect:/owner/product";
    }

}
