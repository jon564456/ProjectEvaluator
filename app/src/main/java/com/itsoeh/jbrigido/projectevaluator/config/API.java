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
    String registrarUsuario =  URL +"?api=registrar";
}
