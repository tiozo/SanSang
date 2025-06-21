<%-- 
    Document   : orderConfirmation
    Created on : Mar 18, 2025, 10:06:49 PM
    Author     : tiozo
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Order Confirmation</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f5f5f5;
                margin: 0;
                padding: 20px;
            }
            header {
                background: linear-gradient(135deg, #4CAF50, #2196F3);
                color: white;
                padding: 20px;
                text-align: center;
                margin-bottom: 30px;
            }
            .container {
                max-width: 800px;
                margin: 0 auto;
                background: #fff;
                padding: 20px;
                border-radius: 8px;
                text-align: center;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            h1, h2 {
                text-align: center;
            }
            .btn {
                display: inline-block;
                padding: 10px 20px;
                background-color: #4CAF50;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                margin-top: 20px;
                transition: background-color 0.3s ease;
            }
            .btn:hover {
                background-color: #45a049;
            }
            /* Announcement styling */
            .alert {
                padding: 15px;
                margin: 20px auto;
                width: 80%;
                text-align: center;
                border-radius: 5px;
                font-size: 1.1em;
            }
            .alert-success { background-color: #d4edda; color: #155724; }
            .alert-error   { background-color: #f8d7da; color: #721c24; }
            .alert-warning { background-color: #fff3cd; color: #856404; }
            .alert-info    { background-color: #d1ecf1; color: #0c5460; }
        </style>
    </head>
    <body>
        <header>
            <h1>Order Confirmation</h1>
        </header>
        <div class="container">
            <!-- Announcement Section: Display MESSAGE attribute if present -->
            <c:if test="${not empty sessionScope.MESSAGE}">
                <div class="alert alert-${sessionScope.MESSAGE.type.cssClass}">
                    <i class="fas ${sessionScope.MESSAGE.type.icon}"></i>
                    ${sessionScope.MESSAGE.rawText}
                </div>
                <c:remove var="MESSAGE" scope="session" />
            </c:if>
            <h2>Thank you for your order!</h2>
            <p>Your order has been successfully placed and is being processed.</p>
            <p>An email confirmation has been sent with your order details.</p>
            <a href="MainController?action=ViewShopping" class="btn">Continue Shopping</a>
        </div>
    </body>
</html>

