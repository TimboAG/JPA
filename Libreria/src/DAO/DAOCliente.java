package DAO;

import entidad.Cliente;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DAOCliente {

    public static EntityManager miEntity() {
        EntityManager em = Persistence.createEntityManagerFactory("Libreria").createEntityManager();
        return em;
    }

    public static Cliente consultaId(Integer id) {
        EntityManager em = miEntity();
        Cliente miCliente = em.find(Cliente.class, id);
        if (miCliente != null) {
            System.out.println(miCliente.toString());
        } else {
            System.out.println("No existe el isbn ingresado ");
        }
        em.close();
        return miCliente;
    }

    public static Integer consultaDniDevuelveId(Long documento) {
        EntityManager em = miEntity();
        Collection<Cliente> miCliente = em.createQuery("SELECT c "
                + " FROM Cliente c "
                + " WHERE c.documento = :documento").
                setParameter("documento", documento).
                getResultList();
        Integer id = 0;
        if (!miCliente.isEmpty()) {
            for (Cliente c : miCliente) {
                id = c.getId();
            }
        } else {
            System.out.println("No hay clientes con ese documento");
        }
        em.close();
        return id;
    }

    public static String consultaDni(Long documento) {
        EntityManager em = miEntity();
        Collection<Cliente> miCliente = em.createQuery("SELECT c"
                + " FROM Cliente c "
                + " WHERE c.documento = :documento").
                setParameter("documento", documento).
                getResultList();
        String dniCliente = "";
        if (!miCliente.isEmpty()) {
            for (Cliente c : miCliente) {
                dniCliente = "El cliente existe";
            }
        } else {
            dniCliente = "No hay clientes con ese documento";
        }
        em.close();
        return dniCliente;
    }

    public static Integer consultaNombreDevuelveId(String nombre) {
        EntityManager em = miEntity();
        Collection<Cliente> miCliente = em.createQuery("SELECT l "
                + " FROM Cliente c "
                + " WHERE c.nombre = :nombre").
                setParameter("nombre", nombre).
                getResultList();
        Integer id = 0;
        if (!miCliente.isEmpty()) {
            for (Cliente c : miCliente) {
                id = c.getId();
            }
        } else {
            System.out.println("No hay clientes con ese nombre");
        }
        em.close();
        return id;
    }

    public static Collection<Cliente> consultaTodos() {
        EntityManager em = miEntity();
        Collection<Cliente> miCliente = em.createQuery("SELECT l "
                + " FROM Cliente l").
                getResultList();
        if (!miCliente.isEmpty()) {
            for (Cliente l : miCliente) {
                System.out.println(l.toString());
            }
        } else {
            System.out.println("No hay libros");
        }
        em.close();
        return miCliente;
    }
}