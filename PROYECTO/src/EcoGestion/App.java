package EcoGestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Clase principal de la aplicación EcoGestión
 * 
 * Esta clase sive como punto de entrada de la aplicación y contiene métodos auxiliares estáticos para gestionar usuarios,
 * actividades de reciclaje, materiales y puntos de reciclaje.
 * 
 * Proporciona acceso directo a los objetos DAO que se utilizan para realizar operaciones
 * con la base de datos.
 * 
 * @author ananu
 * @since 16/05
 * @version 1.0
 */
public class App {
	
	
    private static Scanner sc = new Scanner(System.in);
    
    /**
     * Acceso global a los DAO de Usuario, Punto de Reciclaje, Material y Actividad de Reciclaje
     */
    public static UsuarioDAO bdUsuario = new UsuarioDAO();
    public static PuntoReciclajeDAO bdPunto = new PuntoReciclajeDAO();
    public static MaterialDAO bdMaterial = new MaterialDAO();
    public static ActividadDAO bdActividad = new ActividadDAO();
    
    /**
     * Método principal (entry point) de la aplicación
     * @param args Argumentos pasados desde la línea de comandos
     */
    public static void main(String[] args) {
    	
        
            int opcion= -1;
            
            while (opcion != 0) {
            	System.out.println("---MENÚ GESTIÓN ---");
            	System.out.println("1. Registrar nueva actividad de reciclaje");
            	System.out.println("2. Ver ranking de recicladores");
            	System.out.println("3. Ver historial de actividades de un usuario");
            	System.out.println("4. Obtener bonificación para usuarios destacados");
            	System.out.println("5. Estadísticas por tipo de material");
            	System.out.println("6. Ver usuarios por barrio");
            	System.out.println("7. Registrar nuevo usuario");
            	System.out.println("8. Registrar nuevo punto de reciclaje");
            	System.out.println("9. Buscar usuario por correo");
            	System.out.println("10. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                    	
                    	registrarActividad();
                        break;

                    case 2:
                        
                    	verRankingRecicladores();
                        break;

                    case 3:
                       
                    	verHistorialActividades();
                        break;

                    case 4:
                      
                    	obtenerBonificacion();
                        break;

                    case 5:
                        
                    	verEstadisticasPorMaterial();
                        break;

                    case 6:
                       
                    	verUsuariosPorBarrio();
                        break;

                    case 7:
                    	
                    	registrarUsuario();
                    	break;
                    	
                    case 8:
                    	
                    	registrarPunto();
                    	break;
                    	
                    case 9:
                    	
                    	buscarUsuarioCorreo();
                    	break;
                    	
                    case 10: 
                    	
                        System.out.println("Saliendo del sistema. ¡Gracias por reciclar!");
                    	break;
                    	                

                    default:
                        System.out.println("Opción inválida. Intentelo de nuevo.");
                }
            }       
    }
    
    
    /**
     * Registra una nueva actividad de reciclaje en el sistema
     */
    public static void registrarActividad() {
    	
    	 System.out.println("Introduzca los datos de la nueva actividad: ");
    	    System.out.println("Introduzca el id del usuario: ");
    	    int idUsuario = sc.nextInt();
    	    sc.nextLine(); 
    	    Usuario usuario = new Usuario(idUsuario);

    	    System.out.print("Ingrese el id del punto de reciclaje: ");
    	    int idPunto = sc.nextInt();
    	    sc.nextLine();
    	    PuntoReciclaje punto = new PuntoReciclaje(idPunto);

    	    System.out.println("Introduzca el nombre del material: ");
    	    String nombre = sc.nextLine();

    	    System.out.println("Introduzca la descripcion del material: ");
    	    String desc = sc.nextLine();

    	    System.out.println("Ingrese el tipo de material: ");
    	    System.out.println("1.Vidrio");
    	    System.out.println("2.Plástico");
    	    System.out.println("3.Papel");
    	    System.out.println("4.Orgánico");
    	    int opcion = sc.nextInt();
    	    sc.nextLine();
    	    Material mat = null;

    	    switch(opcion) {
    	        case 1:
    	            System.out.println("Ingrese el grosor en mm: ");
    	            int grosor = sc.nextInt();
    	            sc.nextLine();
    	            mat = new Vidrio(nombre, desc, grosor);
    	            break;
    	        case 2:
    	            System.out.println("¿Es reciclable?. Seleccione: 1.SI o 2.NO");
    	            int op = sc.nextInt();
    	            sc.nextLine();
    	            boolean esRe = (op == 1);
    	            mat = new Plastico(nombre, desc, esRe);
    	            break;
    	        case 3:
    	            System.out.println("Introduzca el tipo: ");
    	            String tipo = sc.nextLine();
    	            mat = new Papel(nombre, desc, tipo);
    	            break;
    	        case 4:
    	            System.out.println("Introduzca el origen: ");
    	            String origen = sc.nextLine();
    	            mat = new Organico(nombre, desc, origen);
    	            break;
    	    }

    	    System.out.print("Ingrese la fecha de la actividad (formato: yyyy-mm-dd): ");
    	    LocalDate fecha = LocalDate.parse(sc.nextLine());

    	    System.out.print("Ingrese el peso en kg: ");
    	    double pesoKg = Double.parseDouble(sc.nextLine());

    	    ActividadReciclaje actividad = new ActividadReciclaje(usuario, punto, mat, fecha, pesoKg);
    	    bdActividad.registrarActividad(actividad);

    	    System.out.println("¡Actividad registrada exitosamente!");
        
    }
    
    /**
     * Ranking de recicladores que participan en EcoGestión
     */
    static void verRankingRecicladores() {
        Map<Integer, Double> ranking = bdActividad.obtenerRankingUsuarios();
        for (Map.Entry<Integer, Double> entry : ranking.entrySet()) {
            Usuario usuario = bdUsuario.buscarPorId(entry.getKey());
            System.out.println("Usuario: " + usuario.getNombre() + ", Total reciclado: " + entry.getValue() + " kg");
        }
    }
    
    /**
     * Historial de actividades de reciclaje que ha realizado el usuario
     */
    private static void verHistorialActividades() {
    	
        System.out.print("Ingrese el ID del usuario: ");
        int usuarioId = Integer.parseInt(sc.nextLine());
        
        List<ActividadReciclaje> actividades = bdActividad.buscarPorUsuario(usuarioId);
        
        for (ActividadReciclaje actividad : actividades) {
            System.out.println("Fecha: " + actividad.getFecha() + ", Material: " + actividad.getMaterial().getNombre() + ", Peso: " + actividad.getPesoKg() + " kg");
        }
    }
    
    /**
     * Agregar puntos de bonificación por ID de usuario 
     */
    private static void obtenerBonificacion() {
    	
    	System.out.print("Introduce el ID del usuario: ");
        int idUsuario = sc.nextInt();
        
        System.out.print("Introduce los puntos extra que se agregarán a la bonificación: ");
        int puntosExtra = sc.nextInt();
              
        Usuario usuario = bdUsuario.buscarPorId(idUsuario);
        
        if (usuario != null) {
            // Mostrar la bonificación actual
            System.out.println("La bonificación actual de " + usuario.getNombre() + " es: " + usuario.getPuntosBonificacion() + " puntos.");
            
            // Agregar los puntos extra a la bonificación
            usuario.agregarPuntosBonificacion(puntosExtra);
            
            // Mostrar la nueva bonificación
            System.out.println("Bonificación actualizada: " + usuario.getPuntosBonificacion() + " puntos.");
            
            // Actualizar la bonificación en la base de datos
            bdUsuario.actualizarPuntos(usuario);
            
            System.out.println("La bonificación de " + usuario.getNombre() + " ha sido actualizada en la base de datos.");
        } else {
            System.out.println("No se encontró un usuario con ID: " + idUsuario);
        }
                
    }
    
    /**
     * Estadística de cuánto se recicla de cada material
     */
    static void verEstadisticasPorMaterial() {
        List<Material> materiales = bdMaterial.obtenerTodos();
        
        for (Material material : materiales) {
            String nombreMaterial = material.getNombre();
            int idMaterial = material.getId();  
            
            double totalReciclado = bdMaterial.obtenerTotalRecicladoPorMaterial(idMaterial);
            
            System.out.println("Material: " + nombreMaterial + ", Total reciclado: " + totalReciclado + " kg");
        }
    }

    /**
     * Usuarios existentes que participan por barrio
     */
    private static void verUsuariosPorBarrio() {
        Map<String, Long> estadisticas = bdUsuario.obtenerEstadisticasPorBarrio();
        for (Map.Entry<String, Long> entry : estadisticas.entrySet()) {
            System.out.println("Barrio: " + entry.getKey() + ", Usuarios: " + entry.getValue());
        }
    }
    
    /**
     * Registra un nuevo usuario en la aplicación
     */
    public static void registrarUsuario() {
    	
    	System.out.println("Introduzca los datos del nuevo usuario: ");
    	System.out.println("Nombre: ");
    	String nom = sc.nextLine();
    	System.out.println("Email: ");
    	String em = sc.nextLine();
    	System.out.println("Barrio: ");
    	String ba = sc.nextLine();
    	System.out.println("Puntos de bonificación: ");
    	int puntos = sc.nextInt();
    	sc.nextLine();
    	Usuario usuario = new Usuario(nom,em,ba,puntos);
    	
    	bdUsuario.create(usuario);
    }
    
    /**
     * Registra un nuevo punto de reciclaje
     */
    public static void registrarPunto() {
    	
    	System.out.println("Introduzca los datos para el nuevo punto de reciclaje: ");
    	System.out.println("Dirección: ");
    	String direccion = sc.nextLine();
    	System.out.println("Barrio: ");
    	String barrio = sc.nextLine();
    	System.out.println("Horario: ");
    	String horario = sc.nextLine();
    	System.out.println("Capacidad Kg: ");
    	int capac = sc.nextInt();
    	sc.nextLine();
    	
    	PuntoReciclaje punto = new PuntoReciclaje(direccion, barrio, horario, capac);
    	bdPunto.insertarPunto(punto);
    	
    }
    
    /**
     * Busca a un usuario de la aplicación por su correo electrónico
     */
    public static void buscarUsuarioCorreo() {
    	
    	System.out.println("Introduzca el correo del usuario que desea buscar: ");
    	String email = sc.nextLine();
    	
    	System.out.println("El usuario que corresponde a ese email es: " + bdUsuario.buscarPorCorreo(email));
    }
}

