package com.alura.literalura.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private CategoriaIdioma idioma;
    private Integer descargas;

    public Libros(){}

    public Libros(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autor().get(0));
        this.descargas = datosLibro.numeroDescargas();
        this.idioma = CategoriaIdioma.fromString(datosLibro.idiomas().toString().split(",")[0].trim());
    }

    @Override
    public String toString(){
        return "****Libro***\n"+
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNombre()+ "\n" +
                "Idioma: " + idioma + "\n" +
                "Numero de descargas: " + descargas + "\n" +
                "**************";
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public CategoriaIdioma getIdioma() {
        return idioma;
    }

    public void setIdioma(CategoriaIdioma idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }
}
