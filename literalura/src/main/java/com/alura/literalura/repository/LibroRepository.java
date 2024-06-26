package com.alura.literalura.repository;

import com.alura.literalura.model.CategoriaIdioma;
import com.alura.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository  extends JpaRepository<Libros, Long> {
    Optional<Libros> findLibroBytitulo(String titulo);
    List<Libros> findLibrosByidioma(CategoriaIdioma idioma);

}
