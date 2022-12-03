package DAO;

import entidad.Libro;
import java.util.Collection;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DAOLibro {

    private static final Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public static EntityManager miEntity() {
        EntityManager em = Persistence.createEntityManagerFactory("Libreria").createEntityManager();
        return em;
    }

    public static Libro consultaISBN(long isbn) {
        EntityManager em = miEntity();
        Libro miLibro = em.find(Libro.class, isbn);
        if (miLibro != null) {
            System.out.println(miLibro.toString());
        } else {
            System.out.println("No existe el isbn ingresado ");
        }
        em.close();
        return miLibro;
    }

    public static int consultaISBNRestante(long isbn) {
        EntityManager em = miEntity();
        Libro miLibro = em.find(Libro.class, isbn);
        int restante = 0;
        if (miLibro != null) {
            if (miLibro.getEjemplaresPrestados() < miLibro.getEjemplaresRestantes()) {
                restante = miLibro.getEjemplaresRestantes();
            } else {
                System.out.println("No hay libro para prestar");
            }
        } else {
            System.out.println("No existe el isbn ingresado ");
        }
        em.close();
        return restante;
    }

    public static void restarEjemplares(long isbn) {
        EntityManager em = miEntity();
        Libro miLibro = em.find(Libro.class, isbn);
        if (miLibro != null) {
            if (miLibro.getEjemplaresPrestados() < miLibro.getEjemplaresRestantes()) {
                actualizarRestante(isbn);
            } else {
                System.out.println("No hay suficientes libros para prestar");
            }
        }
        em.close();
    }

    //merge
    public static void actualizarRestante(long isbn) {
        EntityManager em = miEntity();
        em.getTransaction().begin();
        em.createQuery("UPDATE Libro l "
                + " SET l.ejemplaresRestantes = l.ejemplaresRestantes  - 1  "
                + " WHERE l.isbn = :isbn").
                setParameter("isbn", isbn).
                executeUpdate();
        em.getTransaction().commit();
        actualizarPrestados(isbn);
        em.close();
    }

    public static void actualizarPrestados(long isbn) {
        EntityManager em = miEntity();
        em.getTransaction().begin();
        em.createQuery("UPDATE Libro l "
                + " SET l.ejemplaresPrestados = l.ejemplaresPrestados  + 1  "
                + " WHERE l.isbn = :isbn").
                setParameter("isbn", isbn).
                executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public static String consultaIdAutor(Integer id) {
        EntityManager em = miEntity();
        Collection<Libro> miLibro = em.createQuery("SELECT l "
                + " FROM Libro l "
                + " WHERE l.autor = (SELECT a FROM Autor a WHERE a.id = :id)").
                setParameter("id", id).
                getResultList();
        String libro = "";
        if (!miLibro.isEmpty()) {
            for (Libro l : miLibro) {
                libro = l.toString();
            }
        } else {
            libro = "No hay libros con ese autor";
        }
        em.close();
        return libro;
    }

    public static String consultaIdEditorial(Integer id) {
        EntityManager em = miEntity();
        Collection<Libro> miLibro = em.createQuery("SELECT l "
                + " FROM Libro l "
                + " WHERE l.editorial = (SELECT e FROM Editorial e WHERE e.id = :id)").
                setParameter("id", id).
                getResultList();
        String libro = "";
        if (!miLibro.isEmpty()) {
            for (Libro l : miLibro) {
                libro = l.toString();
            }
        } else {
            libro = "No hay libros con esa editorial";
        }
        em.close();
        return libro;
    }

    public static Collection<Libro> consultaTitulo(String titulo) {
        EntityManager em = miEntity();
        Collection<Libro> miLibro = em.createQuery("SELECT l "
                + " FROM Libro l "
                + " WHERE l.titulo = :titulo").
                setParameter("titulo", titulo).
                getResultList();
        if (!miLibro.isEmpty()) {
            for (Libro l : miLibro) {
                System.out.println(l.toString());
            }
        } else {
            System.out.println("No hay libros con ese titulo");
        }
        em.close();
        return miLibro;
    }

    public static Collection<Libro> consultaTodos() {
        EntityManager em = miEntity();
        Collection<Libro> miLibro = em.createQuery("SELECT l "
                + " FROM Libro l").
                getResultList();
        if (!miLibro.isEmpty()) {
            for (Libro l : miLibro) {
                System.out.println(l.toString());
            }
        } else {
            System.out.println("No hay libros");
        }
        em.close();
        return miLibro;
    }

    public static Libro eliminar(String titulo) {
        EntityManager em = miEntity();
        Collection<Libro> miLibro = em.createQuery("SELECT l "
                + " FROM Libro l "
                + " WHERE l.titulo = :titulo").
                setParameter("titulo", titulo).
                getResultList();
        long codigo = 0;
        Libro miLibro2 = null;
        if (!miLibro.isEmpty()) {
            for (Libro l : miLibro) {
                codigo = l.getIsbn();
                miLibro2 = em.find(Libro.class, codigo);
                em.getTransaction().begin();
                em.remove(miLibro2);
                em.getTransaction().commit();
            }
            System.out.println("El/Los libros fueron eliminados correctamente");
        } else {
            System.out.println("No existe el tirulo del libro ingresado");
        }
        em.close();
        return miLibro2;
    }

    public static Collection<Libro> actualizar(String titulo) {
        EntityManager em = miEntity();
        Collection<Libro> miLibro = em.createQuery("SELECT l "
                + " FROM Libro l "
                + " WHERE l.titulo = :titulo").
                setParameter("titulo", titulo).
                getResultList();
        actualizarLibro(miLibro);
        return miLibro;
    }

    public static Libro actualizarLibro(Collection<Libro> miLibro) {
        EntityManager em = miEntity();
        long codigo = 0;
        Libro miLibro2 = null;
        if (!miLibro.isEmpty()) {
            for (Libro l : miLibro) {
                codigo = l.getIsbn();
            }
            miLibro2 = em.find(Libro.class, codigo);
            System.out.println("Ingrese el titulo nuevo");
            miLibro2.setTitulo(leer.next());
            em.getTransaction().begin();
            em.merge(miLibro);
            em.getTransaction().commit();
        } else {
            System.out.println("No existe el tirulo del libro ingresado");
        }
        em.close();
        return miLibro2;
    }
}