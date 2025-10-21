package run;

import services.dao.MyDao;
import services.interfaces.ICRUD;
import entities.Autor;
import entities.Categoria;
import entities.Libro;
import java.util.List;

public class Main {

    public static final ICRUD dao = new MyDao();

    // --------- CRUD Autor (ya existente) ---------
    public static void insertarAutor() {
        Autor a = new Autor();
        a.setNombre("Gabriel García Marquez");
        a.setNacionalidad("Mexicana");
        dao.insert(a);

        Autor r = new Autor();
        r.setNombre("Ruben Dario");
        r.setNacionalidad("Nicaraguense");
        dao.insert(r);
    }

    public static void listarAutores() {
        System.out.println("Registros Autores:");
        List<Autor> autores = dao.getAll("Autor.findAll", Autor.class);
        autores.forEach(autor -> System.out.println("- " + autor.getId() + ": " + autor.getNombre() + " (" + autor.getNacionalidad() + ")"));
    }

    public static void editarAutor() {
        Autor a = dao.findById(1L, Autor.class);
        if (a != null) {
            a.setNacionalidad("Colombiana");
            dao.update(a);
        } else {
            System.out.println("Autor con id 1 no encontrado para editar.");
        }
    }

    public static void eliminarAutor() {
        Autor a = dao.findById(2L, Autor.class);
        if (a != null) {
            dao.delete(a);
        } else {
            System.out.println("Autor con id 2 no encontrado para eliminar.");
        }
    }

    // --------- CRUD Categoria ---------
    public static void insertarCategorias() {
        Categoria c1 = new Categoria();
        c1.setNombre("Novela");
        dao.insert(c1);

        Categoria c2 = new Categoria();
        c2.setNombre("Poesía");
        dao.insert(c2);
    }

    public static void listarCategorias() {
        System.out.println("Registros Categorías:");
        List<Categoria> categorias = dao.getAll("Categoria.findAll", Categoria.class);
        categorias.forEach(cat -> System.out.println("- " + cat.getId() + ": " + cat.getNombre()));
    }

    public static void editarCategoria() {
        Categoria cat = dao.findById(1L, Categoria.class);
        if (cat != null) {
            cat.setNombre("Novela Histórica");
            dao.update(cat);
        } else {
            System.out.println("Categoría con id 1 no encontrada para editar.");
        }
    }

    public static void eliminarCategoria() {
        Categoria cat = dao.findById(2L, Categoria.class);
        if (cat != null) {
            dao.delete(cat);
        } else {
            System.out.println("Categoría con id 2 no encontrada para eliminar.");
        }
    }

    // --------- Helpers para Libro ---------
    private static Autor ensureAnyAutor() {
        List<Autor> autores = dao.getAll("Autor.findAll", Autor.class);
        if (!autores.isEmpty()) return autores.get(0);
        Autor a = new Autor();
        a.setNombre("Autor Desconocido");
        a.setNacionalidad("N/A");
        dao.insert(a);
        return a;
    }

    private static Categoria ensureAnyCategoria() {
        List<Categoria> categorias = dao.getAll("Categoria.findAll", Categoria.class);
        if (!categorias.isEmpty()) return categorias.get(0);
        Categoria c = new Categoria();
        c.setNombre("General");
        dao.insert(c);
        return c;
    }

    // --------- CRUD Libro ---------
    public static void insertarLibros() {
        Autor autor = ensureAnyAutor();
        Categoria categoria = ensureAnyCategoria();

        Libro l1 = new Libro();
        l1.setTitulo("Cien años de soledad");
        l1.setAnioPub(1967);
        l1.setAutor(autor);
        l1.setCategoria(categoria);
        dao.insert(l1);

        Libro l2 = new Libro();
        l2.setTitulo("Azul");
        l2.setAnioPub(1888);
        l2.setAutor(autor);
        l2.setCategoria(categoria);
        dao.insert(l2);
    }

    public static void listarLibros() {
        System.out.println("Registros Libros:");
        List<Libro> libros = dao.getAll("Libro.findAllWithRel", Libro.class);
        libros.forEach(l -> System.out.println(
                "- " + l.getId() + ": '" + l.getTitulo() + "' (" + l.getAnioPub() + ")"
                        + ", Autor=" + (l.getAutor() != null ? l.getAutor().getNombre() : "(sin autor)")
                        + ", Categoría=" + (l.getCategoria() != null ? l.getCategoria().getNombre() : "(sin categoría)")
        ));
    }

    public static void editarLibro() {
        Libro l = dao.findById(1L, Libro.class);
        if (l != null) {
            l.setTitulo(l.getTitulo() + " (Edición Revisada)");
            l.setAnioPub(Math.max(l.getAnioPub(), 2000));
            dao.update(l);
        } else {
            System.out.println("Libro con id 1 no encontrado para editar.");
        }
    }

    public static void eliminarLibro() {
        Libro l = dao.findById(2L, Libro.class);
        if (l != null) {
            dao.delete(l);
        } else {
            System.out.println("Libro con id 2 no encontrado para eliminar.");
        }
    }

    public static void main(String[] args) {
        // Autor
        insertarAutor();
        listarAutores();
        editarAutor();
        listarAutores();
        eliminarAutor();
        listarAutores();

        // Categoría
        insertarCategorias();
        listarCategorias();
        editarCategoria();
        listarCategorias();
        eliminarCategoria();
        listarCategorias();

        // Libro
        insertarLibros();
        listarLibros();
        editarLibro();
        listarLibros();
        eliminarLibro();
        listarLibros();
    }
}