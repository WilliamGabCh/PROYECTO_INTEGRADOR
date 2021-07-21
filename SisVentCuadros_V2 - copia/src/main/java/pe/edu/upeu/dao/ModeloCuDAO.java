package pe.edu.upeu.dao;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.ModeloCuTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;
/**
 * @author WilliamGabCh
 */
public class ModeloCuDAO extends AppCrud{
    //variables globales
    LeerArchivo lar;
    ModeloCuTO mdpTO;
    //objetos globales por que esta instanciandose
    LeerTeclado lte= new LeerTeclado();
    UtilsX ut = new UtilsX();
    
    public Object[][] crearModelo(){
        mdpTO = new ModeloCuTO();
        lar = new LeerArchivo("Modelo.txt");
        
        System.out.println("------------Registro de Modelo de Cuadros------------");
        mdpTO.setIdModelo(generarId(lar, 0, "MC", 2));
        mdpTO.setNombre(lte.leer("","Nombre del Modelo"));
        mdpTO.setTamaño(lte.leer("","Tamaño del Modelo"));
       
        return agregarContenido(lar, mdpTO);
    }
    
    public void reporteModelos(Object[][] data){
        System.out.println("-----------Reporte de Modelos de producto-----------");
        //lar = new LeerArchivo("Modelo.txt");
//        listarContenido(lar);
        imprimirLista(data);
    }
    public void reporteModelos(){
        System.out.println("----Reporte de Modelos de producto----");
        lar = new LeerArchivo("Modelo.txt");
        Object[][]data= listarContenido(lar);
        String datax="";
        ut.pintarLine('H', 19 );
        ut.pintarTextHeadBody('H', 3, "ID, Nombre, Tamaño");
        System.out.println("");
        ut.pintarLine('H', 19 );
        
        //falta entender
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if(j==0){
                    datax+=" "+data[i][j]; 
                }else{
                    datax+=","+data[i][j];
                } 
            }
            ut.pintarTextHeadBody('B', 3, datax);
            datax="";
        }  
    }
}
