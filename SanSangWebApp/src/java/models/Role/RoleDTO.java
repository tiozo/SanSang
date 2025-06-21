/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.Role;

import java.util.Comparator;

/**
 *
 * @author tiozo
 */
public class RoleDTO {
    private final int roleId;
    private final String roleName;

    // Private constructor chỉ dùng trong Builder
    private RoleDTO(Builder builder) {
        this.roleId = builder.roleId;
        this.roleName = builder.roleName;
    }

    public int compare(RoleDTO o1, RoleDTO o2) {
        return Integer.compare(o1.getRoleId(), o2.getRoleId());
    }

    // ---------- Builder Pattern ----------
    public static class Builder {
        // Các trường bắt buộc
        private final int roleId;
        private final String roleName;

        // Constructor cho các trường BẮT BUỘC
        public Builder(int roleId, String roleName) {
            if (roleId <= 0) {
                throw new IllegalArgumentException("Role ID phải là số dương");
            }
            if (roleName == null || roleName.isEmpty()) {
                throw new IllegalArgumentException("Role name không được để trống");
            }
            if (roleName.length() > 50) {
                throw new IllegalArgumentException("Role name không quá 50 ký tự");
            }
            
            this.roleId = roleId;
            this.roleName = roleName;
        }

        // Xây dựng đối tượng RoleDTO
        public RoleDTO build() {
            return new RoleDTO(this);
        }
    }

    // ---------- Getter ----------
    public int getRoleId() { return roleId; }
    public String getRoleName() { return roleName; }

    // ---------- toString() ----------
    @Override
    public String toString() {
        return "RoleDTO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    // ---------- Factory Methods (tuỳ chọn) ----------
    public static Builder builder(int roleId, String roleName) {
        return new Builder(roleId, roleName);
    }
}
