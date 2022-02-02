package Retail.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orden")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @OneToMany(mappedBy = "orden",targetEntity = DetalleOrden.class ,fetch = FetchType.LAZY)
    private List<DetalleOrden> detallesOrden;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    public Orden(){
        detallesOrden = new ArrayList<>();
    }

}
