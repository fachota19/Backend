package com.frc.backend.services;

import com.frc.backend.dao.DesignerDAO;
import com.frc.backend.modelo.Designer;
import java.util.List;

public class DesignerService {

    private final DesignerDAO designerDAO;

    public DesignerService() {
        this.designerDAO = new DesignerDAO();
    }

    public void crearDesigner(String nombre) {
        Designer d = new Designer(nombre);
        designerDAO.create(d);
    }

    public List<Designer> listarDesigners() {
        return designerDAO.findAll();
    }

    public Designer buscarPorId(int id) {
        return designerDAO.find(id);
    }

    public void cerrar() {
        designerDAO.close();
    }
}
