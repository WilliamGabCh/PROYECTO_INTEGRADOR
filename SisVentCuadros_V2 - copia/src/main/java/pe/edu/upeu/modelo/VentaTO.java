
package pe.edu.upeu.modelo;
import lombok.Data;

/**
 * @author WilliamGabCh
 */
@Data
public class VentaTO {
    public String idVenta;
    public String dniCliente;
    public String fecha;
    public double precioIGV;
    public double precioNeto;
    public double precioTotal;
}
