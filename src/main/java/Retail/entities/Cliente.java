package Retail.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente extends Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    private String dni;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_cliente")
    private TipoDeCliente tipoDeCliente;

    @OneToMany(mappedBy = "cliente",targetEntity = Orden.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Orden> ordenes;

    public Cliente(){
        ordenes = new ArrayList<>();
    }
}
