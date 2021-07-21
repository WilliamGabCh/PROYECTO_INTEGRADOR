package pe.edu.upeu.app;
import java.io.Console;
import pe.edu.upeu.dao.CuadrosDAO;
import pe.edu.upeu.dao.ModeloCuDAO;
import pe.edu.upeu.dao.UsuarioDAO;
import pe.edu.upeu.dao.VentaDAO;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;
import sun.security.util.Password;

/**
 * @author WilliamGabCh
 */
public class App {

    public static void menuMain(){
        String mensaje="       ESGOJA ALGUNA FUNCION"+
                "\n-----------------------------------"+
                "\n1    = Registrar Modelo de cuadros"+
                "\n12   = Reportar Modelos de cuadros "+
                "\n2    = Registrar Cuadros"+
                "\n21   = Reportar Cuadros"+
                "\n22   = Modificar Cuadros"+
                "\n23   = Eliminar Cuadros"+
                "\n3    = Realizar Venta"+
                "\n31   = Reporte de Venta en Rango de Fechas"+
                "\n4    = Registrar Usuario"+
                "\n0    = Salir del programa";
        LeerTeclado lt=new LeerTeclado(); 
        UtilsX ut=new UtilsX();
        ModeloCuDAO mdcDao;      
        UsuarioDAO usuDao;
        CuadrosDAO cuaDao;
        VentaDAO veDao;
////        VentaDAO venDao;
        int opcion=0;
        opcion=lt.leer(0, mensaje);
        do{ 
            switch(opcion){
                case 1:                
                    mdcDao=new ModeloCuDAO(); 
                    mdcDao.crearModelo(); 
                    ut.clearConsole(); break;
                case 12: 
                    ut.clearConsole();
                    mdcDao=new ModeloCuDAO(); 
                    mdcDao.reporteModelos();break;    
                case 2: 
                    ut.clearConsole();
                    cuaDao=new CuadrosDAO();
                    cuaDao.crearCuadro();break;
                case 21: 
                    ut.clearConsole();
                    cuaDao=new CuadrosDAO();
                    cuaDao.reporteCuadros();break;
                case 22: 
                    ut.clearConsole();
                    cuaDao=new CuadrosDAO();
                    cuaDao.modificarCuadro(); break;
                case 23: 
                    ut.clearConsole();
                    cuaDao=new CuadrosDAO();
                    cuaDao.listarCuadros();
                    cuaDao.eliminarCuadro(lt.leer("","Ingrese el Id del Producto a Eliminar"));
                    cuaDao.listarCuadros();break;
                case 3:
                    ut.clearConsole();
                    veDao=new VentaDAO();
                    veDao.registroVentaGeneral();
                    break;
                case 31: 
                    ut.clearConsole();
                    veDao=new VentaDAO();
                    veDao.reporteVentasRangoFecha();break;
                case 4: 
                    ut.clearConsole();
                    usuDao = new UsuarioDAO(); 
                    usuDao.crearNuevoUsuario();break;
                default: System.out.println("La opcion que eligio no exuiste!");break;
            }
            if(opcion!=0){
                if(lt.leer("", "\nDesea seguir probando SI=S/NO=N:").toUpperCase().charAt(0)=='S'){
                    ut.clearConsole();
                    opcion=lt.leer(0, mensaje);
                    
                }else{
                    opcion=0;
                }                
            }
        }while(opcion!=0);        
    }
    
    public static void validarAcceso() {
        UtilsX ut=new UtilsX();
        LeerTeclado lt=new LeerTeclado(); 
        Console constx= System.console();
        ut.clearConsole();
        String usuario=lt.leer("", "Ingrese su usuario:");
        System.out.println("Ingrese su clave:");
        char[] clave=constx.readPassword();
        UsuarioDAO usuDao=new UsuarioDAO();
        if(usuDao.login(usuario, clave)){
            ut.clearConsole();
            System.out.println("BIENVENIDO "+usuario);
            System.out.println("");
            menuMain(); 
        }else{
            ut.clearConsole();
            System.out.println("Intente Nuevamente!!");
            validarAcceso();
        }
    }
    
    public static void main(String[] args) {
        validarAcceso();
        //menuMain();
    }

}
