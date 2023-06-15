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

public class VentanaResultadosForma extends JDialog implements ActionListener{
	private JPanel panelGeneral, panelTabla;
	private JButton aceptar;
	private String[] columna = { "Características", "Resultados" };
	private Object [][]objeto = new Object[10][2];
	private JScrollPane scrollPane;
  	private JTable tabla;
  	private CaracteristicasForma drn;
	
	public VentanaResultadosForma( CaracteristicasForma drn ){
		super();
		setTitle( "Extracción de descriptores de forma" );
		setSize( 600, 450 );
		setLocation( 50, 30 );
		setModal( true );
		
		this.drn = drn;
		
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
        		
		tabla.setValueAt( "Perímetro", 0, 0 );
		tabla.setValueAt( "Área", 1, 0 );
		tabla.setValueAt( "Circularidad", 2, 0 );
		tabla.setValueAt( "Media DRN", 3, 0 );
		tabla.setValueAt( "Desviacion Estandar DRN", 4, 0 );
		tabla.setValueAt( "Compacidad", 5, 0 );
		tabla.setValueAt( "Contraste", 6, 0 );
		tabla.setValueAt( "Skewness", 7, 0 );
		tabla.setValueAt( "Kurtosis", 8, 0 );
		tabla.setValueAt( "Promedio de intensidad dentro de la región de interés", 9, 0 );
		
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
		tabla.setValueAt( drn.perimetro, 0, 1 );
		tabla.setValueAt( drn.area, 1, 1 );
		tabla.setValueAt( drn.circularidad, 2, 1 );
		tabla.setValueAt( drn.mediaDRN, 3, 1 );
		tabla.setValueAt( drn.desviacionEstandarDRN, 4, 1 );
		tabla.setValueAt( drn.compacidad, 5, 1 );
		tabla.setValueAt( drn.contraste, 6, 1 );
		tabla.setValueAt( drn.skewness, 7, 1 );
		tabla.setValueAt( drn.kurtosis, 8, 1 );
		tabla.setValueAt( drn.PixF, 9, 1 );
	}
}