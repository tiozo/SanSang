/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import exceptions.UserNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role.RoleDAO;
import models.Role.RoleDTO;
import models.User.UserDTO;
import utils.Message;

/**
 *
 * @author tiozo
 */
@WebServlet(name = "RoleController", urlPatterns = {"/RoleController"})
public class RoleController extends HttpServlet {

    private static final String ERROR = "access-denied.jsp";
    private static final String ADMIN = "admin.jsp";
    private static final String MANAGER = "manager.jsp";
    private static final String USER = "ViewShoppingController";
    
    private static final String FAIL_MESSAGE = "Failed to fetch Roles.";
    
    private static final String ANNOUNCE = "MESSAGE";
    
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
        String url = ERROR;
        try {
            RoleDAO dao = new RoleDAO();
            List<RoleDTO> list = dao.getListRole();
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            if (user == null) {
                request.setAttribute(ANNOUNCE, Message.error()
                        .withText("User hasn't loaded properly.").build());
                throw new UserNotFoundException("User not found in session. " + "Please load again");
            }
            if (list != null) {
                if (!list.isEmpty()) {
                    switch (user.getRoleId()) {
                        case 1:
                            url = ADMIN;
                            session.setAttribute("ROLES", list);
                            request.setAttribute(ANNOUNCE, Message.success()
                                            .withText("Roles Fetched.").build());
                            break;
                        case 2:
                            url = USER;
                            break;
                        case 3:
                            url = MANAGER;
                            break;
                        default:
                            url = ERROR;
                    }
                } else {
                    request.setAttribute(ANNOUNCE, Message.error()
                        .withText("No roles found").build());
                }
            } else {
                request.setAttribute(ANNOUNCE, Message.error()
                    .withText("Database error occurred").build());
            }
        } catch (Exception e) {
            log("Error at RoleController: " + e.getMessage());
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