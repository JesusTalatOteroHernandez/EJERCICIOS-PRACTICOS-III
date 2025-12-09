package Act3;

/**
 *
 * @author Jesus Talat Otero Hernandez
 * @email 1224100702.jtoh@gmail.com
 */

import java.util.Objects;

/**
 * Clase Libro: Representa un libro en la biblioteca digital.
 * Contiene 5 datos de información: ISBN, título, autor, año de publicación y categoría.
 * Implementa Comparable para permitir ordenamiento por título.
 */
public class Libro implements Comparable<Libro> {
    
    // === ATRIBUTOS (5 datos de información) ===
    private String isbn;          // Código ISBN único del libro
    private String titulo;        // Título del libro
    private String autor;         // Autor del libro
    private int anioPublicacion;  // Año de publicación
    private String categoria;     // Categoría (Ficción, Ciencia, Historia, etc.)
    
    /**
     * Constructor completo de Libro
     * @param isbn Código ISBN del libro
     * @param titulo Título del libro
     * @param autor Autor del libro
     * @param anioPublicacion Año de publicación
     * @param categoria Categoría del libro
     */
    public Libro(String isbn, String titulo, String autor, int anioPublicacion, String categoria) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.categoria = categoria;
    }
    
    /**
     * Constructor sin parámetros
     */
    public Libro() {
        this("", "", "", 0, "");
    }
    
    // === GETTERS Y SETTERS ===
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public int getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    /**
     * Método equals: Dos libros son iguales si tienen el mismo ISBN
     * Esto es importante para que HashSet pueda detectar duplicados
     * @param obj Objeto a comparar
     * @return true si los libros tienen el mismo ISBN
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Libro libro = (Libro) obj;
        return Objects.equals(isbn, libro.isbn);
    }
    
    /**
     * Método hashCode: Genera código hash basado en el ISBN
     * Requerido cuando se sobrescribe equals, especialmente para HashSet
     * @return Código hash del ISBN
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
    
    /**
     * Método compareTo: Compara libros por título (orden alfabético)
     * Permite usar TreeSet para mantener libros ordenados automáticamente
     * @param otro Libro a comparar
     * @return Negativo si este libro va antes, positivo si va después, 0 si son iguales
     */
    @Override
    public int compareTo(Libro otro) {
        // Comparar por título (orden alfabético)
        int comparacionTitulo = this.titulo.compareToIgnoreCase(otro.titulo);
        
        // Si los títulos son iguales, comparar por autor
        if (comparacionTitulo == 0) {
            return this.autor.compareToIgnoreCase(otro.autor);
        }
        
        return comparacionTitulo;
    }
    
    /**
     * Método toString: Representación en texto del libro
     * Formato: "Título" por Autor (Año) - Categoría [ISBN]
     * @return String con información del libro
     */
    @Override
    public String toString() {
        return String.format("\"%s\" por %s (%d) - %s [ISBN: %s]",
                titulo, autor, anioPublicacion, categoria, isbn);
    }
    
    /**
     * Obtiene una representación detallada del libro para mostrar en la tabla
     * @return Array con los 5 datos del libro
     */
    public Object[] toArray() {
        return new Object[]{isbn, titulo, autor, anioPublicacion, categoria};
    }
    
    /**
     * Verifica si el libro pertenece a una categoría específica
     * @param cat Categoría a verificar
     * @return true si el libro pertenece a esa categoría
     */
    public boolean esDeCategoria(String cat) {
        return this.categoria.equalsIgnoreCase(cat);
    }
    
    /**
     * Verifica si el libro fue publicado en un año específico
     * @param anio Año a verificar
     * @return true si el libro fue publicado en ese año
     */
    public boolean publicadoEn(int anio) {
        return this.anioPublicacion == anio;
    }
    
    /**
     * Verifica si el libro fue publicado después de un año dado
     * @param anio Año de referencia
     * @return true si el libro fue publicado después de ese año
     */
    public boolean publicadoDespuesDe(int anio) {
        return this.anioPublicacion > anio;
    }
    
    /**
     * Verifica si el libro contiene un texto en su título o autor
     * @param texto Texto a buscar (no sensible a mayúsculas)
     * @return true si el texto se encuentra en título o autor
     */
    public boolean contiene(String texto) {
        String textoLower = texto.toLowerCase();
        return this.titulo.toLowerCase().contains(textoLower) ||
               this.autor.toLowerCase().contains(textoLower);
    }
}
