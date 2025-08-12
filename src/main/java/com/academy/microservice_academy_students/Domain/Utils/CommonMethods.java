package com.academy.microservice_academy_students.Domain.Utils;

public class CommonMethods {

    public static String GeneralMessages(int opcion)
    {
        return switch (opcion) 
        {
            case 1 -> "Ocurrio un error en el servidor. ";
            case 2 -> "Recurso no encontrado. ";
            case 3 -> "Recurso existente. ";
            case 4 -> "Error en la validacion de campos entrantes. ";
            case 5 -> "Servicio temporalmente no disponible. ";
            case 6 -> "Estudiante guardado correctamente. ";
            case 7 -> "Estudiante obtenido correctamente. ";
            case 8 -> "Estudiantes obtenidos correctamente. ";
            case 9 -> "Estudiante actualizado correctamente. ";
            case 10 -> "Estudiante eliminado correctamente. ";
            default -> "";
        };
    }

}
