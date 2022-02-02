package Retail.repositories;

import Retail.entities.Orden;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Getter
@Setter
@Repository
public class OrdenCriteria {
    @PersistenceContext
    private EntityManager entityManager;

    public Orden buscar(int id) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orden> cq = queryBuilder.createQuery(Orden.class);
        Root<Orden> root = cq.from(Orden.class);
        cq.select(root).where(queryBuilder.equal(root.get("id"),id));
        TypedQuery<Orden> query = this.entityManager.createQuery(cq);
        entityManager.close();

        return query.getSingleResult();
    }

    public List<Orden> buscarAll() {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orden> cq = queryBuilder.createQuery(Orden.class);
        Root<Orden> root = cq.from(Orden.class);
        cq.select(root);
        TypedQuery<Orden> query = this.entityManager.createQuery(cq);
        entityManager.close();

        return query.getResultList();
    }

    public List<Orden> buscarPorCliente(Integer idCliente) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orden> cq = queryBuilder.createQuery(Orden.class);
        Root<Orden> root = cq.from(Orden.class);
        cq.select(root).where(queryBuilder.equal(root.get("cliente").get("id"),idCliente));
        TypedQuery<Orden> query = this.entityManager.createQuery(cq);
        entityManager.close();

        return query.getResultList();
    }

    public void agregar(Orden orden) {
        /*CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orden> cq = queryBuilder.//queryBuilder.createQuery(Orden.class);
        Root<Orden> root = cq.from(Orden.class);
        cq.select(root);
        TypedQuery<Orden> query = this.entityManager.createQuery(cq);
        entityManager.close();*/

    }

    @Transactional
    public void eliminar(Integer id)  {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Orden> criteriaDelete = criteriaBuilder.createCriteriaDelete(Orden.class);
        Root<Orden> root = criteriaDelete.from(Orden.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("id"),id));
        entityManager.createQuery(criteriaDelete).executeUpdate();

        entityManager.close();
    }

}
