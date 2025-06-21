<%-- 
    Document   : viewCart
    Created on : Mar 18, 2025, 7:54:09 PM
    Author     : tiozo
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>View Cart</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 20px;
            }
            h1 {
                text-align: center;
                color: #333;
            }
            table {
                width: 80%;
                margin: 20px auto;
                border-collapse: collapse;
            }
            th, td {
                padding: 12px;
                border: 1px solid #ddd;
                text-align: center;
            }
            th {
                background-color: #4CAF50;
                color: white;
            }
            .empty {
                text-align: center;
                margin: 20px;
                font-size: 1.2em;
                color: #666;
            }
            .total {
                text-align: right;
                margin-right: 10%;
                font-size: 1.5em;
                font-weight: bold;
                color: #333;
            }
            .buttons {
                text-align: center;
                margin: 20px;
            }
            .btn {
                display: inline-block;
                padding: 10px 20px;
                background-color: #4CAF50;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                margin: 5px;
            }
            /* Style for the update form button (different color) */
            .btn-update {
                background-color: #2196F3;
                color: white;
                border: none;
                border-radius: 5px;
                padding: 5px 10px;
                cursor: pointer;
            }
            /* Input Number Style */
            .update-qty {
                width: 60px;
                padding: 3px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <h1>Your Shopping Cart</h1>
        
        <!-- Retrieve the cart from session -->
        <c:set var="cart" value="${sessionScope.CART}" />
        
        <!-- If cart is empty or has no order details -->
        <c:if test="${empty cart or empty cart.orderDetails}">
            <div class="empty">Your cart is empty.</div>
            <div class="buttons">
                <!-- Continue shopping will go to MainController with action ViewShopping -->
                <a href="MainController?action=ViewShopping" class="btn">Continue Shopping</a>
            </div>
        </c:if>
        
        <!-- If cart exists and has items -->
        <c:if test="${not empty cart and not empty cart.orderDetails}">
            <table>
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cart.orderDetails}">
                        <tr>
                            <td>${item.productID}</td>
                            <td>
                                <!-- Update form with an input that allows quantity 0 -->
                                <form action="MainController" method="POST">
                                    <input type="hidden" name="productId" value="${item.productID}" />
                                    <input type="number" name="quantity" value="${item.quantity}" 
                                           min="0" class="update-qty" />
                                    <button type="submit" name="action" value="UpdateCartItem" class="btn-update">
                                        Update
                                    </button>
                                </form>
                            </td>
                            <td>$<c:out value="${item.price}" /></td>
                            <td>$<c:out value="${item.price * item.quantity}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="total">
                Total: $<c:out value="${cart.total}" />
            </div>
            <div class="buttons">
                <a href="MainController?action=ViewShopping" class="btn">Continue Shopping</a>
                <a href="MainController?action=Checkout" class="btn">Proceed to Checkout</a>
            </div>
        </c:if>
    </body>
</html>
