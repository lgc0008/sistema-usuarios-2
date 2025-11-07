package cl.lgalindo.sistema;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class SistemaUsuariosApplicationTests {

    @Test
    void applicationStartsSuccessfully() {
        assertDoesNotThrow(() -> SistemaUsuariosApplication.main(new String[]{}));
    }

}
