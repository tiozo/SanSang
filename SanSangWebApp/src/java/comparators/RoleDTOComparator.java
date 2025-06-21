/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comparators;

import java.util.Comparator;
import models.Role.RoleDTO;

/**
 *
 * @author tiozo
 */
public class RoleDTOComparator {

    // Sắp xếp theo roleId tăng dần
    public static Comparator<RoleDTO> byIdAscending() {
        return Comparator.comparingInt(RoleDTO::getRoleId);
    }

    // Sắp xếp theo roleId giảm dần
    public static Comparator<RoleDTO> byIdDescending() {
        return (r1, r2) -> Integer.compare(r2.getRoleId(), r1.getRoleId());
    }

    // Sắp xếp theo roleName tăng dần (A-Z)
    public static Comparator<RoleDTO> byNameAscending() {
        return Comparator.comparing(RoleDTO::getRoleName, String.CASE_INSENSITIVE_ORDER);
    }

    // Sắp xếp theo roleName giảm dần (Z-A)
    public static Comparator<RoleDTO> byNameDescending() {
        return byNameAscending().reversed();
    }

    // Sắp xếp theo cả roleId và roleName
    public static Comparator<RoleDTO> byIdThenName() {
        return byIdAscending().thenComparing(byNameAscending());
    }
}
