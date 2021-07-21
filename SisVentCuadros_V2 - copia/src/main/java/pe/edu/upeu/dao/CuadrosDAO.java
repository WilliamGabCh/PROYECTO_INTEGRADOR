
package pe.edu.upeu.dao;
import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.CuadrosTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;

/**
 * @author WilliamGabCh
 */
public class CuadrosDAO extends AppCrud{
    CuadrosTO cuTO;
    LeerArchivo lar;
    ModeloCuDAO mdc;
    LeerTeclado lte= new LeerTeclado();
    UtilsX ut = new UtilsX();
    
    public Object[][] crearCuadro(){
        cuTO = new CuadrosTO();
        lar = new LeerArchivo("Cuadros.txt");
        
        System.out.println("-------------------------Registro de Cuadros------------------------");
        cuTO.setIdCuadro(generarId(lar, 0, "C", 1));
        cuTO.setNombre(lte.leer("","Nombre del Cuadro"));
        mostrarModelos();
        cuTO.setIdModelo(lte.leer("","Elija el ID del Modelo"));
        cuTO.setPrecio(lte.leer(0.0,"Ingrese Precio"));
        cuTO.setAño(lte.leer(0,"Ingrese Año"));
        cuTO.setFotos(lte.leer(0,"Ingrese cantidad de fotos "));
        cuTO.setStock(lte.leer(0.0,"Ingrese Stock"));
        
        lar=new LeerArchivo("Cuadros.txt");
        return agregarContenido(lar, cuTO);
        
    }
    ////mostrar 3 campos, como hacer eso
    public void mostrarModelos(){
        
        LeerArchivo la = new LeerArchivo("Modelo.txt");
        Object[][]data=listarContenido(lar);
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i][0]+"="+data[i][1]+","+"="+data[i][2]+"; ");
        } 
    }
    //probar
    public Object[][] eliminarCuadro(String idDato){
        lar=new LeerArchivo("Cuadros.txt");
        return eliminarRegistros(lar, 0, idDato);     
    }
    //probar
    public Object[][] modificarCuadro(){
        lar=new LeerArchivo("Cuadros.txt");
        reporteCuadros();
        System.out.println("------------MODIFICAR CUADROS------------");
        String datoId=lte.leer("", "Ingrese el Id del Producto a Modificar:");
        cuTO =new CuadrosTO();
        cuTO.setNombre(lte.leer("","Nombre del Cuadro"));
        mostrarModelos();
        cuTO.setIdModelo(lte.leer("","Elija el ID del Modelo"));
        cuTO.setPrecio(lte.leer(0.0,"Ingrese Precio"));
        cuTO.setAño(lte.leer(0,"Ingrese Año"));
        cuTO.setFotos(lte.leer(0,"Ingrese cantidad de fotos "));
        cuTO.setStock(lte.leer(0.0,"Ingrese Stock"));   
        lar=new LeerArchivo("Cuadros.txt");
        
        return editarRegistro(lar, 0, datoId, cuTO);
    }
    
    public void reporteCuadros() {
        System.out.println("---------------------------------RREPORTE DE CUADROS--------------------------------");
        lar=new LeerArchivo("Cuadros.txt");
        Object[][] data=listarContenido(lar);
        String dataX="";
        ut.pintarLine('H',45);
        ut.pintarTextHeadBody('H', 3, "ID,Nombre,IDModelo,Precio,Año,Fotos,Stock"); 
        System.out.println("");
        ut.pintarLine('H',45);
       
       for (int i = 0; i < data.length; i++) {
           for (int j = 0; j < data[0].length; j++) {
               if(j==0){
                dataX+=" "+data[i][j];
               }else{
                dataX+=","+data[i][j]; 
               }               
           }
           ut.pintarTextHeadBody('B', 3, dataX);  
           dataX="";
       }
    }
       
       
    public void listarCuadros() {
        lar=new LeerArchivo("Cuadros.txt");
        Object[][] data=listarContenido(lar);
        for (int i = 0; i < data.length; i++) {
            if(Double.parseDouble(String.valueOf(data[i][6]))>0){
                System.out.println(data[i][0]+"="+data[i][1]+"\t"+"(Precio:"+data[i][4]+ 
                ",Stock:"+data[i][6]+"); ");
            }            
        } 
       
    }
    
    
}
