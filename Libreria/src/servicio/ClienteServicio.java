package servicio;

import entidad.Cliente;
import java.util.Scanner;
import javax.persistence.*;
import DAO.DAOCliente;

public class ClienteServicio {

    private static final Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public static EntityManager miEntity() {
        EntityManager em = Persistence.createEntityManagerFactory("Libreria").createEntityManager();
        return em;
    }

    public Cliente crear() {
        EntityManager em = miEntity();
        Cliente miCliente = new Cliente();
        System.out.println("Ingrese el documento del cliente");
        miCliente.setDocumento(leer.nextLong());
        System.out.println("Ingrese el nombre del cliente");
        miCliente.setNombre(leer.next());
        System.out.println("Ingrese el apellido del cliente");
        miCliente.setApellido(leer.next());
        System.out.println("Ingrese el telefono del cliente");
        miCliente.setTelefono(leer.next());
        em.getTransaction().begin();
        em.persist(miCliente);
        em.getTransaction().commit();
        em.close();
        return miCliente;
    }

    public void consultar() {
        DAOCliente.consultaTodos();
    }

    public void consultaDni() {
        System.out.println("Ingrese el DNI  del cliente a consultar");
        Long dni = leer.nextLong();
        System.out.println(DAOCliente.consultaDni(dni));
    }

    public static String consultaDni(Long dni) {
        String consulta = DAOCliente.consultaDni(dni);
        return consulta;
    }

    public static Integer consultaDniDevuelveId(Long dni) {
        Integer consulta = DAOCliente.consultaDniDevuelveId(dni);
        return consulta;
    }

    public Cliente consultaId(Integer id) {
        Cliente miCliente = DAOCliente.consultaId(id);
        return miCliente;
    }
}