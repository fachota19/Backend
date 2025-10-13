package com.frc.backend.gamesapp.dao;

import com.frc.backend.gamesapp.modelo.Juego;
import jakarta.persistence.EntityManager;

public class JuegoDAO extends GenericDAO<Juego> {

    public JuegoDAO(EntityManager em) {
        super(em, Juego.class);
    }
}
