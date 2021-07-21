package pe.edu.upeu.dao;
import java.text.SimpleDateFormat;
import java.util.Date;
import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.CuadrosTO;
import pe.edu.upeu.modelo.DetalleVeTO;
import pe.edu.upeu.modelo.VentaTO;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;


/**
 * @author WilliamGabCh
 */
public class VentaDAO extends AppCrud{
   
    CuadrosTO cuTO;
    LeerArchivo lar;
    VentaTO veTO;
    DetalleVeTO dvTO;
    
    LeerTeclado lte= new LeerTeclado();
    UtilsX ut = new UtilsX();
    
    SimpleDateFormat formato=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    SimpleDateFormat formatoFecha=new SimpleDateFormat("dd-MM-yyyy"); 
    
    public void registroVentaGeneral(){
        String venta="SI";
        VentaTO vent=crearVenta();
        dvTO = new DetalleVeTO();
        double preciototal=0;
        do{
            carritoVentas(vent);
            preciototal=preciototal+dvTO.getPrecio_Total();
            venta=lte.leer("","Algo más (Si=S / No=N)").toUpperCase();
        }while(venta.charAt(0)=='S');
        vent.setPrecioTotal(preciototal);
        vent.setPrecioNeto(vent.getPrecioTotal()/1.18);
        vent.setPrecioIGV(vent.getPrecioNeto()*0.18);
        
        lar=new LeerArchivo("Venta.txt");
        ut.pintarLine('H', 20);
        System.out.println("Precio Neto S/."+vent.getPrecioNeto()+
                " IGV S/."+vent.getPrecioIGV()+" Total S/."+vent.getPrecioTotal());
        ut.pintarLine('H', 20);
        editarRegistro(lar, 0, vent.getIdVenta(), vent);
    }
    
    public VentaTO crearVenta(){
        System.out.println("-----------REGISTRO DE VENTA-----------");
        veTO=new VentaTO();
        lar=new LeerArchivo("Venta.txt");
        veTO.setIdVenta(generarId(lar, 0, "V", 1));
        veTO.setDniCliente(lte.leer("","Ingrese DNI de Cliente"));
        veTO.setFecha(formato.format(new Date()));
        veTO.setPrecioIGV(0.0);
        veTO.setPrecioNeto(0.0);
        veTO.setPrecioTotal(0.0);
        lar=new LeerArchivo("Venta.txt"); 
        agregarContenido(lar, veTO);
        return veTO;
    }

    public DetalleVeTO carritoVentas(VentaTO vTO) {
        ut.clearConsole();
        mostrarCuadros();
        dvTO=new DetalleVeTO();
        lar=new LeerArchivo("DetalleVenta.txt");
        dvTO.setIdDetVent(generarId(lar, 0, "DV", 2));
        dvTO.setIdVenta(vTO.getIdVenta());
        dvTO.setIdCuadro(lte.leer("", "Ingrese ID del Cuadro"));
        dvTO.setCantidad(lte.leer(0, "Ingrese cantidad"));
        LeerArchivo lear= new LeerArchivo("Cuadros.txt");
        Object[][] dataCuadro= buscarContenido(lear, 0, dvTO.getIdCuadro());
        double pre_uni=Double.parseDouble(String.valueOf(dataCuadro[0][3]));
        dvTO.setPrecio_Uni(pre_uni);
        dvTO.setPrecio_Total(dvTO.getCantidad()*dvTO.getPrecio_Uni());
        lar=new LeerArchivo("DetalleVenta.txt");
        agregarContenido(lar, dvTO);
        disminuirCuadro(dvTO.getIdCuadro());
        ut.clearConsole();
        
        return null;
    }
    
    
    public void disminirCuadro(String idCuadro){
//        dvTO=new DetalleVeTO();
//        lar=new LeerArchivo("Cuadros.txt");
//        Object[][] dataCu= buscarContenido(lar, 0, idCuadro);
//        double cantidadCu=Double.parseDouble(String.valueOf(dataCu[0][6]));
//        double cantidadVe = dvTO.getCantidad();
//        double resto= cantidadCu-cantidadVe; 
//        String datoId=dvTO.getIdCuadro();
//        cuTO =new CuadrosTO();
//        cuTO.setNombre(String.valueOf(dataCu[0][1]));
//        cuTO.setIdModelo(String.valueOf(dataCu[0][2]));
//        cuTO.setPrecio(Double.parseDouble(String.valueOf(dataCu[0][3])));
//        cuTO.setAño(Integer.parseInt(String.valueOf(dataCu[0][4])));
//        cuTO.setFotos(Integer.parseInt(String.valueOf(dataCu[0][5])));
//        cuTO.setStock(resto);   
//        lar=new LeerArchivo("Cuadros.txt");
//        editarRegistro(lar, 0, datoId, cuTO);
    }
    
        public void disminuirCuadro(String idCuadro){
        
        lar=new LeerArchivo("Cuadros.txt");
        Object[][] dataCu= buscarContenido(lar, 0, idCuadro);
        double cantidadCu=Double.parseDouble(String.valueOf(dataCu[0][6]));
        double cantidadVe = dvTO.getCantidad();
        double resto= cantidadCu-cantidadVe; 
        String datoId=dvTO.getIdCuadro();
        cuTO =new CuadrosTO();
        cuTO.setNombre(String.valueOf(dataCu[0][1]));
        cuTO.setIdModelo(String.valueOf(dataCu[0][2]));
        cuTO.setPrecio(Double.parseDouble(String.valueOf(dataCu[0][3])));
        cuTO.setAño(Integer.parseInt(String.valueOf(dataCu[0][4])));
        cuTO.setFotos(Integer.parseInt(String.valueOf(dataCu[0][5])));
        cuTO.setStock(resto);   
        lar=new LeerArchivo("Cuadros.txt");
        editarRegistro(lar, 0, datoId, cuTO);
    }
     
    public void mostrarCuadros(){
        ut.clearConsole();
        System.out.println("Agregar Cuadros a la venta");
        lar=new LeerArchivo("Cuadros.txt");
        Object[][]data=listarContenido(lar);
        for (int i = 0; i < data.length; i++) {
            if(Double.parseDouble(String.valueOf(data[i][6]))>0){
               System.out.println(data[i][0]+"="+"\t"+data[i][1]+"(Precio: "+
                    data[i][3]+" Stock: "+data[i][6]+"); "); 
            }
        }
    }
    
    public void reporteVentasRangoFecha() {
        System.out.println("------------------REPORTE DE VENTAS POR RANGO DE FECHA------------------");
        String fechaIni=lte.leer("", "Ingrese Fecha de Inicio (dd-MM-yyyy): ");
        String fechaFin=lte.leer("", "Ingrese Fecha Final (dd-MM-yyyy): ");
        lar=new LeerArchivo("Venta.txt");
        Object[][] dataV=listarContenido(lar);
        int contarVentasRF=0;
        try {
            for (int i = 0; i < dataV.length; i++) {
                String[] fechaVenta=String.valueOf(dataV[i][2]).split(" ");
                //System.out.println("Datos Fecha:"+fechaVenta[0]+"  Fecha Teclado:"+formatoFecha.format(formatoFecha.parse(fechaIni)));
                Date fechaVentaX=formatoFecha.parse(fechaVenta[0]);
                if  ( 
                    (fechaVentaX.after(formatoFecha.parse(fechaIni)) || fechaVenta[0].equals(fechaIni)) && 
                    (fechaVentaX.before(formatoFecha.parse(fechaFin)) || fechaVenta[0].equals(fechaFin))
                    ) {
                    contarVentasRF=contarVentasRF+1;
                }
            }
            
            Object[][] dataVRF=new Object[contarVentasRF][dataV[0].length];
            int filaIndice=0;
            double netoTotalX=0, igvX=0, precioTotalX=0;
            for (int fila = 0; fila < dataV.length; fila++) {
                String[] fechaVenta=String.valueOf(dataV[fila][2]).split(" ");
                Date fechaVentaX=formatoFecha.parse(fechaVenta[0]);
                if ( 
                    (fechaVentaX.after(formatoFecha.parse(fechaIni)) || fechaVenta[0].equals(fechaIni)) && 
                    (fechaVentaX.before(formatoFecha.parse(fechaFin)) || fechaVenta[0].equals(fechaFin))
                ) {
                    for (int j = 0; j < dataV[0].length; j++) {
                        dataVRF[filaIndice][j]=dataV[fila][j];
                        if(j==3){netoTotalX+=Double.parseDouble(String.valueOf(dataV[fila][j]));}
                        if(j==4){igvX+=Double.parseDouble(String.valueOf(dataV[fila][j]));}
                        if(j==5){precioTotalX+=Double.parseDouble(String.valueOf(dataV[fila][j]));}                        
                    }
                    filaIndice++;
                }
            }
            ut.clearConsole();
            System.out.println("------------------------REPORTE DE VENTAS-------------------");
            System.out.println("----------------Entre "+fechaIni+" a "+fechaFin +"-----------------");
            ut.pintarLine('H', 45);
            ut.pintarTextHeadBody('H', 3, "ID, DNI Cliente, Fecha, Precio_IGV,Precio_Neto,Total");
            System.out.println("");
            ut.pintarLine('H', 45);
            for (Object[] objects:dataVRF) {
                
                String dataCadena=""+objects[0]+", "+objects[1]+", "+objects[2]+
                        ","+ Math.round((Double.parseDouble(String.valueOf(objects[3]))*100.0)/100.0)+
                        ","+ Math.round(Double.parseDouble(String.valueOf(objects[4])))  +","+objects[5];
                ut.pintarTextHeadBody('B', 3, dataCadena);
            }
            System.out.println("");
            ut.pintarLine('H', 45);

            //Ansi colorX=new Ansi();
            System.out.println(" Total Neto: S/."+(Math.round(netoTotalX*100.0)/100.0)+
                    "|  Total IGV: S/."+ (Math.round(igvX*100.0)/100.0)+
                    "|  Monto Recaudado: S/."+(Math.round(precioTotalX*100.0)/100.0));
            /*Ansi colorX=new Ansi();
            System.out.println(colorX.render("@|red Total Neto: S/.|@ @|green "+(Math.round(netoTotalX*100.0)/100.0)+"|@ | @|red "+
            "Total IGV :S/. |@ @|green "+ (Math.round(igvX*100.0)/100.0)+"|@ | @|red "+"Monto Recaudado: S/.|@ @|green "+
            (Math.round(precioTotalX*100.0)/100.0)+"|@"));*/
            ut.pintarLine('H', 45); 
            
//            Math.round(Double.parseDouble(String.valueOf(objects[4])));
//            Double.parseDouble(String.valueOf(dataCuadro[0][3]));
            
        } catch (Exception e) {
        }
        
    }
}
