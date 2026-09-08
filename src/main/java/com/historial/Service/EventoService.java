package com.historial.Service;

import com.historial.dto.EventoDTO;
import com.historial.entity.Evento;
import com.historial.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public Evento registrarEvento(Long itemFavorite, String action) {
        Evento evento = new Evento(itemFavorite, action, LocalDateTime.now());
        return eventoRepository.save(evento);
    }

    public List<EventoDTO> listarTodo() {
        return eventoRepository.findAllByOrderByDateActionDesc()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<EventoDTO> listarPorProducto(Long itemFavorite) {
        return eventoRepository.findByItemFavoriteOrderByDateActionDesc(itemFavorite)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private EventoDTO convertirADTO(Evento evento) {
        return new EventoDTO(
                evento.getIdHistory(),
                evento.getItemFavorite(),
                evento.getAction(),
                evento.getDateAction()
        );
    }
}