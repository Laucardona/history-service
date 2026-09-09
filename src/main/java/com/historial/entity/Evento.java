package com.historial.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "history_favorite")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistory;

    private Long idItemFavorite;

    private String action;

    private LocalDateTime dateAction;

    public Evento() {
    }

    public Evento(Long idItemFavorite, String action, LocalDateTime dateAction) {
        this.idItemFavorite = idItemFavorite;
        this.action = action;
        this.dateAction = dateAction;
    }

    public Long getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(Long idHistory) {
        this.idHistory = idHistory;
    }

    public Long getIdItemFavorite() {
        return idItemFavorite;
    }

    public void setIdItemFavorite(Long idItemFavorite) {
        this.idItemFavorite = idItemFavorite;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getDateAction() {
        return dateAction;
    }

    public void setDateAction(LocalDateTime dateAction) {
        this.dateAction = dateAction;
    }
}