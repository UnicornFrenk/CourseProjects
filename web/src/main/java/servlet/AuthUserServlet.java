package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authUser")
public class AuthUserServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/authUser.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
        String login = rq.getParameter("login");
        rq.setAttribute("login", login);
        rq.getRequestDispatcher("/authUser.jsp").forward(rq, rs);
    }
}