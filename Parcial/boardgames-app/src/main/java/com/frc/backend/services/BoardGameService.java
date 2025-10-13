package com.frc.backend.services;

import com.frc.backend.dao.BoardGameDAO;
import com.frc.backend.modelo.BoardGame;
import java.util.List;

public class BoardGameService {

    private final BoardGameDAO boardGameDAO;

    public BoardGameService() {
        this.boardGameDAO = new BoardGameDAO();
    }

    // Crear nuevo juego
    public void crearJuego(BoardGame game) {
        boardGameDAO.create(game);
    }

    // Listar todos los juegos
    public List<BoardGame> listarJuegos() {
        return boardGameDAO.findAll();
    }

    // Buscar por ID
    public BoardGame buscarPorId(int id) {
        return boardGameDAO.find(id);
    }

    // Buscar juegos por categoría
    public List<BoardGame> buscarPorCategoria(String categoria) {
        return boardGameDAO.findByCategory(categoria);
    }

    // Buscar juegos por diseñador
    public List<BoardGame> buscarPorDesigner(String designer) {
        return boardGameDAO.findByDesigner(designer);
    }

    // Calcular promedio de rating por categoría
    public double promedioRatingPorCategoria(String categoria) {
        return boardGameDAO.averageRatingByCategory(categoria);
    }

    // Cerrar recursos
    public void cerrar() {
        boardGameDAO.close();
    }
}
