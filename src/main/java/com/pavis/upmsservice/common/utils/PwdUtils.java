package com.pavis.upmsservice.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Random;

public class PwdUtils {

    private final static String[] word = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "j", "k", "m", "n",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G",
            "H", "J", "K", "M", "N",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    private final static String[] num = {
            "2", "3", "4", "5", "6", "7", "8", "9"
    };

    private final static BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String randomPassword() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random(new Date().getTime());
        boolean flag = false;
        int length = random.nextInt(3) + 8;
        for (int i = 0; i < length; i++) {
            if (flag) {
                builder.append(num[random.nextInt(num.length)]);
            } else {
                builder.append(word[random.nextInt(word.length)]);
            }
            flag = !flag;
        }
        return builder.toString();
    }

    public static String encrypt(CharSequence rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
