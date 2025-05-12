package webProject.service.member;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class PasswordEncoder {
    private static final int ITERATIONS = 85319;
    private static final int KEY_LENGTH = 128;

    // 비밀번호 암호화 (salt 포함)
    public String encrypt(String password) {
        try {
            // salt 생성
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // 해시 생성
            byte[] hash = hashPassword(password, salt);

            // salt와 해시를 Base64로 인코딩해서 저장
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 비밀번호 확인
    public boolean matches(String rawPassword, String storedPassword) {
        try {
            // salt와 해시 분리
            String[] parts = storedPassword.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHash = Base64.getDecoder().decode(parts[1]);

            // 입력된 비밀번호로 해시 생성
            byte[] inputHash = hashPassword(rawPassword, salt);

            // 두 해시를 비교
            return java.util.Arrays.equals(storedHash, inputHash);
        } catch (Exception e) {
            return false;
        }
    }

    // 비밀번호 + salt → 해시 생성
    private byte[] hashPassword(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }
}
