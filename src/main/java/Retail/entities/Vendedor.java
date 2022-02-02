package Retail.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "vendedor")
public class Vendedor extends Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @OneToMany(mappedBy = "vendedor", targetEntity = Orden.class, fetch = FetchType.LAZY)
    private List<Orden> ordenes;

    public Vendedor(){
        ordenes = new ArrayList<>();
    }

}
