package com.frc.backend.dao;

import com.frc.backend.modelo.BoardGame;
import javax.persistence.TypedQuery;
import java.util.List;

public class BoardGameDAO extends GenericDAO<BoardGame> {

    public BoardGameDAO() {
        super(BoardGame.class);
    }

    public List<BoardGame> findByCategory(String categoryName) {
        String jpql = "SELECT b FROM BoardGame b WHERE b.category.name = :category";
        TypedQuery<BoardGame> query = em.createQuery(jpql, BoardGame.class);
        query.setParameter("category", categoryName);
        return query.getResultList();
    }

    public List<BoardGame> findByDesigner(String designerName) {
        String jpql = "SELECT b FROM BoardGame b WHERE b.designer.name = :designer";
        TypedQuery<BoardGame> query = em.createQuery(jpql, BoardGame.class);
        query.setParameter("designer", designerName);
        return query.getResultList();
    }

    public double averageRatingByCategory(String categoryName) {
        String jpql = "SELECT AVG(b.averageRating) FROM BoardGame b WHERE b.category.name = :category";
        TypedQuery<Double> query = em.createQuery(jpql, Double.class);
        query.setParameter("category", categoryName);
        Double result = query.getSingleResult();
        return result != null ? result : 0.0;
    }
}
