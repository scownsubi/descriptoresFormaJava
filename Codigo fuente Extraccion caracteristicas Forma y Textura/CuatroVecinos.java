import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Iterator;

public class CuatroVecinos {
	public byte [][]MATRIZ_IMAGEN;
	public byte [][]MATRIZ_IMAGEN_ORIGINAL;
	public byte []VECTOR_IMAGEN;
	public int HEIGHT = 0;
	public int WIDTH = 0;
	public byte []vectorOriginal;
	
	public double centroX = 0;
	public double centroY = 0;
	
	public ArrayList< NodoPixel > pixelImagenArea = new ArrayList< NodoPixel >();
	public ArrayList< NodoPixel > pixelContorno = new ArrayList< NodoPixel >();
	public double promedioNivelGrisRegion = 0;
	public double promedioNivelGrisContorno = 0;
	public double skewness = 0;
	public double kurtosis = 0;
	public double vectorDistnaciaRN[];
	
	public CuatroVecinos( byte []vector, int height, int width, double centroX, double centroY,  byte []vectorOriginal){
		this.VECTOR_IMAGEN = vector;
		this.HEIGHT = height;
		this.WIDTH = width;
		
		this.centroX = centroX;
		this.centroY = centroY;
		
		this.MATRIZ_IMAGEN = new byte [ this.HEIGHT ][ this.WIDTH ];
		this.MATRIZ_IMAGEN_ORIGINAL = new byte [ this.HEIGHT ][ this.WIDTH ];
		this.vectorOriginal = vectorOriginal;
		
		
		
		llenarMatriz();
		calcularBordeImagen();
		rellenarImagenFondo();
		recorreBorde();
		calcularSkewness_Kurtosis();
		this.vectorDistnaciaRN = new double[ this.pixelContorno.size() ];
		obtenerDistanciaRadialNormalizada();
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
	
	public void rellenarImagenFondo(){
		Iterator< NodoPixel > iterador = pixelImagenArea.iterator();
		while ( iterador.hasNext() ) {
			NodoPixel pos = iterador.next();
			int i = (int)pos.getX();
			int j = (int)pos.getY();
			MATRIZ_IMAGEN[ i ][ j ] = (byte)255;
			promedioNivelGrisRegion += this.MATRIZ_IMAGEN_ORIGINAL[ i ][ j ]&255;
		}
		contornoPixelcentral();
		MATRIZ_IMAGEN[ ( int )this.centroX ][ ( int )this.centroY ] = (byte)0;
		promedioNivelGrisRegion = promedioNivelGrisRegion / pixelImagenArea.size();
	}
	
	public void calcularSkewness_Kurtosis(){
		Iterator< NodoPixel > iterador = pixelImagenArea.iterator();
		double suma1 = 0;
		double suma2 = 0;
		double suma3 = 0;
		while ( iterador.hasNext() ) {
			NodoPixel pos = iterador.next();
			int i = (int)pos.getX();
			int j = (int)pos.getY();
			double x = (this.MATRIZ_IMAGEN_ORIGINAL[ i ][ j ]&255) - promedioNivelGrisRegion;
			suma1 += Math.pow( x, 3 );
			suma2 += Math.pow( x, 3 );
			suma3 += Math.pow( x, 4 );
		}
		skewness = suma1 / ( (Math.pow( Math.pow(suma2,2), 2/3) ) * this.pixelImagenArea.size() );
		//System.out.println( suma1 + "  " + suma2 + "  "+ (Math.sqrt( suma2 ) * this.pixelImagenArea.size()) );
		kurtosis = suma3 / ( Math.sqrt( suma3 ) * this.pixelImagenArea.size() );
	}
	
	public void recorreBorde(){
		Iterator< NodoPixel > iterador = this.pixelContorno.iterator();
		while ( iterador.hasNext() ) {
			NodoPixel pos = iterador.next();
			int i = (int)pos.getX();
			int j = (int)pos.getY();
			promedioNivelGrisContorno += this.MATRIZ_IMAGEN_ORIGINAL[ i ][ j ]&255;
		}
		promedioNivelGrisContorno = promedioNivelGrisContorno / this.pixelContorno.size();
	}
	
	public void llenarMatriz(){
		MATRIZ_IMAGEN = new byte[HEIGHT][WIDTH];
		int x = 0;
		for( int i = 0; i < HEIGHT; i++ ){
			for( int j = 0; j <  WIDTH; j++ ){
				MATRIZ_IMAGEN[ i ][ j ] = VECTOR_IMAGEN[ x ];
				MATRIZ_IMAGEN_ORIGINAL[ i ][ j ] = vectorOriginal[ x ];
				x++;
			}
		}
	}
	
	public void calcularBordeImagen(){
		NodoPixel nodoPixel;
		for( int i = 0; i < HEIGHT; i++ ){
            for( int j = 0; j < WIDTH; j++ ){
            	if( MATRIZ_IMAGEN[ i ][ j ]  == 0 ){
            		nodoPixel = new NodoPixel();
            		if( cuatroVecinos( i, j ) ){
            			nodoPixel.setX( i );
            			nodoPixel.setY( j );
            			
            			pixelImagenArea.add( nodoPixel );
            		}
            		else{
            			nodoPixel.setX( i );
            			nodoPixel.setY( j );
            			
            			pixelContorno.add( nodoPixel );
            			
            			//System.out.print( nodoPixel.getX()+ " posicion pixel: " + nodoPixel.getY());
            		}
            	}
           }
		}
	}
	
	boolean cuatroVecinos( int i, int j ){
		int vecino = 0;
		if( ( j - 1 ) > -1 )
			if( MATRIZ_IMAGEN[ i ][ j - 1] == 0 )
				vecino++;
		
		if( ( i - 1 ) > -1 )
			if( MATRIZ_IMAGEN[ i - 1 ][ j ] == 0 )
				vecino++;

		if( ( j + 1 ) < WIDTH )
			if( MATRIZ_IMAGEN[ i ][ j + 1] == 0 )
				vecino++;

		if( ( i + 1 ) < HEIGHT )
			if( MATRIZ_IMAGEN[ i + 1 ][ j ] == 0 )
				vecino++;
			
		
		if( vecino == 4 )
			return true;
		else
			return false;
	}
	
	public BufferedImage construirImagen( ) {
		byte pixel[] = new byte[ HEIGHT * WIDTH ];
		int x = 0;
		for( int i = 0; i < HEIGHT; i++ ){
			for( int j = 0; j <  WIDTH; j++ ){
				pixel[x] = MATRIZ_IMAGEN[ i ][ j ] ;
				x++;
			}
		}
	    final BufferedImage imagen = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_BYTE_GRAY );
	    final byte[] ansPixels = ((DataBufferByte)imagen.getRaster().getDataBuffer()).getData();
	    //System.arraycopy(pixel, 0, ansPixels, 0, this.WIDTH*this.HEIGHT);
	    return imagen;
	  }
	
	public void contornoPixelcentral(){
		MATRIZ_IMAGEN[ ( int )this.centroX - 1 ][ ( int )this.centroY ] = (byte)0;
		MATRIZ_IMAGEN[ ( int )this.centroX + 1 ][ ( int )this.centroY ] = (byte)0;
		
		MATRIZ_IMAGEN[ ( int )this.centroX ][ ( int )this.centroY - 1 ] = (byte)0;
		MATRIZ_IMAGEN[ ( int )this.centroX ][ ( int )this.centroY + 1 ] = (byte)0;
		
		MATRIZ_IMAGEN[ ( int )this.centroX - 1 ][ ( int )this.centroY - 1 ] = (byte)0;
		MATRIZ_IMAGEN[ ( int )this.centroX + 1 ][ ( int )this.centroY + 1 ] = (byte)0;
		
		MATRIZ_IMAGEN[ ( int )this.centroX - 1 ][ ( int )this.centroY + 1 ] = (byte)0;
		MATRIZ_IMAGEN[ ( int )this.centroX + 1 ][ ( int )this.centroY - 1 ] = (byte)0;
	}
}
