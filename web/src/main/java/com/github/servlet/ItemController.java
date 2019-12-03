package com.github.servlet;

import com.github.CategoryService;
import com.github.ItemService;
import com.github.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    //admin


    //create
    @PostMapping("/createitem")
    public String getItem(HttpServletRequest rq) {
        String name = rq.getParameter("name");
        String description = rq.getParameter("description");
        Integer quantity = Integer.valueOf(rq.getParameter("quantity"));
        Integer price = Integer.valueOf(rq.getParameter("price"));
        Integer categoryId = Integer.valueOf(rq.getParameter("categoryId"));
        Item item1 = new Item(name, description, quantity, price);
        Item item = itemService.createItem(item1, categoryId);
        if (item != null) {
            rq.getSession().setAttribute("item", item);
            rq.getSession().setAttribute("id", item.getId());
            rq.getSession().setAttribute("name", name);
            rq.getSession().setAttribute("description", description);
            rq.getSession().setAttribute("quantity", quantity);
            rq.getSession().setAttribute("price", price);
            return "itemlistadmin";
        }
        return "itemlistadmin";
    }


    //read
    @GetMapping("/readitem")
    public String readItem(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Item item = itemService.readItem(id);
        request.setAttribute("item", item);
        return "items";
    }

    @PostMapping("/readitem")
    protected String doPost(HttpServletRequest request) {

        String item_name = request.getParameter("item_name");
        itemService.readItem(item_name);
        return "itemlistadmin";

    }


    //update
    @GetMapping("/updateitem")
    public String updateItem(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer price = Integer.parseInt(request.getParameter("price"));
        itemService.updateItem(price, id);
        log.info("item updated:{} at {}", id, LocalDateTime.now());
        return "redirect:/itemlistadmin";
    }


    //delete
    @PostMapping("/deleteitem")
    public String deleteItem(HttpServletRequest request) {

        Integer id = Integer.parseInt(request.getParameter("del"));
        request.setAttribute("id", id);
        itemService.deleteItem(id);
        return "itemlistadmin";
    }


    //all
    //getAll
    @GetMapping("/itemlistadmin")
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

        return "itemlistadmin";
    }


}

