package DAO;

import entidad.Editorial;
import entidad.Libro;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import servicio.LibroServicio;

public class DAOEditorial {

    public static EntityManager miEntity() {
        EntityManager em = Persistence.createEntityManagerFactory("Libreria").createEntityManager();
        return em;
    }

    public static void consultaLibroPorEditorial(String nombre) {
        EntityManager em = miEntity();
        Collection<Editorial> miEditorial = em.createQuery("SELECT e "
                + " FROM Editorial e "
                + " WHERE e.nombre = :nombre").
                setParameter("nombre", nombre).
                getResultList();
        Integer idEditorial = 0;
        if (!miEditorial.isEmpty()) {
            for (Editorial e : miEditorial) {
                idEditorial = e.getId();
                String miLibroEditorial = LibroServicio.consultaIdEditorial(idEditorial);
                if (miLibroEditorial.equalsIgnoreCase("No hay libros con esa editorial")) {
                } else {
                    System.out.println(LibroServicio.consultaIdEditorial(idEditorial));
                }
            }
        } else {
            System.out.println("El autor ingresado no existe");
        }
        em.close();
    }

    public static Editorial consultaNombreLibro(String nombre) {
        EntityManager em = miEntity();
        Collection<Libro> consultaTitulo = LibroServicio.consultaTitulo(nombre);
        Integer idEditorial = 0;
        Editorial miEditorial = null;
        if (!consultaTitulo.isEmpty()) {
            for (Libro l : consultaTitulo) {
                idEditorial = l.getEditorial().getId();
                System.out.println(l.toString());
            }
            miEditorial = em.find(Editorial.class, idEditorial);
            System.out.println(miEditorial.toString());
        } else {
            System.out.println("No hay libros con ese titulo");
        }
        em.close();
        return miEditorial;
    }

    public static Collection<Editorial> consultaNombre(String nombre) {
        EntityManager em = miEntity();
        Collection<Editorial> miEditorial = em.createQuery("SELECT l "
                + " FROM Editorial e "
                + " WHERE e.nombre = :nombre").
                setParameter("nombre", nombre).
                getResultList();
        if (!miEditorial.isEmpty()) {
            for (Editorial e : miEditorial) {
                System.out.println(e.getId());
                System.out.println(e.getNombre());
            }
        } else {
            System.out.println("No existe el nombre de la editorial ingresada");
        }
        em.close();
        return miEditorial;
    }
}