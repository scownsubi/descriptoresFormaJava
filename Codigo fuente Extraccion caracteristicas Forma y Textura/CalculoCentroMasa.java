
public class CalculoCentroMasa {
	public byte [][]MATRIZ_IMAGEN;
	public byte [][]MATRIZ_IMAGEN_ORIGINAL;
	public byte []VECTOR_IMAGEN;
	public int HEIGHT = 0;
	public int WIDTH = 0;
	
	public double xBarra = 0;
	public double yBarra = 0;
	
	public byte []vectorOriginal;
	public CalculoCentroMasa( byte []vector, int height, int width, byte []vectorOriginal){
		this.VECTOR_IMAGEN = vector;
		this.HEIGHT = height;
		this.WIDTH = width;
		
		this.MATRIZ_IMAGEN = new byte [ this.HEIGHT ][ this.WIDTH ];
		this.MATRIZ_IMAGEN_ORIGINAL = new byte [ this.HEIGHT ][ this.WIDTH ];
		
		this.vectorOriginal = vectorOriginal;
		
		llenarMatriz();

		momentoCentral();
	}
	
	public double momentoDeOrden( int p, int q ){
		double suma = 0;
		for( int i = 0; i < this.HEIGHT; i++ ){
			for( int j = 0; j < this.WIDTH; j++ ){
				if( (this.MATRIZ_IMAGEN[ i ][ j ]& 255) != 255 ){
					suma = suma + ( Math.pow( i, p ) * Math.pow( j, q ) * this.MATRIZ_IMAGEN_ORIGINAL[ i ][ j ] );
					//System.out.println( this.MATRIZ_IMAGEN_ORIGINAL[ i ][ j ] & 255 );
				}
            }
		}
		return suma;
	}
	
	public void momentoCentral(){
		xBarra = momentoDeOrden( 1, 0 ) / momentoDeOrden( 0, 0 );
		yBarra = momentoDeOrden( 0, 1 ) / momentoDeOrden( 0, 0 );
		
		//System.out.println( xBarra + " " + yBarra );
		//System.out.println( this.HEIGHT + " " + this.WIDTH );
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
}
