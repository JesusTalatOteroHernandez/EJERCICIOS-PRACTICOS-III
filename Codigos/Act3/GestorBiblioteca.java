package Act3;

/**
 *
 * @author Jesus Talat Otero Hernandez
 * @email 1224100702.jtoh@gmail.com
 */

import java.util.*;

/**
 * Clase GestorBiblioteca: Gestiona las colecciones de libros usando diferentes tipos de Set.
 * Implementa 6+ operaciones con conjuntos:
 * 1. Agregar (add)
 * 2. Eliminar (remove)
 * 3. Buscar/Contiene (contains)
 * 4. Unión (addAll)
 * 5. Intersección (retainAll)
 * 6. Diferencia (removeAll)
 * 7. Subconjunto (containsAll)
 * 8. Vaciar (clear)
 * 9. Tamaño (size)
 */
public class GestorBiblioteca {
    
    // === CONJUNTOS DE LIBROS (usando diferentes implementaciones de Set) ===
    
    /**
     * Colección principal de libros usando HashSet
     * - No permite duplicados (por ISBN)
     * - No mantiene orden específico
     * - Operaciones O(1) en promedio
     */
    private Set<Libro> coleccionPrincipal;
    
    /**
     * Colección ordenada usando TreeSet
     * - Mantiene libros ordenados automáticamente por título
     * - Usa el método compareTo de Libro
     * - Operaciones O(log n)
     */
    private TreeSet<Libro> coleccionOrdenada;
    
    /**
     * Colección temporal usando LinkedHashSet
     * - Mantiene el orden de inserción
     * - Útil para operaciones temporales
     */
    private LinkedHashSet<Libro> coleccionTemporal;
    
    /**
     * Conjunto de categorías disponibles
     */
    private Set<String> categoriasDisponibles;
    
    /**
     * Constructor: Inicializa las colecciones y categorías predeterminadas
     */
    public GestorBiblioteca() {
        // Inicializar colección principal (HashSet - sin orden, rápido)
        this.coleccionPrincipal = new HashSet<>();
        
        // Inicializar colección ordenada (TreeSet - ordenado automáticamente)
        this.coleccionOrdenada = new TreeSet<>();
        
        // Inicializar colección temporal (LinkedHashSet - mantiene orden de inserción)
        this.coleccionTemporal = new LinkedHashSet<>();
        
        // Inicializar categorías disponibles
        this.categoriasDisponibles = new HashSet<>();
        inicializarCategorias();
    }
    
    /**
     * Inicializa las categorías predeterminadas de libros
     */
    private void inicializarCategorias() {
        categoriasDisponibles.add("Ficción");
        categoriasDisponibles.add("Ciencia");
        categoriasDisponibles.add("Historia");
        categoriasDisponibles.add("Tecnología");
        categoriasDisponibles.add("Arte");
        categoriasDisponibles.add("Filosofía");
        categoriasDisponibles.add("Biografía");
        categoriasDisponibles.add("Poesía");
    }
    
    // ========== OPERACIÓN 1: AGREGAR (add) ==========
    
    /**
     * OPERACIÓN 1: Agrega un libro a la colección principal
     * Usa el método add() de Set
     * @param libro Libro a agregar
     * @return true si se agregó (no existía), false si ya existía (duplicado por ISBN)
     */
    public boolean agregarLibro(Libro libro) {
        // Agregar a colección principal (HashSet)
        boolean agregado = coleccionPrincipal.add(libro);
        
        // Si se agregó exitosamente, también agregar a colección ordenada
        if (agregado) {
            coleccionOrdenada.add(libro);
        }
        
        return agregado;
    }
    
    /**
     * Agrega múltiples libros de una vez
     * @param libros Colección de libros a agregar
     * @return Cantidad de libros agregados exitosamente
     */
    public int agregarVariosLibros(Collection<Libro> libros) {
        int contador = 0;
        for (Libro libro : libros) {
            if (agregarLibro(libro)) {
                contador++;
            }
        }
        return contador;
    }
    
    // ========== OPERACIÓN 2: ELIMINAR (remove) ==========
    
    /**
     * OPERACIÓN 2: Elimina un libro de todas las colecciones
     * Usa el método remove() de Set
     * @param libro Libro a eliminar
     * @return true si se eliminó (existía), false si no existía
     */
    public boolean eliminarLibro(Libro libro) {
        // Eliminar de todas las colecciones
        boolean eliminado = coleccionPrincipal.remove(libro);
        
        if (eliminado) {
            coleccionOrdenada.remove(libro);
        }
        
        return eliminado;
    }
    
    /**
     * Elimina un libro por su ISBN
     * @param isbn ISBN del libro a eliminar
     * @return true si se eliminó
     */
    public boolean eliminarPorISBN(String isbn) {
        // Buscar el libro con ese ISBN
        for (Libro libro : coleccionPrincipal) {
            if (libro.getIsbn().equals(isbn)) {
                return eliminarLibro(libro);
            }
        }
        return false;
    }
    
    // ========== OPERACIÓN 3: BUSCAR/CONTIENE (contains) ==========
    
    /**
     * OPERACIÓN 3: Verifica si un libro existe en la colección
     * Usa el método contains() de Set
     * @param libro Libro a buscar
     * @return true si el libro existe (por ISBN)
     */
    public boolean contieneLibro(Libro libro) {
        return coleccionPrincipal.contains(libro);
    }
    
    /**
     * Busca un libro por ISBN
     * @param isbn ISBN a buscar
     * @return El libro si se encuentra, null si no existe
     */
    public Libro buscarPorISBN(String isbn) {
        for (Libro libro : coleccionPrincipal) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }
    
    /**
     * Busca libros que contengan un texto en título o autor
     * @param texto Texto a buscar
     * @return Set con los libros encontrados
     */
    public Set<Libro> buscarPorTexto(String texto) {
        Set<Libro> resultados = new HashSet<>();
        for (Libro libro : coleccionPrincipal) {
            if (libro.contiene(texto)) {
                resultados.add(libro);
            }
        }
        return resultados;
    }
    
    // ========== OPERACIÓN 4: UNIÓN (addAll) ==========
    
    /**
     * OPERACIÓN 4: Une dos colecciones de libros
     * Usa el método addAll() de Set
     * Unión: A ∪ B = todos los elementos de A o B (sin duplicados)
     * @param otraColeccion Colección a unir con la principal
     * @return Nueva colección con la unión
     */
    public Set<Libro> unionConOtraColeccion(Set<Libro> otraColeccion) {
        // Crear una nueva colección con todos los libros de la colección principal
        Set<Libro> union = new HashSet<>(coleccionPrincipal);
        
        // Agregar todos los libros de la otra colección (addAll)
        // Los duplicados (por ISBN) se ignoran automáticamente
        union.addAll(otraColeccion);
        
        return union;
    }
    
    /**
     * Une la colección temporal con la principal
     * @return Cantidad de libros nuevos agregados
     */
    public int fusionarColeccionTemporal() {
        int sizeAntes = coleccionPrincipal.size();
        coleccionPrincipal.addAll(coleccionTemporal);
        coleccionOrdenada.addAll(coleccionTemporal);
        int sizeAhora = coleccionPrincipal.size();
        
        // Limpiar temporal después de fusionar
        coleccionTemporal.clear();
        
        return sizeAhora - sizeAntes;
    }
    
    // ========== OPERACIÓN 5: INTERSECCIÓN (retainAll) ==========
    
    /**
     * OPERACIÓN 5: Obtiene la intersección de dos colecciones
     * Usa el método retainAll() de Set
     * Intersección: A ∩ B = elementos que están en ambas colecciones
     * @param otraColeccion Colección a intersectar
     * @return Nueva colección con libros comunes
     */
    public Set<Libro> interseccionConOtraColeccion(Set<Libro> otraColeccion) {
        // Crear copia de la colección principal
        Set<Libro> interseccion = new HashSet<>(coleccionPrincipal);
        
        // Retener solo los elementos que también están en la otra colección
        interseccion.retainAll(otraColeccion);
        
        return interseccion;
    }
    
    /**
     * Obtiene libros que están en una categoría específica
     * @param categoria Categoría a filtrar
     * @return Set con libros de esa categoría
     */
    public Set<Libro> obtenerLibrosPorCategoria(String categoria) {
        Set<Libro> librosPorCategoria = new HashSet<>();
        for (Libro libro : coleccionPrincipal) {
            if (libro.esDeCategoria(categoria)) {
                librosPorCategoria.add(libro);
            }
        }
        return librosPorCategoria;
    }
    
    // ========== OPERACIÓN 6: DIFERENCIA (removeAll) ==========
    
    /**
     * OPERACIÓN 6: Obtiene la diferencia entre dos colecciones
     * Usa el método removeAll() de Set
     * Diferencia: A - B = elementos que están en A pero no en B
     * @param otraColeccion Colección a restar
     * @return Nueva colección con libros que solo están en la principal
     */
    public Set<Libro> diferenciaConOtraColeccion(Set<Libro> otraColeccion) {
        // Crear copia de la colección principal
        Set<Libro> diferencia = new HashSet<>(coleccionPrincipal);
        
        // Remover todos los elementos que están en la otra colección
        diferencia.removeAll(otraColeccion);
        
        return diferencia;
    }
    
    /**
     * Obtiene libros que NO están en una categoría específica
     * @param categoria Categoría a excluir
     * @return Set con libros que no son de esa categoría
     */
    public Set<Libro> obtenerLibrosExceptoCategoria(String categoria) {
        Set<Libro> resultado = new HashSet<>();
        for (Libro libro : coleccionPrincipal) {
            if (!libro.esDeCategoria(categoria)) {
                resultado.add(libro);
            }
        }
        return resultado;
    }
    
    // ========== OPERACIÓN 7: SUBCONJUNTO (containsAll) ==========
    
    /**
     * OPERACIÓN 7: Verifica si una colección es subconjunto de otra
     * Usa el método containsAll() de Set
     * Subconjunto: A ⊆ B si todos los elementos de A están en B
     * @param posibleSubconjunto Colección a verificar
     * @return true si es subconjunto de la colección principal
     */
    public boolean esSubconjunto(Set<Libro> posibleSubconjunto) {
        // Verificar si la colección principal contiene todos los libros del posible subconjunto
        return coleccionPrincipal.containsAll(posibleSubconjunto);
    }
    
    /**
     * Verifica si todos los libros de una lista están en la biblioteca
     * @param libros Lista de libros a verificar
     * @return true si todos están en la colección
     */
    public boolean contieneTodos(List<Libro> libros) {
        return coleccionPrincipal.containsAll(libros);
    }
    
    // ========== OPERACIÓN 8: VACIAR (clear) ==========
    
    /**
     * OPERACIÓN 8: Vacía todas las colecciones
     * Usa el método clear() de Set
     */
    public void vaciarBiblioteca() {
        coleccionPrincipal.clear();
        coleccionOrdenada.clear();
        coleccionTemporal.clear();
    }
    
    /**
     * Vacía solo la colección temporal
     */
    public void vaciarColeccionTemporal() {
        coleccionTemporal.clear();
    }
    
    // ========== OPERACIÓN 9: TAMAÑO (size) ==========
    
    /**
     * OPERACIÓN 9: Obtiene el tamaño de la colección
     * Usa el método size() de Set
     * @return Cantidad de libros en la colección principal
     */
    public int obtenerTamano() {
        return coleccionPrincipal.size();
    }
    
    /**
     * Obtiene el tamaño de la colección temporal
     * @return Cantidad de libros en la colección temporal
     */
    public int obtenerTamanoTemporal() {
        return coleccionTemporal.size();
    }
    
    /**
     * Verifica si la colección está vacía
     * @return true si no hay libros
     */
    public boolean estaVacia() {
        return coleccionPrincipal.isEmpty();
    }
    
    // ========== OPERACIONES ADICIONALES ==========
    
    /**
     * Agrega un libro a la colección temporal
     * @param libro Libro a agregar
     * @return true si se agregó
     */
    public boolean agregarATemporales(Libro libro) {
        return coleccionTemporal.add(libro);
    }
    
    /**
     * Obtiene la colección principal
     * @return Set de libros
     */
    public Set<Libro> getColeccionPrincipal() {
        return new HashSet<>(coleccionPrincipal);
    }
    
    /**
     * Obtiene la colección ordenada
     * @return TreeSet de libros ordenados por título
     */
    public TreeSet<Libro> getColeccionOrdenada() {
        return new TreeSet<>(coleccionOrdenada);
    }
    
    /**
     * Obtiene la colección temporal
     * @return LinkedHashSet de libros temporales
     */
    public LinkedHashSet<Libro> getColeccionTemporal() {
        return new LinkedHashSet<>(coleccionTemporal);
    }
    
    /**
     * Obtiene todas las categorías disponibles
     * @return Set de categorías
     */
    public Set<String> getCategoriasDisponibles() {
        return new HashSet<>(categoriasDisponibles);
    }
    
    /**
     * Agrega una nueva categoría
     * @param categoria Nueva categoría
     * @return true si se agregó
     */
    public boolean agregarCategoria(String categoria) {
        return categoriasDisponibles.add(categoria);
    }
    
    /**
     * Obtiene estadísticas de la biblioteca
     * @return String con estadísticas
     */
    public String obtenerEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS DE LA BIBLIOTECA ===\n");
        stats.append("Total de libros: ").append(obtenerTamano()).append("\n");
        stats.append("Libros temporales: ").append(obtenerTamanoTemporal()).append("\n");
        stats.append("Categorías disponibles: ").append(categoriasDisponibles.size()).append("\n");
        
        // Contar libros por categoría
        stats.append("\nLibros por categoría:\n");
        for (String cat : categoriasDisponibles) {
            int count = obtenerLibrosPorCategoria(cat).size();
            if (count > 0) {
                stats.append("  - ").append(cat).append(": ").append(count).append("\n");
            }
        }
        
        return stats.toString();
    }
    
    /**
     * Obtiene libros publicados después de un año
     * @param anio Año de referencia
     * @return Set de libros recientes
     */
    public Set<Libro> obtenerLibrosRecientes(int anio) {
        Set<Libro> recientes = new HashSet<>();
        for (Libro libro : coleccionPrincipal) {
            if (libro.publicadoDespuesDe(anio)) {
                recientes.add(libro);
            }
        }
        return recientes;
    }
}