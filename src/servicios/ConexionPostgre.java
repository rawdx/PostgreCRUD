package servicios;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionPostgre implements IConexionPostgre {

	public Connection generarConexion() {

		Connection conexion = null;
		String[] parametrosConexion = configuracionConexion();

		if (!parametrosConexion[2].isEmpty()) {
			try {
				Class.forName("org.postgresql.Driver");

				conexion = DriverManager.getConnection(parametrosConexion[0], parametrosConexion[1],
						parametrosConexion[2]);
				boolean esValida = conexion.isValid(50000);
				if (esValida == false) {
					conexion = null;
				}
				System.out.println(esValida ? "Conexión a PostgreSQL válida" : "Conexión a PostgreSQL no válida");
				return conexion;

			} catch (ClassNotFoundException cnfe) {
				System.out.println("Error en registro driver PostgreSQL: " + cnfe);
				return conexion = null;
			} catch (SQLException jsqle) {
				System.out.println("Error en conexión a PostgreSQL (" + parametrosConexion[0] + "): " + jsqle);
				return conexion = null;
			}
		}
		return null;
	}

	/**
	 * Recoge los datos necesarios para realizar la conexión
	 * 
	 * @return array con los datos o null
	 */
	private String[] configuracionConexion() {

		String user = "", pass = "", port = "", host = "", db = "", url = "";

		Properties propiedadesConexionPostgre = new Properties();

		try {
			propiedadesConexionPostgre
					.load(new FileInputStream(new File(".\\src\\utils\\conexion_postgre.properties")));
			user = propiedadesConexionPostgre.getProperty("user");
			pass = propiedadesConexionPostgre.getProperty("pass");
			port = propiedadesConexionPostgre.getProperty("port");
			host = propiedadesConexionPostgre.getProperty("host");
			db = propiedadesConexionPostgre.getProperty("db");
			url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
			String[] stringConfiguracion = { url, user, pass };

			return stringConfiguracion;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
