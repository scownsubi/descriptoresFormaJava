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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class VentanaDatosGLCM extends JDialog implements ActionListener{
	private JPanel panelGeneral, datosGLCM, panelBoton;
	private JButton aceptar, cancelar;
	private JRadioButton grado_0, grado_45, grado_90, grado_135;
	private JLabel etiqueta;
	public JComboBox distancia;
	public double [][]MATRIZ_IMAGE;
	public int iniAlto;
	public int iniAncho;
	public int g0 = 0;
	public int g45 = 0;
	public int g90 = 0;
	public int g135 = 0;
	
	public VentanaDatosGLCM( double [][]MATRIZ_IMAGE, int iniAlto, int iniAncho ){
		setTitle( "Grados y distancias GLCM" );
		setSize( 260, 370 );
		setLocation( 50, 30 );
		setModal( true );
		
		this.MATRIZ_IMAGE = MATRIZ_IMAGE;
		this.iniAlto = iniAlto;
		this.iniAncho = iniAlto;
		
		panelGeneral = new JPanel();
		panelGeneral.setLayout( null );
		
		getContentPane().add( panelGeneral );
		
		Border etched = BorderFactory.createEtchedBorder();
		TitledBorder tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		tituloBorde = BorderFactory.createTitledBorder(  etched, "Seleccionar grado y distancia" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		datosGLCM = new JPanel( );
		datosGLCM.setBorder( tituloBorde );
		datosGLCM.setLayout( null );
		datosGLCM.setBounds( 15, 25, 220, 220 );
		datosGLCM.setBorder(tituloBorde);
		
		panelGeneral.add( datosGLCM );
		
		grado_0 = new JRadioButton( "0°" );
		grado_0.setBounds( 20, 30, 70, 25 );
		grado_0.setSelected( true );
		grado_0.addActionListener( this );
		datosGLCM.add( grado_0 );
		
		grado_45 = new JRadioButton( "45°" );
		grado_45.setBounds( 150, 30, 60, 25 );
		grado_45.addActionListener( this );
		datosGLCM.add( grado_45 );
		
		grado_90 = new JRadioButton( "90°" );
		grado_90.setBounds( 20, 100, 70, 25 );
		grado_90.addActionListener( this );
		datosGLCM.add( grado_90 );
		
		grado_135 = new JRadioButton( "135°" );
		grado_135.setBounds( 150, 100, 60, 25 );
		grado_135.addActionListener( this );
		datosGLCM.add( grado_135 );
		
		etiqueta = new JLabel( "Distancia: " );
		etiqueta.setBounds( 20, 170, 100, 25 );
		datosGLCM.add( etiqueta );

		distancia = new JComboBox();
		distancia.addActionListener( this );
		distancia.setBounds( 100, 170 , 90, 25 );
		
		for( int i = 1; i < 100; i++)
			distancia.addItem( String.valueOf( i ) );
		
		datosGLCM.add( distancia );
		
		tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		panelBoton = new JPanel( );
		panelBoton.setBorder( tituloBorde );
		panelBoton.setLayout( null );
		panelBoton.setBounds( 15, 245, 220, 68 );
		panelBoton.setBorder( tituloBorde );
		
		panelGeneral.add( panelBoton );
		
		aceptar = new JButton( "Aceptar" );
		aceptar.setBounds( 13, 15 , 90, 35 );
		aceptar.setEnabled( true );
		aceptar.addActionListener( this );
		panelBoton.add( aceptar );
		
		cancelar = new JButton( "Cancelar" );
		cancelar.setBounds( 113, 15 , 90, 35 );
		cancelar.setEnabled( true );
		cancelar.addActionListener( this );
		panelBoton.add( cancelar );
		
		this.setResizable( false );
		setVisible( true );
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource().equals( aceptar )){
			if( !grado_0.isSelected() && !grado_45.isSelected() && !grado_90.isSelected() && !grado_135.isSelected() ){
				JOptionPane.showMessageDialog( null, "Debe especificar el grado", "Error", JOptionPane.ERROR_MESSAGE );
			}
			else{
				
				this.hide();
				
				if( grado_0.isSelected() )
					g0 = 1;
				else
					g0 = 0;
				
				if( grado_45.isSelected() )
					g45 = 1;
				else
					g45 = 0;
				
				if( grado_90.isSelected() )
					g90 = 1;
				else
					g90 = 0;
				
				if( grado_135.isSelected() )
					g135 = 1;
				else
					g135 = 0;
				
				VentanaResultadosTextura vr = new VentanaResultadosTextura( MATRIZ_IMAGE, iniAlto, iniAncho, g0, g45, g90, g135, Integer.parseInt( distancia.getSelectedItem().toString() ) );
			}
		}
		if( e.getSource().equals( cancelar )){
			this.hide();
		}
	}
}