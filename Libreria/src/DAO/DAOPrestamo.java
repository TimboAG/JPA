package DAO;

import entidad.Cliente;
import entidad.Prestamo;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DAOPrestamo {

    public static EntityManager miEntity() {
        EntityManager em = Persistence.createEntityManagerFactory("Libreria").createEntityManager();
        return em;
    }

    public Prestamo consultaId(Integer id) {
        EntityManager em = miEntity();
        Prestamo miPrestamo = em.find(Prestamo.class, id);
        if (miPrestamo != null) {
            System.out.println(miPrestamo.toString());
        } else {
            System.out.println("No existe el id ingresado ");
        }
        em.close();
        return miPrestamo;
    }

    public static String consultaIdCliente(Integer id) {
        EntityManager em = miEntity();
        Collection<Cliente> miCliente = em.createQuery("SELECT p "
                + " FROM Prestamo p "
                + " WHERE p.cliente = (SELECT c FROM Cliente a WHERE c.id = :id)").
                setParameter("id", id).
                getResultList();
        String cliente = "";
        if (!miCliente.isEmpty()) {
            for (Cliente c : miCliente) {
                cliente = c.toString();
            }
        } else {
            cliente = "No hay prestamos a clientes con ese nombre";
        }
        em.close();
        return cliente;
    }

    public static String consultaDniCliente(Long documento) {
        EntityManager em = miEntity();
        Collection<Cliente> miCliente = em.createQuery("SELECT p "
                + " FROM Prestamo p "
                + " WHERE p.cliente = (SELECT c FROM Cliente a WHERE c.documento = :documento)").
                setParameter("documento", documento).
                getResultList();
        String documentoCliente = "";
        if (!miCliente.isEmpty()) {
            for (Cliente c : miCliente) {
                documentoCliente = c.toString();
            }
        } else {
            documentoCliente = "No hay prestamos a clientes con ese nombre";
        }
        em.close();
        return documentoCliente;
    }

    public static Collection<Prestamo> consultaTodos() {
        EntityManager em = miEntity();
        Collection<Prestamo> miPrestamo = em.createQuery("SELECT p "
                + " FROM Prestamo p").
                getResultList();
        if (!miPrestamo.isEmpty()) {
            for (Prestamo p : miPrestamo) {
                System.out.println(p.toString());
            }
        } else {
            System.out.println("No hay prestamos");
        }
        em.close();
        return miPrestamo;
    }

}