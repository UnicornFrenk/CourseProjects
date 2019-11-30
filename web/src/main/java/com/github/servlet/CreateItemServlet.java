package com.github.servlet;

import com.github.CategoryService;
import com.github.impl.DefaultCategoryService;
import com.github.impl.DefaultItemService;
import com.github.model.Category;
import com.github.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/createitem")
public class CreateItemServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ItemsServlet.class);
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = DefaultCategoryService.getInstance().getAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/createitem.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
      //  Integer id = Integer.valueOf(rq.getParameter("id"));
        String name = rq.getParameter("name");
        String description = rq.getParameter("description");
        Integer quantity = Integer.valueOf(rq.getParameter("quantity"));
        Integer price = Integer.valueOf(rq.getParameter("price"));
        Integer categoryId = Integer.valueOf(rq.getParameter("categoryId"));

        Item item1 = new Item(name, description, quantity, price);
        Item item = DefaultItemService.getInstance().createItem(item1, categoryId);

        if (item != null) {
            rq.getSession().setAttribute("item", item);
            rq.getSession().setAttribute("id",item.getId());
            rq.getSession().setAttribute("name", name);
            rq.getSession().setAttribute("description", description);
            rq.getSession().setAttribute("quantity", quantity);
            rq.getSession().setAttribute("price", price);

            try {
                rs.sendRedirect(rq.getContextPath() + "/itemlistadmin");
            } catch (IOException e) {
                log.error("unknown error", e);
                throw new RuntimeException(e);
            }
        }
    }}
