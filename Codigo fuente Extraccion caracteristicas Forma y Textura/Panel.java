import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

class Panel extends JPanel{
	Image image;
	public Panel( Image image ){
		this.image = image;
	}
	public Panel( ){
	}

	public void paint (Graphics g){
        super.paint(g);
        g.drawImage (image, 5, 5, this);
    }
}