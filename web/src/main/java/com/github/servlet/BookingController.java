package com.github.servlet;

import com.github.OrderService;
import com.github.hib.entity.Address;
import com.github.hib.entity.ItemEntity;
import com.github.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class BookingController {


    @Autowired
    OrderService orderService;

    public BookingController(OrderService orderService) {
        this.orderService = orderService;

    }

    //create
    @PostMapping("/createorder")
    public String createOrder(HttpServletRequest request){
        List <ItemEntity> itemList = new ArrayList<>();
//        = request.getParameter("itemList");
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        String userName = request.getParameter("userName");
        Address address = new Address();
        Integer sum = Integer.valueOf(request.getParameter("sum"));

        Order order = new Order(orderId,userName,itemList,sum,address);
        orderService.createOrder(order);
        return null;
    }

    //read
    @GetMapping ("myOrders")
    public String getOrderByUserName(HttpServletRequest request){

        return "orders";
    }

    //update
    @PostMapping("/updateorder")
    public String updateOrder(HttpServletRequest request){
        return null;
    }

    //delete
    @PostMapping("/deleteorder")
    public String deleteOrder(HttpServletRequest request)
    {
        return "redirect:/orders";
    }

}
