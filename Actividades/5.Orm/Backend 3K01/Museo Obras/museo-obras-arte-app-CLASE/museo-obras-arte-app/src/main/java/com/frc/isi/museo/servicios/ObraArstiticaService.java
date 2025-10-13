package com.frc.isi.museo.servicios;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.frc.isi.museo.entidades.ObraArtistica;
import com.frc.isi.museo.entidades.modelos.TotalesAsegurados;
import com.frc.isi.museo.repositorios.ObraArtisticaRepository;

public class ObraArstiticaService {
    private final ObraArtisticaRepository obraArtisticaRepository;
    private final AutorService autorService;

    public ObraArstiticaService() {
        obraArtisticaRepository = new ObraArtisticaRepository();
        autorService = new AutorService();
    }

    public void bulkInsert(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
        .skip(1)
        .forEach(linea -> {
            ObraArtistica obra = procesarLinea(linea);
            if (!this.obraArtisticaRepository.existeByNombreOrDescripcion(obra.getNombre()))
                this.obraArtisticaRepository.add(obra);
        });
    }

    public List<TotalesAsegurados> informarTotalesAsegurados() {
        var obras = this.obraArtisticaRepository.getAllStrem();

        var mapeo = obras
                .collect(Collectors.groupingBy(ObraArtistica::isSeguroTotal,
                        Collectors.summingDouble(ObraArtistica::getMontoAsegurado)));

        var resultado = mapeo.entrySet()
                .stream()
                .map(p -> {
                    var descripcion = "Totales No Seguro Total";
                    if (p.getKey())
                        descripcion = "Totales Seguro Total";
                    return new TotalesAsegurados(descripcion, p.getValue());
                }).collect(Collectors.toList());

        var totalGeneral = resultado.stream()
                .collect(Collectors.summingDouble(TotalesAsegurados::total));

        resultado.add(new TotalesAsegurados("Total General", totalGeneral));

        return resultado;
    }

    private ObraArtistica procesarLinea(String linea) {
        String[] tokens = linea.split(",");
        ObraArtistica obra = new ObraArtistica();

        String nombre = tokens[2];
        var autor = autorService.getOrCreateAutor(nombre);
        obra.setAutor(autor);

        nombre = tokens[3];//Implementar para museo

        nombre = tokens[4];//Implementar para estilo artistico

        obra.setNombre(tokens[0]);
        obra.setAnio(tokens[1]);
        obra.setMontoAsegurado(Double.parseDouble(tokens[5]));
        obra.setSeguroTotal(tokens[6].equalsIgnoreCase("1"));

        return obra;
    }
}
