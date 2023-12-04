package com.itsoeh.jbrigido.projectevaluator.modelo;

/**
 * La clase Usuario representa a un usuario en el sistema.
 * Cada usuario tiene atributos como nombre, apellidos, contraseña, correo y estado.
 */
public class Usuario {

    // Variable que almacena el nombre del usuario.
    private String nombre;

    // Variable que almacena el primer apellido del usuario.
    private String appa;

    // Variable que almacena el segundo apellido del usuario.
    private String apma;

    // Variable que almacena la contraseña del usuario.
    private String contrasena;

    // Variable que almacena el correo electrónico del usuario.
    private String correo;

    // Variable que almacena el estado del usuario.
    private String status;

    /**
     * Constructor por defecto de la clase Usuario.
     */
    public Usuario() {
    }

    /**
     * Constructor con parámetros de la clase Usuario.
     * Permite crear un objeto Usuario con valores específicos para cada atributo.
     *
     * @param contrasena La contraseña del usuario.
     * @param correo     El correo electrónico del usuario.
     */
    public Usuario(String contrasena, String correo) {
        this.contrasena = contrasena;
        this.correo = correo;
    }

    /**
     * Constructor con parámetros extendido de la clase Usuario.
     * Permite crear un objeto Usuario con valores específicos para cada atributo, incluyendo nombre y apellidos.
     *
     * @param nombre     El nombre del usuario.
     * @param appa       El primer apellido del usuario.
     * @param apma       El segundo apellido del usuario.
     * @param contrasena La contraseña del usuario.
     * @param correo     El correo electrónico del usuario.
     */
    public Usuario(String nombre, String appa, String apma, String contrasena, String correo) {
        this.nombre = nombre;
        this.appa = appa;
        this.apma = apma;
        this.contrasena = contrasena;
        this.correo = correo;
    }

    // Métodos de acceso (Getters y Setters) para cada atributo de la clase...

    /**
     * Método que devuelve el estado del usuario.
     *
     * @return El estado del usuario.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Método que establece el estado del usuario.
     *
     * @param status El nuevo estado del usuario.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
