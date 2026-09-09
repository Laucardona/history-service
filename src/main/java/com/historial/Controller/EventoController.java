package com.historial.Controller;

import com.historial.dto.EventoDTO;
import com.historial.Service.EventoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<EventoDTO> listarTodo() {
        return eventoService.listarTodo();
    }

    @GetMapping("/producto/{idItemFavorite}")
    public List<EventoDTO> listarPorProducto(@PathVariable Long idItemFavorite) {
        return eventoService.listarPorProducto(idItemFavorite);
    }

    @PostMapping("/test")
    public String registrarPrueba(@RequestParam Long idItemFavorite, @RequestParam String action) {
        eventoService.registrarEvento(idItemFavorite, action);
        return "Evento registrado correctamente";
    }
}