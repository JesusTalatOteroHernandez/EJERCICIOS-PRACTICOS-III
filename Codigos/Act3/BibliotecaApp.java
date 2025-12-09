package Act3;

/**
 *
 * @author Jesus Talat Otero Hernandez
 * @email 1224100702.jtoh@gmail.com
 */

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;

/**
 * BibliotecaApp: Aplicación principal con interfaz gráfica Swing
 * para gestionar una biblioteca digital usando conjuntos (Set) de Java.
 * 
 * Características:
 * - Gestión de libros con 5 datos (ISBN, título, autor, año, categoría)
 * - 6+ operaciones con conjuntos (add, remove, contains, union, intersection, difference, etc.)
 * - Interfaz intuitiva con pestañas
 * - Visualización en tablas
 * - Operaciones de conjunto con colección temporal
 */
public class BibliotecaApp extends JFrame {
    
    // Gestor de la biblioteca (usa HashSet, TreeSet, LinkedHashSet)
    private GestorBiblioteca gestor;
    
    // Componentes de la interfaz
    private JTabbedPane tabbedPane;
    
    // Pestaña 1: Gestión de Libros
    private JTable tablaPrincipal;
    private DefaultTableModel modeloTablaPrincipal;
    private JTextField txtISBN, txtTitulo, txtAutor, txtAnio;
    private JComboBox<String> comboCategoria;
    
    // Pestaña 2: Operaciones con Conjuntos
    private JTable tablaOrdenada;
    private DefaultTableModel modeloTablaOrdenada;
    private JTable tablaTemporal;
    private DefaultTableModel modeloTablaTemporal;
    private JTextArea txtResultadosOperaciones;
    
    // Pestaña 3: Búsqueda y Filtros
    private JTable tablaResultados;
    private DefaultTableModel modeloTablaResultados;
    private JTextField txtBusqueda;
    private JComboBox<String> comboCategoriaFiltro;
    
    // Colores de la aplicación
    private final Color COLOR_PRIMARY = new Color(63, 81, 181);
    private final Color COLOR_ACCENT = new Color(255, 64, 129);
    private final Color COLOR_SUCCESS = new Color(76, 175, 80);
    private final Color COLOR_WARNING = new Color(255, 152, 0);
    private final Color COLOR_DANGER = new Color(244, 67, 54);
    
    /**
     * Constructor principal
     */
    public BibliotecaApp() {
        super("📚 Biblioteca Digital - Gestión con Conjuntos de Java");
        
        // Inicializar el gestor de biblioteca
        gestor = new GestorBiblioteca();
        
        // Configurar la interfaz
        configurarInterfaz();
        
        // Agregar libros de ejemplo
        agregarLibrosDeEjemplo();
        
        // Configuración de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
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
        
        // Panel central: Pestañas
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        
        // Pestaña 1: Gestión de Libros
        tabbedPane.addTab("📖 Gestión de Libros", crearPestanaGestion());
        
        // Pestaña 2: Operaciones con Conjuntos
        tabbedPane.addTab("🔄 Operaciones de Conjuntos", crearPestanaOperaciones());
        
        // Pestaña 3: Búsqueda y Filtros
        tabbedPane.addTab("🔍 Búsqueda y Filtros", crearPestanaBusqueda());
        
        // Pestaña 4: Estadísticas
        tabbedPane.addTab("📊 Estadísticas", crearPestanaEstadisticas());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Panel inferior: Barra de estado
        add(crearBarraEstado(), BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel de título superior
     */
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_PRIMARY);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("📚 Biblioteca Digital con Conjuntos");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        
        JLabel subtitulo = new JLabel("Gestión avanzada usando HashSet, TreeSet y LinkedHashSet");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(200, 200, 255));
        
        JPanel panelTexto = new JPanel(new GridLayout(2, 1));
        panelTexto.setOpaque(false);
        panelTexto.add(titulo);
        panelTexto.add(subtitulo);
        
        panel.add(panelTexto, BorderLayout.WEST);
        
        return panel;
    }
    
    /**
     * Crea la pestaña de gestión de libros
     */
    private JPanel crearPestanaGestion() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Panel izquierdo: Formulario de entrada
        JPanel panelFormulario = crearPanelFormulario();
        
        // Panel derecho: Tabla de libros
        JPanel panelTabla = crearPanelTablaPrincipal();
        
        // Dividir la pantalla
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                                               panelFormulario, panelTabla);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.3);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel de formulario para agregar libros
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
            "Agregar Nuevo Libro",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        // Panel de campos
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // ISBN
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        txtISBN = new JTextField(15);
        panelCampos.add(txtISBN, gbc);
        
        // Título
        gbc.gridx = 0; gbc.gridy = 1;
        panelCampos.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        txtTitulo = new JTextField(15);
        panelCampos.add(txtTitulo, gbc);
        
        // Autor
        gbc.gridx = 0; gbc.gridy = 2;
        panelCampos.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        txtAutor = new JTextField(15);
        panelCampos.add(txtAutor, gbc);
        
        // Año
        gbc.gridx = 0; gbc.gridy = 3;
        panelCampos.add(new JLabel("Año:"), gbc);
        gbc.gridx = 1;
        txtAnio = new JTextField(15);
        panelCampos.add(txtAnio, gbc);
        
        // Categoría
        gbc.gridx = 0; gbc.gridy = 4;
        panelCampos.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        comboCategoria = new JComboBox<>();
        actualizarComboCategorias();
        panelCampos.add(comboCategoria, gbc);
        
        panel.add(panelCampos, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton btnAgregar = crearBoton("➕ Agregar a Principal", COLOR_SUCCESS);
        btnAgregar.addActionListener(e -> agregarLibro());
        
        JButton btnAgregarTemp = crearBoton("📋 Agregar a Temporal", COLOR_WARNING);
        btnAgregarTemp.addActionListener(e -> agregarLibroTemporal());
        
        JButton btnEliminar = crearBoton("🗑️ Eliminar Seleccionado", COLOR_DANGER);
        btnEliminar.addActionListener(e -> eliminarLibroSeleccionado());
        
        JButton btnLimpiar = crearBoton("🔄 Limpiar Formulario", COLOR_PRIMARY);
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnAgregarTemp);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        panel.add(panelBotones, BorderLayout.CENTER);
        
        // Información sobre conjuntos
        JTextArea txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        txtInfo.setBackground(new Color(240, 248, 255));
        txtInfo.setText("ℹ️ OPERACIONES CON CONJUNTOS:\n\n" +
                       "• HashSet: Sin orden, O(1)\n" +
                       "• TreeSet: Ordenado, O(log n)\n" +
                       "• LinkedHashSet: Orden inserción\n\n" +
                       "No se permiten ISBN duplicados");
        txtInfo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        panel.add(txtInfo, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel con la tabla principal de libros
     */
    private JPanel crearPanelTablaPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARY, 2),
            "Colección Principal (HashSet)",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        // Crear tabla
        String[] columnas = {"ISBN", "Título", "Autor", "Año", "Categoría"};
        modeloTablaPrincipal = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPrincipal = new JTable(modeloTablaPrincipal);
        tablaPrincipal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrincipal.setRowHeight(25);
        tablaPrincipal.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scroll = new JScrollPane(tablaPrincipal);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea la pestaña de operaciones con conjuntos
     */
    private JPanel crearPestanaOperaciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Panel superior: Tablas
        JPanel panelTablas = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Tabla ordenada (TreeSet)
        JPanel panelOrdenada = crearPanelTablaOrdenada();
        panelTablas.add(panelOrdenada);
        
        // Tabla temporal (LinkedHashSet)
        JPanel panelTemporal = crearPanelTablaTemporal();
        panelTablas.add(panelTemporal);
        
        // Panel inferior: Operaciones y resultados
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        
        // Botones de operaciones
        JPanel panelBotones = crearPanelOperacionesConjuntos();
        panelInferior.add(panelBotones, BorderLayout.NORTH);
        
        // Área de resultados
        txtResultadosOperaciones = new JTextArea(10, 40);
        txtResultadosOperaciones.setEditable(false);
        txtResultadosOperaciones.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtResultadosOperaciones.setBorder(BorderFactory.createTitledBorder("Resultados de Operaciones"));
        JScrollPane scrollResultados = new JScrollPane(txtResultadosOperaciones);
        panelInferior.add(scrollResultados, BorderLayout.CENTER);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                                               panelTablas, panelInferior);
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.5);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel con la tabla ordenada (TreeSet)
     */
    private JPanel crearPanelTablaOrdenada() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_SUCCESS, 2),
            "Colección Ordenada (TreeSet - por Título)",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13)
        ));
        
        String[] columnas = {"ISBN", "Título", "Autor", "Año", "Categoría"};
        modeloTablaOrdenada = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaOrdenada = new JTable(modeloTablaOrdenada);
        tablaOrdenada.setRowHeight(25);
        
        JScrollPane scroll = new JScrollPane(tablaOrdenada);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel con la tabla temporal (LinkedHashSet)
     */
    private JPanel crearPanelTablaTemporal() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_WARNING, 2),
            "Colección Temporal (LinkedHashSet)",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13)
        ));
        
        String[] columnas = {"ISBN", "Título", "Autor", "Año", "Categoría"};
        modeloTablaTemporal = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaTemporal = new JTable(modeloTablaTemporal);
        tablaTemporal.setRowHeight(25);
        
        JScrollPane scroll = new JScrollPane(tablaTemporal);
        panel.add(scroll, BorderLayout.CENTER);
        
        JButton btnVaciarTemp = crearBoton("🗑️ Vaciar Temporal", COLOR_DANGER);
        btnVaciarTemp.addActionListener(e -> vaciarTemporal());
        panel.add(btnVaciarTemp, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel con botones de operaciones de conjuntos
     */
    private JPanel crearPanelOperacionesConjuntos() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // OPERACIÓN 4: Unión
        JButton btnUnion = crearBoton("∪ Unión (Principal ∪ Temporal)", COLOR_SUCCESS);
        btnUnion.addActionListener(e -> operacionUnion());
        panel.add(btnUnion);
        
        // OPERACIÓN 5: Intersección
        JButton btnInterseccion = crearBoton("∩ Intersección (Principal ∩ Temporal)", COLOR_PRIMARY);
        btnInterseccion.addActionListener(e -> operacionInterseccion());
        panel.add(btnInterseccion);
        
        // OPERACIÓN 6: Diferencia
        JButton btnDiferencia = crearBoton("− Diferencia (Principal − Temporal)", COLOR_ACCENT);
        btnDiferencia.addActionListener(e -> operacionDiferencia());
        panel.add(btnDiferencia);
        
        // OPERACIÓN 7: Subconjunto
        JButton btnSubconjunto = crearBoton("⊆ ¿Temporal ⊆ Principal?", COLOR_WARNING);
        btnSubconjunto.addActionListener(e -> operacionSubconjunto());
        panel.add(btnSubconjunto);
        
        // Fusionar temporal con principal
        JButton btnFusionar = crearBoton("🔀 Fusionar Temporal → Principal", new Color(156, 39, 176));
        btnFusionar.addActionListener(e -> fusionarTemporal());
        panel.add(btnFusionar);
        
        // Actualizar vistas
        JButton btnActualizar = crearBoton("🔄 Actualizar Vistas", new Color(96, 125, 139));
        btnActualizar.addActionListener(e -> actualizarTodasLasTablas());
        panel.add(btnActualizar);
        
        // Vaciar principal
        JButton btnVaciarPrincipal = crearBoton("🗑️ Vaciar Principal", COLOR_DANGER);
        btnVaciarPrincipal.addActionListener(e -> vaciarPrincipal());
        panel.add(btnVaciarPrincipal);
        
        // Tamaños
        JButton btnTamanos = crearBoton("📊 Ver Tamaños", new Color(0, 150, 136));
        btnTamanos.addActionListener(e -> mostrarTamanos());
        panel.add(btnTamanos);
        
        return panel;
    }
    
    /**
     * Crea la pestaña de búsqueda y filtros
     */
    private JPanel crearPestanaBusqueda() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Panel superior: Controles de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda y Filtros"));
        
        panelBusqueda.add(new JLabel("Buscar por texto:"));
        txtBusqueda = new JTextField(20);
        panelBusqueda.add(txtBusqueda);
        
        JButton btnBuscarTexto = crearBoton("🔍 Buscar", COLOR_PRIMARY);
        btnBuscarTexto.addActionListener(e -> buscarPorTexto());
        panelBusqueda.add(btnBuscarTexto);
        
        panelBusqueda.add(new JLabel("    Filtrar por categoría:"));
        comboCategoriaFiltro = new JComboBox<>();
        comboCategoriaFiltro.addItem("-- Todas --");
        for (String cat : gestor.getCategoriasDisponibles()) {
            comboCategoriaFiltro.addItem(cat);
        }
        panelBusqueda.add(comboCategoriaFiltro);
        
        JButton btnFiltrar = crearBoton("🔽 Filtrar", COLOR_SUCCESS);
        btnFiltrar.addActionListener(e -> filtrarPorCategoria());
        panelBusqueda.add(btnFiltrar);
        
        panel.add(panelBusqueda, BorderLayout.NORTH);
        
        // Tabla de resultados
        String[] columnas = {"ISBN", "Título", "Autor", "Año", "Categoría"};
        modeloTablaResultados = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaResultados = new JTable(modeloTablaResultados);
        tablaResultados.setRowHeight(25);
        
        JScrollPane scroll = new JScrollPane(tablaResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea la pestaña de estadísticas
     */
    private JPanel crearPestanaEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextArea txtEstadisticas = new JTextArea();
        txtEstadisticas.setEditable(false);
        txtEstadisticas.setFont(new Font("Courier New", Font.PLAIN, 14));
        txtEstadisticas.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(txtEstadisticas);
        panel.add(scroll, BorderLayout.CENTER);
        
        JButton btnActualizarStats = crearBoton("🔄 Actualizar Estadísticas", COLOR_PRIMARY);
        btnActualizarStats.addActionListener(e -> {
            txtEstadisticas.setText(gestor.obtenerEstadisticas());
        });
        panel.add(btnActualizarStats, BorderLayout.SOUTH);
        
        // Cargar estadísticas iniciales
        txtEstadisticas.setText(gestor.obtenerEstadisticas());
        
        return panel;
    }
    
    /**
     * Crea la barra de estado inferior
     */
    private JPanel crearBarraEstado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 10, 5, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel lblEstado = new JLabel("✅ Aplicación lista - Usando HashSet, TreeSet y LinkedHashSet");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(lblEstado, BorderLayout.WEST);
        
        return panel;
    }
    
    /**
     * Crea un botón estilizado
     */
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 11));
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
    
    // ========== OPERACIONES DE LA APLICACIÓN ==========
    
    /**
     * OPERACIÓN 1: Agrega un libro a la colección principal
     */
    private void agregarLibro() {
        try {
            Libro libro = obtenerLibroDesdeFormulario();
            boolean agregado = gestor.agregarLibro(libro);
            
            if (agregado) {
                actualizarTodasLasTablas();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this,
                    "✅ Libro agregado exitosamente a la colección principal (HashSet).",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "⚠️ El libro con ISBN " + libro.getIsbn() + " ya existe.\n" +
                    "Los conjuntos (Set) no permiten duplicados.",
                    "Duplicado",
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Agrega un libro a la colección temporal
     */
    private void agregarLibroTemporal() {
        try {
            Libro libro = obtenerLibroDesdeFormulario();
            boolean agregado = gestor.agregarATemporales(libro);
            
            if (agregado) {
                actualizarTodasLasTablas();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this,
                    "✅ Libro agregado a la colección temporal (LinkedHashSet).",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "⚠️ El libro ya existe en la colección temporal.",
                    "Duplicado",
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * OPERACIÓN 2: Elimina el libro seleccionado
     */
    private void eliminarLibroSeleccionado() {
        int fila = tablaPrincipal.getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Por favor selecciona un libro de la tabla.",
                "Selección Requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String isbn = (String) modeloTablaPrincipal.getValueAt(fila, 0);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de eliminar el libro con ISBN " + isbn + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = gestor.eliminarPorISBN(isbn);
            
            if (eliminado) {
                actualizarTodasLasTablas();
                JOptionPane.showMessageDialog(this,
                    "✅ Libro eliminado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * OPERACIÓN 4: Unión de conjuntos
     */
    private void operacionUnion() {
        Set<Libro> temporal = gestor.getColeccionTemporal();
        Set<Libro> union = gestor.unionConOtraColeccion(temporal);
        
        StringBuilder resultado = new StringBuilder();
        resultado.append("=== OPERACIÓN: UNIÓN (Principal ∪ Temporal) ===\n\n");
        resultado.append("Tamaño Principal: ").append(gestor.obtenerTamano()).append("\n");
        resultado.append("Tamaño Temporal: ").append(temporal.size()).append("\n");
        resultado.append("Tamaño Unión: ").append(union.size()).append("\n\n");
        resultado.append("Libros en la Unión:\n");
        
        for (Libro libro : union) {
            resultado.append("  • ").append(libro.toString()).append("\n");
        }
        
        txtResultadosOperaciones.setText(resultado.toString());
        
        JOptionPane.showMessageDialog(this,
            "✅ Unión calculada: " + union.size() + " libros únicos",
            "Operación Unión",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * OPERACIÓN 5: Intersección de conjuntos
     */
    private void operacionInterseccion() {
        Set<Libro> temporal = gestor.getColeccionTemporal();
        Set<Libro> interseccion = gestor.interseccionConOtraColeccion(temporal);
        
        StringBuilder resultado = new StringBuilder();
        resultado.append("=== OPERACIÓN: INTERSECCIÓN (Principal ∩ Temporal) ===\n\n");
        resultado.append("Tamaño Principal: ").append(gestor.obtenerTamano()).append("\n");
        resultado.append("Tamaño Temporal: ").append(temporal.size()).append("\n");
        resultado.append("Tamaño Intersección: ").append(interseccion.size()).append("\n\n");
        resultado.append("Libros comunes (en ambas colecciones):\n");
        
        if (interseccion.isEmpty()) {
            resultado.append("  (No hay libros comunes)\n");
        } else {
            for (Libro libro : interseccion) {
                resultado.append("  • ").append(libro.toString()).append("\n");
            }
        }
        
        txtResultadosOperaciones.setText(resultado.toString());
        
        JOptionPane.showMessageDialog(this,
            "✅ Intersección calculada: " + interseccion.size() + " libros comunes",
            "Operación Intersección",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * OPERACIÓN 6: Diferencia de conjuntos
     */
    private void operacionDiferencia() {
        Set<Libro> temporal = gestor.getColeccionTemporal();
        Set<Libro> diferencia = gestor.diferenciaConOtraColeccion(temporal);
        
        StringBuilder resultado = new StringBuilder();
        resultado.append("=== OPERACIÓN: DIFERENCIA (Principal − Temporal) ===\n\n");
        resultado.append("Tamaño Principal: ").append(gestor.obtenerTamano()).append("\n");
        resultado.append("Tamaño Temporal: ").append(temporal.size()).append("\n");
        resultado.append("Tamaño Diferencia: ").append(diferencia.size()).append("\n\n");
        resultado.append("Libros que están en Principal pero NO en Temporal:\n");
        
        if (diferencia.isEmpty()) {
            resultado.append("  (Todos los libros de Principal están en Temporal)\n");
        } else {
            for (Libro libro : diferencia) {
                resultado.append("  • ").append(libro.toString()).append("\n");
            }
        }
        
        txtResultadosOperaciones.setText(resultado.toString());
        
        JOptionPane.showMessageDialog(this,
            "✅ Diferencia calculada: " + diferencia.size() + " libros únicos en Principal",
            "Operación Diferencia",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * OPERACIÓN 7: Verificar subconjunto
     */
    private void operacionSubconjunto() {
        Set<Libro> temporal = gestor.getColeccionTemporal();
        boolean esSubconjunto = gestor.esSubconjunto(temporal);
        
        StringBuilder resultado = new StringBuilder();
        resultado.append("=== OPERACIÓN: SUBCONJUNTO (¿Temporal ⊆ Principal?) ===\n\n");
        resultado.append("Tamaño Principal: ").append(gestor.obtenerTamano()).append("\n");
        resultado.append("Tamaño Temporal: ").append(temporal.size()).append("\n\n");
        resultado.append("¿Temporal es subconjunto de Principal? ");
        resultado.append(esSubconjunto ? "SÍ ✓" : "NO ✗").append("\n\n");
        
        if (esSubconjunto) {
            resultado.append("Todos los libros de Temporal están en Principal.\n");
        } else {
            resultado.append("Hay libros en Temporal que NO están en Principal.\n");
        }
        
        txtResultadosOperaciones.setText(resultado.toString());
        
        JOptionPane.showMessageDialog(this,
            esSubconjunto ? 
                "✅ Temporal ES subconjunto de Principal" : 
                "❌ Temporal NO es subconjunto de Principal",
            "Operación Subconjunto",
            esSubconjunto ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Fusiona la colección temporal con la principal
     */
    private void fusionarTemporal() {
        int agregados = gestor.fusionarColeccionTemporal();
        actualizarTodasLasTablas();
        
        JOptionPane.showMessageDialog(this,
            "✅ Fusión completada.\n" +
            agregados + " libros nuevos agregados a Principal.\n" +
            "Colección Temporal ha sido vaciada.",
            "Fusión Exitosa",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * OPERACIÓN 8: Vacía la colección temporal
     */
    private void vaciarTemporal() {
        if (gestor.obtenerTamanoTemporal() == 0) {
            JOptionPane.showMessageDialog(this,
                "ℹ️ La colección temporal ya está vacía.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de vaciar la colección temporal?\n" +
            "Se eliminarán " + gestor.obtenerTamanoTemporal() + " libros.",
            "Confirmar Vaciado",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            gestor.vaciarColeccionTemporal();
            actualizarTodasLasTablas();
            JOptionPane.showMessageDialog(this,
                "✅ Colección temporal vaciada.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * OPERACIÓN 8: Vacía la colección principal
     */
    private void vaciarPrincipal() {
        if (gestor.estaVacia()) {
            JOptionPane.showMessageDialog(this,
                "ℹ️ La colección principal ya está vacía.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "⚠️ ¿Estás seguro de vaciar TODA la biblioteca?\n" +
            "Se eliminarán " + gestor.obtenerTamano() + " libros.\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar Vaciado Total",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            gestor.vaciarBiblioteca();
            actualizarTodasLasTablas();
            JOptionPane.showMessageDialog(this,
                "✅ Biblioteca vaciada completamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * OPERACIÓN 9: Muestra los tamaños de las colecciones
     */
    private void mostrarTamanos() {
        StringBuilder info = new StringBuilder();
        info.append("=== TAMAÑOS DE LAS COLECCIONES ===\n\n");
        info.append("📚 Colección Principal (HashSet): ").append(gestor.obtenerTamano()).append(" libros\n");
        info.append("📋 Colección Temporal (LinkedHashSet): ").append(gestor.obtenerTamanoTemporal()).append(" libros\n");
        info.append("🔢 Total único: ").append(gestor.unionConOtraColeccion(gestor.getColeccionTemporal()).size()).append(" libros\n");
        info.append("⭐ Categorías disponibles: ").append(gestor.getCategoriasDisponibles().size());
        
        txtResultadosOperaciones.setText(info.toString());
        
        JOptionPane.showMessageDialog(this,
            info.toString(),
            "Tamaños de Colecciones",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * OPERACIÓN 3: Busca libros por texto
     */
    private void buscarPorTexto() {
        String texto = txtBusqueda.getText().trim();
        
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Por favor ingresa un texto para buscar.",
                "Campo Vacío",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Set<Libro> resultados = gestor.buscarPorTexto(texto);
        
        modeloTablaResultados.setRowCount(0);
        for (Libro libro : resultados) {
            modeloTablaResultados.addRow(libro.toArray());
        }
        
        JOptionPane.showMessageDialog(this,
            "🔍 Búsqueda completada.\n" +
            "Encontrados: " + resultados.size() + " libros",
            "Resultados de Búsqueda",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Filtra libros por categoría
     */
    private void filtrarPorCategoria() {
        String categoria = (String) comboCategoriaFiltro.getSelectedItem();
        
        if (categoria.equals("-- Todas --")) {
            modeloTablaResultados.setRowCount(0);
            for (Libro libro : gestor.getColeccionPrincipal()) {
                modeloTablaResultados.addRow(libro.toArray());
            }
        } else {
            Set<Libro> resultados = gestor.obtenerLibrosPorCategoria(categoria);
            modeloTablaResultados.setRowCount(0);
            for (Libro libro : resultados) {
                modeloTablaResultados.addRow(libro.toArray());
            }
            
            JOptionPane.showMessageDialog(this,
                "🔽 Filtrado por categoría: " + categoria + "\n" +
                "Encontrados: " + resultados.size() + " libros",
                "Resultados del Filtro",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    /**
     * Obtiene un libro desde los campos del formulario
     */
    private Libro obtenerLibroDesdeFormulario() throws Exception {
        String isbn = txtISBN.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String anioStr = txtAnio.getText().trim();
        String categoria = (String) comboCategoria.getSelectedItem();
        
        if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty() || anioStr.isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }
        
        int anio;
        try {
            anio = Integer.parseInt(anioStr);
            if (anio < 1000 || anio > 2100) {
                throw new Exception("Año inválido (debe estar entre 1000 y 2100)");
            }
        } catch (NumberFormatException e) {
            throw new Exception("El año debe ser un número válido");
        }
        
        return new Libro(isbn, titulo, autor, anio, categoria);
    }
    
    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarFormulario() {
        txtISBN.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAnio.setText("");
        comboCategoria.setSelectedIndex(0);
        txtISBN.requestFocus();
    }
    
    /**
     * Actualiza todas las tablas con los datos actuales
     */
    private void actualizarTodasLasTablas() {
        // Actualizar tabla principal (HashSet)
        modeloTablaPrincipal.setRowCount(0);
        for (Libro libro : gestor.getColeccionPrincipal()) {
            modeloTablaPrincipal.addRow(libro.toArray());
        }
        
        // Actualizar tabla ordenada (TreeSet)
        modeloTablaOrdenada.setRowCount(0);
        for (Libro libro : gestor.getColeccionOrdenada()) {
            modeloTablaOrdenada.addRow(libro.toArray());
        }
        
        // Actualizar tabla temporal (LinkedHashSet)
        modeloTablaTemporal.setRowCount(0);
        for (Libro libro : gestor.getColeccionTemporal()) {
            modeloTablaTemporal.addRow(libro.toArray());
        }
    }
    
    /**
     * Actualiza el combo de categorías
     */
    private void actualizarComboCategorias() {
        comboCategoria.removeAllItems();
        for (String cat : gestor.getCategoriasDisponibles()) {
            comboCategoria.addItem(cat);
        }
    }
    
    /**
     * Agrega libros de ejemplo para demostración
     */
    private void agregarLibrosDeEjemplo() {
        gestor.agregarLibro(new Libro("978-0-14-143951-8", "1984", "George Orwell", 1949, "Ficción"));
        gestor.agregarLibro(new Libro("978-0-06-112008-4", "To Kill a Mockingbird", "Harper Lee", 1960, "Ficción"));
        gestor.agregarLibro(new Libro("978-0-7432-7356-5", "A Brief History of Time", "Stephen Hawking", 1988, "Ciencia"));
        gestor.agregarLibro(new Libro("978-0-553-38016-3", "Sapiens", "Yuval Noah Harari", 2011, "Historia"));
        gestor.agregarLibro(new Libro("978-0-452-28423-4", "The Art of War", "Sun Tzu", -500, "Filosofía"));
        gestor.agregarLibro(new Libro("978-0-307-47424-7", "Thinking, Fast and Slow", "Daniel Kahneman", 2011, "Ciencia"));
        gestor.agregarLibro(new Libro("978-1-59420-285-8", "Clean Code", "Robert C. Martin", 2008, "Tecnología"));
        gestor.agregarLibro(new Libro("978-0-596-00907-6", "Head First Design Patterns", "Eric Freeman", 2004, "Tecnología"));
        
        // Agregar algunos a temporal para demostración
        gestor.agregarATemporales(new Libro("978-0-14-143951-8", "1984", "George Orwell", 1949, "Ficción"));
        gestor.agregarATemporales(new Libro("978-0-316-76948-0", "The Catcher in the Rye", "J.D. Salinger", 1951, "Ficción"));
        
        actualizarTodasLasTablas();
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
        
        SwingUtilities.invokeLater(() -> new BibliotecaApp());
    }
}