import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class VentanaMenuPrincipal extends JFrame{
	JTabbedPane tabbedPane = new JTabbedPane();
	
	public VentanaMenuPrincipal( String title ) {
		super( title );
		addComponents();
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setSize( 800, 630 );
		setLocation(100, 100 );
		setVisible( true ); 
		setResizable( false );
	}
	
	protected void addComponents() {
		tabbedPane.addTab( "Forma", new PanelForma() );
		
		tabbedPane.addTab( "Textura", new PanelTextura() );
		
		
		JPanel buttonPanel3 = new JPanel();
		tabbedPane.addTab( "Intensidad", buttonPanel3 );
		
		getContentPane().add( tabbedPane, BorderLayout.CENTER );
	}
}