package servicios;

import java.sql.Connection;
import java.util.ArrayList;

import dtos.LibroDto;

public interface IConsultasPostgre {
	public void actualizar(Connection conexion, ArrayList<LibroDto> listaLibros);
	public void insertar(Connection conexion, ArrayList<LibroDto> listaLibros);
	public void borrar(Connection conexion);
	public void modificar(Connection conexion, ArrayList<LibroDto> listaLibros);
}
