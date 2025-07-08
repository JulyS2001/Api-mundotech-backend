package com.backend.api_mundotech.controllers;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.backend.api_mundotech.models.Producto;
import com.backend.api_mundotech.repositories.CategoriaRepository;
import com.backend.api_mundotech.repositories.ProductoRepository;
import com.backend.api_mundotech.servicesImpl.ProductoServiceImpl;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProductoServiceImpl productoService; 

    // GET: Listar todos los productos
    @GetMapping
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    // POST: Crear nuevo producto
    @PostMapping()
    public ResponseEntity<?> crear(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") float precio,
            @RequestParam("stock") int stock,
            @RequestParam("categoriaId") int categoriaId,
            @RequestParam(name = "imagen", required = false) MultipartFile imagenFile) {

            // Guardar archivo imagen en servidor
            String nombreImagen = guardarImagen(imagenFile);

            // Buscar categoría
            var categoria = categoriaRepository.findById(categoriaId);
            if (categoria.isEmpty()) {
                return ResponseEntity.badRequest().body("Categoría no encontrada");
            }

            // Crear producto
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setImagen(nombreImagen);  // guardamos el nombre de la imagen
            producto.setCategoria(categoria.get());

            Producto productoGuardado = productoService.guardar(producto);

            return ResponseEntity.ok(productoGuardado);

    }

    // Método para guardar imagen y devolver nombre generado
    private String guardarImagen(MultipartFile archivo) {
        if (archivo.isEmpty()) return "default.jpg"; // Imagen por defecto

        try {
            String nombre = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
            Path ruta = Paths.get("src/main/resources/static/images/" + nombre); 
            Files.copy(archivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
            return nombre;
        } catch (IOException e) {
            e.printStackTrace();
            return "default.jpg";
        }
    }

    // GET: Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable int id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT: Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") float precio,
            @RequestParam("stock") int stock,
            @RequestParam("categoriaId") int categoriaId,
            @RequestParam(name = "imagen", required = false) MultipartFile imagenFile) {
    	
    	// Guardar archivo imagen en servidor
        String nombreImagen = guardarImagen(imagenFile);

        // Buscar categoría
        var categoria = categoriaRepository.findById(categoriaId);
        

     // Buscar producto existente
        Optional<Producto> productoOptional = productoService.traerPorId(id);
        if (productoOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Producto con ID " + id + " no encontrado");
        }

        Producto producto = productoOptional.get();
        
        // Actualizar campos
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setImagen(nombreImagen);
        producto.setCategoria(categoria.get());

        // Guardar cambios
        Producto productoActualizado = productoService.guardar(producto);

        return ResponseEntity.ok(productoActualizado);
    }

    // DELETE: Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}