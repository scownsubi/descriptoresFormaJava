
public class GLCM {
	private int REN;
	private int COL;
	private double [][]MATRIZ_ORIGINAL;
	public GLCM(double [][]MAT, int REN, int COL){
		this.MATRIZ_ORIGINAL = MAT;
		this.REN = REN;
		this.COL = COL;
	}
	
	public double[][] calcularMATGLCM( double MATRIZ[][], int grado, int dist, int PIXMAX ){
		for( int i = 0; i < PIXMAX; i++ ){
            for( int j = 0; j < PIXMAX; j++ ){
            	if( grado == 0 )
            		MATRIZ[ i ][ j ] = numPixGrado_0( i, j, dist );

            	if( grado == 45 )
            		MATRIZ[ i ][ j ] = numPixGrado_45( i, j, dist );

            	if( grado == 90 )
            		MATRIZ[ i ][ j ] = numPixGrado_90( i, j, dist );

            	if( grado == 135 )
            		MATRIZ[ i ][ j ] = numPixGrado_135( i, j, dist );
            }
		}
		return MATRIZ;
	}
	public int numPixGrado_0( int x, int y, int dist ){
		int numPix = 0;
		for( int i = 0; i < REN ; i++ ){
            for( int j = 0; j < COL; j++ ){
            	if( ( j - dist ) > -1 )
            		if( MATRIZ_ORIGINAL[ i ][ j ] == x && MATRIZ_ORIGINAL[ i ][ j - dist ] == y )
            			numPix++;
            		
            	if( ( j + dist ) < COL )
            		if( MATRIZ_ORIGINAL[ i ][ j ] == x && MATRIZ_ORIGINAL[ i ][ j + dist ] == y )
            			numPix++;
            }
		}
		return numPix;
	}
	
	public int numPixGrado_45( int x, int y, int dist ){
		int numPix = 0;
		for( int i = 0; i < REN ; i++ ){
            for( int j = 0; j < COL; j++ ){
            	if( ( i - dist ) > -1 && ( j + dist ) < COL )
            		if( MATRIZ_ORIGINAL[ i ][ j ] == x && MATRIZ_ORIGINAL[ i - dist ][ j + dist ] == y )
            			numPix++;
           		
            	if( ( i + dist ) < REN && ( j - dist ) > -1 )
            		if( MATRIZ_ORIGINAL[ i ][ j ] == x && MATRIZ_ORIGINAL[ i + dist ][ j - dist ] == y )
            			numPix++;
            }
		}
		return numPix;
	}
	
	public int numPixGrado_90( int x, int y, int dist ){
		int numPix = 0;
		for( int i = 0; i < REN ; i++ ){
            for( int j = 0; j < COL; j++ ){
            	if( ( i - dist ) > -1 )
            		if( MATRIZ_ORIGINAL[ i ][ j ] == x && MATRIZ_ORIGINAL[ i - dist ][ j ] == y )
            			numPix++;
           		
            	if( ( i + dist ) < COL )
            		if( MATRIZ_ORIGINAL[ i ][ j ] == x && MATRIZ_ORIGINAL[ i + dist ][ j ] == y )
            			numPix++;
            }
		}
		return numPix;
	}
	
	public int numPixGrado_135( int x, int y, int dist ){
		int numPix = 0;
		for( int i = 0; i < REN; i++ ){
            for( int j = 0; j < COL; j++ ){
            	if( ( i - dist ) > -1 && ( j - dist ) > -1 )
            		if( MATRIZ_ORIGINAL[ i ][ j ] == x && MATRIZ_ORIGINAL[ i - dist ][ j - dist ] == y )
            			numPix++;
           		
            	if( ( i + dist ) < REN && ( j + dist ) < COL )
            		if( MATRIZ_ORIGINAL[ i ][ j ] == x && MATRIZ_ORIGINAL[ i + dist ][ j + dist ] == y )
            			numPix++;
            }
		}
		return numPix;
	}
	
	public int buscarPixMax(){
		int mayor = (int)MATRIZ_ORIGINAL[ 0 ][ 0 ];
		for( int i = 0; i < REN; i++ )
            for( int j = 0; j < COL; j++ )
            	if( MATRIZ_ORIGINAL[ i ][ j ] > mayor )
            		mayor = (int)MATRIZ_ORIGINAL[i][j];            		
	
		return mayor;
	}
}
