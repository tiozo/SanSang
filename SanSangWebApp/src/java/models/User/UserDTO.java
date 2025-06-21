/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.User;

/**
 *
 * @author tiozo
 */
public class UserDTO {
    private final String userId;    // Bắt buộc (NOT NULL)
    private final String name;      // Bắt buộc (NOT NULL)
    private final int roleId;       // Bắt buộc (NOT NULL)
    private final int addressId;    // Bắt buộc (NOT NULL)
    private final String password;  // Bắt buộc (NOT NULL)
    private String phone;           // Tùy chọn (NULL được phép)
    private String role;
    private String orderID;

    // Private constructor chỉ dùng trong Builder
    private UserDTO(Builder builder) {
        this.userId = builder.userId;
        this.name = builder.name;
        this.roleId = builder.roleId;
        this.addressId = builder.addressId;
        this.password = builder.password;
        this.phone = builder.phone;
    }

    // ---------- Builder Pattern ----------
    public static class Builder {
        // Các trường bắt buộc
        private final String userId;
        private final String name;
        private final int roleId;
        private final int addressId;
        private final String password;
        
        // Các trường tùy chọn
        private String phone;

        public Builder(UserDTO user) {
            this.userId = user.userId;
            this.name = user.name;
            this.roleId = user.roleId;
            this.addressId = user.addressId;
            this.password = user.password;
            this.phone = user.phone;
        }
        
        // Constructor cho các trường BẮT BUỘC
        public Builder(String userId, String name, int roleId, int addressId, String password) {
            // Validate các trường bắt buộc
            if (userId == null || userId.isEmpty()) 
                throw new IllegalArgumentException("UserID không được để trống");
            if (name == null || name.isEmpty()) 
                throw new IllegalArgumentException("Tên không được để trống");
            if (password == null || password.isEmpty()) 
                throw new IllegalArgumentException("Mật khẩu không được để trống");
            
            this.userId = userId;
            this.name = name;
            this.roleId = roleId;
            this.addressId = addressId;
            this.password = password;
        }

        // Phương thức `with` cho trường TÙY CHỌN
        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        // Xây dựng đối tượng UserDTO
        public UserDTO build() {
            return new UserDTO(this);
        }
    }
    
    // ---------- Setter ----------
    public void setRole(String role) { this.role = role; }
    public void setOrderID(String orderID) { this.orderID = orderID; }
    
    
    // ---------- Getter ----------
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public int getRoleId() { return roleId; }
    public int getAddressId() { return addressId; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getRole() {return role; }
    public String getOrderID() { return orderID; }


    // ---------- toString() ----------
    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", roleId=" + roleId +
                ", addressId=" + addressId +
                ", phone='" + phone + '\'' +
                ", password='[PROTECTED]'}";
    }
}
