<%-- 
    Document   : shopping
    Created on : Mar 18, 2025, 12:48:26 AM
    Author     : tiozo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product Catalog</title>
    <link rel="stylesheet" 
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #4CAF50;
            --secondary-color: #2196F3;
            --background-color: #f5f5f5;
            --text-color: #333;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--background-color);
            margin: 0;
            padding: 0;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        header {
            background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            color: white;
            padding: 20px;
            text-align: center;
            margin-bottom: 30px;
        }
        
        h1 {
            margin: 0;
        }
        
        .search-bar {
            display: flex;
            margin-bottom: 20px;
            justify-content: center;
            align-items: center;
        }
        
        .search-bar input {
            padding: 10px;
            width: 60%;
            border: 1px solid #ddd;
            border-radius: 4px 0 0 4px;
            font-size: 16px;
        }
        
        .search-bar button {
            padding: 10px 15px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            border-radius: 0 4px 4px 0;
            cursor: pointer;
            font-size: 16px;
        }
        
        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }
        
        .product-card {
            background-color: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        
        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }
        
        .product-image {
            height: 200px;
            background-color: #f0f0f0;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .product-image img {
            max-width: 100%;
            max-height: 100%;
            object-fit: cover;
        }
        
        .product-info {
            padding: 15px;
        }
        
        .product-name {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 10px;
        }
        
        .product-description {
            color: #666;
            font-size: 14px;
            margin-bottom: 15px;
            height: 60px;
            overflow: hidden;
        }
        
        .product-price {
            font-size: 22px;
            font-weight: 700;
            color: var(--primary-color);
            margin-bottom: 15px;
        }
        
        .product-stock {
            font-size: 14px;
            color: #666;
            margin-bottom: 15px;
        }
        
        .product-actions {
            display: flex;
            justify-content: space-between;
        }
        
        .btn {
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
            transition: background-color 0.3s ease;
        }
        
        .btn-primary {
            background-color: var(--primary-color);
            color: white;
            flex-grow: 1;
            margin-right: 10px;
        }
        
        .btn-secondary {
            background-color: #f8f9fa;
            border: 1px solid #ddd;
            color: #333;
        }
        
        .btn:hover {
            opacity: 0.9;
        }
        
        .no-products {
            text-align: center;
            grid-column: 1 / -1;
            padding: 50px;
            color: #666;
            font-size: 18px;
        }
        
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 30px;
        }
        
        .pagination a {
            color: var(--text-color);
            float: left;
            padding: 8px 16px;
            text-decoration: none;
            border: 1px solid #ddd;
            margin: 0 4px;
            transition: background-color 0.3s;
        }
        
        .pagination a.active {
            background-color: var(--primary-color);
            color: white;
            border: 1px solid var(--primary-color);
        }
        
        .pagination a:hover:not(.active) {
            background-color: #ddd;
        }
        
        @media (max-width: 768px) {
            .product-grid {
                grid-template-columns: repeat(2, 1fr);
            }
            
            .search-bar input {
                width: 70%;
            }
        }
        
        @media (max-width: 480px) {
            .product-grid {
                grid-template-columns: 1fr;
            }
            
            .search-bar input {
                width: 80%;
            }
        }
        
        /* Announcement alert styling */
        .alert {
            padding: 15px;
            margin: 20px auto;
            width: 80%;
            text-align: center;
            border-radius: 5px;
            font-size: 1.1em;
        }
        .alert-success { background-color: #d4edda; color: #155724; }
        .alert-error { background-color: #f8d7da; color: #721c24; }
        .alert-warning { background-color: #fff3cd; color: #856404; }
        .alert-info { background-color: #d1ecf1; color: #0c5460; }
    </style>
</head>
<body>
    <header>
        <h1><i class="fas fa-store"></i> Product Catalog</h1>
    </header>
    
    <div class="container">
        <!-- Announcement Section: Display MESSAGE attribute if present -->
        <c:if test="${not empty MESSAGE}">
            <div class="alert alert-${MESSAGE.type.cssClass}">
                <i class="fas ${MESSAGE.type.icon}"></i>
                ${MESSAGE.rawText}
            </div>
            <c:remove var="MESSAGE"/>
        </c:if>
        
        <div class="search-bar">
            <!-- Note: Search bar implementation can be updated as needed -->
            <!-- View Cart Button -->
            <form action="MainController" method="GET" style="display: flex; align-items: center;">
                <button type="submit" name="action" value="ViewCart" class="btn btn-secondary" 
                        style="margin-left: 10px;">
                    <i class="fas fa-shopping-cart"></i> View Cart
                </button>
            </form>
        </div>
        
        <div class="product-grid">
            <c:if test="${empty PRODUCTS}">
                <div class="no-products">
                    <i class="fas fa-box-open fa-3x"></i>
                    <p>No products found</p>
                </div>
            </c:if>
            
            <c:forEach items="${PRODUCTS}" var="product">
                <div class="product-card">
                    <div class="product-image">
                        <img src="images/product-placeholder.png" alt="${product.name}">
                    </div>
                    <div class="product-info">
                        <div class="product-name">${product.name}</div>
                        <div class="product-description">${product.description}</div>
                        <div class="product-price">$${product.price}</div>
                        <div class="product-stock">
                            <c:choose>
                                <c:when test="${product.quantity >= -1}">
                                    <span class="in-stock">
                                        <i class="fas fa-check-circle"></i> In Stock (${product.quantity})
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="out-of-stock">
                                        <i class="fas fa-times-circle"></i> Out of Stock
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="product-actions">
                            <form action="MainController" method="POST">
                                <input type="hidden" name="productId" value="${product.productID}">
                                <button type="submit" name="action" value="AddToCart" class="btn btn-primary">
                                    <i class="fas fa-shopping-cart"></i> Add to Cart
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
