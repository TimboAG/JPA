package servicio;

import entidad.Prestamo;
import java.util.Scanner;
import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import DAO.DAOPrestamo;
import java.util.Collection;

public class PrestamoServicio {

    private static final Scanner leer = new Scanner(System.in).useDelimiter("\n");
    Prestamo miPrestamo = new Prestamo();

    public static EntityManager miEntity() {
        EntityManager em = Persistence.createEntityManagerFactory("Libreria").createEntityManager();
        return em;
    }

    public Prestamo crear() throws ParseException {
        EntityManager em = miEntity();
        System.out.println("Ingrese el dni del cliente");
        Long dni = leer.nextLong();
        ClienteServicio miServicioCliente = new ClienteServicio();
        if (miServicioCliente.consultaDni(dni).equalsIgnoreCase("No hay clientes con ese documento")) {
            System.out.println("No existe cliente con ese DNI desea ingresar un cliente nuevo? S-N ");
            String respuesta = leer.next();
            switch (respuesta.toLowerCase()) {
                case "n":
                case "no":
                    em.close();
                    break;
                case "s":
                case "si":
                    miServicioCliente.crear();
                    crearPrestamo();
                    break;
                default:
                    System.out.println("Dato ingresado no valido");
                    break;
            }
        } else {
            Integer id = miServicioCliente.consultaDniDevuelveId(dni);
            miPrestamo.setCliente(miServicioCliente.consultaId(id));
            crearPrestamo();
        }
        return miPrestamo;
    }

    public void crearPrestamo() throws ParseException {
        EntityManager em = miEntity();
        System.out.println("Ingrese el isbn libro");
        Long isbn = leer.nextLong();
        LibroServicio miLibroServicio = new LibroServicio();
        if (miLibroServicio.consultaISBN(isbn) != null) {
            if (miLibroServicio.consultaISBNRestante(isbn) != 0) {
                miLibroServicio.restarEjemplares(isbn);
                miPrestamo.setLibro(miLibroServicio.consultaISBN(isbn));
                Date fechaActual = Date.from(Instant.now());
                miPrestamo.setFechaPrestamo(fechaActual);
                System.out.println("Ingrese la fecha de devolucion del prestamo (ej: 03/4/2022)");
                String fechaComoTextoDevolucion = leer.next();
                SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha2 = simple.parse(fechaComoTextoDevolucion);
                if (fechaActual.before(fecha2)) {
                    miPrestamo.setFechaDevolucion(fechaActual);
                    em.getTransaction().begin();
                    em.persist(miPrestamo);
                    em.getTransaction().commit();
                    em.close();
                } else {
                    System.out.println("Se produjo un error. La fecha de devolucion debe ser distinta a la fecha actual o mayor");
                }
            } else {
                System.out.println("El libro no se puede prestar, no hay cantidad suficiente");
            }

        } else {
            em.close();
        }
    }

    public Collection<Prestamo> consultaTodos() {
        Collection<Prestamo> consulta = DAOPrestamo.consultaTodos();
        return consulta;
    }

    public void consultaDniCliente() {
        System.out.println("Ingrese DNI del cliente");
        Long dniCliente = leer.nextLong();
        System.out.println(DAOPrestamo.consultaDniCliente(dniCliente));
    }
}