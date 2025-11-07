package cl.lgalindo.sistema.service.impl;

import cl.lgalindo.sistema.dto.UsuarioRequest;
import cl.lgalindo.sistema.entity.Usuario;
import cl.lgalindo.sistema.exception.EmailYaExisteException;
import cl.lgalindo.sistema.repository.UsuarioRepository;
import cl.lgalindo.sistema.util.TokenGeneradorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TokenGeneradorUtil tokenGeneradorUtil;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuarioConDatosValidos() {
        UsuarioRequest request = new UsuarioRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("Password123");
        request.setPhones(Collections.emptyList());

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(tokenGeneradorUtil.generateToken(request.getEmail())).thenReturn("generated-token");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario usuario = usuarioService.crearUsuario(request);

        assertNotNull(usuario);
        assertEquals(request.getEmail(), usuario.getEmail());
        assertEquals("generated-token", usuario.getToken());
        assertTrue(new BCryptPasswordEncoder().matches(request.getPassword(), usuario.getPassword()));
    }

    @Test
    void crearUsuarioConEmailExistenteLanzaExcepcion() {
        UsuarioRequest request = new UsuarioRequest();
        request.setEmail("existing.email@example.com");

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Usuario()));

        assertThrows(EmailYaExisteException.class, () -> usuarioService.crearUsuario(request));
    }

    @Test
    void crearUsuarioConErrorInternoLanzaExcepcion() {
        UsuarioRequest request = new UsuarioRequest();
        request.setEmail("error.email@example.com");

        when(usuarioRepository.findByEmail(request.getEmail())).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(request));
        assertEquals("Error interno al crear el usuario", exception.getMessage());
    }
}
