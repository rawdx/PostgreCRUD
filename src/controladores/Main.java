package controladores;

import java.sql.Connection;
import java.util.ArrayList;

import dtos.LibroDto;
import servicios.ConexionPostgre;
import servicios.ConsultasPostgre;
import servicios.IConexionPostgre;
import servicios.IConsultasPostgre;
import servicios.IMenu;
import servicios.Menu;

public class Main {

	public static void main(String[] args) {
		
		IConexionPostgre conn = new ConexionPostgre();
		IConsultasPostgre consulta = new ConsultasPostgre();
		IMenu menu = new Menu();
		ArrayList<LibroDto> listaLibros = new ArrayList<>();

		try {
			Connection conexion = conn.generarConexion();
			consulta.actualizar(conexion, listaLibros);
			int opcion;
			do {
				opcion = menu.mostrarMenu();
				
				switch(opcion) {
				case 1:
					break;
				case 2:
					consulta.insertar(conexion, listaLibros);
					break;
				case 3:
					consulta.actualizar(conexion, listaLibros);
					break;
				case 4:
					consulta.borrar(conexion);
					break;
					
				}
			}while(opcion != 5);
			
//			if (conexion != null) {
//				cons.consultaSelect(conexion, listaLibros);
//				for (int i = 0; i < listaLibros.size(); i++) {
//					System.out.println(listaLibros.get(i).toString());
//				}
//			}

		} catch (Exception e) {
			System.out.println("[ERROR-Main] Se ha producido un error al ejecutar la aplicación: " + e);
		}
	}

}
