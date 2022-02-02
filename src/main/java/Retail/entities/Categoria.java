package Retail.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    private String descripcion;

    @OneToMany(mappedBy = "categoria", targetEntity = Producto.class, fetch = FetchType.LAZY)
    private List<Producto> productos;

    @OneToMany(mappedBy = "categoria", targetEntity = Proveedor.class, fetch = FetchType.LAZY)
    private  List<Proveedor> proveedores;

    public Categoria(){
        productos = new ArrayList<>();
        proveedores = new ArrayList<>();
    }
}
