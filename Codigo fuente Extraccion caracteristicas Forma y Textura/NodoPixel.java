
public class NodoPixel {
	private double posXIn;
	private double posYIn;
	
	private double pixelPerimetroX;
	private double pixelPerimetroY;
	
	public void setX( int x){
		this.posXIn = x;
	}
	public double getX(){
		return this.posXIn;
	}
	public void setY( int y){
		this.posYIn = y;
	}
	public double getY(){
		return this.posYIn;
	}
	
	public void setPixelPerimetroX( int pixelPerimetroX){
		this.pixelPerimetroX = pixelPerimetroX;
	}
	public double getPixelPerimetroX(){
		return this.pixelPerimetroX;
	}
	public void setPixelPerimetroY( int pixelPerimetroY){
		this.pixelPerimetroY = pixelPerimetroY;
	}
	public double getPixelPerimetroY(){
		return this.pixelPerimetroY;
	}
}
