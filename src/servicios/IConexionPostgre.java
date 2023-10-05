package servicios;

import java.sql.Connection;

public interface IConexionPostgre {
	/**
	 * Establece la conexion con la base de datos de postgre.
	 * 
	 * @return La conexion establecida o null si no consigue establecerse
	 */
	public Connection generarConexion();
}
