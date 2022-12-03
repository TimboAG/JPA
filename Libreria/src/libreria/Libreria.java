package libreria;

import servicio.MenuServicio;

public class Libreria {

    public static void main(String[] args) {
        try {
            MenuServicio miMenu = new MenuServicio();
            miMenu.menu();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error : " + e.getMessage().getClass() + " programa finalizado");
        }
    }

}