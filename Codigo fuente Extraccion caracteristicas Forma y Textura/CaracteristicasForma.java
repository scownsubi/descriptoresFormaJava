import java.util.ArrayList;
import java.util.Iterator;


public class CaracteristicasForma {
	private ArrayList< NodoPixel > pixelContorno;
	private ArrayList< NodoPixel > pixelImagenArea;
		
	public double centroX = 0;
	public double centroY = 0;
	
	public double vectorDistnaciaRN[];
	
	public double perimetro = 0;
	public double area = 0;
	public double circularidad = 0;
	public double mediaDRN = 0;
	public double desviacionEstandarDRN = 0;
	public double compacidad = 0;
	public double contraste = 0;
	public double PixF = 0;
	public double PixB = 0;
	public double skewness;
	public double kurtosis;
	
	public CaracteristicasForma( ArrayList< NodoPixel > pixelContorno, ArrayList< NodoPixel > pixelImagenArea, double centroX, double centroY, double PixF, double PixB, double skewness, double kurtosis, double [] vectorD ){
		
		this.pixelContorno = pixelContorno;
		this.pixelImagenArea = pixelImagenArea;
		this.centroX = centroX;
		this.centroY = centroY;
		this.PixF = PixF;
		this.PixB = PixB;
		this.skewness = skewness;
		this.kurtosis = kurtosis;
		this.vectorDistnaciaRN = vectorD;
		
		
		
		//obtenerDistanciaRadialNormalizada();
		normalizarVectorDRN();
		
		obtenerCaracteristicasForma();
	}
	public void obtenerCaracteristicasForma(){
		perimetro = this.pixelContorno.size();
		area = this.pixelImagenArea.size() + perimetro;
		circularidad = ( 4 * Math.PI * area ) / (Math.pow( perimetro, 2 ) );
		mediaDRN = calcularMediaDRN();
		desviacionEstandarDRN = calcularDesviacionEstandarDRN();
		compacidad = Math.pow( perimetro, 2) / area;
		contraste = ( PixF - PixB ) / PixF; 
		
	}
	public double calcularMediaDRN(){
		double suma = 0;
		for(int i = 0; i < perimetro; i++ ){
			suma = suma + vectorDistnaciaRN[ i ];
		}
		//System.out.println( "suma: " +suma+" pixel contorno: "+ this.pixelContorno.size());
		return suma / (this.pixelContorno.size() * 1.0);
		
		
	}
	public double calcularDesviacionEstandarDRN(){
		double suma = 0;
		for(int i = 0; i < this.pixelContorno.size(); i++ ){
			suma = suma + ( Math.pow( vectorDistnaciaRN[ i ] - mediaDRN, 2 ) );
		}
		return Math.sqrt( suma / (this.pixelContorno.size() * 1.0) );
		
	}
	public void normalizarVector(){
		
	}
	public void obtenerDistanciaRadialNormalizada(){
		Iterator< NodoPixel > iterador = this.pixelContorno.iterator();
		int x = 0;
		while ( iterador.hasNext() ) {
			NodoPixel pos = iterador.next();
			vectorDistnaciaRN[ x ] = Math.sqrt( Math.pow( pos.getX() - centroX, 2) + Math.pow( pos.getY() - centroY, 2) );
			//System.out.println( pos.getX() + "  posicion: " + pos.getY());
			x++;
		}
	}
	public void normalizarVectorDRN(){
		double mayor = this.vectorDistnaciaRN[ 0 ];
		for( int i = 1; i < this.pixelContorno.size() ; i++ )
            if( vectorDistnaciaRN[ i ] > mayor )
            	mayor = vectorDistnaciaRN[ i ];   
		
		//System.out.println("mayor: " + mayor);
		
		for( int i = 0; i < this.pixelContorno.size() ; i++ ){
			//System.out.println( "vector distancia: " + vectorDistnaciaRN[i]);
            vectorDistnaciaRN[ i ] = (vectorDistnaciaRN[ i ]*1.0) / (mayor*1.0); 	
            //System.out.println("elemento: " + vectorDistnaciaRN[ i ]);
		}
	}
}
