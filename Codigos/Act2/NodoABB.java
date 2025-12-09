package Act2;

/**
 *
 * @author Jesus Talat Otero Hernandez
 * @email 1224100702.jtoh@gmail.com
 */
public class NodoABB {
     private int valor;           // Valor almacenado en el nodo
    private NodoABB izquierdo;   // Hijo izquierdo (valores menores)
    private NodoABB derecho;     // Hijo derecho (valores mayores)
    private int x, y;            // Coordenadas para visualización gráfica
    private boolean resaltado;   // Indica si el nodo está resaltado (para búsqueda)
    
    /**
     * Constructor del nodo
     * @param valor Valor entero del nodo
     */
    public NodoABB(int valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
        this.x = 0;
        this.y = 0;
        this.resaltado = false;
    }
    
    /**
     * Inserta un nuevo valor en el árbol de forma recursiva
     * Propiedad ABB: Izquierda < Raíz < Derecha
     * @param nuevoValor Valor a insertar
     * @return true si se insertó, false si es duplicado
     */
    public boolean insertar(int nuevoValor) {
        // Verificar duplicados (no se permiten en este ABB)
        if (nuevoValor == this.valor) {
            return false; // Valor duplicado, no se inserta
        }
        
        // Si el valor es menor, va al subárbol izquierdo
        if (nuevoValor < this.valor) {
            if (this.izquierdo == null) {
                this.izquierdo = new NodoABB(nuevoValor);
                return true;
            } else {
                return this.izquierdo.insertar(nuevoValor);
            }
        } 
        // Si el valor es mayor, va al subárbol derecho
        else {
            if (this.derecho == null) {
                this.derecho = new NodoABB(nuevoValor);
                return true;
            } else {
                return this.derecho.insertar(nuevoValor);
            }
        }
    }
    
    /**
     * Busca un valor en el árbol
     * @param valorBuscado Valor a buscar
     * @return El nodo si se encuentra, null si no existe
     */
    public NodoABB buscar(int valorBuscado) {
        if (this.valor == valorBuscado) {
            return this;
        }
        
        if (valorBuscado < this.valor && this.izquierdo != null) {
            return this.izquierdo.buscar(valorBuscado);
        }
        
        if (valorBuscado > this.valor && this.derecho != null) {
            return this.derecho.buscar(valorBuscado);
        }
        
        return null; // No encontrado
    }
    
    /**
     * Encuentra el nodo con el valor mínimo del subárbol
     * (el nodo más a la izquierda)
     * @return Nodo con valor mínimo
     */
    public NodoABB encontrarMinimo() {
        if (this.izquierdo == null) {
            return this;
        }
        return this.izquierdo.encontrarMinimo();
    }
    
    /**
     * RECORRIDO INORDEN (Izquierda - Raíz - Derecha)
     * Resultado: Valores ordenados de menor a mayor
     * @param resultado StringBuilder para acumular el recorrido
     */
    public void recorridoInorden(StringBuilder resultado) {
        if (this.izquierdo != null) {
            this.izquierdo.recorridoInorden(resultado);
        }
        resultado.append(this.valor).append(" ");
        if (this.derecho != null) {
            this.derecho.recorridoInorden(resultado);
        }
    }
    
    /**
     * RECORRIDO PREORDEN (Raíz - Izquierda - Derecha)
     * Útil para copiar el árbol
     * @param resultado StringBuilder para acumular el recorrido
     */
    public void recorridoPreorden(StringBuilder resultado) {
        resultado.append(this.valor).append(" ");
        if (this.izquierdo != null) {
            this.izquierdo.recorridoPreorden(resultado);
        }
        if (this.derecho != null) {
            this.derecho.recorridoPreorden(resultado);
        }
    }
    
    /**
     * RECORRIDO POSTORDEN (Izquierda - Derecha - Raíz)
     * Útil para eliminar el árbol (hojas primero)
     * @param resultado StringBuilder para acumular el recorrido
     */
    public void recorridoPostorden(StringBuilder resultado) {
        if (this.izquierdo != null) {
            this.izquierdo.recorridoPostorden(resultado);
        }
        if (this.derecho != null) {
            this.derecho.recorridoPostorden(resultado);
        }
        resultado.append(this.valor).append(" ");
    }
    
    /**
     * Calcula la altura del árbol (máxima profundidad)
     * @return Altura del árbol
     */
    public int calcularAltura() {
        int alturaIzq = (this.izquierdo != null) ? this.izquierdo.calcularAltura() : 0;
        int alturaDer = (this.derecho != null) ? this.derecho.calcularAltura() : 0;
        return 1 + Math.max(alturaIzq, alturaDer);
    }
    
    /**
     * Cuenta el número total de nodos en el árbol
     * @return Cantidad de nodos
     */
    public int contarNodos() {
        int count = 1; // Este nodo
        if (this.izquierdo != null) {
            count += this.izquierdo.contarNodos();
        }
        if (this.derecho != null) {
            count += this.derecho.contarNodos();
        }
        return count;
    }
    
    /**
     * Reinicia el resaltado de todos los nodos
     */
    public void reiniciarResaltado() {
        this.resaltado = false;
        if (this.izquierdo != null) {
            this.izquierdo.reiniciarResaltado();
        }
        if (this.derecho != null) {
            this.derecho.reiniciarResaltado();
        }
    }
    
    // Getters y Setters
    public int getValor() {
        return valor;
    }
    
    public void setValor(int valor) {
        this.valor = valor;
    }
    
    public NodoABB getIzquierdo() {
        return izquierdo;
    }
    
    public void setIzquierdo(NodoABB izquierdo) {
        this.izquierdo = izquierdo;
    }
    
    public NodoABB getDerecho() {
        return derecho;
    }
    
    public void setDerecho(NodoABB derecho) {
        this.derecho = derecho;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public boolean isResaltado() {
        return resaltado;
    }
    
    public void setResaltado(boolean resaltado) {
        this.resaltado = resaltado;
    }
    
    /**
     * Elimina un nodo del árbol de forma recursiva
     * CASOS DE ELIMINACIÓN:
     * 1. Nodo hoja (sin hijos): Simplemente se elimina
     * 2. Nodo con un hijo: Se reemplaza por su hijo
     * 3. Nodo con dos hijos: Se reemplaza por su sucesor inorden
     * @param valorEliminar Valor del nodo a eliminar
     * @param padre Nodo padre (para actualizar referencias)
     * @return true si se eliminó correctamente
     */
    public boolean eliminarNodo(int valorEliminar, NodoABB padre) {
        // Buscar el nodo a eliminar
        if (valorEliminar < this.valor) {
            if (this.izquierdo != null) {
                return this.izquierdo.eliminarNodo(valorEliminar, this);
            }
            return false; // No encontrado
        } else if (valorEliminar > this.valor) {
            if (this.derecho != null) {
                return this.derecho.eliminarNodo(valorEliminar, this);
            }
            return false; // No encontrado
        } else {
            // Nodo encontrado, proceder con la eliminación
            
            // CASO 3: Nodo con dos hijos
            if (this.izquierdo != null && this.derecho != null) {
                // Encontrar el sucesor inorden (mínimo del subárbol derecho)
                NodoABB sucesor = this.derecho.encontrarMinimo();
                this.valor = sucesor.getValor();
                // Eliminar el sucesor del subárbol derecho
                return this.derecho.eliminarNodo(this.valor, this);
            }
            // CASO 1 y CASO 2: Nodo con un hijo o sin hijos
            else if (padre.getIzquierdo() == this) {
                padre.setIzquierdo((this.izquierdo != null) ? this.izquierdo : this.derecho);
                return true;
            } else if (padre.getDerecho() == this) {
                padre.setDerecho((this.izquierdo != null) ? this.izquierdo : this.derecho);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}