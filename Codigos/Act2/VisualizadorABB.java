package Act2;

/**
 *
 * @author Jesus Talat Otero Hernandez
 * @email 1224100702.jtoh@gmail.com
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * VisualizadorABB: Aplicación principal para visualizar y manipular
 * un Árbol Binario de Búsqueda (ABB) de forma interactiva.
 * 
 * Operaciones soportadas:
 * - Insertar nodos
 * - Eliminar nodos
 * - Buscar nodos (con resaltado visual)
 * - Recorridos: Inorden, Preorden, Postorden
 * - Limpiar árbol
 * - Pruebas automáticas predefinidas
 */
public class VisualizadorABB extends JFrame {
    
    // Árbol Binario de Búsqueda
    private ArbolBinarioBusqueda arbol;
    
    // Componentes de la interfaz
    private PanelArbol panelArbol;
    private JTextField txtValor;
    private JTextArea txtRecorridos;
    private JLabel lblEstadisticas;
    
    // Constantes de visualización
    private static final int RADIO_NODO = 25;
    private static final int ESPACIO_HORIZONTAL = 60;
    private static final int ESPACIO_VERTICAL = 80;
    
    /**
     * Constructor principal
     */
    public VisualizadorABB() {
        super("Visualizador de Árbol Binario de Búsqueda (ABB)");
        
        arbol = new ArbolBinarioBusqueda();
        
        configurarInterfaz();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Configura toda la interfaz gráfica
     */
    private void configurarInterfaz() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior: Título
        add(crearPanelTitulo(), BorderLayout.NORTH);
        
        // Panel central: Visualización del árbol
        panelArbol = new PanelArbol();
        JScrollPane scrollArbol = new JScrollPane(panelArbol);
        scrollArbol.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 2),
            "Visualización del Árbol",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        add(scrollArbol, BorderLayout.CENTER);
        
        // Panel derecho: Controles y recorridos
        add(crearPanelDerecho(), BorderLayout.EAST);
        
        // Panel inferior: Estadísticas
        lblEstadisticas = new JLabel("Nodos: 0 | Altura: 0");
        lblEstadisticas.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEstadisticas.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(lblEstadisticas, BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel de título
     */
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(33, 150, 243));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("🌲 Árbol Binario de Búsqueda (ABB)");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        
        JLabel subtitulo = new JLabel("Propie dad: Izquierda < Raíz < Derecha");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(230, 230, 230));
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(subtitulo, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel derecho con controles
     */
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setPreferredSize(new Dimension(350, 0));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Panel de operaciones básicas
        panel.add(crearPanelOperaciones(), BorderLayout.NORTH);
        
        // Panel de recorridos
        panel.add(crearPanelRecorridos(), BorderLayout.CENTER);
        
        // Panel de pruebas
        panel.add(crearPanelPruebas(), BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel de operaciones básicas
     */
    private JPanel crearPanelOperaciones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Operaciones Básicas",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Campo de valor
        JLabel lblValor = new JLabel("Valor:");
        lblValor.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panel.add(lblValor, gbc);
        
        txtValor = new JTextField();
        txtValor.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.gridwidth = 2;
        panel.add(txtValor, gbc);
        
        // Botones de operación
        gbc.gridwidth = 1;
        gbc.weightx = 0.33;
        
        JButton btnInsertar = crearBoton("+ Insertar", new Color(76, 175, 80));
        btnInsertar.addActionListener(e -> insertarValor());
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(btnInsertar, gbc);
        
        JButton btnEliminar = crearBoton("- Eliminar", new Color(244, 67, 54));
        btnEliminar.addActionListener(e -> eliminarValor());
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(btnEliminar, gbc);
        
        JButton btnBuscar = crearBoton("🔍 Buscar", new Color(255, 152, 0));
        btnBuscar.addActionListener(e -> buscarValor());
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(btnBuscar, gbc);
        
        JButton btnLimpiar = crearBoton("🗑️ Limpiar Árbol", new Color(158, 158, 158));
        btnLimpiar.addActionListener(e -> limpiarArbol());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        panel.add(btnLimpiar, gbc);
        
        return panel;
    }
    
    /**
     * Crea el panel de recorridos
     */
    private JPanel crearPanelRecorridos() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Recorridos del Árbol",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13)
        ));
        
        txtRecorridos = new JTextArea();
        txtRecorridos.setEditable(false);
        txtRecorridos.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtRecorridos.setBackground(new Color(245, 245, 245));
        txtRecorridos.setLineWrap(true);
        txtRecorridos.setWrapStyleWord(true);
        
        JScrollPane scroll = new JScrollPane(txtRecorridos);
        scroll.setPreferredSize(new Dimension(0, 150));
        panel.add(scroll, BorderLayout.CENTER);
        
        // Botones de recorrido
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 5, 5));
        
        JButton btnInorden = crearBotonPequeno("Inorden", new Color(103, 58, 183));
        btnInorden.addActionListener(e -> mostrarRecorridoInorden());
        panelBotones.add(btnInorden);
        
        JButton btnPreorden = crearBotonPequeno("Preorden", new Color(63, 81, 181));
        btnPreorden.addActionListener(e -> mostrarRecorridoPreorden());
        panelBotones.add(btnPreorden);
        
        JButton btnPostorden = crearBotonPequeno("Postorden", new Color(33, 150, 243));
        btnPostorden.addActionListener(e -> mostrarRecorridoPostorden());
        panelBotones.add(btnPostorden);
        
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel de pruebas automáticas
     */
    private JPanel crearPanelPruebas() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Pruebas Automáticas",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13)
        ));
        
        JButton btnP11 = crearBotonPequeno("P1.1: Árbol Balanceado", new Color(0, 150, 136));
        btnP11.addActionListener(e -> ejecutarPrueba11());
        panel.add(btnP11);
        
        JButton btnP12 = crearBotonPequeno("P1.2: Degenerado Derecha", new Color(0, 150, 136));
        btnP12.addActionListener(e -> ejecutarPrueba12());
        panel.add(btnP12);
        
        JButton btnP13 = crearBotonPequeno("P1.3: Degenerado Izquierda", new Color(0, 150, 136));
        btnP13.addActionListener(e -> ejecutarPrueba13());
        panel.add(btnP13);
        
        JButton btnP14 = crearBotonPequeno("P1.4: Duplicados", new Color(0, 150, 136));
        btnP14.addActionListener(e -> ejecutarPrueba14());
        panel.add(btnP14);
        
        return panel;
    }
    
    /**
     * Crea un botón estilizado
     */
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
     * Crea un botón pequeño
     */
    private JButton crearBotonPequeno(String texto, Color color) {
        JButton boton = crearBoton(texto, color);
        boton.setFont(new Font("Arial", Font.PLAIN, 11));
        return boton;
    }
    
    // ========== OPERACIONES DEL ÁRBOL ==========
    
    /**
     * Inserta un valor en el árbol
     */
    private void insertarValor() {
        try {
            String texto = txtValor.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor ingresa un valor.",
                    "Campo Vacío", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int valor = Integer.parseInt(texto);
            boolean insertado = arbol.insertar(valor);
            
            if (insertado) {
                actualizarVisualizacion();
                txtValor.setText("");
                txtValor.requestFocus();
                JOptionPane.showMessageDialog(this, 
                    "Valor " + valor + " insertado correctamente.",
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "El valor " + valor + " ya existe en el árbol.\nNo se permiten duplicados.",
                    "Duplicado", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingresa un número entero válido.",
                "Error de Formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Elimina un valor del árbol
     */
    private void eliminarValor() {
        try {
            String texto = txtValor.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor ingresa un valor.",
                    "Campo Vacío", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int valor = Integer.parseInt(texto);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de eliminar el valor " + valor + "?",
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = arbol.eliminar(valor);
                
                if (eliminado) {
                    actualizarVisualizacion();
                    txtValor.setText("");
                    JOptionPane.showMessageDialog(this, 
                        "Valor " + valor + " eliminado correctamente.",
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "El valor " + valor + " no existe en el árbol.",
                        "No Encontrado", 
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingresa un número entero válido.",
                "Error de Formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Busca un valor en el árbol
     */
    private void buscarValor() {
        try {
            String texto = txtValor.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor ingresa un valor.",
                    "Campo Vacío", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int valor = Integer.parseInt(texto);
            boolean encontrado = arbol.buscar(valor);
            
            actualizarVisualizacion();
            
            if (encontrado) {
                JOptionPane.showMessageDialog(this, 
                    "✓ Valor " + valor + " ENCONTRADO en el árbol.\n(Resaltado en rojo)",
                    "Búsqueda Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "✗ Valor " + valor + " NO ENCONTRADO en el árbol.",
                    "No Encontrado", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingresa un número entero válido.",
                "Error de Formato", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Limpia el árbol completo
     */
    private void limpiarArbol() {
        if (arbol.estaVacio()) {
            JOptionPane.showMessageDialog(this, 
                "El árbol ya está vacío.",
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de limpiar todo el árbol?\nEsta acción no se puede deshacer.",
            "Confirmar Limpieza", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            arbol.limpiar();
            actualizarVisualizacion();
            txtRecorridos.setText("");
            JOptionPane.showMessageDialog(this, 
                "Árbol limpiado exitosamente.",
                "Limpieza Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // ========== RECORRIDOS ==========
    
    private void mostrarRecorridoInorden() {
        String resultado = arbol.getRecorridoInorden();
        txtRecorridos.setText("INORDEN (Izq - Raíz - Der):\n" + resultado + 
                            "\n\n(Resultado: Valores ordenados de menor a mayor)");
    }
    
    private void mostrarRecorridoPreorden() {
        String resultado = arbol.getRecorridoPreorden();
        txtRecorridos.setText("PREORDEN (Raíz - Izq - Der):\n" + resultado + 
                            "\n\n(Útil para copiar la estructura del árbol)");
    }
    
    private void mostrarRecorridoPostorden() {
        String resultado = arbol.getRecorridoPostorden();
        txtRecorridos.setText("POSTORDEN (Izq - Der - Raíz):\n" + resultado + 
                            "\n\n(Útil para eliminar el árbol, hojas primero)");
    }
    
    // ========== PRUEBAS AUTOMÁTICAS ==========
    
    /**
     * P1.1: Árbol balanceado (50, 30, 70, 20, 40, 60, 80)
     */
    private void ejecutarPrueba11() {
        arbol.limpiar();
        int[] valores = {50, 30, 70, 20, 40, 60, 80};
        for (int v : valores) {
            arbol.insertar(v);
        }
        actualizarVisualizacion();
        mostrarRecorridoInorden();
        JOptionPane.showMessageDialog(this, 
            "P1.1: Árbol Balanceado\nInsertados: 50, 30, 70, 20, 40, 60, 80\n\n" +
            "Resultado esperado: Árbol completo y balanceado\n" +
            "Inorden: 20 30 40 50 60 70 80",
            "Prueba P1.1", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * P1.2: Árbol degenerado a la derecha
     */
    private void ejecutarPrueba12() {
        arbol.limpiar();
        int[] valores = {10, 20, 30, 40, 50, 60, 70};
        for (int v : valores) {
            arbol.insertar(v);
        }
        actualizarVisualizacion();
        mostrarRecorridoInorden();
        JOptionPane.showMessageDialog(this, 
            "P1.2: Árbol Degenerado (Derecha)\nInsertados: 10, 20, 30, 40, 50, 60, 70\n\n" +
            "Resultado esperado: Árbol degenerado (similar a lista enlazada)\n" +
            "Todos los nodos a la derecha\n" +
            "Peor caso de ABB: O(n) en búsqueda",
            "Prueba P1.2", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * P1.3: Árbol degenerado a la izquierda
     */
    private void ejecutarPrueba13() {
        arbol.limpiar();
        int[] valores = {70, 60, 50, 40, 30, 20, 10};
        for (int v : valores) {
            arbol.insertar(v);
        }
        actualizarVisualizacion();
        mostrarRecorridoInorden();
        JOptionPane.showMessageDialog(this, 
            "P1.3: Árbol Degenerado (Izquierda)\nInsertados: 70, 60, 50, 40, 30, 20, 10\n\n" +
            "Resultado esperado: Árbol degenerado (similar a lista enlazada)\n" +
            "Todos los nodos a la izquierda\n" +
            "Peor caso opuesto",
            "Prueba P1.3", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * P1.4: Prueba de duplicados
     */
    private void ejecutarPrueba14() {
        arbol.limpiar();
        arbol.insertar(50);
        boolean duplicado = arbol.insertar(50);
        actualizarVisualizacion();
        JOptionPane.showMessageDialog(this, 
            "P1.4: Prueba de Duplicados\nIntento de insertar: 50, 50\n\n" +
            "Resultado: El segundo 50 fue " + (duplicado ? "ACEPTADO" : "RECHAZADO") + "\n" +
            "Implementación actual: NO se permiten duplicados\n\n" +
            "(Esto cumple con la propiedad estricta del ABB)",
            "Prueba P1.4", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Actualiza la visualización del árbol
     */
    private void actualizarVisualizacion() {
        panelArbol.repaint();
        lblEstadisticas.setText("Nodos: " + arbol.getTotalNodos() + 
                               " | Altura: " + arbol.getAltura());
    }
    
    // ========== PANEL DE VISUALIZACIÓN DEL ÁRBOL ==========
    
    /**
     * Panel personalizado para dibujar el árbol
     */
    private class PanelArbol extends JPanel {
        
        public PanelArbol() {
            setPreferredSize(new Dimension(900, 600));
            setBackground(Color.WHITE);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Activar antialiasing para mejor calidad
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                               RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (!arbol.estaVacio()) {
                int ancho = getWidth();
                calcularPosiciones(arbol.getRaiz(), ancho / 2, 50, ancho / 4);
                dibujarArbol(g2d, arbol.getRaiz());
            } else {
                // Mostrar mensaje cuando el árbol está vacío
                g2d.setFont(new Font("Arial", Font.PLAIN, 18));
                g2d.setColor(Color.GRAY);
                String mensaje = "Árbol vacío - Inserta valores para comenzar";
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(mensaje)) / 2;
                int y = getHeight() / 2;
                g2d.drawString(mensaje, x, y);
            }
        }
        
        /**
         * Calcula las posiciones (x, y) de cada nodo para visualización
         */
        private void calcularPosiciones(NodoABB nodo, int x, int y, int espacioH) {
            if (nodo == null) return;
            
            nodo.setX(x);
            nodo.setY(y);
            
            if (nodo.getIzquierdo() != null) {
                calcularPosiciones(nodo.getIzquierdo(), 
                                 x - espacioH, 
                                 y + ESPACIO_VERTICAL, 
                                 espacioH / 2);
            }
            
            if (nodo.getDerecho() != null) {
                calcularPosiciones(nodo.getDerecho(), 
                                 x + espacioH, 
                                 y + ESPACIO_VERTICAL, 
                                 espacioH / 2);
            }
        }
        
        /**
         * Dibuja el árbol completo
         */
        private void dibujarArbol(Graphics2D g2d, NodoABB nodo) {
            if (nodo == null) return;
            
            // Dibujar líneas a los hijos primero (para que queden atrás)
            if (nodo.getIzquierdo() != null) {
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(nodo.getX(), nodo.getY(), 
                           nodo.getIzquierdo().getX(), 
                           nodo.getIzquierdo().getY());
                dibujarArbol(g2d, nodo.getIzquierdo());
            }
            
            if (nodo.getDerecho() != null) {
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(nodo.getX(), nodo.getY(), 
                           nodo.getDerecho().getX(), 
                           nodo.getDerecho().getY());
                dibujarArbol(g2d, nodo.getDerecho());
            }
            
            // Dibujar el nodo
            dibujarNodo(g2d, nodo);
        }
        
        /**
         * Dibuja un nodo individual
         */
        private void dibujarNodo(Graphics2D g2d, NodoABB nodo) {
            int x = nodo.getX();
            int y = nodo.getY();
            
            // Color del nodo (rojo si está resaltado, azul si no)
            if (nodo.isResaltado()) {
                g2d.setColor(new Color(244, 67, 54)); // Rojo
            } else {
                g2d.setColor(new Color(33, 150, 243)); // Azul
            }
            
            // Dibujar círculo
            g2d.fillOval(x - RADIO_NODO, y - RADIO_NODO, 
                        RADIO_NODO * 2, RADIO_NODO * 2);
            
            // Borde del círculo
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(x - RADIO_NODO, y - RADIO_NODO, 
                        RADIO_NODO * 2, RADIO_NODO * 2);
            
            // Dibujar el valor
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String valor = String.valueOf(nodo.getValor());
            FontMetrics fm = g2d.getFontMetrics();
            int textoX = x - fm.stringWidth(valor) / 2;
            int textoY = y + fm.getAscent() / 2 - 2;
            g2d.drawString(valor, textoX, textoY);
        }
    }
    
    /**
     * Método main: Punto de entrada de la aplicación
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new VisualizadorABB());
    }
}
