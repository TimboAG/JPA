package servicio;

import entidad.Libro;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import DAO.DAOLibro;
import java.util.Collection;

public class LibroServicio {

    private static final Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public static EntityManager miEntity() {
        EntityManager em = Persistence.createEntityManagerFactory("Libreria").createEntityManager();
        return em;
    }

    public Libro crear() {
        EntityManager em = miEntity();
        Libro miLibro = new Libro();
        System.out.println("Ingrese el isbn del libro");
        miLibro.setIsbn(leer.nextLong());
        System.out.println("Ingrese el titulo del libro");
        miLibro.setTitulo(leer.next());
        System.out.println("Ingrese el año del libro");
        miLibro.setAnio(leer.nextInt());
        System.out.println("Ingrese Cantidad de ejemplares");
        int ejemplares = leer.nextInt();
        miLibro.setEjemplares(ejemplares);
        miLibro.setEjemplaresPrestados(0);
        System.out.println("Ingrese alta (alta o baja)");
        String alta = leer.next();
        if ("alta".equalsIgnoreCase(alta)) {
            miLibro.setAlta(Boolean.TRUE);
        } else {
            miLibro.setAlta(Boolean.FALSE);
        }
        AutorServicio miAutor = new AutorServicio();
        miLibro.setAutor(miAutor.crear());
        EditorialServicio miEditorial = new EditorialServicio();
        miLibro.setEditorial(miEditorial.crear());
        miLibro.setEjemplaresRestantes(ejemplares);
        em.getTransaction().begin();
        em.persist(miLibro);
        em.getTransaction().commit();
        em.close();
        return miLibro;
    }

    public void consultaISBN() {
        System.out.println("Ingrese el ISBN a buscar");
        DAOLibro.consultaISBN(leer.nextLong());
    }

    public static void consultaTitulo() {
        System.out.println("Ingrese el titulo del libro a buscar");
        DAOLibro.consultaTitulo(leer.next());
    }

    public void eliminar() {
        System.out.println("Ingrese el titulo del libro a eliminar");
        DAOLibro.eliminar(leer.next());
    }

    public void actualizar() {
        System.out.println("Ingrese el titulo del libro a actualizar");
        DAOLibro.actualizar(leer.next());
    }

    public void consultarTodos() {
        DAOLibro.consultaTodos();
    }
    
    public static String consultaIdAutor(Integer idAutor){
        String consulta = DAOLibro.consultaIdAutor(idAutor);
        return consulta;
    }
    
    public static String consultaIdEditorial(Integer idEditorial){
        String consulta = DAOLibro.consultaIdEditorial(idEditorial);
        return consulta;
    }
  
    public static Collection<Libro> consultaTitulo(String nombre){
        Collection<Libro> consulta = DAOLibro.consultaTitulo(nombre);
        return consulta;
    }
    
    public static Libro consultaISBN(Long isbn){
        Libro miLibro = DAOLibro.consultaISBN(isbn);
        return miLibro;
    }
    
    public static int consultaISBNRestante(Long isbn){
       int consulta =  DAOLibro.consultaISBNRestante(isbn);
       return consulta;
    }
    
    public static void restarEjemplares(Long isbn){
        DAOLibro.restarEjemplares(isbn);
    }
}