<%-- 
    Document   : index
    Created on : Mar 18, 2025, 12:59:09 AM
    Author     : tiozo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Index Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            /* Base styles */
            body {
                font-family: 'Arial', sans-serif;
                margin: 0;
                padding: 0;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            }

            .container {
                text-align: center;
                background: white;
                padding: 2rem;
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.1);
                width: 90%;
                max-width: 400px;
            }

            h1 {
                color: #2c3e50;
                margin-bottom: 2rem;
                font-size: 1.8rem;
            }

            .function-select {
                display: flex;
                flex-direction: column;
                gap: 1rem;
            }

            input[type="submit"] {
                padding: 1rem 2rem;
                border: none;
                border-radius: 8px;
                background: #3498db;
                color: white;
                font-size: 1rem;
                cursor: pointer;
                transition: all 0.3s ease;
                text-transform: uppercase;
                letter-spacing: 1px;
                font-weight: bold;
            }

            input[type="submit"]:hover {
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(0,0,0,0.2);
            }

            input[name="action"][value="Login"] {
                background: #2ecc71;
            }

            input[name="action"][value="ViewShopping"] {
                background: #e67e22;
            }

            /* Add some hover effects */
            input[name="action"][value="Login"]:hover {
                background: #27ae60;
            }

            input[name="action"][value="ViewShopping"]:hover {
                background: #d35400;
            }

            /* Responsive design */
            @media (max-width: 480px) {
                .container {
                    padding: 1.5rem;
                }
                
                input[type="submit"] {
                    padding: 0.8rem 1.5rem;
                    font-size: 0.9rem;
                }
            }

            /* Add some decorative elements */
            body::before {
                content: '';
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                z-index: -1;
                opacity: 0.1;
                background-image: radial-gradient(circle, #7f8c8d 1px, transparent 1px);
                background-size: 30px 30px;
            }

            .page-title {
                font-size: 2rem;
                color: #2c3e50;
                margin-bottom: 2rem;
                text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
            }

            /* Add some animation */
            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(20px); }
                to { opacity: 1; transform: translateY(0); }
            }

            .container {
                animation: fadeIn 0.6s ease-out;
            }
            
            /* Add message styles */
            .alert {
                padding: 15px;
                margin: 20px 0;
                border-radius: 5px;
                display: flex;
                align-items: center;
                gap: 12px;
                border-left: 5px solid;
                width: 100%;
                max-width: 400px;
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
        </style>
    </head>
    <body>
        <div class="container">
            <div class="page-title">Welcome to SanSang Store</div>
            <div class="function-select">
                <form action="MainController" method="POST">
                    <input type="submit" name="action" value="Login"/>
                    <input type="submit" name="action" value="ViewShopping"/>
                    <input type="submit" name="action" value="CreateUser"/>
                </form>
            </div>
        </div>
        <!-- Message Display -->
        <c:if test="${not empty MESSAGE}">
            <div class="alert ${MESSAGE.type.cssClass}">
                <i class="fa ${MESSAGE.type.icon}"></i>
                <div>
                    <c:if test="${not empty MESSAGE.title}">
                        <h4>${MESSAGE.title}</h4>
                    </c:if>
                    ${MESSAGE.rawText}
                </div>
            </div>
        </c:if>
    </body>
</html>