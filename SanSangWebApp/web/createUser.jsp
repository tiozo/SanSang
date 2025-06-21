<%-- 
    Document   : createUser
    Created on : Mar 18, 2025, 10:42:13 AM
    Author     : tiozo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create New Customer</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
                padding: 3rem;
            }

            .container {
                max-width: 900px; /* Tăng kích thước container */
                margin: 0 auto;
                background: white;
                border-radius: 12px;
                box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                overflow: hidden;
            }

            .form-header {
                background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
                color: white;
                padding: 1.5rem;
                margin-bottom: 2rem;
            }

            .form-header h2 {
                margin: 0;
                font-weight: 600;
                display: flex;
                align-items: center;
                gap: 1rem;
            }

            .form-content {
                padding: 0 2rem 2rem;
            }

            .form-grid {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                gap: 0.5rem; /* Giảm gap */
                margin-bottom: 2rem;
            }

            .form-group {
                position: relative;
                margin-bottom: 1rem;
            }

            .form-group label {
                display: block;
                margin-bottom: 0.5rem;
                color: var(--text-color);
                font-weight: 500;
            }

            .input-icon {
                position: absolute;
                left: 10px;
                top: 38px;
                color: #666;
            }

            .input-group {
                margin-bottom: 2rem;
            }

            input:not([type="submit"]) {
                width: 100%;
                padding: 0.6rem 0.6rem 0.6rem 30px; /* Giảm padding */
                border: 2px solid #ddd;
                border-radius: 8px;
                font-size: 0.9rem; /* Giảm kích thước font */
                transition: all 0.3s ease;
                box-sizing: border-box; /* Thêm box-sizing */
            }

            input:focus {
                border-color: var(--primary-color);
                outline: none;
                box-shadow: 0 0 5px rgba(76, 175, 80, 0.3);
            }

            .address-section {
                background: #f8f9fa;
                padding: 0.5rem;
                border-radius: 8px;
                margin-top: 1rem;
                border: 1px solid #dee2e6;
            }

            .address-section h3 {
                margin-top: 0;
                color: var(--secondary-color);
                display: flex;
                align-items: center;
                gap: 0.5rem;
            }

            .button-group {
                display: flex;
                gap: 1rem;
                margin-top: 2rem;
                justify-content: flex-end;
            }

            .btn {
                padding: 0.8rem 1.5rem;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                font-weight: 600;
                transition: all 0.3s ease;
                display: flex;
                align-items: center;
                gap: 0.5rem;
            }

            .btn-primary {
                background-color: var(--primary-color);
                color: white;
            }

            .btn-secondary {
                background-color: #666;
                color: white;
            }

            .btn:hover {
                opacity: 0.9;
                transform: translateY(-1px);
            }

            .alert {
                padding: 1rem;
                margin: 1rem 0;
                border-radius: 8px;
                display: flex;
                align-items: center;
                gap: 1rem;
                border-left: 4px solid;
            }

            .alert i {
                font-size: 1.2rem;
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

            @media (max-width: 768px) {
                .form-grid {
                    grid-template-columns: 1fr;
                }

                .container {
                    margin: 1rem;
                }
            }

            .password-match-error {
                color: #dc3545;
                font-size: 0.875em;
                margin-top: 0.25rem;
                display: none;
            }

            .password-match-error.show {
                display: block;
            }

            .form-grid {
                grid-template-columns: repeat(2, 1fr);
            }

            .section-title {
                margin-bottom: 1.5rem;
                padding-bottom: 0.5rem;
                border-bottom: 2px solid var(--secondary-color);
            }

            .form-note {
                margin-top: 2rem;
                padding: 1rem;
                background: #e9ecef;
                border-radius: 6px;
                font-size: 0.9em;
            }
        </style>
</head>
<body>
    <div class="container">
        <div class="form-header">
            <h2>
                <i class="fas fa-user-plus"></i>
                Create New Customer Account
            </h2>
        </div>

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

        <div class="form-content">
            <form action="CreateUserController" method="POST">
                <input type="hidden" name="roleID" value="2">

                <div class="form-grid">
                    <!-- User Information -->
                    <div class="form-group">
                        <label for="userID"><i class="fas fa-user-circle"></i> User ID</label>
                        <i class="fas fa-id-card input-icon"></i>
                        <input type="text" id="userID" name="userID" required 
                               placeholder="Enter unique user ID">
                    </div>

                    <div class="form-group">
                        <label for="name"><i class="fas fa-signature"></i> Full Name</label>
                        <i class="fas fa-user input-icon"></i>
                        <input type="text" id="name" name="name" required 
                               placeholder="John Doe">
                    </div>
                    
                    <!-- Modified Password Section -->
                    <div class="form-group">
                        <label for="password"><i class="fas fa-lock"></i> Password</label>
                        <i class="fas fa-key input-icon"></i>
                        <input type="password" id="password" name="password" required 
                               placeholder="••••••••">
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword"><i class="fas fa-lock"></i> Confirm Password</label>
                        <i class="fas fa-redo input-icon"></i>
                        <input type="password" id="confirmPassword" name="confirmPassword" required 
                               placeholder="••••••••">
                        <div id="passwordError" class="password-match-error">
                            <i class="fas fa-exclamation-circle"></i> Passwords do not match
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phone"><i class="fas fa-phone"></i> Phone</label>
                        <i class="fas fa-mobile-alt input-icon"></i>
                        <input type="text" id="phone" name="phone" 
                               placeholder="+1 234 567 890">
                    </div>
                </div>

                <!-- Address Section -->
                <div class="address-section">
                    <h3>
                        <i class="fas fa-map-marker-alt"></i>
                        Address Information
                    </h3>
                    
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="street">Street</label>
                            <i class="fas fa-road input-icon"></i>
                            <input type="text" id="street" name="street"  
                                   placeholder="123 Main St">
                        </div>

                        <div class="form-group">
                            <label for="city">City</label>
                            <i class="fas fa-city input-icon"></i>
                            <input type="text" id="city" name="city"  
                                   placeholder="New York">
                        </div>

                        <div class="form-group">
                            <label for="state">State/Province</label>
                            <i class="fas fa-flag input-icon"></i>
                            <input type="text" id="state" name="state" 
                                   placeholder="NY">
                        </div>

                        <div class="form-group">
                            <label for="postalCode">Postal Code</label>
                            <i class="fas fa-mail-bulk input-icon"></i>
                            <input type="text" id="postalCode" name="postalCode"  
                                   placeholder="10001">
                        </div>

                        <div class="form-group">
                            <label for="country">Country</label>
                            <i class="fas fa-globe input-icon"></i>
                            <input type="text" id="country" name="country"  
                                   placeholder="United States">
                        </div>
                    </div>
                </div>

                <div class="form-note">
                    <i class="fas fa-info-circle"></i>
                    Để trống tất cả trường địa chỉ để sử dụng địa chỉ mặc định
                </div>
                
                <div class="button-group">
                    <button type="button" onclick="window.history.back()" 
                            class="btn btn-secondary">
                        <i class="fas fa-times"></i>
                        Cancel
                    </button>
                    <button type="submit" name="action" value="CreateUser" 
                            class="btn btn-primary">
                        <i class="fas fa-user-plus"></i>
                        Create User
                    </button>
                </div>
                
                
            </form>
        </div>
    </div>
</body>
</html>