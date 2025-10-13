package com.frc.backend.modelo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BOARD_GAMES")
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_boardgame")
    @SequenceGenerator(name = "seq_boardgame", sequenceName = "SEQ_BOARD_GAME_ID", allocationSize = 1)
    @Column(name = "ID_GAME")
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(name = "YEAR_PUBLISHED")
    private Integer yearPublished;

    @Column(name = "MIN_AGE")
    private Integer minAge;

    @Column(name = "AVERAGE_RATING", precision = 3, scale = 2)
    private BigDecimal averageRating;

    @Column(name = "USERS_RATING")
    private Integer usersRating;

    @Column(name = "MIN_PLAYERS")
    private Integer minPlayers;

    @Column(name = "MAX_PLAYERS")
    private Integer maxPlayers;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_DESIGNER")
    private Designer designer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PUBLISHER")
    private Publisher publisher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_CATEGORY")
    private Category category;

    public BoardGame() {}

    public BoardGame(String name, Integer yearPublished, Integer minAge, BigDecimal averageRating,
                     Integer usersRating, Integer minPlayers, Integer maxPlayers,
                     Designer designer, Publisher publisher, Category category) {
        this.name = name;
        this.yearPublished = yearPublished;
        this.minAge = minAge;
        this.averageRating = averageRating;
        this.usersRating = usersRating;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.designer = designer;
        this.publisher = publisher;
        this.category = category;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getYearPublished() { return yearPublished; }
    public void setYearPublished(Integer yearPublished) { this.yearPublished = yearPublished; }

    public Integer getMinAge() { return minAge; }
    public void setMinAge(Integer minAge) { this.minAge = minAge; }

    public BigDecimal getAverageRating() { return averageRating; }
    public void setAverageRating(BigDecimal averageRating) { this.averageRating = averageRating; }

    public Integer getUsersRating() { return usersRating; }
    public void setUsersRating(Integer usersRating) { this.usersRating = usersRating; }

    public Integer getMinPlayers() { return minPlayers; }
    public void setMinPlayers(Integer minPlayers) { this.minPlayers = minPlayers; }

    public Integer getMaxPlayers() { return maxPlayers; }
    public void setMaxPlayers(Integer maxPlayers) { this.maxPlayers = maxPlayers; }

    public Designer getDesigner() { return designer; }
    public void setDesigner(Designer designer) { this.designer = designer; }

    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public String toString() {
        return String.format(
            "%s (%d) - %s / %s / %s [%.2f⭐ %d votos]",
            name,
            yearPublished,
            designer != null ? designer.getName() : "-",
            publisher != null ? publisher.getName() : "-",
            category != null ? category.getName() : "-",
            averageRating != null ? averageRating : 0.0,
            usersRating != null ? usersRating : 0
        );
    }
}
