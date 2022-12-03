
package servicio;

import entidad.Editorial;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import DAO.DAOEditorial;

public class EditorialServicio {

    private static final Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public static EntityManager miEntity() {
        EntityManager em = Persistence.createEntityManagerFactory("Libreria").createEntityManager();
        return em;
    }

    public Editorial crear() {
        EntityManager em = miEntity();
        Editorial miEditorial = new Editorial();
        System.out.println("Ingrese nombre de la editorial");
        miEditorial.setNombre(leer.next());
        System.out.println("Ingrese alta (alta o baja)");
        String alta = leer.next();
        if ("alta".equalsIgnoreCase(alta)) {
            miEditorial.setAlta(Boolean.TRUE);
        } else {
            miEditorial.setAlta(Boolean.FALSE);
        }
        em.getTransaction().begin();
        em.persist(miEditorial);
        em.getTransaction().commit();
        return miEditorial;
    }

    public void consultaLibroPorEditorial() {
        System.out.println("Ingrese el nombre de editorial para buscar sus libros");
        DAOEditorial.consultaLibroPorEditorial(leer.next());
    }

    public void consultaNombre() {
        System.out.println("Ingrese el nombre de la editorial a buscar");
        DAOEditorial.consultaNombre(leer.next());
    }
}