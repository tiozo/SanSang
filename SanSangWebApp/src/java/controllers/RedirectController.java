/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User.UserDTO;
import utils.Message;

/**
 * Class nay sinh ra de thuc hien vai tro ban dau cua RoleController ve sau bo sung them chuc nang nen khong dung nua
 * @author tiozo
 */
@WebServlet(name = "RedirectController", urlPatterns = {"/RedirectController"})
public class RedirectController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    
    private static final String ADMIN_PAGE = "admin.jsp";
    private static final String MANAGER_PAGE = "manager.jsp";
    private static final String SHOPPING_PAGE = "shopping.jsp";

    
    private static final String ANNOUNCE = "MESSAGE";
    private static final String USER = "LOGIN_USER";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO)session.getAttribute(USER);
            if (user == null) {
                session.setAttribute(ANNOUNCE, Message.error()
                           .withText("User Object is Null").build());
            } else {
                switch (user.getRoleId()) {
                    case 1:
                        url = ADMIN_PAGE;
                        break;
                    case 2:
                        url = MANAGER_PAGE;
                        break;
                    case 3:
                        url = SHOPPING_PAGE;
                        break;
                    default: 
                        session.setAttribute(ANNOUNCE, Message.error()
                           .withText("User Role isn't supported").build());
                }
            }
        } catch (Exception e) {
            log("Error at RedirectController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
