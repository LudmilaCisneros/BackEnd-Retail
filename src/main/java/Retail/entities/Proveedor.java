package Retail.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    @Column
    private String empresa;
    @Column
    private String contacto;
    @Column
    private String direccion;

    /*
    @ManyToMany(targetEntity =  Categoria.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "proveedor_categoria", joinColumns = {
            @JoinColumn(name = "proveedor_id")
    },inverseJoinColumns = {
            @JoinColumn(name = "categoria_id")
    })
    private List<Categoria> categorias;*/
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private  Categoria categoria;

    @OneToMany(targetEntity = Producto.class, fetch = FetchType.LAZY)
    private List<Producto> productos;

    public Proveedor(){
        productos = new ArrayList<>();
    }

}
