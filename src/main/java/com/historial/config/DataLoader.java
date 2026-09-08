package com.historial.config;

import com.historial.entity.Producto;
import com.historial.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    public DataLoader(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) {
        if (productoRepository.count() == 0) {
            productoRepository.save(new Producto("Cuaderno A4", "Cuaderno cuadriculado 100 hojas", 5900.0, 25));
            productoRepository.save(new Producto("Resma de papel", "Resma carta 500 hojas", 18500.0, 0));
            productoRepository.save(new Producto("Marcador permanente", "Punta gruesa negra", 3200.0, 40));
            productoRepository.save(new Producto("Agenda 2026", "Agenda ejecutiva tapa dura", 24900.0, 8));
            productoRepository.save(new Producto("Carpeta plástica", "Carpeta oficio con broche", 4300.0, 0));
        }
    }
}