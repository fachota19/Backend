package ar.edu.utn.frc.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "JUEGOS")
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "juego_seq")
    @SequenceGenerator(name = "juego_seq", sequenceName = "SEQ_JUEGOS", allocationSize = 1)
    @Column(name = "JUEGO_ID")
    private Integer id;

    @Column(name = "TITULO", nullable = false)
    private String titulo;

    // INTEGER en DDL: si mañana querés año (YYYY) esto te sirve
    @Column(name = "FECHA_LANZAMIENTO")
    private Integer fechaLanzamiento;

    // Enum -> VARCHAR(4) usando converter
    @Convert(converter = ClasificacionEsrbConverter.class)
    @Column(name = "CLASIFICACION_ESRB", length = 4)
    private ClasificacionEsrb clasificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENERO_ID", referencedColumnName = "GEN_ID")
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESARROLLADOR_ID", referencedColumnName = "DESA_ID")
    private Desarrollador desarrollador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLATAFORMA_ID", referencedColumnName = "PLAT_ID")
    private Plataforma plataforma;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "JUEGOS_FINALIZADOS")
    private Integer juegosFinalizados;

    @Column(name = "JUGANDO")
    private Integer jugando;

    @Lob
    @Column(name = "RESUMEN", nullable = false)
    private String resumen;

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getFechaLanzamiento() { return fechaLanzamiento; }
    public void setFechaLanzamiento(Integer fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }
    public ClasificacionEsrb getClasificacion() { return clasificacion; }
    public void setClasificacion(ClasificacionEsrb clasificacion) { this.clasificacion = clasificacion; }
    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }
    public Desarrollador getDesarrollador() { return desarrollador; }
    public void setDesarrollador(Desarrollador desarrollador) { this.desarrollador = desarrollador; }
    public Plataforma getPlataforma() { return plataforma; }
    public void setPlataforma(Plataforma plataforma) { this.plataforma = plataforma; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Integer getJuegosFinalizados() { return juegosFinalizados; }
    public void setJuegosFinalizados(Integer juegosFinalizados) { this.juegosFinalizados = juegosFinalizados; }
    public Integer getJugando() { return jugando; }
    public void setJugando(Integer jugando) { this.jugando = jugando; }
    public String getResumen() { return resumen; }
    public void setResumen(String resumen) { this.resumen = resumen; }
}