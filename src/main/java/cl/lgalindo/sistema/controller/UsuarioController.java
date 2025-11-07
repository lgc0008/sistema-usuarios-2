package cl.lgalindo.sistema.controller;

import cl.lgalindo.sistema.dto.UsuarioRequest;
import cl.lgalindo.sistema.entity.Usuario;
import cl.lgalindo.sistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;

import java.util.Collections;


@RestController
@RequestMapping("/sistema/usuarios")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private static final Logger log = getLogger(UsuarioController.class);

    @PostMapping(value = "/crear", consumes = "application/json")
    public ResponseEntity<?> crearUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        try {
            log.info("Se inicia creación Usuario para Mail " + usuarioRequest.getEmail());
            Usuario usuario = usuarioService.crearUsuario(usuarioRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("mensaje: ", e.getMessage()));
        }
    }
}