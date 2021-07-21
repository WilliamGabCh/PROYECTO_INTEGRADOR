
package pe.edu.upeu.modelo;
import lombok.Data;

/**
 * @author WilliamGabCh
 */
@Data
public class DetalleVeTO {
    public String idDetVent;
    public String idVenta;
    public String idCuadro;
    public double cantidad;
    public double precio_Uni;
    public double precio_Total;
}
