/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comparators;

import java.util.Comparator;
import models.Address.AddressDTO;

/**
 *
 * @author tiozo
 */
public class AddressDTOComparator {
    // Sắp xếp theo roleId tăng dần
    public static Comparator<AddressDTO> byIdAscending() {
        return Comparator.comparingInt(AddressDTO::getAddressID);
    }

    // Sắp xếp theo roleId giảm dần
    public static Comparator<AddressDTO> byIdDescending() {
        return (r1, r2) -> Integer.compare(r2.getAddressID(), r1.getAddressID());
    }
}
