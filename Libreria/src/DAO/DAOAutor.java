package DAO;

import entidad.Autor;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import servicio.LibroServicio;

public class DAOAutor {

    public static EntityManager miEntity() {
        EntityManager em = Persistence.createEntityManagerFactory("Libreria").createEntityManager();
        return em;
    }

    public static void consultaLibroPorAutor(String nombre) {
        EntityManager em = miEntity();
        Collection<Autor> miAutor = em.createQuery("SELECT a "
                + " FROM Autor a "
                + " WHERE a.nombre = :nombre").
                setParameter("nombre", nombre).
                getResultList();
        Integer idAutor = 0;
        if (!miAutor.isEmpty()) {
            for (Autor a : miAutor) {
                idAutor = a.getId();
                String miLibroAutor = LibroServicio.consultaIdAutor(idAutor);
                if (miLibroAutor.equalsIgnoreCase("No hay libros con ese autor")) {
                } else {
                    System.out.println(LibroServicio.consultaIdAutor(idAutor));
                }
            }
        } else {
            System.out.println("El autor ingresado no existe");
        }
        em.close();
    }

    public static Collection<Autor> consultaAutor(String nombre) {
        EntityManager em = miEntity();
        Collection<Autor> miAutor = em.createQuery("SELECT a "
                + " FROM Autor a "
                + " WHERE a.nombre = :nombre").
                setParameter("nombre", nombre).
                getResultList();
        if (!miAutor.isEmpty()) {
            for (Autor a : miAutor) {
                System.out.println(a.getId());
                System.out.println(a.getNombre());
            }
        } else {
            System.out.println("El autor ingresado no existe");
        }
        em.close();
        return miAutor;
    }
}