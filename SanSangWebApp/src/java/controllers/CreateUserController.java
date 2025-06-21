/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import comparators.AddressDTOComparator;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Address.AddressDAO;
import models.Address.AddressDTO;
import models.User.UserDAO;
import models.User.UserDTO;
import utils.Message;

/**
 *
 * @author tiozo
 */
@WebServlet(name = "CreateUserController", urlPatterns = {"/CreateUserController"})
public class CreateUserController extends HttpServlet {

    private static final String CREATE_PAGE = "createUser.jsp";
    
    private static final String FROM_ADMIN = "SearchUserController";
            
    private static final String SUCCESS = "index.jsp";
    
    private static final String ANNOUNCE = "MESSAGE";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = CREATE_PAGE;
        UserDTO user = null;
        AddressDTO address = null;
        Message message = null;
        
        String msg = "";
        String exMsg = "";
        String suffixMsg = "Please press cancel to go back to index page.";
        
        try {
            HttpSession session = request.getSession();
            UserDTO userLogin = (UserDTO) session.getAttribute("LOGIN_USER");
            
            int initRole = 0;
            if (userLogin != null && userLogin.getRoleId() == 1) {
                url = FROM_ADMIN; suffixMsg = "";
                String dummy = request.getParameter("addressID");
                initRole = Integer.parseInt(dummy);
            }
            
            UserDAO userDao = new UserDAO();
            AddressDAO dao = new AddressDAO();
            List<AddressDTO> listAddress = dao.getListAddress();
            listAddress.sort(AddressDTOComparator.byIdAscending());
            
            if (listAddress == null) 
                throw new NullPointerException("list is emptied.");
            
            // --------- Handle User ----------
            String userID = request.getParameter("userID");
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String rePassword = request.getParameter("confirmPassword");
            String phone = request.getParameter("phone");
            String role = request.getParameter("roleID");
            int roleID = 0;
            if (role != null)
                roleID = Integer.parseInt(role);
            
            if (userDao.checkDuplicate(userID)) {
                exMsg += "UserID existed in the database. ";
                msg += exMsg + '\n';
            }
            
            if (!password.equals(rePassword)) {
                exMsg += "Password and Confirm Password doesn't match. ";
                msg += "Password and Confirm Password doesn't match.\n";
            }
            
            if (!exMsg.isEmpty()) {
                throw new IllegalArgumentException(exMsg);
            }
            
            if (userLogin == null) {
                // --------- Hanle Address --------
                String street = request.getParameter("street");
                String city = request.getParameter("city");
                String state = request.getParameter("state");
                String postal = request.getParameter("postalCode");
                String country = request.getParameter("country");

                // --- setup hoi lo lo ma xong truoc da lat cai thien sau
                    if (street.isEmpty()) street = listAddress.get(0).getStreet();
                    if (city.isEmpty()) city = listAddress.get(0).getCity();
                    if (state.isEmpty()) state = listAddress.get(0).getState();
                    if (postal.isEmpty()) postal = listAddress.get(0).getPostalCode();
                    if (country.isEmpty()) country = listAddress.get(0).getCountry();

                address = AddressDTO.builder(street, city, country)
                                    .withPostalCode(postal)
                                    .withState(state).build();    

                boolean check = false;    
                for (AddressDTO item: listAddress) {
                    address = new AddressDTO.Builder(address)
                                            .withAddressID(item.getAddressID())
                                            .build();
                    if (address.equals(item)) {
                        check = true;
                        break;
                    }
                }    

                if (!check) {
                    if (listAddress != null && !listAddress.isEmpty())
                        address = new AddressDTO.Builder(address)
                                            .withAddressID(listAddress.size())
                                            .build();
                    if (!dao.create(address)) {
                        msg += "Address adding failed.";
                    }
                }
                
                initRole = address.getAddressID();
            }
            // --------- init user ------------
            user = new UserDTO.Builder(userID, name, 
                              roleID, initRole, password)
                              .withPhone(phone).build();
            if (!userDao.create(user)) {
                msg += "User Creation failed.";
            }
            
            request.setAttribute(ANNOUNCE, Message.success().withText("User created successfully. " + suffixMsg).build());
            
        } catch (Exception e) {
            if (null != e.getMessage() && !e.getMessage().isEmpty())
                request.setAttribute(ANNOUNCE, Message.error().withText(msg).build());
            log("Error at CreateUserController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
