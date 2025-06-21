/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role.RoleDTO;
import models.User.UserDAO;
import models.User.UserDTO;
import utils.Message;
import comparators.RoleDTOComparator;

/**
 *
 * @author tiozo
 */
@WebServlet(name = "SearchUserController", urlPatterns = {"/SearchUserController"})
public class SearchUserController extends HttpServlet {

    private static final String ERROR = "admin.jsp";
    private static final String SUCCESS = "admin.jsp";
    
    private static final String SEARCH_FAIL = "No user's name was found.";
    
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
            String search = request.getParameter("search");
            if (request.getParameter("userID") != null) {
                search = request.getParameter("userID");
            }
            String roleParam = request.getParameter("roleFilter");
            HttpSession session = request.getSession();
            List<RoleDTO> listRole = (List<RoleDTO>) session.getAttribute("ROLES");
            listRole.sort(RoleDTOComparator.byIdAscending());
            UserDAO dao = new UserDAO();
            List<UserDTO> listUser = dao.getListUser(search);
            if (listUser != null) {
                if (!listUser.isEmpty()) {
                    for (UserDTO user: listUser) {
                        for (RoleDTO role: listRole) {
                            if (user.getRoleId() == role.getRoleId()) {
                                user.setRole(role.getRoleName());
                            }
                        }
                    }
                    if (roleParam != null && !roleParam.isEmpty()) {
                        List<UserDTO> finalList = new ArrayList();
                        for (UserDTO user: listUser) {
                            if (user.getRoleId() == Integer.parseInt(roleParam)) {
                                finalList.add(user);
                            }
                        }
                        session.setAttribute("USERS", finalList);
                    } else {
                        session.setAttribute("USERS", listUser);
                    }
                    url = SUCCESS;
                } else {
                    request.setAttribute(ANNOUNCE, Message.error()
                        .withText("No users found").build());
                }
            } else {
                request.setAttribute(ANNOUNCE, Message.error()
                    .withText("Database error occurred").build());
            }
            request.setAttribute("lastSearch", search);
        } catch (Exception e) {
            log("Error at SearchUserController: " + e.getMessage());
            request.setAttribute(ANNOUNCE, Message.error()
                                .withText("Error searching users: " + e.getMessage()).build());
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
