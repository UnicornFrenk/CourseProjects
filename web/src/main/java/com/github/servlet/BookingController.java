package com.github.servlet;

import com.github.ItemService;
import com.github.OrderService;
import com.github.hib.entity.Address;
import com.github.hib.entity.ItemEntity;
import com.github.model.Item;
import com.github.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping()
public class BookingController {


    OrderService orderService;

    ItemService itemService;

    public BookingController(OrderService orderService, ItemService itemService) {

        this.orderService = orderService;
        this.itemService = itemService;

    }

    //create

    @GetMapping("/neworder")
    public String createNewOrder(HttpServletRequest request) {
        String pageNumber1 = request.getParameter("pageNumber");
        if (pageNumber1 == null) {
            pageNumber1 = "1";
        }

        Integer pageNumber = Integer.valueOf(pageNumber1);

        List<Item> items = itemService.getPage(pageNumber);
        request.setAttribute("items", items);

        Long countOfPage = itemService.countOfPage();
        request.setAttribute("pageCount", countOfPage);
        return "neworder";
    }

    @PostMapping("/neworder")
    public String createOrder(HttpServletRequest request) {
        request.setAttribute("items", itemService.getAll());
        Integer id = Integer.valueOf(request.getParameter("id"));
        if (id != null) {
            String name = request.getParameter("name");
            Integer price = Integer.valueOf(request.getParameter("price"));
            Integer quantity = Integer.valueOf(
                    request.getParameter("quantity"));
            int sum = price * quantity;
            request.setAttribute("result",sum);
        }
        return "redirect:/basket";
    }

    //read
    @GetMapping("myOrders")
    public String getOrderByUserName(HttpServletRequest request) {

        return "orders";
    }

    //update
    @PostMapping("/updateorder")
    public String updateOrder(HttpServletRequest request) {
        return null;
    }

    //delete
    @PostMapping("/deleteorder")
    public String deleteOrder(HttpServletRequest request) {
        return "redirect:/orders";
    }

}
