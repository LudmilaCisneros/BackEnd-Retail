package Retail.dtos;

import Retail.entities.Producto;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

@Getter
@Setter
public class ProductoDTO {
    private Integer id;
    private String descripcion;
    private Float precioUnitario;
    private Integer unidadesStock;
    private Integer unidadesReposicion;
    private Boolean flgDiscontinuo;
    private Integer idCategoria;
    private Integer idProveedor;

    public ProductoDTO() {
    }
    public ProductoDTO(Producto p) {
        try {
            BeanUtils.copyProperties(this, p);
            this.idCategoria = p.getCategoria().getId();
            this.idProveedor = p.getProveedor().getId();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
