package com.can.controller;

import com.can.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/customer/order/add", method = RequestMethod.POST)
    public String customerAddsOrder(@RequestParam(value = "addOrderProductName")String productName,
                                    @RequestParam(value = "addOrderPiece")String piece,
                                    HttpServletRequest request){
        String message = orderService.addOrder((String) request.getSession().getAttribute("customerUsername"), productName, Integer.parseInt(piece));
        request.getSession().setAttribute("newOrderAddedMessage", message);
        return "redirect:/customer/order";
    }



    @RequestMapping(value = "/owner/order/add", method = RequestMethod.POST)
    public String ownerAddsOrder(@RequestParam(value = "addOrderCustomerUsername")String customerUsername,
                                 @RequestParam(value = "addOrderProductName")String productName,
                                 @RequestParam(value = "addOrderPiece")String piece,
                                 HttpServletRequest request){
        String message = orderService.addOrder(customerUsername, productName, Integer.parseInt(piece));
        request.getSession().setAttribute("newOrderAddedMessage", message);
        return "redirect:/owner/order";
    }

    @RequestMapping(value = "/owner/order/update", method = RequestMethod.POST)
    public String ownerUpdatesOrder(@RequestParam(value = "updateOrderID")String orderID,
                                    @RequestParam(value = "updateOrderStatus")String status,
                                    HttpServletRequest request){
        String message = orderService.updateOrder(Integer.parseInt(orderID), status);
        request.getSession().setAttribute("orderUpdatedMessage", message);
        return "redirect:/owner/order";
    }


}
