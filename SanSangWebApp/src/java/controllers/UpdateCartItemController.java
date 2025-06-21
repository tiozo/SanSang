/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Order.OrderDTO;
import models.Order.OrderDetailDTO;
import utils.Message;

/**
 *
 * @author tiozo
 */
@WebServlet(name = "UpdateCartItemController", urlPatterns = {"/UpdateCartItemController"})
public class UpdateCartItemController extends HttpServlet {

    private static final String DEFAULT = "ViewCartController";
    
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
        String url = DEFAULT;
        try {
            String dummyProdId = request.getParameter("productId");
            int productID = Integer.parseInt(dummyProdId);
            String quantDummy = request.getParameter("quantity");
            int quantity = Integer.parseInt(quantDummy);
            
            HttpSession session = request.getSession();
            OrderDTO cart = (OrderDTO) session.getAttribute("CART");
            
            if (cart == null) {
                request.setAttribute(ANNOUNCE, Message.error()
                        .withText("No carts was found.").build());
                throw new NullPointerException("Cart hasn't initialized yet.");
            }
            
            List<OrderDetailDTO> items = cart.getOrderDetails();
            
            for (int i = 0; i < items.size(); i++) {
                OrderDetailDTO item = items.get(i);
                if (item.getProductID() == productID) {
                    if (quantity > 0) {
                        OrderDetailDTO updatedItem = new OrderDetailDTO.Builder()
                                .withOrderID(item.getOrderID())
                                .withProductID(item.getProductID())
                                .withQuantity(quantity)
                                .withPrice(item.getPrice())
                                .build();
                        items.set(i, updatedItem);
                    } else if (quantity == 0) {
                        items.remove(i);
                    }
                    break;
                }
            }

            
            BigDecimal total = items.stream()
                    .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            OrderDTO updatedCart = new OrderDTO.Builder()
                    .withUserID(cart.getUserID())
                    .withDate(cart.getDate())
                    .withOrderDetails(items)
                    .withTotal(total)
                    .build();
            
            session.setAttribute("CART", updatedCart);
                request.setAttribute(ANNOUNCE, Message.success().withText("Cart updated.").build());
            url = DEFAULT;
            
        } catch (Exception e) {
            log("Error at UpdateCartItemController: " + e.toString());
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
