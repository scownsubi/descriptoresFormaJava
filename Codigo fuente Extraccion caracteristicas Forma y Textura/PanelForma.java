import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
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

public class PanelForma extends JPanel implements ActionListener{
	private JPanel panelGeneral, panelCargarImagen, panelImagenOriginal, panelImagenBorde, panelResultado, panelTexto;
	private JButton abrirImagen, abrirImagenOriginal, verImagenOriginal, desactivarTodo, resultado, verTodo;
	private Panel panelI, panelII, panelIII, panelImg1, panelImg2, panelImg3; 
	private JLabel etiqueta;	
	public Border etched;
	public TitledBorder tituloBorde;
	public CuatroVecinos cuatroVecinos;
	public InputStream entrada;
    public GreyMap ans;	
    public InputStream entradaImagenOriginal;
    public GreyMap ansImagenOriginal;	
    public CaracteristicasForma drn;
    public double centroX = 0;
	public double centroY = 0;
	public PanelForma(){
		
		panelGeneral = new JPanel();
		panelGeneral.setLayout( null );
		
		this.add( panelGeneral );
		
		etched = BorderFactory.createEtchedBorder();
		tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		panelCargarImagen = new JPanel( );
		panelCargarImagen.setBorder( tituloBorde );
		panelCargarImagen.setLayout( null );
		panelCargarImagen.setBounds( 10, 20, 510, 62 );
		panelCargarImagen.setBorder( tituloBorde );
		
		this.add( panelCargarImagen );
		
		
		etched = BorderFactory.createEtchedBorder();
		tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		panelTexto = new JPanel( );
		panelTexto.setBorder( tituloBorde );
		panelTexto.setLayout( null );
		panelTexto.setBounds( 520, 20, 255, 62 );
		panelTexto.setBorder( tituloBorde );
		
		this.add( panelTexto );
		
		abrirImagen = new JButton( "Abrir Imagen Original" );
		abrirImagen.setBounds( 10, 15 , 235, 30 );
		abrirImagen.setEnabled( true );
		abrirImagen.addActionListener( this );
		panelCargarImagen.add( abrirImagen );
		
		abrirImagenOriginal = new JButton( "Abrir Imagen Segmentada" ); 
		abrirImagenOriginal.setBounds( 265, 15, 235, 30 );
		abrirImagenOriginal.addActionListener( this );
		abrirImagenOriginal.setEnabled( true );
		panelCargarImagen.add( abrirImagenOriginal );
		
		verImagenOriginal = new JButton( "Ver" ); 
		verImagenOriginal.setBounds( 410, 15, 80, 30 );
		verImagenOriginal.addActionListener( this );
		verImagenOriginal.setEnabled( true );
		
		tituloBorde = BorderFactory.createTitledBorder(  etched, "Imagen original" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		panelImagenOriginal = new JPanel( );
		panelImagenOriginal.setBorder( tituloBorde );
		panelImagenOriginal.setLayout( null );
		panelImagenOriginal.setBounds( 10, 90, 255, 300 );
		panelImagenOriginal.setBorder( tituloBorde );
		
		panelImagenBorde = new JPanel( );
		panelImagenBorde.setBorder( tituloBorde );
		panelImagenBorde.setLayout( null );
		panelImagenBorde.setBounds( 265, 90, 255, 300 );
		panelImagenBorde.setBorder( tituloBorde );
		
		tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
			
		panelI = new Panel( );
		panelI.setBounds(  10, 90, 255, 300  );
		panelI.setBorder( tituloBorde );
		panelImagenOriginal.add( panelI );
		this.add( panelI );
		
		tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
	    panelII = new Panel( );
		panelII.setBounds(  265, 90, 255, 300  );
		panelII.setBorder( tituloBorde );
		this.add( panelII );
		
		tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
	    panelIII = new Panel( );
		panelIII.setBounds(  520, 90, 255, 300  );
		panelIII.setBorder( tituloBorde );
		this.add( panelIII );
		
		etiqueta = new JLabel( "Resultado" );
		etiqueta.setBounds( 80, 10, 200, 40 );
		etiqueta.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
		panelTexto.add( etiqueta );
		
		tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		panelResultado = new JPanel( );
		panelResultado.setBorder( tituloBorde );
		panelResultado.setLayout( null );
		panelResultado.setBounds( 10, 440, 760, 100 );
		panelResultado.setBorder( tituloBorde );
		this.add( panelResultado );
		
		resultado = new JButton( "Contorno y centro de la masa" );
		resultado.setBounds( 30, 30 , 210, 40 );
		resultado.setEnabled( false );
		resultado.addActionListener( this );
		panelResultado.add( resultado );
		
		desactivarTodo = new JButton( "Descriptores de forma" );
		desactivarTodo.setBounds( 280, 30 , 210, 40 );
		desactivarTodo.setEnabled( true );
		desactivarTodo.addActionListener( this );
		panelResultado.add( desactivarTodo );
		
		verTodo = new JButton( "Salir" );
		verTodo.setBounds( 530, 30 , 210, 40 );
		verTodo.setEnabled( true );
		verTodo.addActionListener( this );
		panelResultado.add( verTodo );

		this.setLayout(null);
		this.setBounds(10, 10, 290, 380);
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource().equals( abrirImagenOriginal )){
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
				
					if( archivoelegido.exists() ){
						try {
							entrada = new BufferedInputStream(new FileInputStream(ruta));
						    ans = new GreyMap(entrada);
						    entrada.close();
					
						    panelImg2 = new Panel( ans.toImage() );
						    panelImg2.setBounds(  10, 10, 235, 280  );
							panelII.add( panelImg2 );
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
		if( e.getSource().equals( verImagenOriginal )){
			new VentanaImagenOriginal( this.ansImagenOriginal.toImage() );
		}
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
					if( archivoelegido.exists() ){
						try {
							entradaImagenOriginal = new BufferedInputStream(new FileInputStream(ruta));
						    ansImagenOriginal = new GreyMap(entradaImagenOriginal);
						    entradaImagenOriginal.close();
							
						    panelImg1 = new Panel( ansImagenOriginal.toImage() );
						    panelImg1.setBounds(  10, 10, 235, 280  );
							panelI.add( panelImg1 );
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
			
			CalculoCentroMasa centroMasa = new CalculoCentroMasa( ans.pixels, ans.height, ans.width, ansImagenOriginal.pixels  );
			centroX = centroMasa.xBarra;
			centroY = centroMasa.yBarra;
			cuatroVecinos = new CuatroVecinos( ans.pixels, ans.height, ans.width, centroX, centroY, ansImagenOriginal.pixels);
		    panelImg3 = new Panel( cuatroVecinos.construirImagen() );
		    panelImg3.setBounds(  10, 10, 225, 280  );
			panelIII.add( panelImg3 );
			repaint();
		}
		if(e.getSource().equals( desactivarTodo )){
			drn = new CaracteristicasForma( cuatroVecinos.pixelContorno, cuatroVecinos.pixelImagenArea, centroX, centroY, cuatroVecinos.promedioNivelGrisRegion, cuatroVecinos.promedioNivelGrisContorno, cuatroVecinos.skewness, cuatroVecinos.kurtosis,cuatroVecinos.vectorDistnaciaRN );
			new VentanaResultadosForma( drn );
		}
		if(e.getSource().equals( verTodo )){
			System.exit( 0 );
		}
	}
}
