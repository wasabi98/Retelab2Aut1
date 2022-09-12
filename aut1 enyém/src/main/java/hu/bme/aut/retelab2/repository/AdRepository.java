package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.domain.Note;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AdRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ad save(Ad advert) {
        return em.merge(advert);
    }

    public List<Ad> findAll() {
        return em.createQuery("SELECT n FROM Ad n", Ad.class).getResultList();
    }

    public Ad findById(long id) {
        return em.find(Ad.class, id);
    }

    public List<Ad> findByPriceRange(int min, int max)
    {
        return em.createQuery("SELECT n FROM Ad n WHERE n.price > ?1 AND n.price < ?2", Ad.class).setParameter(1, min).setParameter(2, max).getResultList();
    }
    public List<Ad> findByKeyword(String keyword) {
        return em.createQuery("SELECT n FROM Ad n WHERE n.title LIKE ?1", Ad.class).setParameter(1, '%' + keyword + '%').getResultList();
    }

    @Transactional
    public void deleteById(long id) {
        Ad todo = findById(id);
        em.remove(todo);
    }
}
