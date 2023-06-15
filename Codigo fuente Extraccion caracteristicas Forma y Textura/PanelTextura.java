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
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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

public class PanelTextura extends JPanel implements ActionListener{
	private JPanel panelGeneral, panelCargarImagen, panelOperacion, panelResultado;
	private JButton abrirImagen, desactivarTodo, resultado;
	private JRadioButton momentoAngular, contraste, correlacion, momentoDiferencia, 
	                     varianza, energia, sumaPromedio, sumaEntropia, sumaVarianza, 
	                     entropia;

	private Panel panelI; 
	
	private JTextField rutaArchivo;
	private JLabel etiqueta;
	
	public double [][]MATRIZ_IMAGE;
	public int iniAlto = 0;
	public int iniAncho = 0;
	
	public Border etched;
	public TitledBorder tituloBorde;

	public PanelTextura(){
		
		panelGeneral = new JPanel();
		panelGeneral.setLayout( null );
		
		//getContentPane().add( panelGeneral );
		this.add( panelGeneral );
		
		etched = BorderFactory.createEtchedBorder();
		tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		panelCargarImagen = new JPanel( );
		panelCargarImagen.setBorder( tituloBorde );
		panelCargarImagen.setLayout( null );
		panelCargarImagen.setBounds( 10, 28, 300, 90 );
		panelCargarImagen.setBorder( tituloBorde );
		
		this.add( panelCargarImagen );
		
		abrirImagen = new JButton( "Abrir Imagen" );
		abrirImagen.setBounds( 10, 20 , 120, 50 );
		abrirImagen.setEnabled( true );
		abrirImagen.addActionListener( this );
		panelCargarImagen.add( abrirImagen );
		
		etiqueta = new JLabel( "Nombre imagen" );
		etiqueta.setBounds( 170, 10, 150, 25 );
		panelCargarImagen.add( etiqueta );
		
		
		rutaArchivo = new JTextField(); 
		rutaArchivo.setBounds( 140, 35, 150, 30 );
		rutaArchivo.setEnabled( false );
		panelCargarImagen.add( rutaArchivo );
		
		tituloBorde = BorderFactory.createTitledBorder(  etched, "Valores a calcular" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		panelOperacion = new JPanel( );
		panelOperacion.setBorder( tituloBorde );
		panelOperacion.setLayout( null );
		panelOperacion.setBounds( 320, 20, 200, 410 );
		panelOperacion.setBorder( tituloBorde );
		
		this.add( panelOperacion );
		
		momentoAngular = new JRadioButton( "Segundo Momento A." );
		momentoAngular.setBounds( 20, 30, 150, 25 );
		momentoAngular.setSelected( true );
		momentoAngular.setEnabled( false );
		panelOperacion.add( momentoAngular );
		
		contraste = new JRadioButton( "Contraste" );
		contraste.setBounds( 20, 65, 150, 25 );
		contraste.setEnabled( false );
		contraste.setSelected( true );
		panelOperacion.add( contraste );
		
		correlacion = new JRadioButton( "Correlación" );
		correlacion.setBounds( 20, 100, 150, 25 );
		correlacion.setEnabled( false );
		correlacion.setSelected( true );
		panelOperacion.add( correlacion );
		
		momentoDiferencia = new JRadioButton( "Momento diferencia I." );
		momentoDiferencia.setBounds( 20, 135, 150, 25 );
		momentoDiferencia.setEnabled( false );
		momentoDiferencia.setSelected( true );
		panelOperacion.add( momentoDiferencia );
		
		varianza = new JRadioButton( "Varianza" );
		varianza.setBounds( 20, 170, 150, 25 );
		varianza.setEnabled( false );
		varianza.setSelected( true );
		panelOperacion.add( varianza );
		
		energia = new JRadioButton( "Energia" );
		energia.setBounds( 20, 205, 150, 25 );
		energia.setEnabled( false );
		energia.setSelected( true );
		panelOperacion.add( energia );
		
		sumaPromedio = new JRadioButton( "Suma promedio" );
		sumaPromedio.setBounds( 20, 240, 150, 25 );
		sumaPromedio.setEnabled( false );
		sumaPromedio.setSelected( true );
		panelOperacion.add( sumaPromedio );
		
		sumaEntropia = new JRadioButton( "Suma entropía" );
		sumaEntropia.setBounds( 20, 275, 150, 25 );
		sumaEntropia.setEnabled( false );
		sumaEntropia.setSelected( true );
		panelOperacion.add( sumaEntropia );
		
		sumaVarianza = new JRadioButton( "Suma varianza" );
		sumaVarianza.setBounds( 20, 310, 150, 25 );
		sumaVarianza.setEnabled( false );
		sumaVarianza.setSelected( true );
		panelOperacion.add( sumaVarianza );
		
		entropia = new JRadioButton( "Entropía" );
		entropia.setBounds( 20, 345, 150, 25 );
		entropia.setEnabled( false );
		entropia.setSelected( true );
		panelOperacion.add( entropia );
		
		tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		panelResultado = new JPanel( );
		panelResultado.setBorder( tituloBorde );
		panelResultado.setLayout( null );
		panelResultado.setBounds( 10, 440, 510, 100 );
		panelResultado.setBorder( tituloBorde );
		this.add( panelResultado );
		
		resultado = new JButton( "Aceptar" );
		resultado.setBounds( 30, 30 , 210, 40 );
		resultado.setEnabled( false );
		resultado.addActionListener( this );
		panelResultado.add( resultado );
		
		desactivarTodo = new JButton( "Salir" );
		desactivarTodo.setBounds( 270, 30 , 210, 40 );
		desactivarTodo.setEnabled( true );
		desactivarTodo.addActionListener( this );
		panelResultado.add( desactivarTodo );

		this.setLayout(null);
		this.setBounds(10, 10, 290, 380);
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource().equals( abrirImagen )){
			JFileChooser selector=new JFileChooser();
			int estado=selector.showOpenDialog(null);
			if( estado == 0){
				File archivoelegido=selector.getSelectedFile();
				String ruta=archivoelegido.getPath();
				
				StringTokenizer st = new StringTokenizer(ruta, ".");
				String rutaArch = "";
				String extension = "";

				while(st.hasMoreTokens()) {
					rutaArch = st.nextToken();
					extension = st.nextToken();
				}
				   
				if( extension.compareTo( "pgm") == 0){ 
				
					rutaArchivo.setText( archivoelegido.getName() );
				
					if( archivoelegido.exists() ){
						try {
							InputStream entrada = new BufferedInputStream(new FileInputStream(ruta));
						    GreyMap ans = new GreyMap(entrada);
						    entrada.close();
						    MATRIZ_IMAGE = ans.MATRIZ_IMAGE;
						    iniAlto = ans.height;
						    iniAncho = ans.width;		
						    
						    tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
							tituloBorde.setTitleJustification(TitledBorder.LEFT);
							
						    panelI = new Panel( ans.toImage() );
							panelI.setBounds( 10, 130, 300, 300 );
							panelI.setBorder( tituloBorde );
							this.add( panelI );
							
							repaint();
							
						    resultado.setEnabled( true );
						} 
						catch (IOException e1) {
						      e1.printStackTrace();
						      System.exit(0);
						}
					}
					else
						JOptionPane.showMessageDialog( null, "No se pudo cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE );
				}
				else
					JOptionPane.showMessageDialog( null, "Sólo puede abrir imágenes con extesión *.pgm", "Imagen entrada", JOptionPane.INFORMATION_MESSAGE );
			}
		}
		if( e.getSource().equals( resultado )){
			VentanaDatosGLCM vdglcm = new VentanaDatosGLCM( MATRIZ_IMAGE, iniAlto, iniAncho );
		}
		if( e.getSource().equals( desactivarTodo )){
			System.exit( 0 );
		}
	}
}