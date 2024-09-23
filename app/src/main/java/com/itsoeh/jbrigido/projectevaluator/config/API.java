package com.itsoeh.jbrigido.projectevaluator.config;

public interface API {
    String URL = "http://192.168.100.10/webservicespe/";
    String login = URL + "?api=login";
    String listarEvaludares = URL + "?api=listarEvaluadores";
    String listarProyectos = URL + "?api=listarProyectos";
    String listarResultados = URL + "?api=listarResultados";
    String cargarInformacionProyecto = URL + "?api=mostrarProyecto";
    String listarIntegrante = URL + "?api=listarIntegrantes";
    String listarDisponibles = URL + "?api=listarProyectosDisponibles";
    String listarAsignado = URL + "?api=listarAsignados";
    String asignarProyecto = URL + "?api=asignarProyecto";
    String eliminarAsignacion = URL + "?api=eliminarAsignacion";
    String listarCategoria = URL + "?api=listarCategoria";
    String agregarAtributo = URL + "?api=agregarAtributo";
    String listarAtributo = URL + "?api=listarAtributo";
    String actualizarAtributo = URL + "?api=actualizarAtributo";
    String eliminarAtributo = URL + "?api=eliminarAtributo&id=";
    String agregarCategoria = URL + "?api=agregarCategoria";
    String actualizarCategoria = URL + "?api=actualizarCategoria";
    String eliminarCategoria = URL + "?api=eliminarCategoria&id=";
}
