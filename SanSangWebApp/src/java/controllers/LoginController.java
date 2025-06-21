/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Address.AddressDAO;
import models.User.UserDAO;
import models.User.UserDTO;
import utils.Message;

/**
 *
 * @author tiozo
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    
    /// su dung RoleController de load listRole
    private static final String SUCCESS = "RoleController";
    
    private static final String ANNOUCE = "MESSAGE";
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
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            if (userID != null && password != null) {
                Message message = null;
                UserDAO dao = new UserDAO();
                UserDTO user = dao.getCredential(userID, password);
                HttpSession session = request.getSession();
                if (user == null) {
                    message = Message.error()
                                .withText("Username or Password doesn't match with any credential.")
                                .build();
                    request.setAttribute(ANNOUCE, message);
                } else {
                    url = SUCCESS;
                    message = Message.success()
                                .withText("Login successfully.")
                                .build();
                    request.setAttribute(ANNOUCE, message);
                    session.setAttribute(USER, user);
                    AddressDAO addressDao = new AddressDAO();
                    session.setAttribute("ADDRESS", addressDao.getAddressByID(user.getAddressId()));
                }
            }
        } catch (Exception e) {
            log("Error at LoginController: " + e.getMessage());
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
