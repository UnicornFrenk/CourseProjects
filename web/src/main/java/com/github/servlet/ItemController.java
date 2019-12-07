package com.github.servlet;

import com.github.CategoryService;
import com.github.ItemService;
import com.github.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("")
public class ItemController {
    private static final Logger log = LoggerFactory.getLogger(
            ItemController.class);

    private CategoryService categoryService;

    private ItemService itemService;

    public ItemController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;

    }

    //create
    @GetMapping("/createitem")
    public String createItem(HttpServletRequest rq) {
        return "createitem";
    }

    @PostMapping("/createitem")
    public String createItemPost(HttpServletRequest rq){

        String name = rq.getParameter("name");
        String description = rq.getParameter("description");
        Integer quantity = Integer.valueOf(rq.getParameter("quantity"));
        Integer price = Integer.valueOf(rq.getParameter("price"));
        Integer category = Integer.valueOf(rq.getParameter("categoryName"));
        Item item = new Item(name,description,quantity,price);
        itemService.createItem(item,category);
        return "redirect:/itemlistadmin";
    }

    //update
    @GetMapping("/updateitem")
    public String updateItem(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Item item = itemService.readItem(id);
        request.setAttribute("item", item);
        return "updateitem";
    }

    @PostMapping("/updateitem")
    public String updateItemPost(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer price = Integer.parseInt(request.getParameter("price"));
        Item item = itemService.readItem(id);
        request.setAttribute("item", item);
        itemService.updateItemById(price, id);
        log.info("item updated:{} at {}", id, LocalDateTime.now());
        return "redirect:/itemlistadmin";
    }


    //delete
    @PostMapping("/deleteitem")
    public String deleteItem(HttpServletRequest request) {

        Integer id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("id", id);
        itemService.deleteItem(id);
        return "redirect:/itemlistadmin";
    }


    //all
    //getAll
    @GetMapping("/itemlistadmin")
    public String showAllItemsAdmin(HttpServletRequest request) {

        String pageNumber1 = request.getParameter("pageNumber");
        if (pageNumber1 == null) {
            pageNumber1 = "1";
        }

        Integer pageNumber = Integer.valueOf(pageNumber1);

        List<Item> items = itemService.getPage(pageNumber);
        request.setAttribute("items", items);

        Long countOfPage = itemService.countOfPage();
        request.setAttribute("pageCount", countOfPage);

        return "itemlistadmin";
    }

    @GetMapping("/itemlist")
    public String showAllItems(HttpServletRequest request) {

        String pageNumber1 = request.getParameter("pageNumber");
        if (pageNumber1 == null) {
            pageNumber1 = "1";
        }

        Integer pageNumber = Integer.valueOf(pageNumber1);

        List<Item> items = itemService.getPage(pageNumber);
        request.setAttribute("items", items);

        Long countOfPage = itemService.countOfPage();
        request.setAttribute("pageCount", countOfPage);

        return "itemlist";
    }

    @PostMapping("/totalsum")
    public String getTotalSum(HttpServletRequest request) {
        Integer quantity = Integer.valueOf(request.getParameter("quantity"));

        return "";
    }

}

