package com.historial.Controller;

import com.historial.entity.Producto;
import com.historial.Service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> obtenerCatalogo() {
        return productoService.listarCatalogo();
    }
}