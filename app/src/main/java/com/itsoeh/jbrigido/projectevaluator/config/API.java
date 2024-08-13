package com.itsoeh.jbrigido.projectevaluator.config;

public interface API {
    String URL = "http://192.168.1.81/ws/Api.php";
    String LISTAR_ADMINISTRADOR = URL + "?apicall=listarAdministradores";
    String Guardar_ADMINISTRADOR = URL + "?apicall=guardarAdministrador";
    String ACTUALIZAR_ADMINISTRADOR = URL + "?apicall=editarAdministrador";
    String SUBE_FOTO = URL + "?apicall=subirfoto";
    String BUSCAR_ADMINISTRADOR = URL + "?apicall=buscarAdministrador";
    String LISTAR_EVALUADORES = URL + "?apicall=listarEvaluadores";
    String LISTAR_DISPONIBLES = URL + "?apicall=listarDisponibles";
    String LISTAR_INTEGRANTES = URL + "?apicall=obtenerIntegrantes";
    String LISTAR_EQUIPOS = URL + "?apicall=listarEquipos";
    String ASIGNAR = URL + "?apicall=asignar";
    String LISTAR_CALIFICACIONES = URL+"?apicall=listarCalificaciones";
    String CONTAR_ALUMNNOS = URL +"?apicall=contarusuarios";
    String CONTAR_EVALUADORES = URL +"?apicall=contarevaluadores";
    String CONTAR_PROYECTOS = URL +"?apicall=contarProyectos";

}
