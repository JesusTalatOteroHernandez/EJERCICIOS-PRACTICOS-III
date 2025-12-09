package Act1;

/**
 *
 * @author Jesus Talat Otero Hernandez
 * @email 1224100702.jtoh@gmail.com
 */

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.*;


public class SimuladorDOM extends JFrame{
    // Componentes principales de la interfaz
    private JTree arbolVisual;              // Árbol visual para mostrar el DOM
    private DefaultTreeModel modeloArbol;   // Modelo del árbol Swing
    private JTextArea vistaHTML;            // Área de texto para mostrar HTML
    private Nodo raiz;                      // Raíz del árbol DOM (nodo <html>)
    
    // Paneles de la interfaz
    private JPanel panelControl;
    private JScrollPane scrollArbol;
    private JScrollPane scrollHTML;
    
    /**
     * Constructor principal: inicializa la interfaz y el árbol DOM
     */
    public SimuladorDOM() {
        super("Simulador DOM - Creación de Páginas Web");
        
        // Inicializar el árbol DOM con estructura básica HTML
        inicializarArbolDOM();
        
        // Configurar la interfaz gráfica
        configurarInterfaz();
        
        // Actualizar vista inicial
        actualizarVistaHTML();
        
        // Configuraciones de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Inicializa el árbol DOM con una estructura HTML básica
     * Crea: html > head, body
     */
    private void inicializarArbolDOM() {
        // Crear nodo raíz HTML
        raiz = new Nodo("html");
        
        // Crear estructura básica
        Nodo head = new Nodo("head");
        Nodo title = new Nodo("title", "Mi Página Web");
        head.agregarHijo(title);
        
        Nodo body = new Nodo("body");
        body.setId("main-body");
        
        raiz.agregarHijo(head);
        raiz.agregarHijo(body);
        
        // Crear el modelo del árbol visual
        DefaultMutableTreeNode nodoRaizVisual = crearNodoVisual(raiz);
        modeloArbol = new DefaultTreeModel(nodoRaizVisual);
        arbolVisual = new JTree(modeloArbol);
        
        // Configurar el árbol visual
        arbolVisual.setFont(new Font("Monospaced", Font.PLAIN, 14));
        arbolVisual.setRowHeight(25);
        
        // Expandir todos los nodos por defecto
        for (int i = 0; i < arbolVisual.getRowCount(); i++) {
            arbolVisual.expandRow(i);
        }
    }
    
    /**
     * Crea un nodo visual (DefaultMutableTreeNode) a partir de un Nodo DOM
     * Recursivamente crea todos los hijos
     * @param nodo Nodo del árbol DOM
     * @return Nodo visual para el JTree
     */
    private DefaultMutableTreeNode crearNodoVisual(Nodo nodo) {
        DefaultMutableTreeNode nodoVisual = new DefaultMutableTreeNode(nodo);
        
        for (Nodo hijo : nodo.getHijos()) {
            nodoVisual.add(crearNodoVisual(hijo));
        }
        
        return nodoVisual;
    }
    
    /**
     * Configura toda la interfaz gráfica de la aplicación
     */
    private void configurarInterfaz() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior con título y descripción
        JPanel panelTitulo = crearPanelTitulo();
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central dividido: árbol | HTML
        JSplitPane panelCentral = crearPanelCentral();
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel de controles (botones de acción)
        panelControl = crearPanelControl();
        add(panelControl, BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel de título con información de la aplicación
     */
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(60, 120, 180));
        
        JLabel titulo = new JLabel("🌳 Simulador de Árbol DOM");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        
        JLabel subtitulo = new JLabel("Visualiza cómo se construye una página web mediante el Document Object Model");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(220, 220, 220));
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(subtitulo, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel central con el árbol y la vista HTML
     */
    private JSplitPane crearPanelCentral() {
        // Panel izquierdo: Árbol DOM
        JPanel panelArbol = new JPanel(new BorderLayout());
        panelArbol.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Estructura del Árbol DOM",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        scrollArbol = new JScrollPane(arbolVisual);
        panelArbol.add(scrollArbol, BorderLayout.CENTER);
        
        // Panel derecho: Vista HTML
        JPanel panelHTML = new JPanel(new BorderLayout());
        panelHTML.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Vista HTML Generada",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        vistaHTML = new JTextArea();
        vistaHTML.setFont(new Font("Courier New", Font.PLAIN, 13));
        vistaHTML.setEditable(false);
        vistaHTML.setBackground(new Color(40, 44, 52));
        vistaHTML.setForeground(new Color(171, 178, 191));
        vistaHTML.setCaretColor(Color.WHITE);
        vistaHTML.setTabSize(2);
        
        scrollHTML = new JScrollPane(vistaHTML);
        panelHTML.add(scrollHTML, BorderLayout.CENTER);
        
        // Crear el split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelArbol, panelHTML);
        splitPane.setDividerLocation(500);
        splitPane.setResizeWeight(0.5);
        
        return splitPane;
    }
    
    /**
     * Crea el panel de controles con todos los botones de acción
     */
    private JPanel crearPanelControl() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setBackground(new Color(240, 240, 240));
        
        // Botón: Agregar Nodo
        JButton btnAgregar = crearBoton("➕ Agregar Nodo", new Color(46, 125, 50));
        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        
        // Botón: Eliminar Nodo
        JButton btnEliminar = crearBoton("🗑️ Eliminar Nodo", new Color(211, 47, 47));
        btnEliminar.addActionListener(e -> eliminarNodoSeleccionado());
        
        // Botón: Editar Atributos
        JButton btnEditar = crearBoton("✏️ Editar Atributos", new Color(255, 152, 0));
        btnEditar.addActionListener(e -> mostrarDialogoEditar());
        
        // Botón: Actualizar Vista
        JButton btnActualizar = crearBoton("🔄 Actualizar Vista", new Color(33, 150, 243));
        btnActualizar.addActionListener(e -> actualizarVistaHTML());
        
        // Botón: Exportar HTML
        JButton btnExportar = crearBoton("💾 Exportar HTML", new Color(94, 53, 177));
        btnExportar.addActionListener(e -> exportarHTML());
        
        // Botón: Limpiar Árbol
        JButton btnLimpiar = crearBoton("🗑️ Limpiar Todo", new Color(158, 158, 158));
        btnLimpiar.addActionListener(e -> limpiarArbol());
        
        panel.add(btnAgregar);
        panel.add(btnEliminar);
        panel.add(btnEditar);
        panel.add(btnActualizar);
        panel.add(btnExportar);
        panel.add(btnLimpiar);
        
        return panel;
    }
    
    /**
     * Crea un botón estilizado con color personalizado
     */
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }
    
    /**
     * Muestra el diálogo para agregar un nuevo nodo
     */
    private void mostrarDialogoAgregar() {
        // Obtener nodo seleccionado (será el padre)
        DefaultMutableTreeNode nodoVisualSeleccionado = 
            (DefaultMutableTreeNode) arbolVisual.getLastSelectedPathComponent();
        
        if (nodoVisualSeleccionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona un nodo padre donde agregar el nuevo elemento.",
                "Selección Requerida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Nodo nodoPadre = (Nodo) nodoVisualSeleccionado.getUserObject();
        
        // Panel del diálogo
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Etiquetas HTML comunes
        String[] tiposEtiqueta = {"div", "p", "span", "h1", "h2", "h3", 
                                   "ul", "li", "a", "img", "button", "input", 
                                   "section", "article", "header", "footer", "nav"};
        
        JComboBox<String> comboTipo = new JComboBox<>(tiposEtiqueta);
        comboTipo.setEditable(true);
        
        JTextField txtContenido = new JTextField();
        JTextField txtId = new JTextField();
        JTextField txtClase = new JTextField();
        
        panel.add(new JLabel("Tipo de etiqueta:"));
        panel.add(comboTipo);
        panel.add(new JLabel("Contenido (texto):"));
        panel.add(txtContenido);
        panel.add(new JLabel("ID (opcional):"));
        panel.add(txtId);
        panel.add(new JLabel("Clase (opcional):"));
        panel.add(txtClase);
        
        int resultado = JOptionPane.showConfirmDialog(this, panel, 
            "Agregar Nuevo Nodo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (resultado == JOptionPane.OK_OPTION) {
            String tipo = (String) comboTipo.getSelectedItem();
            String contenido = txtContenido.getText().trim();
            String id = txtId.getText().trim();
            String clase = txtClase.getText().trim();
            
            // Validar tipo de etiqueta
            if (tipo == null || tipo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "El tipo de etiqueta no puede estar vacío.",
                    "Error de Validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear nuevo nodo
            Nodo nuevoNodo = new Nodo(tipo.trim(), contenido);
            nuevoNodo.setId(id);
            nuevoNodo.setClase(clase);
            
            // Agregar al árbol DOM
            nodoPadre.agregarHijo(nuevoNodo);
            
            // Actualizar vista visual
            DefaultMutableTreeNode nuevoNodoVisual = crearNodoVisual(nuevoNodo);
            nodoVisualSeleccionado.add(nuevoNodoVisual);
            modeloArbol.reload(nodoVisualSeleccionado);
            
            // Expandir el nodo padre
            arbolVisual.expandPath(new TreePath(nodoVisualSeleccionado.getPath()));
            
            // Actualizar HTML
            actualizarVistaHTML();
            
            JOptionPane.showMessageDialog(this, 
                "Nodo agregado exitosamente: <" + tipo + ">",
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Elimina el nodo seleccionado del árbol
     */
    private void eliminarNodoSeleccionado() {
        DefaultMutableTreeNode nodoVisualSeleccionado = 
            (DefaultMutableTreeNode) arbolVisual.getLastSelectedPathComponent();
        
        if (nodoVisualSeleccionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona un nodo para eliminar.",
                "Selección Requerida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // No permitir eliminar la raíz
        if (nodoVisualSeleccionado.isRoot()) {
            JOptionPane.showMessageDialog(this, 
                "No se puede eliminar el nodo raíz <html>.",
                "Operación No Permitida", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Nodo nodoAEliminar = (Nodo) nodoVisualSeleccionado.getUserObject();
        
        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de eliminar el nodo <" + nodoAEliminar.getTipo() + "> y todos sus hijos?",
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Obtener padre del nodo visual
            DefaultMutableTreeNode padreVisual = 
                (DefaultMutableTreeNode) nodoVisualSeleccionado.getParent();
            
            if (padreVisual != null) {
                Nodo nodoPadre = (Nodo) padreVisual.getUserObject();
                
                // Eliminar del árbol DOM
                nodoPadre.eliminarHijo(nodoAEliminar);
                
                // Eliminar del árbol visual
                modeloArbol.removeNodeFromParent(nodoVisualSeleccionado);
                
                // Actualizar HTML
                actualizarVistaHTML();
                
                JOptionPane.showMessageDialog(this, 
                    "Nodo eliminado exitosamente.",
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Muestra el diálogo para editar atributos del nodo seleccionado
     */
    private void mostrarDialogoEditar() {
        DefaultMutableTreeNode nodoVisualSeleccionado = 
            (DefaultMutableTreeNode) arbolVisual.getLastSelectedPathComponent();
        
        if (nodoVisualSeleccionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona un nodo para editar.",
                "Selección Requerida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Nodo nodo = (Nodo) nodoVisualSeleccionado.getUserObject();
        
        // Panel del diálogo
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JTextField txtTipo = new JTextField(nodo.getTipo());
        JTextField txtContenido = new JTextField(nodo.getContenido());
        JTextField txtId = new JTextField(nodo.getId());
        JTextField txtClase = new JTextField(nodo.getClase());
        
        panel.add(new JLabel("Tipo de etiqueta:"));
        panel.add(txtTipo);
        panel.add(new JLabel("Contenido:"));
        panel.add(txtContenido);
        panel.add(new JLabel("ID:"));
        panel.add(txtId);
        panel.add(new JLabel("Clase:"));
        panel.add(txtClase);
        
        int resultado = JOptionPane.showConfirmDialog(this, panel, 
            "Editar Atributos del Nodo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (resultado == JOptionPane.OK_OPTION) {
            // Actualizar atributos
            nodo.setTipo(txtTipo.getText().trim());
            nodo.setContenido(txtContenido.getText().trim());
            nodo.setId(txtId.getText().trim());
            nodo.setClase(txtClase.getText().trim());
            
            // Actualizar vista visual
            modeloArbol.nodeChanged(nodoVisualSeleccionado);
            
            // Actualizar HTML
            actualizarVistaHTML();
            
            JOptionPane.showMessageDialog(this, 
                "Atributos actualizados exitosamente.",
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Actualiza la vista HTML con el código generado del árbol DOM
     */
    private void actualizarVistaHTML() {
        String htmlGenerado = "<!DOCTYPE html>\n" + raiz.generarHTML(0);
        vistaHTML.setText(htmlGenerado);
        vistaHTML.setCaretPosition(0); // Scroll al inicio
    }
    
    /**
     * Exporta el código HTML a un archivo
     */
    private void exportarHTML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar HTML");
        fileChooser.setSelectedFile(new File("mi_pagina.html"));
        
        int resultado = fileChooser.showSaveDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write("<!DOCTYPE html>\n");
                writer.write(raiz.generarHTML(0));
                
                JOptionPane.showMessageDialog(this, 
                    "HTML exportado exitosamente a:\n" + archivo.getAbsolutePath(),
                    "Exportación Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al exportar el archivo:\n" + e.getMessage(),
                    "Error de Exportación", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Limpia el árbol y lo reinicia con estructura básica
     */
    private void limpiarArbol() {
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de limpiar todo el árbol? Esta acción no se puede deshacer.",
            "Confirmar Limpieza", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            inicializarArbolDOM();
            modeloArbol.setRoot(crearNodoVisual(raiz));
            
            // Expandir todos los nodos
            for (int i = 0; i < arbolVisual.getRowCount(); i++) {
                arbolVisual.expandRow(i);
            }
            
            actualizarVistaHTML();
            
            JOptionPane.showMessageDialog(this, 
                "Árbol reiniciado con estructura HTML básica.",
                "Limpieza Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Método main: punto de entrada de la aplicación
     */
    public static void main(String[] args) {
        // Usar look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new SimuladorDOM());
    }
}
