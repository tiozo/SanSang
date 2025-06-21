<%-- 
    Document   : login
    Created on : Mar 17, 2025, 10:31:58 PM
    Author     : tiozo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        
        <!-- Font Awesome CSS -->
        <link rel="stylesheet" 
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        
        <!-- Custom CSS -->
        <style>
            body {
                font-family: Arial, sans-serif;
                max-width: 500px;
                margin: 50px auto;
                padding: 20px;
            }
            
            h1 {
                color: #333;
                text-align: center;
                margin-bottom: 30px;
            }
            
            form {
                background: #f9f9f9;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            
            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 8px;
                margin: 8px 0 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            
            input[type="submit"],
            input[type="reset"] {
                background: #4CAF50;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                margin-right: 10px;
            }
            
            input[type="submit"]:hover,
            input[type="reset"]:hover {
                background: #45a049;
            }
            
            /* Message styles */
            .alert {
                padding: 15px;
                margin: 20px 0;
                border-radius: 5px;
                display: flex;
                align-items: center;
                gap: 12px;
                border-left: 5px solid;
            }
            
            .alert i {
                font-size: 20px;
            }
            
            .alert.success {
                background: #e8f5e9;
                border-color: #4CAF50;
                color: #2e7d32;
            }
            
            .alert.error {
                background: #ffebee;
                border-color: #ef5350;
                color: #c62828;
            }
            
            .alert.warning {
                background: #fff8e1;
                border-color: #ffc107;
                color: #ff6f00;
            }
            
            .alert.info {
                background: #e3f2fd;
                border-color: #2196F3;
                color: #1565c0;
            }
            
            .alert h4 {
                margin: 0 0 5px 0;
                font-size: 16px;
            }
        </style>
    </head>
    <body>
        <h1>Login Credential</h1>
        <form action="MainController" method="POST">
            UserID: <input type="text" name="userID" value="" required> <br>
            Password: <input type="password" name="password" value="" required> <br>
            <input type="submit" name="action" value="Login"> 
            <input type="reset" value="Reset">
        </form>
        
        <c:if test="${not empty MESSAGE}">
            <div class="alert ${MESSAGE.type.cssClass}">
                <i class="fa ${MESSAGE.type.icon}"></i>
                <div>
                    <c:if test="${not empty MESSAGE.title}">
                        <h4>${MESSAGE.title}</h4>
                    </c:if>
                    
                    <c:choose>
                        <c:when test="${not empty MESSAGE.key}">
                            <fmt:message key="${MESSAGE.key}">
                                <c:forEach items="${MESSAGE.params}" var="param">
                                    <fmt:param value="${param}" />
                                </c:forEach>
                            </fmt:message>
                        </c:when>
                        <c:otherwise>
                            ${MESSAGE.rawText}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:if>
    </body>
</html>