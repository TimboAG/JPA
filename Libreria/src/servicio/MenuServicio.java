package servicio;

import java.util.Scanner;

public class MenuServicio {

    private static Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public void menu() {
        int opcion = 0;
        do {
            System.out.println("1-Búsqueda de un Autor por nombre");
            System.out.println("2-Búsqueda de un libro por ISBN");
            System.out.println("3-Búsqueda de un libro por Título");
            System.out.println("4-Búsqueda de un libro por nombre de autor");
            System.out.println("5-Búsqueda de un libro por nombre de editorial");
            System.out.println("6-Agregar libro");
            System.out.println("7-Eliminar Libro/s por nombre/s");
            System.out.println("8-Actualizar libro por titulo");
            System.out.println("9-Agregar editorial ");
            System.out.println("10-Agregar Autor");
            System.out.println("11-Consultar editorial por nombre");
            System.out.println("12-Consultar autor por nombre");
            System.out.println("13-Mostrar todos los libros");
            System.out.println("14-Prestar un libro");
            System.out.println("15-Crear Cliente");
            System.out.println("16-Ver todos los clientes");
            System.out.println("17-Consultar Cliente por DNI");
            System.out.println("18-Consultar prestamos");
            System.out.println("19-Consulta prestamos por DNI cliente");
            System.out.println("20-Salir");
            try {
                opcion = leer.nextInt();
                LibroServicio miLibro = new LibroServicio();
                AutorServicio miAutor = new AutorServicio();
                EditorialServicio miEditorial = new EditorialServicio();
                PrestamoServicio miPrestamo = new PrestamoServicio();
                ClienteServicio miCliente = new ClienteServicio();
                switch (opcion) {
                    case 1:
                        miAutor.busquedaPorNombre();
                        break;
                    case 2:
                        miLibro.consultaISBN();
                        break;
                    case 3:
                        LibroServicio.consultaTitulo();
                        break;
                    case 4:
                        miAutor.consultaLibro();
                        break;
                    case 5:
                        miEditorial.consultaLibroPorEditorial();
                        break;
                    case 6:
                        miLibro.crear();
                        break;
                    case 7:
                        miLibro.eliminar();
                        break;
                    case 8:
                        miLibro.actualizar();
                        break;
                    case 9:
                        miEditorial.crear();
                        break;
                    case 10:
                        miAutor.crear();
                        break;
                    case 11:
                        miEditorial.consultaNombre();
                        break;
                    case 12:
                        break;
                    case 13:
                        miLibro.consultarTodos();
                        break;
                    case 14:
                        miPrestamo.crear();
                        break;
                    case 15:
                        miCliente.crear();
                    case 16:
                        miCliente.consultar();
                        break;
                    case 17:
                        miCliente.consultaDni();
                        break;
                    case 18:
                        miPrestamo.consultaTodos();
                        break;
                    case 19:
                        miPrestamo.consultaDniCliente();
                        break;
                    case 20:
                        System.out.println("Eligio SALIR. Programa terminado");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("La opcion elegida es invalida");
                        break;
                }
            } catch (Exception e) {
                leer.next();
                System.out.println("Ha ocurrido un error : " + e.getMessage().getClass() + " programa finalizado");
                System.exit(0);
            }
        } while (opcion != 0);
    }
}