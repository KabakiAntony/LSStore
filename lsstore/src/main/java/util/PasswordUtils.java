package util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.util.Arrays;

public class PasswordUtils {

    private static final int COST = 12;

    public static String hashPassword(char[] plainPassword) {
        try {
            return BCrypt.withDefaults().hashToString(COST, plainPassword);
        } finally {
            Arrays.fill(plainPassword, '0'); 
        }
    }

    public static boolean verifyPassword(char[] plainPassword, String hashedPassword) {
        try {
            return BCrypt.verifyer()
                         .verify(plainPassword, hashedPassword)
                         .verified;
        } finally {
            Arrays.fill(plainPassword, '0');
        }
    }
}
