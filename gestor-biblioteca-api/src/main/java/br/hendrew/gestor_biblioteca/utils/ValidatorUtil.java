package br.hendrew.gestor_biblioteca.utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private static final String PHONE_PATTERN = "^(\\(\\d{2}\\))\\s?(?:9\\d{4}|\\d{4})\\d{4}$";
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    public static boolean isDataValida(LocalDate data) {
        return data != null && data.isAfter(LocalDate.now());
    }
}
