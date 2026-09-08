package com.historial.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entit
@Table(name = "history_favorite")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistory;

    private Long itemFavorite;

    private String action;

    private LocalDateTime dateAction;

    public Evento() {
    }

    public Evento(Long itemFavorite, String action, LocalDateTime dateAction) {
        this.itemFavorite = itemFavorite;
        this.action = action;
        this.dateAction = dateAction;
    }

    public Long getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(Long idHistory) {
        this.idHistory = idHistory;
    }

    public Long getItemFavorite() {
        return itemFavorite;
    }

    public void setItemFavorite(Long itemFavorite) {
        this.itemFavorite = itemFavorite;
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