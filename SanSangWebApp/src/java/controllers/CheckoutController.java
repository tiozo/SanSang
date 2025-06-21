/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Order.OrderDTO;
import models.Order.OrderDetailDTO;
import models.Checkout.CheckoutDTO;
import models.Checkout.CheckoutDAO;
import models.Product.ProductDAO;
import utils.EmailUtility;
import utils.Message;

@WebServlet(name = "CheckoutController", urlPatterns = {"/CheckoutController"})
public class CheckoutController extends HttpServlet {

    private static final String DEFAULT = "checkout.jsp";
    private static final String ANNOUNCE = "MESSAGE"; // Constant for announcement messages

    /**
     * Processes requests for both HTTP GET and POST methods.
     * In the "PlaceOrder" action the controller:
     * - Retrieves shipping information from checkout form.
     * - Obtains the current cart (OrderDTO) from session.
     * - Creates a checkout record using CheckoutDTO and CheckoutDAO.
     * - If successful, deducts the purchased quantities from product inventory 
     *   via ProductDAO, sends an email confirmation, clears the cart, 
     *   then forwards to an order confirmation page.
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
        HttpSession session = request.getSession();
        try {
            String action = request.getParameter("action");

            if (action != null && action.equals("PlaceOrder")) {
                // Retrieve checkout form fields (shipping information)
                String fullName = request.getParameter("fullName");
                String street = request.getParameter("street");
                String city = request.getParameter("city");
                String state = request.getParameter("state");
                String postalCode = request.getParameter("postalCode");
                String country = request.getParameter("country");
                String email = request.getParameter("email");

                // Retrieve the cart from the session
                OrderDTO cart = (OrderDTO) session.getAttribute("CART");
                if (cart == null) {
                    session.setAttribute(ANNOUNCE, Message.error().withText("Your cart is empty.").build());
                    url = "checkout.jsp";
                } else {
                    CheckoutDAO checkoutDao = new CheckoutDAO();
                    // Note: The orderId will be set by OrderDAO.create(order) via createCheckoutAndOrder.
                    // Here, we assume that createCheckoutAndOrder will return true only if both ORDERED and CHECKOUT records are created.
                    BigDecimal amount = cart.getTotal();

                    // Set default payment method and status.
                    String paymentMethod = "COD";  // Cash on Delivery
                    String paymentStatus = "Pending";

                    // Create a CheckoutDTO using builder pattern (orderID will be generated via OrderDAO)
                    CheckoutDTO checkout = new CheckoutDTO.Builder()
                                                .withPaymentMethod(paymentMethod)
                                                .withAmount(amount)
                                                .withPaymentStatus(paymentStatus)
                                                .build();

                    // Persist the checkout record and create the order using CheckoutDAO.
                    boolean success = checkoutDao.createCheckoutAndOrder(cart, checkout);

                    if (success) {
                        // Remove the purchased amounts from inventory.
                        ProductDAO productDao = new ProductDAO();
                        for (OrderDetailDTO detail : cart.getOrderDetails()) {
                            // reduceStock should subtract detail.getQuantity() from available stock.
                            boolean stockUpdated = productDao.reduceStock(detail.getProductID(), detail.getQuantity());
                            if (!stockUpdated) {
                                log("Failed to update stock for productID: " + detail.getProductID());
                            }
                        }

                        // Determine email recipient:
                        // If the email field is empty or null, use a default recipient.
                        String recipientEmail = (email == null || email.trim().isEmpty())
                                ? "default@example.com" : email;

                        // Send confirmation email.
                        try {
                            // Replace the SMTP credentials and settings with your actual values.
                            String host = "smtp.gmail.com";
                            String port = "587";
                            String senderEmail = "ghrtisshrp@s.lb.edu.vn";
                            String senderPassword = "jvpw xyvp eaam rkhz"; // Consider using an app-specific password
                            String subject = "Order Confirmation";
                            String messageBody = "Dear " + fullName + ",\n\n"
                                    + "Thank you for your order. Your order has been placed successfully.\n"
                                    + "We will notify you once your order is on the way.\n\n"
                                    + "Best regards,\nYour Store Team";

                            EmailUtility.sendEmail(host, port, senderEmail, senderPassword, recipientEmail, subject, messageBody);
                        } catch (MessagingException me) {
                            log("Error sending confirmation email: " + me.getMessage());
                        }

                        session.setAttribute(ANNOUNCE, Message.success().withText("Order placed successfully.").build());
                        session.removeAttribute("CART"); // Clear the cart after placing the order
                        url = "orderConfirmation.jsp";  // Forward to an order confirmation page
                    } else {
                        session.setAttribute(ANNOUNCE, Message.error().withText("Failed to place order.").build());
                        url = "checkout.jsp";
                    }
                }
            } else {
                // For non-PlaceOrder actions, simply forward to checkout page.
                url = "checkout.jsp";
            }
        } catch (Exception e) {
            log("Error at CheckoutController: " + e.toString());
            session.setAttribute(ANNOUNCE, Message.error().withText("Error placing order: " + e.getMessage()).build());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Checkout management controller";
    }
    // </editor-fold>
}
