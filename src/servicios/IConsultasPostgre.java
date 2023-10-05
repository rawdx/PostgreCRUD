package servicios;

import java.sql.Connection;
import java.util.ArrayList;

import dtos.LibroDto;

public interface IConsultasPostgre {
	/**
	 * Limpia y actualiza la lista de libros recogiendo los registros de la base de
	 * datos.
	 * 
	 * @param conexion
	 * @param listaLibros
	 */
	public void actualizar(Connection conexion, ArrayList<LibroDto> listaLibros);

	/**
	 * Muestra todos los datos de los libros de la base de datos o solo uno de
	 * ellos.
	 * 
	 * @param conexion
	 * @param listaLibros
	 */
	public void seleccionar(Connection conexion, ArrayList<LibroDto> listaLibros);

	/**
	 * Inserta registros en la base de datos.
	 * 
	 * @param conexion
	 * @param listaLibros
	 */
	public void insertar(Connection conexion, ArrayList<LibroDto> listaLibros);

	/**
	 * Modifica un registro de la base de datos.
	 * 
	 * @param conexion
	 * @param listaLibros
	 */
	public void modificar(Connection conexion, ArrayList<LibroDto> listaLibros);

	/**
	 * Borra un registro de la base de datos.
	 * 
	 * @param conexion
	 */
	public void borrar(Connection conexion);
}
