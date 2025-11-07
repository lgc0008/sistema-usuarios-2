package cl.lgalindo.sistema.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UsuarioRequest {

    @NotNull(message = "El nombre es obligatorio")
    private String name;

//    @Email(message = "Formato de correo inválido")
    @NotNull(message = "El correo no puede estar vacío")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "Correo con formato inválido")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$", message = "Formato de contraseña inválido")
    private String password;

    @Valid
    private List<TelefonoRequest> phones;

    public UsuarioRequest() {
    }

    public UsuarioRequest(String name, String email, String password, List<TelefonoRequest> phones) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TelefonoRequest> getPhones() {
        return phones;
    }

    public void setPhones(List<TelefonoRequest> phones) {
        this.phones = phones;
    }
}