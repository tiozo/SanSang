<%-- Document : admin
    Created on : Mar 18, 2025, 12:09:00 AM
    Author : tiozo
    --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard</title>
        <style>
            /* Inherit CSS from login page */
            body {
            font-family: Arial, sans-serif;
            max-width: 1200px;
            margin: 50px auto;
            padding: 20px;
            }
            .alert {
            padding: 15px;
            margin: 20px 0;
            border-radius: 5px;
            display: flex;
            align-items: center;
            gap: 12px;
            border-left: 5px solid;
            }
            .alert i { font-size: 20px; }
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
            table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            }
            th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
            vertical-align: middle;
            }
            th { background-color: #f5f5f5; }
            .action-buttons form {
            display: inline-block;
            margin-right: 5px;
            }
            .search-form {
            margin: 20px 0;
            display: flex;
            gap: 10px;
            }
            .welcome-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            }
            /* Additional styles */
            .no-data {
            text-align: center;
            padding: 20px;
            color: #666;
            font-style: italic;
            }
            #addUserModal {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 20px rgba(0,0,0,0.2);
            z-index: 1000;
            }
            .modal-backdrop {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            z-index: 999;
            }
            /* --- New CSS for prettifying inline editing boxes & buttons --- */
            table input[type="text"],
            table select {
            width: 90%;
            padding: 8px;
            margin: 4px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
            }
            table input[type="text"]:focus,
            table select:focus {
            border-color: #66afe9;
            box-shadow: 0 0 8px rgba(102,175,233,0.6);
            outline: none;
            }
            .action-buttons button {
            padding: 8px 16px;
            margin: 2px;
            border: none;
            border-radius: 4px;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            }
            /* Update button style */
            .action-buttons button[name="action"][value="UpdateUser"] {
            background-color: #4CAF50;
            color: white;
            }
            /* Delete button style */
            .action-buttons button[name="action"][value="DeleteUser"] {
            background-color: #ef5350;
            color: white;
            }
            .action-buttons button:hover {
            opacity: 0.9;
            }
        </style>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <script>
            function updateConfirmPassword() {
              const password = document.getElementById('password').value;
              document.getElementById('confirmPassword').value = password;
            }
            // Also update when the form is submitted
            document.querySelector('#addUserModal form').addEventListener('submit', function() {
              updateConfirmPassword();
            });
            
            function openModal() {
              document.getElementById('addUserModal').style.display = 'block';
            }
            
            function closeModal() {
              document.getElementById('addUserModal').style.display = 'none';
            }
            
            window.onclick = function(event) {
              const modal = document.getElementById('addUserModal');
              if (event.target === modal) {
                modal.style.display = "none";
              }
            }
        </script>
    </head>
    <body>
        <!-- Authentication Check -->
        <c:choose>
            <c:when test="${empty sessionScope.LOGIN_USER}">
                <c:set var="MESSAGE" scope="session" 
                    value="${Message.error().withText('Vui lòng đăng nhập!').build()}"/>
                <c:redirect url="login.jsp"/>
            </c:when>
            <c:when test="${sessionScope.LOGIN_USER.roleId ne 1}">
                <c:set var="MESSAGE" scope="session" 
                    value="${Message.error().withText('Không đủ quyền truy cập!').build()}"/>
                <c:redirect url="MainController?action=ViewShopping"/>
            </c:when>
        </c:choose>
        <div class="welcome-header">
            <h1>Welcome, ${LOGIN_USER.name}</h1>
            <form action="MainController" method="POST">
                <input type="submit" name="action" value="Logout"/>
            </form>
        </div>
        <!-- Search and Add User Section -->
        <div class="search-form">
            <form action="MainController" method="GET">
                <input type="text" name="search" placeholder="Search by name or ID">
                <select name="roleFilter">
                    <option value="">All Roles</option>
                    <c:forEach items="${ROLES}" var="role">
                        <option value="${role.roleId}">${role.roleName}</option>
                    </c:forEach>
                </select>
                <input type="submit" name="action" value="SearchUser">
            </form>
            <button onclick="openModal()">Add New User</button>
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
        <!-- User Table with Inline Editing -->
        <table>
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Role</th>
                    <th>Phone</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty USERS}">
                        <!-- Each user row is now a form so that the inputs are submitted -->
                        <c:forEach items="${USERS}" var="user">
                            <form action="MainController" method="POST">
                                <tr>
                                    <!-- The userID field is read-only to avoid accidental editing -->
                                    <td>
                                        <input type="text" name="userID" value="${user.userId}" readonly>
                                    </td>
                                    <!-- Other editable fields -->
                                    <td>
                                        <input type="text" name="name" value="${user.name}">
                                    </td>
                                    <td>
                                        <select name="roleID">
                                            <c:forEach items="${ROLES}" var="role">
                                                <option value="${role.roleId}" 
                                                <c:if test="${role.roleId eq user.roleId}">selected</c:if>
                                                >
                                                ${role.roleName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="text" name="phone" value="${user.phone}">
                                    </td>
                                    <!-- Two action buttons: Update and Delete -->
                                    <td class="action-buttons">
                                        <button type="submit" name="action" value="UpdateUser">Update</button>
                                        <button type="submit" name="action" value="DeleteUser">Delete</button>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="5" class="no-data">Không có dữ liệu người dùng</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        <!-- Add User Modal -->
        <div id="addUserModal" style="display:none;">
            <h2>Add New User</h2>
            <form action="MainController" method="POST">
                User ID: <input type="text" name="userID" required><br>
                Name: <input type="text" name="name" required><br>
                Password: 
                <input type="password" name="password" id="password" required onchange="updateConfirmPassword()"><br>
                <input type="password" id="confirmPassword" name="confirmPassword" hidden>
                Role:  
                <select name="roleID" required>
                    <c:forEach items="${ROLES}" var="role">
                        <option value="${role.roleId}">${role.roleName}</option>
                    </c:forEach>
                </select>
                <br>
                Phone: <input type="text" name="phone"><br>
                Address ID: <input type="number" name="addressID" value="0" readonly=""><br>
                <input type="submit" name="action" value="CreateUser">
                <button type="button" onclick="closeModal()">Cancel</button>
            </form>
        </div>
    </body>
</html>