package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAPI(
        @JsonAlias("count") Integer cantidadLibros,
        @JsonAlias("next") String proximaPag,
        @JsonAlias("previous") String anteriorPag,
        @JsonAlias("results") List<DatosLibro> resultado
) {
}
