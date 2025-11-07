package cl.lgalindo.sistema.dto;

import javax.validation.constraints.NotNull;

public class TelefonoRequest {

    @NotNull(message = "El número no puede estar vacío")
    private String number;

    @NotNull(message = "El código de ciudad no puede estar vacío")
    private String citycode;

    @NotNull(message = "El código de país no puede estar vacío")
    private String contrycode;

    public TelefonoRequest() {
    }

    // Getters y Setters
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getContrycode() {
        return contrycode;
    }

    public void setContrycode(String contrycode) {
        this.contrycode = contrycode;
    }
}