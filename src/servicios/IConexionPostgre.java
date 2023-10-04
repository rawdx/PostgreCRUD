package servicios;

import java.sql.Connection;

public interface IConexionPostgre {
	public Connection generarConexion();
}
