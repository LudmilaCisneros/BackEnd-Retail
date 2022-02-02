package Retail.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleOrdenDTO {
    private Integer id;
    private Integer cantidad;
    private Integer idOrden;
    private Integer idProducto;
    private String descripcion;
    private Double precioUnitario;
}
