package servicio;

import DAO.DAOAutor;
import entidad.Autor;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class AutorServicio {

    private static final Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public static EntityManager miEntity() {
        EntityManager em = Persistence.createEntityManagerFactory("Libreria").createEntityManager();
        return em;
    }

    public void busquedaPorNombre() {
        System.out.println("Ingrese el nombre del autor a buscar");
        DAOAutor.consultaAutor(leer.next());
    }

    public void consultaLibro() {
        System.out.println("Ingrese el nombre del autor a buscar");
        DAOAutor.consultaLibroPorAutor(leer.next());
    }

    public Autor crear() {
        EntityManager em = miEntity();
        Autor miAutor = new Autor();
        System.out.println("Ingrese nombre del autor");
        miAutor.setNombre(leer.next());
        System.out.println("Ingrese alta (alta o baja)");
        String alta = leer.next();
        if ("alta".equalsIgnoreCase(alta)) {
            miAutor.setAlta(Boolean.TRUE);
        } else {
            miAutor.setAlta(Boolean.FALSE);
        }
        em.getTransaction().begin();
        em.persist(miAutor);
        em.getTransaction().commit();
        return miAutor;
    }
}