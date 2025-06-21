<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Checkout</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            /* Global Styling */
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
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
            }
            /* Announcement Alert Styling */
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
            /* Table Styling */
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
            .total {
                text-align: right;
                margin-right: 10%;
                font-size: 1.5em;
                font-weight: bold;
                color: #333;
            }
            .empty {
                text-align: center;
                margin: 20px;
                font-size: 1.2em;
                color: #666;
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
                border: none;
                cursor: pointer;
            }
            .btn-secondary {
                background-color: #f8f9fa;
                border: 1px solid #ddd;
                color: #333;
            }
            .btn:hover { opacity: 0.9; }
            
            /* Checkout Form Styling */
            .form-group {
                margin: 15px auto;
                width: 80%;
                max-width: 500px;
            }
            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            .form-group input {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 16px;
            }
        </style>
    </head>
    <body>
        <header>
            <h1>Checkout</h1>
        </header>
        
        <div class="container">
            <!-- Announcement Section -->
            <c:if test="${not empty sessionScope.MESSAGE}">
                <div class="alert alert-${sessionScope.MESSAGE.type.cssClass}">
                    <i class="fas ${sessionScope.MESSAGE.type.icon}"></i>
                    ${sessionScope.MESSAGE.rawText}
                </div>
                <c:remove var="MESSAGE" scope="session" />
            </c:if>
            
            <!-- Order Summary -->
            <c:set var="cart" value="${sessionScope.CART}" />
            <c:if test="${empty cart or empty cart.orderDetails}">
                <div class="empty">Your cart is empty.</div>
                <div class="buttons">
                    <a href="ProductController" class="btn">Continue Shopping</a>
                </div>
            </c:if>
            <c:if test="${not empty cart and not empty cart.orderDetails}">
                <h2>Order Summary</h2>
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
                                <td>${item.quantity}</td>
                                <td>$<c:out value="${item.price}" /></td>
                                <td>$<c:out value="${item.price * item.quantity}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="total">
                    Total: $<c:out value="${cart.total}" />
                </div>
                
                <!-- Retrieve login user and address from the session -->
                <c:set var="user" value="${sessionScope.LOGIN_USER}" />
                <c:set var="address" value="${sessionScope.ADDRESS}" />
                
                <!-- Checkout Form with Shipping Information pre-populated from AddressDTO -->
                <h2>Shipping Information</h2>
                <form action="CheckoutController" method="POST">
                    <input type="hidden" name="action" value="PlaceOrder" />
                    
                    <div class="form-group">
                        <label for="fullName">Full Name:</label>
                        <input type="text" id="fullName" name="fullName" 
                               value="${not empty user ? user.name : ''}" required/>
                    </div>
                    
                    <div class="form-group">
                        <label for="street">Street Address:</label>
                        <input type="text" id="street" name="street" 
                               value="${not empty address ? address.street : ''}" required/>
                    </div>
                    
                    <div class="form-group">
                        <label for="city">City:</label>
                        <input type="text" id="city" name="city" 
                               value="${not empty address ? address.city : ''}" required/>
                    </div>
                    
                    <div class="form-group">
                        <label for="state">State:</label>
                        <input type="text" id="state" name="state" 
                               value="${not empty address ? address.state : ''}" required/>
                    </div>
                    
                    <div class="form-group">
                        <label for="postalCode">Postal Code:</label>
                        <input type="text" id="postalCode" name="postalCode" 
                               value="${not empty address ? address.postalCode : ''}" required/>
                    </div>
                    
                    <div class="form-group">
                        <label for="country">Country:</label>
                        <input type="text" id="country" name="country" 
                               value="${not empty address ? address.country : ''}" required/>
                    </div>
                    
                    <!-- Optional Email Field -->
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email"/>
                    </div>
                    
                    <div class="buttons">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-check"></i> Place Order
                        </button>
                        <a href="viewCart.jsp" class="btn btn-secondary">Back to Cart</a>
                    </div>
                </form>
            </c:if>
        </div>
    </body>
</html>
