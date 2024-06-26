package com.alura.literalura.model;

public enum CategoriaIdioma {
    ESPAMOL("[es]", "Español"),
    INGLES("[en]", "Ingles"),
    FRANCES("[fr]", "Frances"),
    PORTUGUES("[pt]", "Portugues");

    private String categoriaGutendex;
    private  String categoriaEspanol;

    CategoriaIdioma(String categoriaGutendex, String categoriaEspanol) {
        this.categoriaEspanol = categoriaEspanol;
        this.categoriaGutendex = categoriaGutendex;
    }

    public static CategoriaIdioma fromString(String texto) {
        for (CategoriaIdioma categoriaIdioma : CategoriaIdioma.values()) {
            if (categoriaIdioma.categoriaGutendex.equalsIgnoreCase(texto)) {
                return categoriaIdioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + texto);
    }

    public static CategoriaIdioma fromEspanol(String texto){
        for (CategoriaIdioma categoriaIdioma : CategoriaIdioma.values()){
            if (categoriaIdioma.categoriaEspanol.equalsIgnoreCase(texto)){
                return categoriaIdioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + texto);
    }
}
