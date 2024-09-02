package com.itsoeh.jbrigido.projectevaluator.config;

public interface API {
    String URL = "http://192.168.1.81/webservicespe/";
    String login = URL + "?api=login";
    String listarEvaludares = URL + "?api=listarEvaluadores";
    String listarProyectos = URL + "?api=listarProyectos";
    String listarResultados = URL + "?api=listarResultados";
    String cargarInformacionProyecto = URL + "?api=mostrarProyecto";
    String listarIntegrante = URL + "?api=listarIntegrantes";
    String listarDisponibles =  URL + "?api=listarProyectosDisponibles";
    String listarAsignado = URL + "?api=listarAsignados";
    String asignarProyecto = URL + "?api=asignarProyecto";
    String eliminarAsignacion = URL + "?api=eliminarAsignacion";
    String registrarUsuario =  URL +"?api=registrar";
    String recuperarContrasena =  URL +"?api=restablecerContraseña";
}
