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

public class VentanaImagenOriginal extends JDialog implements ActionListener{
	private JPanel panelGeneral, panelTabla;
	private JButton aceptar;
	private BufferedImage bf;
	private Panel panelI;
	public VentanaImagenOriginal( BufferedImage bf ){
		super();
		setTitle( "Imagen original" );
		setSize( 370, 440 );
		setLocation( 50, 30 );
		setModal( true );
		
		this.bf = bf;
		
		panelGeneral = new JPanel();
		panelGeneral.setLayout( null );
		
		getContentPane().add( panelGeneral );
		
		Border etched = BorderFactory.createEtchedBorder();
		TitledBorder tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		tituloBorde.setTitleJustification(TitledBorder.LEFT);
		
		//tituloBorde = BorderFactory.createTitledBorder(  etched, "" );
		//tituloBorde.setTitleJustification(TitledBorder.LEFT);
			
		
		panelTabla = new JPanel( );
		panelTabla.setBorder( tituloBorde );
		panelTabla.setLayout( null );
		panelTabla.setBounds( 20, 20, 320, 370 );
		//panelTabla.setBorder(tituloBorde);
		
		panelGeneral.add( panelTabla );
		
		panelI = new Panel( this.bf );
		panelI.setBounds(  10, 10, 300, 300  );
		panelI.setBorder( tituloBorde );
		panelTabla.add( panelI );
		//this.add( panelI );
		
		aceptar = new JButton( "Aceptar" );
		aceptar.setBounds( 190, 320 , 120, 35 );
		aceptar.setEnabled( true );
		aceptar.addActionListener( this );
		panelTabla.add( aceptar );
		
		this.setResizable( false );
		setVisible( true );
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource().equals( aceptar )){
			this.hide();
		}
	}
}