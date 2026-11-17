import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {
    @Test
    void testValidEmail() {
        EmailValidator validator = new EmailValidator();
        boolean result = validator.isValidEmail("user@example.com");
        assertTrue(result);
    }

    @Test
    void testInvalidEmail() {
        EmailValidator validator = new EmailValidator();
        boolean result = validator.isValidEmail("userexample.com");
        assertFalse(result);
    }
}