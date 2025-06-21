/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.Message;

/**
 *
 * @author tiozo
 */
public class MainController extends HttpServlet {

    private static final String INDEX_PAGE = "index.jsp";
    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";
    private static final String SEARCH_USER = "SearchUser";
    private static final String SEARCH_USER_CONTROLLER = "SearchUserController";
    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";
    private static final String CREATE_USER = "CreateUser";
    private static final String CREATE_USER_CONTROLLER = "CreateUserController";
    private static final String UPDATE_USER = "UpdateUser";
    private static final String UPDATE_USER_CONTROLLER = "UpdateUserController";
    private static final String DELETE_USER = "DeleteUser";
    private static final String DELETE_USER_CONTROLLER = "DeleteUserController";
    private static final String VIEW_SHOPPING = "ViewShopping";
    private static final String VIEW_SHOPPING_CONTROLLER = "ViewShoppingController";    
    private static final String ADD_TO_CART = "AddToCart";
    private static final String ADD_TO_CART_CONTROLLER = "AddToCartController";
    private static final String VIEW_CART = "ViewCart";
    private static final String VIEW_CART_CONTROLLER = "ViewCartController";
    private static final String CHECKOUT = "Checkout";
    private static final String CHECKOUT_CONTROLLER = "CheckoutController";
    private static final String UPDATE_CART_ITEM = "UpdateCartItem";
    private static final String UPDATE_CART_ITEM_CONTROLLER = "UpdateCartItemController";
    
    
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
        String url = INDEX_PAGE;
        HttpSession session = request.getSession();
        Message message = null;
        try {
            String action = request.getParameter("action");
            switch (action) {
                case LOGIN:
                    url = LOGIN_CONTROLLER;
                    break;
                case SEARCH_USER:
                    url = SEARCH_USER_CONTROLLER;
                    break;
                case LOGOUT:
                    url = LOGOUT_CONTROLLER;
                    break;
                case CREATE_USER:
                    url = CREATE_USER_CONTROLLER;
                    break;
                case UPDATE_USER:
                    url = UPDATE_USER_CONTROLLER;
                    break;
                case DELETE_USER:
                    url = DELETE_USER_CONTROLLER;
                    break;
                case VIEW_SHOPPING:
                    url = VIEW_SHOPPING_CONTROLLER;
                    break;
                case ADD_TO_CART:
                    url = ADD_TO_CART_CONTROLLER;
                    break;
                case VIEW_CART:
                    url = VIEW_CART_CONTROLLER;
                    break;
                case CHECKOUT:
                    url = CHECKOUT_CONTROLLER;
                    break;
                case UPDATE_CART_ITEM:
                    url = UPDATE_CART_ITEM_CONTROLLER;
                    break;
                default: 
                    url = INDEX_PAGE;
                    request.setAttribute(ANNOUNCE, Message.error()
                                        .withText("Function isn't supported yet.").build());
                    
            }
        } catch (Exception e) {
            log("Error at Main Controller: " + e.getMessage());
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
