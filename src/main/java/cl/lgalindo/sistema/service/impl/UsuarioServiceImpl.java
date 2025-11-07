package cl.lgalindo.sistema.service.impl;

import cl.lgalindo.sistema.dto.TelefonoRequest;
import cl.lgalindo.sistema.dto.UsuarioRequest;
import cl.lgalindo.sistema.entity.Telefono;
import cl.lgalindo.sistema.entity.Usuario;
import cl.lgalindo.sistema.exception.EmailYaExisteException;
import cl.lgalindo.sistema.repository.UsuarioRepository;
import cl.lgalindo.sistema.service.UsuarioService;
import cl.lgalindo.sistema.util.TokenGeneradorUtil;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenGeneradorUtil tokenGeneradorUtil;

    @Value("${password.regex}")
    private String passwordRegex;

    private static final Logger log = getLogger(UsuarioService.class);

    @Transactional
    @Override
    public Usuario crearUsuario(UsuarioRequest usuarioRequest) {
        try {
            if (usuarioRepository.findByEmail(usuarioRequest.getEmail()).isPresent()) {
                log.info("El correo " + usuarioRequest.getEmail() + " ya se encuentra registrado");
                throw new EmailYaExisteException("El correo ya se encuentra registrado");
            }

            Usuario user = new Usuario();
//            user.setId(UUID.randomUUID());
            user.setName(usuarioRequest.getName());
            user.setEmail(usuarioRequest.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(usuarioRequest.getPassword()));
            user.setCreated(LocalDateTime.now());
            user.setModified(LocalDateTime.now());
            user.setLastLogin(LocalDateTime.now());
            user.setActive(true);
            user.setToken(tokenGeneradorUtil.generateToken(usuarioRequest.getEmail()));
            user.setPhones(mapPhones(usuarioRequest.getPhones()));

            return usuarioRepository.save(user);

        } catch (EmailYaExisteException e) {
            // Re-lanzamos la excepción personalizada para que el controlador la maneje
            throw e;
        } catch (Exception e) {
            log.error("Error al crear el usuario: " + e.getMessage(), e);
            throw new RuntimeException("Error interno al crear el usuario");
        }
    }



    private List<Telefono> mapPhones(List<TelefonoRequest> telefonoRequests) {
        return telefonoRequests.stream().map(req -> {
            Telefono telefono = new Telefono();
            telefono.setNumber(req.getNumber());
            telefono.setCitycode(req.getCitycode());
            telefono.setContrycode(req.getContrycode());
            return telefono;
        }).collect(Collectors.toList());
    }

}