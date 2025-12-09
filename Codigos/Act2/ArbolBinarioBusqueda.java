package Act2;

/**
 *
 * @author Jesus Talat Otero Hernandez
 * @email 1224100702.jtoh@gmail.com
 */
public class ArbolBinarioBusqueda {
    private NodoABB raiz; // Raíz del árbol
    
    /**
     * Constructor: Inicializa un árbol vacío
     */
    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }
    
    /**
     * Inserta un valor en el árbol
     * @param valor Valor a insertar
     * @return true si se insertó, false si es duplicado
     */
    public boolean insertar(int valor) {
        if (raiz == null) {
            raiz = new NodoABB(valor);
            return true;
        } else {
            return raiz.insertar(valor);
        }
    }
    
    /**
     * Busca un valor en el árbol y lo resalta
     * @param valor Valor a buscar
     * @return true si se encontró, false si no existe
     */
    public boolean buscar(int valor) {
        // Primero reiniciar todos los resaltados
        if (raiz != null) {
            raiz.reiniciarResaltado();
        }
        
        NodoABB nodoEncontrado = (raiz != null) ? raiz.buscar(valor) : null;
        
        if (nodoEncontrado != null) {
            nodoEncontrado.setResaltado(true);
            return true;
        }
        
        return false;
    }
    
    /**
     * Elimina un valor del árbol
     * @param valor Valor a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminar(int valor) {
        if (raiz == null) {
            return false;
        }
        
        // Caso especial: eliminar la raíz
        if (raiz.getValor() == valor) {
            NodoABB auxiliar = new NodoABB(0);
            auxiliar.setIzquierdo(raiz);
            boolean resultado = raiz.eliminarNodo(valor, auxiliar);
            raiz = auxiliar.getIzquierdo();
            return resultado;
        }
        
        return raiz.eliminarNodo(valor, null);
    }
    
    /**
     * Obtiene el recorrido Inorden del árbol
     * @return String con los valores en orden
     */
    public String getRecorridoInorden() {
        if (raiz == null) {
            return "Árbol vacío";
        }
        StringBuilder resultado = new StringBuilder();
        raiz.recorridoInorden(resultado);
        return resultado.toString().trim();
    }
    
    /**
     * Obtiene el recorrido Preorden del árbol
     * @return String con los valores en preorden
     */
    public String getRecorridoPreorden() {
        if (raiz == null) {
            return "Árbol vacío";
        }
        StringBuilder resultado = new StringBuilder();
        raiz.recorridoPreorden(resultado);
        return resultado.toString().trim();
    }
    
    /**
     * Obtiene el recorrido Postorden del árbol
     * @return String con los valores en postorden
     */
    public String getRecorridoPostorden() {
        if (raiz == null) {
            return "Árbol vacío";
        }
        StringBuilder resultado = new StringBuilder();
        raiz.recorridoPostorden(resultado);
        return resultado.toString().trim();
    }
    
    /**
     * Limpia todo el árbol
     */
    public void limpiar() {
        raiz = null;
    }
    
    /**
     * Verifica si el árbol está vacío
     * @return true si está vacío
     */
    public boolean estaVacio() {
        return raiz == null;
    }
    
    /**
     * Obtiene la raíz del árbol
     * @return Nodo raíz
     */
    public NodoABB getRaiz() {
        return raiz;
    }
    
    /**
     * Obtiene la altura del árbol
     * @return Altura del árbol
     */
    public int getAltura() {
        return (raiz != null) ? raiz.calcularAltura() : 0;
    }
    
    /**
     * Obtiene el número total de nodos
     * @return Cantidad de nodos
     */
    public int getTotalNodos() {
        return (raiz != null) ? raiz.contarNodos() : 0;
    }
    
    /**
     * Reinicia todos los resaltados de nodos
     */
    public void reiniciarResaltados() {
        if (raiz != null) {
            raiz.reiniciarResaltado();
        }
    }
}

