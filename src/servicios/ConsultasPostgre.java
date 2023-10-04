package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import dtos.LibroDto;
import utils.ConversionDto;

public class ConsultasPostgre implements IConsultasPostgre {

	@Override
	public void actualizar(Connection conexion, ArrayList<LibroDto> listaLibros) {
		ConversionDto adto = new ConversionDto();

		Statement declaracionSQL = null;
		ResultSet resultadoConsulta = null;
		try {
			declaracionSQL = conexion.createStatement();

			resultadoConsulta = declaracionSQL.executeQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros;");

			adto.ConvertirADto(resultadoConsulta, listaLibros);

			resultadoConsulta.close();
			declaracionSQL.close();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void insertar(Connection conexion, ArrayList<LibroDto> listaLibros) {

		if (conexion != null) {
			Scanner sc = new Scanner(System.in);

			System.out.print("Introduce el titulo del libro que quieres añadir: ");
			String titulo = sc.nextLine();
			System.out.print("Introduce su autor: ");
			String autor = sc.nextLine();
			System.out.print("Introduce el ISBN: ");
			String isbn = sc.nextLine();

			try {
				PreparedStatement declaracion = conexion.prepareStatement(
						"INSERT INTO gbp_almacen.gbp_alm_cat_libros (titulo, autor, isbn) VALUES (?, ?, ?);");
				declaracion.setString(1, titulo);
				declaracion.setString(2, autor);
				declaracion.setString(3, isbn);

				int filas = declaracion.executeUpdate();
				if (filas > 0)
					System.out.println("El libro se ha insertado correctamente.");

			} catch (SQLException e) {
				System.out.println(e);
			}
		} else
			System.out.println("Error: No hay conexion con la base de datos.");
	}

	public void borrar(Connection conexion) {

		if (conexion != null) {
			Scanner sc = new Scanner(System.in);

			System.out.print("Introduce el ISBN del libro que quieres borrar: ");
			String isbn = sc.nextLine();

			try {
				PreparedStatement declaracion = conexion
						.prepareStatement("DELETE FROM gbp_almacen.gbp_alm_cat_libros WHERE isbn = ?;");
				declaracion.setString(1, isbn);

				declaracion.executeUpdate();

			} catch (Exception e) {
				System.out.println(e);
			}
		} else
			System.out.println("Error: No hay conexion con la base de datos.");
	}

	public void modificar(Connection conexion, ArrayList<LibroDto> listaLibros) {

		if (conexion != null) {
			Scanner sc = new Scanner(System.in);

			System.out.print("Introduce el ISBN del libro a modificar: ");
			String isbn = sc.nextLine();

			boolean existe = false;
			for (LibroDto libro : listaLibros)
				if (libro.getIsbn().equals(isbn))
					existe = true;

			if (existe) {
				System.out.print("Introduce un nuevo titulo: ");
				String titulo = sc.nextLine();

				System.out.print("Introduce un nuevo autor: ");
				String autor = sc.nextLine();

				System.out.print("Introduce un nuevo ISBN: ");
				String nuevoIsbn = sc.nextLine();

				try {
					PreparedStatement declaracion = conexion.prepareStatement(
							"UPDATE gbp_almacen.gbp_alm_cat_libros SET titulo=?, autor=?, isbn=? WHERE isbn = ?;");
					declaracion.setString(1, titulo);
					declaracion.setString(2, autor);
					declaracion.setString(3, nuevoIsbn);
					declaracion.setString(4, isbn);

					int filas = declaracion.executeUpdate();
					if (filas > 0)
						System.out.println("El libro se ha insertado correctamente.");

				} catch (Exception e) {
					System.out.println(e);
				}
			} else
				System.out.println("El libro con ISBN " + isbn + " no existe.");
		} else
			System.out.println("Error: No hay conexión con la base de datos.");
	}
}
