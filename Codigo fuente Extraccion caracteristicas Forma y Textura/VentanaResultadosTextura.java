import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaResultadosTextura extends JDialog implements ActionListener{
	private JPanel panelGeneral, panelTabla;
	private JButton aceptar;
	private String[] columna = { "Características", "0°", "45°", "90°", "135°" };
	private Object [][]objeto = new Object[10][5];
	private JScrollPane scrollPane;
  	private JTable tabla;
  	public double [][]MATRIZ_IMAGE;
	public int iniAlto;
	public int iniAncho;
	
	private int REN;
	private int COL;
	private double [][]MATRIZ_ORIGINAL;
	
	public double [][]  MATRIZ_GLCM_GRADO_0;
	public double [][] MATRIZ_GLCM_GRADO_45;
	public double [][] MATRIZ_GLCM_GRADO_90;
	public double [][]MATRIZ_GLCM_GRADO_135;
	
	private int PIXMAX = 0;
	
	public int g0 = 0;
	public int g45 = 0;
	public int g90 = 0;
	public int g135 = 0;
	
	public int DIST;
	
	public VentanaResultadosTextura( double [][]MATRIZ_IMAGE, int iniAlto, int iniAncho, int g0, int g45, int g90, int g135, int DIST ){
		super();
		setTitle( "Extracción de características GLCM" );
		setSize( 600, 450 );
		setLocation( 50, 30 );
		setModal( true );
		
		this.g0 = g0;
		this.g45 = g45;
		this.g90 = g90;
		this.g135 = g135;
		
		this.MATRIZ_ORIGINAL = MATRIZ_IMAGE;
		this.REN = iniAlto;
		this.COL = iniAncho;
		this.DIST = DIST;
		
		panelGeneral = new JPanel();
		panelGeneral.setLayout( null );
		
		getContentPane().add( panelGeneral );
		
		Border etched = BorderFactory.createEtchedBorder();
		TitledBorder tituloBorde = BorderFactory.createTitledBorder(  etched, "Resultados" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		panelTabla = new JPanel( );
		panelTabla.setBorder( tituloBorde );
		panelTabla.setLayout( null );
		panelTabla.setBounds( 20, 20, 550, 370 );
		panelTabla.setBorder(tituloBorde);
		
		panelGeneral.add( panelTabla );
		
		tabla = new JTable( objeto, columna );
        tabla.setBackground(Color.white);
        tabla.setForeground(Color.BLACK);
        tabla.setGridColor(Color.BLUE);
       
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(20,30,505,263);  
        panelTabla.add(scrollPane);
        		
		tabla.setValueAt( "Segundo M. A.", 0, 0 );
		tabla.setValueAt( "Contraste", 1, 0 );
		tabla.setValueAt( "Correlación", 2, 0 );
		tabla.setValueAt( "Momento de la D. I.", 3, 0 );
		tabla.setValueAt( "Varianza", 4, 0 );
		tabla.setValueAt( "Energía", 5, 0 );
		tabla.setValueAt( "Suma Promedio", 6, 0 );
		tabla.setValueAt( "Segundo Entropía", 7, 0 );
		tabla.setValueAt( "Segundo Varianza", 8, 0 );
		tabla.setValueAt( "Entropía", 9, 0 );
		
		aceptar = new JButton( "Aceptar" );
		aceptar.setBounds( 405, 310 , 120, 35 );
		aceptar.setEnabled( true );
		aceptar.addActionListener( this );
		panelTabla.add( aceptar );
		
		realizarOperacion();
		
		this.setResizable( false );
		setVisible( true );
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource().equals( aceptar )){
			this.hide();
		}
	}
	public void realizarOperacion(){
		GLCM glcm = new GLCM( MATRIZ_ORIGINAL, REN , COL );
		PIXMAX = glcm.buscarPixMax() + 1;
	
		if( g0 == 1){
			MATRIZ_GLCM_GRADO_0   = new double[ PIXMAX ][ PIXMAX ];
			MATRIZ_GLCM_GRADO_0   = glcm.calcularMATGLCM( MATRIZ_GLCM_GRADO_0,     0, DIST, PIXMAX );
			NormalizarGLCM NGLCM = new NormalizarGLCM( MATRIZ_GLCM_GRADO_0, PIXMAX );
			tabla.setValueAt( NGLCM.segundoMA, 0, 1 );
			tabla.setValueAt( NGLCM.contraste, 1, 1 );
			tabla.setValueAt( NGLCM.correlacion, 2, 1 );
			tabla.setValueAt( NGLCM.momentDI, 3, 1 );
			tabla.setValueAt( NGLCM.varianza, 4, 1 );
			tabla.setValueAt( NGLCM.energia, 5, 1 );
			tabla.setValueAt( NGLCM.sumaPromedio, 6, 1 );
			tabla.setValueAt( NGLCM.sumaEntropia, 7, 1 );
			tabla.setValueAt( NGLCM.sumaVarianza, 8, 1 );
			tabla.setValueAt( NGLCM.entropia, 9, 1 );
		}
		if( g45 == 1){
			MATRIZ_GLCM_GRADO_45  = new double[ PIXMAX ][ PIXMAX ];
			MATRIZ_GLCM_GRADO_45  = glcm.calcularMATGLCM( MATRIZ_GLCM_GRADO_45,   45, DIST, PIXMAX );
			NormalizarGLCM NGLCM = new NormalizarGLCM( MATRIZ_GLCM_GRADO_45, PIXMAX );
			tabla.setValueAt( NGLCM.segundoMA, 0, 2 );
			tabla.setValueAt( NGLCM.contraste, 1, 2 );
			tabla.setValueAt( NGLCM.correlacion, 2, 2 );
			tabla.setValueAt( NGLCM.momentDI, 3, 2 );
			tabla.setValueAt( NGLCM.varianza, 4, 2 );
			tabla.setValueAt( NGLCM.energia, 5, 2 );
			tabla.setValueAt( NGLCM.sumaPromedio, 6, 2 );
			tabla.setValueAt( NGLCM.sumaEntropia, 7, 2 );
			tabla.setValueAt( NGLCM.sumaVarianza, 8, 2 );
			tabla.setValueAt( NGLCM.entropia, 9, 2 );
		}
		if( g90 == 1){
			MATRIZ_GLCM_GRADO_90  = new double[ PIXMAX ][ PIXMAX ];
			MATRIZ_GLCM_GRADO_90  = glcm.calcularMATGLCM( MATRIZ_GLCM_GRADO_90,   90, DIST, PIXMAX );
			NormalizarGLCM NGLCM = new NormalizarGLCM( MATRIZ_GLCM_GRADO_90, PIXMAX );
			tabla.setValueAt( NGLCM.segundoMA, 0, 3 );
			tabla.setValueAt( NGLCM.contraste, 1, 3 );
			tabla.setValueAt( NGLCM.correlacion, 2, 3 );
			tabla.setValueAt( NGLCM.momentDI, 3, 3 );
			tabla.setValueAt( NGLCM.varianza, 4, 3 );
			tabla.setValueAt( NGLCM.energia, 5, 3 );
			tabla.setValueAt( NGLCM.sumaPromedio, 6, 3 );
			tabla.setValueAt( NGLCM.sumaEntropia, 7, 3 );
			tabla.setValueAt( NGLCM.sumaVarianza, 8, 3 );
			tabla.setValueAt( NGLCM.entropia, 9, 3 );
		}
		if( g135 == 1){
			MATRIZ_GLCM_GRADO_135 = new double[ PIXMAX ][ PIXMAX ];
			MATRIZ_GLCM_GRADO_135 = glcm.calcularMATGLCM( MATRIZ_GLCM_GRADO_135, 135, DIST, PIXMAX );
			NormalizarGLCM NGLCM = new NormalizarGLCM( MATRIZ_GLCM_GRADO_135, PIXMAX );
			tabla.setValueAt( NGLCM.segundoMA, 0, 4 );
			tabla.setValueAt( NGLCM.contraste, 1, 4 );
			tabla.setValueAt( NGLCM.correlacion, 2, 4 );
			tabla.setValueAt( NGLCM.momentDI, 3, 4 );
			tabla.setValueAt( NGLCM.varianza, 4, 4 );
			tabla.setValueAt( NGLCM.energia, 5, 4 );
			tabla.setValueAt( NGLCM.sumaPromedio, 6, 4 );
			tabla.setValueAt( NGLCM.sumaEntropia, 7, 4 );
			tabla.setValueAt( NGLCM.sumaVarianza, 8, 4 );
			tabla.setValueAt( NGLCM.entropia, 9, 4 );
		}
	}
}