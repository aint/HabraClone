package ua.sg.academy.java2.habraclone.webController.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import ua.sg.academy.java2.habraclone.service.transactional.UserService;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static java.time.ZoneId.systemDefault;

@Component
public class TokenHelper {

    private static final char[] PASSWORD = "somepassword".toCharArray();
    private static final char[] SALT = "5c0744940b5c369b".toCharArray();

    private final TextEncryptor Encryptor = Encryptors.text(new String(PASSWORD), new String(SALT));

    private final Pattern TOKEN_PATTERN = Pattern.compile("[0-9A-Za-z]{10,}");

    private final long expirationDate =
            LocalDateTime.now().plusDays(14).atZone(systemDefault()).toInstant().toEpochMilli();

    private final UserService userService;

    @Autowired
    public TokenHelper(UserService userService) {
        this.userService = userService;
    }

    public boolean validate(String token) {
        return TOKEN_PATTERN.matcher(token).matches();
    }

    public String getToken(String username) throws GeneralSecurityException, UnsupportedEncodingException {
        return encrypt(username + expirationDate);
    }

    public UserDetails getUserFromToken(String token) {
        String decryptedToken = decrypt(token);
        String username = decryptedToken.substring(0, decryptedToken.length() - 13);
        return userService.getByUserName(username);
    }

    private String encrypt(String text) {
        return Encryptor.encrypt(text);
    }

    private String decrypt(String text) {
        return Encryptor.decrypt(text);
    }

}
