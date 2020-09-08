/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Hp
 */
public class MaHoa {
    public String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            String st=new String();
            for (byte b : messageDigest) {
                st += String.format("%02X", b);
            }
            return st;
        }   catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
            }
    }
    
}
