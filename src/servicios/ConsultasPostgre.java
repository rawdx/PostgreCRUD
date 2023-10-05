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

	public void actualizar(Connection conexion, ArrayList<LibroDto> listaLibros) {
		ConversionDto adto = new ConversionDto();

		Statement declaracionSQL = null;
		ResultSet resultadoConsulta = null;
		try {
			declaracionSQL = conexion.createStatement();
			resultadoConsulta = declaracionSQL.executeQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros;");
			
			listaLibros.clear();
			adto.ConvertirADto(resultadoConsulta, listaLibros);

			resultadoConsulta.close();
			declaracionSQL.close();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void seleccionar(Connection conexion, ArrayList<LibroDto> listaLibros) {
		Scanner sc = new Scanner(System.in);

		System.out.println("\n1. Ver todos los datos.");
		System.out.println("2. Ver los datos de un libro.");

		// nextLine necesario para que limpie el buffer
		int opcion = sc.nextInt();
		sc.nextLine();

		while (opcion != 1 && opcion != 2) {
			System.out.print("Error, Introduce una opción válida: ");
			opcion = sc.nextInt();
			sc.nextLine();
		}

		if (opcion == 1) {
			for (LibroDto libro : listaLibros) {
				System.out.println(libro.toString());
			}
		} else {
			System.out.print("Introduce el ISBN del libro que quieres ver: ");
			String isbn = sc.nextLine();

			boolean existe = false;
			for (LibroDto libro : listaLibros)
				if (libro.getIsbn().equals(isbn)) {
					existe = true;
					System.out.println(libro.toString());
					break;
				}

			if (!existe)
				System.out.println("El libro con ISBN: " + isbn + " no existe.");
		}
	}

	public void insertar(Connection conexion, ArrayList<LibroDto> listaLibros) {
		Scanner sc = new Scanner(System.in);
		ConsultasPostgre consulta = new ConsultasPostgre();

		String titulo;
		String autor;
		String isbn;
		boolean existe;
		int filas;
		String opcion = "";

		do {
			if (conexion != null) {
				System.out.print("\nIntroduce el titulo del libro que quieres añadir: ");
				titulo = sc.nextLine();
				System.out.print("Introduce su autor: ");
				autor = sc.nextLine();
				System.out.print("Introduce el ISBN: ");
				isbn = sc.nextLine();

				existe = false;
				for (LibroDto libro : listaLibros)
					if (libro.getIsbn().equals(isbn)) {
						System.out.println("Error: El ISBN introducido ya existe.");
						existe = true;
						break;
					}

				try {
					if (!existe && conexion != null) {
						PreparedStatement declaracion = conexion.prepareStatement(
								"INSERT INTO gbp_almacen.gbp_alm_cat_libros (titulo, autor, isbn) VALUES (?, ?, ?);");
						declaracion.setString(1, titulo);
						declaracion.setString(2, autor);
						declaracion.setString(3, isbn);

						filas = declaracion.executeUpdate();
						if (filas > 0) {
							consulta.actualizar(conexion, listaLibros);
							System.out.println("El libro se ha insertado correctamente.");
						}
					}
					System.out.println("\n¿Quieres seguir insertando?");
					System.out.print("Introduce 1 para seguir insertando o cualquier cosa para salir: ");
					opcion = sc.nextLine();

				} catch (SQLException e) {
					System.out.println(e);
				}
			} else
				System.out.println("Error: No hay conexión con la base de datos.");
		} while (opcion.equals("1"));
	}

	public void modificar(Connection conexion, ArrayList<LibroDto> listaLibros) {
		Scanner sc = new Scanner(System.in);

		if (conexion != null) {

			System.out.print("\nIntroduce el ISBN del libro a modificar: ");
			String isbn = sc.nextLine();

			boolean existe = false;
			for (LibroDto libro : listaLibros)
				if (libro.getIsbn().equals(isbn)) {
					existe = true;
					break;
				}

			if (existe) {
				System.out.print("Introduce un nuevo titulo: ");
				String titulo = sc.nextLine();

				System.out.print("Introduce un nuevo autor: ");
				String autor = sc.nextLine();

				System.out.print("Introduce un nuevo ISBN: ");
				String nuevoIsbn = sc.nextLine();

				existe = false;
				for (LibroDto libro : listaLibros)
					if (libro.getIsbn().equals(nuevoIsbn)) {
						System.out.println("Error: El ISBN introducido ya existe.");
						existe = true;
						break;
					}

				try {
					if (!existe && conexion != null) {
						PreparedStatement declaracion = conexion.prepareStatement(
								"UPDATE gbp_almacen.gbp_alm_cat_libros SET titulo=?, autor=?, isbn=? WHERE isbn = ?;");
						declaracion.setString(1, titulo);
						declaracion.setString(2, autor);
						declaracion.setString(3, nuevoIsbn);
						declaracion.setString(4, isbn);

						int filas = declaracion.executeUpdate();
						if (filas > 0)
							System.out.println("El libro se ha modificado correctamente.");
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			} else
				System.out.println("El libro con ISBN " + isbn + " no existe.");
		} else
			System.out.println("Error: No hay conexión con la base de datos.");
	}

	public void borrar(Connection conexion) {

		if (conexion != null) {
			Scanner sc = new Scanner(System.in);

			System.out.print("\nIntroduce el ISBN del libro que quieres borrar: ");
			String isbn = sc.nextLine();

			try {
				PreparedStatement declaracion = conexion
						.prepareStatement("DELETE FROM gbp_almacen.gbp_alm_cat_libros WHERE isbn = ?;");
				declaracion.setString(1, isbn);

				int filas = declaracion.executeUpdate();
				if (filas > 0)
					System.out.println("El libro se ha borrado correctamente.");
				else
					System.out.println("No existe el libro con ISBN " + isbn + ".");

			} catch (Exception e) {
				System.out.println(e);
			}
		} else
			System.out.println("Error: No hay conexión con la base de datos.");
	}
}
