package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Libro.findAll",
                query = "SELECT l FROM Libro l"),
        @NamedQuery(name = "Libro.findAllWithRel",
                query = "SELECT l FROM Libro l LEFT JOIN FETCH l.autor LEFT JOIN FETCH l.categoria")
})
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "anio_pub")
    private int anioPub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}