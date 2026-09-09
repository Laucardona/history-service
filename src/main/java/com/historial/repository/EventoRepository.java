package com.historial.repository;

import com.historial.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByIdItemFavoriteOrderByDateActionDesc(Long idItemFavorite);

    List<Evento> findAllByOrderByDateActionDesc();
}