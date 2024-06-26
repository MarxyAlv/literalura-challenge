package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteJson;
import org.antlr.v4.runtime.InputMismatchException;

import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteJson convierteJson = new ConvierteJson();
    private List<Libros> libros;
    private List<Autor>  autores;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public  Principal(AutorRepository autorRepository, LibroRepository libroRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu(){
        int opcion = -1;
        String menu = """
                \n ****** Bienvenido al buscador de libros Literalura *****
                1) Buscar libro por titulo
                2) Buscar libros registrados
                3) Lista autores registrados
                4) Lista de autores vivos por año
                5) Lista de libros por idioma
                0) Salir
                **********************************************
                """;

        while (opcion != 0){
            System.out.println(menu);
            try{
                opcion = teclado.nextInt();
                teclado.nextLine();
            }catch (InputMismatchException e) {
                System.out.println("Opcion invalida, por favor escoja un numero del 1 al 5.");
                teclado.nextLine();
                continue;
            }
            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listaLibrosBuscados();
                    break;
                case 3:
                    listaAutoresRegistrados();
                    break;
                case 4:
                    listaAutoresPorAno();
                    break;
                case 5:
                    listaLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo del programa.....");
                    break;
                default:
                    System.out.println("Opcion invalida. Por favor ingrese un numero del 0 al 5.\n");
            }
            //teclado.close();
        }
    }

    private void listaAutoresPorAno() {
        System.out.println("Ingrese el año del que desea buscar: ");
        try{
            Integer year = teclado.nextInt();
            autores = autorRepository.findAutoresByYear(year);
            if (autores.isEmpty()){
                System.out.println("No hay autores en ese año");
            }else {
                autores.stream().forEach(System.out::println);
            }
        }catch (InputMismatchException e){
            System.out.println("Ingrese un año valido");
            teclado.nextLine();
        }
    }

    private void listaLibrosPorIdioma() {
        String menuIdioma = """
                Ingrese el idioma que desea buscar:
                 es -> Español
                 en -> Ingles
                 fr -> Frances
                 pt -> Portugues
                """;
        //System.out.println("Opcion 5");
        System.out.println(menuIdioma);
        String idiomaBuscado = teclado.nextLine();
        CategoriaIdioma  idioma = null;
        //teclado.next();
        switch (idiomaBuscado){
            case "es":
                idioma = CategoriaIdioma.fromEspanol("Español");
                break;
            case "en":
                idioma = CategoriaIdioma.fromEspanol("Ingles");
                break;
            case "fr":
                idioma = CategoriaIdioma.fromEspanol("Frances");
                break;
            case "pt":
                idioma = CategoriaIdioma.fromEspanol("Portugues");
                break;
            default:
                System.out.println("Opcion no valida");
                return;
        }
        buscarPorIdioma(idioma);
    }

    private void buscarPorIdioma(CategoriaIdioma idioma) {
        libros = libroRepository.findLibrosByidioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
        } else {
            libros.stream().forEach(System.out::println);
        }
    }

    private void listaAutoresRegistrados(){
        autores = autorRepository.findAll();
        autores.stream().forEach(System.out::println);
    }

    private void listaLibrosBuscados(){
        libros = libroRepository.findAll();
        libros.stream().forEach(System.out::println);
    }

    private String realizarConsulta(){
        System.out.println("Escriba el nombre del libro que desea buscar: ");
        var nombreLibro = teclado.nextLine();
        String url = URL_BASE + "?search=" + nombreLibro.replace(" ", "%20");
        System.out.println("Esperando respuesta...");
        String respuesta = consumoAPI.obtenerDatosApi(url);
        return respuesta;
    }

    private void buscarLibroPorTitulo(){
        String respuesta = realizarConsulta();
        DatosAPI datosAPI = convierteJson.obtenerDatos(respuesta, DatosAPI.class);
        if (datosAPI.cantidadLibros() != 0){
            DatosLibro primerLibro = datosAPI.resultado().get(0);
            Autor autorLibro = new Autor(primerLibro.autor().get(0));
            Optional<Libros> libroBase = libroRepository.findLibroBytitulo(primerLibro.titulo());
            if (libroBase.isPresent()){
                System.out.println("No se puede registrar el mismo libro.");
            }else {
                Optional<Autor> autorBase = autorRepository.findByNombre(autorLibro.getNombre());
                if (autorBase.isPresent()){
                    autorLibro = autorBase.get();
                }else {
                    autorRepository.save(autorLibro);
                }
                Libros libro = new Libros(primerLibro);
                libro.setAutor(autorLibro);
                libroRepository.save(libro);
                System.out.println(libro);
            }
        }else{
            System.out.println("Libro no encontrado.");
        }
    }

}
