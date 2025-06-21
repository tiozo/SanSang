package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Order.OrderDAO;
import models.Order.OrderDTO;
import models.Order.OrderDetailDTO;
import models.Product.ProductDAO;
import models.Product.ProductDTO;
import models.User.UserDTO;
import utils.Message;

@WebServlet(name = "AddToCartController", urlPatterns = {"/AddToCartController"})
public class AddToCartController extends HttpServlet {

    private static final String ERROR = "ViewShoppingController";
    private static final String SUCCESS = "ViewShoppingController";
    private static final String ANNOUNCE = "MESSAGE";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
            
            boolean flag = false;
            
            if (user == null) {
                request.setAttribute(ANNOUNCE, Message.error().withText("Please login to add items to cart").build());
                url = "LoginController";
                flag = true;
            }
            
            if (!flag) {
                // Get product details
                String productID = request.getParameter("productId");
                int quantity = 0; // Default quantity
                try {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                } catch (NumberFormatException e) {
                    // Use default quantity
                    quantity = 1;
                }

                ProductDAO productDAO = new ProductDAO();
                ProductDTO product = productDAO.getProductById(Integer.parseInt(productID));

                if (product == null || product.getQuantity() < quantity) {
                    request.setAttribute(ANNOUNCE, Message.error().withText("Product not available").build());
                    return;
                }

                OrderDAO orderDao = new OrderDAO();
                int orderID = orderDao.getAllOrderDTO().size() + 1;
                
                OrderDTO cart = (OrderDTO) session.getAttribute("CART");
                if (cart == null) {
                    cart = new OrderDTO.Builder()
                        .withUserID(user.getUserId())
                        .withDate(LocalDateTime.now())
                        .withTotal(BigDecimal.ZERO)
                        .withOrderDetails(new ArrayList<>())
                        .build();
                }

                boolean itemExists = false;
                List<OrderDetailDTO> items = cart.getOrderDetails();
                for (int i = 0; i < items.size(); i++) {
                    OrderDetailDTO item = items.get(i);
                    if (item.getProductID() == product.getProductID()) {
                        OrderDetailDTO updatedItem = new OrderDetailDTO.Builder()
                            .withOrderID(item.getOrderID())
                            .withProductID(item.getProductID())
                            .withQuantity(item.getQuantity() + quantity)
                            .withPrice(item.getPrice())
                            .build();
                        items.set(i, updatedItem);
                        itemExists = true;
                        break;
                    }
                }

                if (!itemExists) {
                    items.add(new OrderDetailDTO.Builder()
                        .withProductID(product.getProductID())
                        .withQuantity(quantity)
                        .withPrice(BigDecimal.valueOf(product.getPrice()))
                        .build());
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
                request.setAttribute(ANNOUNCE, Message.success().withText("Product added to cart").build());
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at AddToCartController: " + e.toString());
            request.setAttribute(ANNOUNCE, Message.error().withText("Error adding to cart: " + e.getMessage()).build());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

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
        return "Cart management controller";
    }
}
