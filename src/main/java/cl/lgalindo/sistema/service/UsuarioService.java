package cl.lgalindo.sistema.service;

import cl.lgalindo.sistema.dto.UsuarioRequest;
import cl.lgalindo.sistema.entity.Usuario;

public interface UsuarioService {
    Usuario crearUsuario(UsuarioRequest usuarioRequest);
}