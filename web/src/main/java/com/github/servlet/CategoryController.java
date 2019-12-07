package com.github.servlet;

import com.github.CategoryService;
import com.github.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //create
    @PostMapping("/createCategory")
    public String createCategory(HttpServletRequest request) {
        String name = request.getParameter("name");
        Category category = new Category(name);
        categoryService.createCategory(category);
        return "redirect:/categories";
    }

    //read
    @GetMapping("/category")
    public String getCategoryByItemName(HttpServletRequest request) {
        String name = request.getParameter("name");

        categoryService.readCategory(name);
        return "items";
    }


    //update
    @PostMapping("/category")
    public String updateCategory(HttpServletRequest request) {
        String name = request.getParameter("name");
        Integer id = Integer.valueOf(request.getParameter("id"));
        request.setAttribute("id", id);

        categoryService.updateCategory(name, id);
        return "items";
    }

    //delete
    @PostMapping("deleteCategory")
    public String deleteCategory(HttpServletRequest request) {
        String name = request.getParameter("name");
        categoryService.deleteCategory(name);

        return "items";
    }


    //getAll
    @GetMapping("/allcategories")
    public String getAll(HttpServletRequest request) {
        List<Category> all = categoryService.getAll();
        return "categories";
    }


}
