package com.frc.backend.services;

import com.frc.backend.dao.PublisherDAO;
import com.frc.backend.modelo.Publisher;
import java.util.List;

public class PublisherService {

    private final PublisherDAO publisherDAO;

    public PublisherService() {
        this.publisherDAO = new PublisherDAO();
    }

    public void crearPublisher(String nombre) {
        Publisher p = new Publisher(nombre);
        publisherDAO.create(p);
    }

    public List<Publisher> listarPublishers() {
        return publisherDAO.findAll();
    }

    public Publisher buscarPorId(int id) {
        return publisherDAO.find(id);
    }

    public void cerrar() {
        publisherDAO.close();
    }
}
