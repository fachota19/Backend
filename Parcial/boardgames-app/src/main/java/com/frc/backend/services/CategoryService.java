package com.frc.backend.services;

import com.frc.backend.dao.CategoryDAO;
import com.frc.backend.modelo.Category;
import java.util.List;

public class CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    public void crearCategoria(String nombre) {
        Category c = new Category(nombre);
        categoryDAO.create(c);
    }

    public List<Category> listarCategorias() {
        return categoryDAO.findAll();
    }

    public Category buscarPorId(int id) {
        return categoryDAO.find(id);
    }

    public void cerrar() {
        categoryDAO.close();
    }
}
