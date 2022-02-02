package Retail.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrdenDTO {
    private Integer id;
    private Integer idCliente;
    private Integer idVendedor;
    List<DetalleOrdenDTO> detalles;

    public OrdenDTO (){
        detalles = new ArrayList<>();
    }

}
