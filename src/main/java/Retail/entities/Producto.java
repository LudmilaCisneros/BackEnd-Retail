package Retail.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    @Column
    private String descripcion;
    @Column(name = "precio_unitario")
    private Double precioUnitario;
    @Column(name = "unidades_stock")
    private Integer unidadesStock;
    @Column(name = "unidades_reposicion")
    private Integer unidadesReposicion;
    @Column(name = "flag_discontinuo")
    private boolean flgDiscontinuo;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

}
