package guru.sfg.brewery.web.controllers;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTests {


    private static final String PASSWORD = "password";


    @Test
    void testNoOp() {
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();
        System.out.println(noOp.encode(PASSWORD));
    }

    @Test
    void testLdap() {
        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        System.out.println(ldap.encode(PASSWORD));
        System.out.println(ldap.encode(PASSWORD));
        System.out.println(ldap.encode("tiger"));

        String encoded = ldap.encode(PASSWORD);

        assertTrue(ldap.matches(PASSWORD, encoded));
    }

    @Test
    void testSha256() {
        PasswordEncoder sha256 = new StandardPasswordEncoder();
        System.out.println(sha256.encode(PASSWORD));
        System.out.println(sha256.encode(PASSWORD));
        System.out.println(sha256.encode("passworduser"));

        String encoded = sha256.encode(PASSWORD);

        assertTrue(sha256.matches(PASSWORD, encoded));
    }

    //Default passwordEncoder from Spring Security
    @Test
    void testByCrypt() {
        PasswordEncoder byCrypt = new BCryptPasswordEncoder();
        System.out.println(byCrypt.encode(PASSWORD));
        System.out.println(byCrypt.encode(PASSWORD));
        System.out.println(byCrypt.encode("passwordadmin"));

        String encoded = byCrypt.encode(PASSWORD);

        assertTrue(byCrypt.matches(PASSWORD, encoded));
    }

    @Test
    void testByCrypt15() {
        PasswordEncoder byCrypt = new BCryptPasswordEncoder(15);
        System.out.println(byCrypt.encode(PASSWORD));
        System.out.println(byCrypt.encode(PASSWORD));
        System.out.println(byCrypt.encode("passwordadmin"));

        String encoded = byCrypt.encode(PASSWORD);

        assertTrue(byCrypt.matches(PASSWORD, encoded));
    }

    @Test
    public void hashingExample() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

        final String salted = PASSWORD + "myTestSalt";

        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }
}
