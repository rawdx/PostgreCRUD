package servicios;

import java.util.Scanner;

public class Menu implements IMenu {

	public int mostrarMenu() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nLibros");
		System.out.println("1. Obtener datos");
		System.out.println("2. Insertar datos");
		System.out.println("3. Cambiar datos");
		System.out.println("4. Borrar datos");
		System.out.println("5. Salir");
		System.out.print("Introduce una opción: ");
		int opcion = sc.nextInt();
		
		while(opcion < 1 || opcion > 5) {
			System.out.print("Introduce una opción válida: ");
			opcion = sc.nextInt();
		}
		return opcion;
	}

}
