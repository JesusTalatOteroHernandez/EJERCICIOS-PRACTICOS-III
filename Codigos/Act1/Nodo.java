package Act1;

/**
 *
 * @author otero
 */

import java.util.ArrayList;
import java.util.List;

public class Nodo {
    private String tipo;              // Tipo de etiqueta HTML (html, body, div, p, etc.)
    private String contenido;         // Contenido de texto del nodo
    private List<Nodo> hijos;        // Lista de nodos hijos
    private Nodo padre;              // Referencia al nodo padre
    private String id;               // ID del elemento (opcional)
    private String clase;            // Clase CSS del elemento (opcional)
    
    /**
     * Constructor principal del Nodo
     * @param tipo Etiqueta HTML del nodo
     * @param contenido Contenido de texto (puede ser vacío)
     */
    public Nodo(String tipo, String contenido) {
        this.tipo = tipo;
        this.contenido = contenido;
        this.hijos = new ArrayList<>();
        this.padre = null;
        this.id = "";
        this.clase = "";
    }
    
    /**
     * Constructor simplificado sin contenido
     * @param tipo Etiqueta HTML del nodo
     */
    public Nodo(String tipo) {
        this(tipo, "");
    }
    
    /**
     * Agrega un nodo hijo a este nodo
     * @param hijo Nodo a agregar como hijo
     */
    public void agregarHijo(Nodo hijo) {
        if (hijo != null) {
            this.hijos.add(hijo);
            hijo.setPadre(this);
        }
    }
    
    /**
     * Elimina un nodo hijo específico
     * @param hijo Nodo a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    public boolean eliminarHijo(Nodo hijo) {
        if (hijo != null && this.hijos.remove(hijo)) {
            hijo.setPadre(null);
            return true;
        }
        return false;
    }
    
    /**
     * Genera el código HTML de este nodo y sus hijos
     * @param nivel Nivel de indentación para formato
     * @return String con el código HTML formateado
     */
    public String generarHTML(int nivel) {
        StringBuilder html = new StringBuilder();
        String indent = "  ".repeat(nivel);
        
        // Construir la etiqueta de apertura con atributos
        html.append(indent).append("<").append(tipo);
        
        // Agregar atributos si existen
        if (!id.isEmpty()) {
            html.append(" id=\"").append(id).append("\"");
        }
        if (!clase.isEmpty()) {
            html.append(" class=\"").append(clase).append("\"");
        }
        html.append(">");
        
        // Si tiene contenido o hijos, agregar salto de línea
        if (!contenido.isEmpty() || !hijos.isEmpty()) {
            html.append("\n");
        }
        
        // Agregar contenido de texto si existe
        if (!contenido.isEmpty()) {
            html.append(indent).append("  ").append(contenido).append("\n");
        }
        
        // Agregar hijos recursivamente
        for (Nodo hijo : hijos) {
            html.append(hijo.generarHTML(nivel + 1));
        }
        
        // Etiqueta de cierre
        if (!contenido.isEmpty() || !hijos.isEmpty()) {
            html.append(indent);
        }
        html.append("</").append(tipo).append(">\n");
        
        return html.toString();
    }
    
    /**
     * Busca un nodo por su referencia en el árbol
     * @param objetivo Nodo a buscar
     * @return El nodo si se encuentra, null en caso contrario
     */
    public Nodo buscarNodo(Nodo objetivo) {
        if (this == objetivo) {
            return this;
        }
        
        for (Nodo hijo : hijos) {
            Nodo encontrado = hijo.buscarNodo(objetivo);
            if (encontrado != null) {
                return encontrado;
            }
        }
        
        return null;
    }
    
    /**
     * Obtiene la profundidad del nodo en el árbol
     * @return Profundidad (0 para la raíz)
     */
    public int obtenerProfundidad() {
        int profundidad = 0;
        Nodo actual = this.padre;
        while (actual != null) {
            profundidad++;
            actual = actual.getPadre();
        }
        return profundidad;
    }
    
    // Getters y Setters
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public List<Nodo> getHijos() {
        return hijos;
    }
    
    public Nodo getPadre() {
        return padre;
    }
    
    public void setPadre(Nodo padre) {
        this.padre = padre;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getClase() {
        return clase;
    }
    
    public void setClase(String clase) {
        this.clase = clase;
    }
    
    /**
     * Representación en String del nodo para mostrar en el árbol
     * @return Descripción legible del nodo
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(tipo).append(">");
        
        if (!id.isEmpty()) {
            sb.append(" #").append(id);
        }
        if (!clase.isEmpty()) {
            sb.append(" .").append(clase);
        }
        if (!contenido.isEmpty()) {
            String preview = contenido.length() > 20 ? 
                           contenido.substring(0, 20) + "..." : contenido;
            sb.append(" - \"").append(preview).append("\"");
        }
        
        return sb.toString();
    }
}

