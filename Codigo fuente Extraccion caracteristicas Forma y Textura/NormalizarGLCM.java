
public class NormalizarGLCM {
	public double [][]  MATRIZ_GLCM;
	private int PIXMAX = 0;
	private double R = 0;
	
	public double segundoMA = 0;
	public double contraste = 0;
	public double correlacion = 0;
	public double momentDI = 0;
	public double varianza = 0;
	public double energia = 0;
	public double sumaPromedio = 0;
	public double sumaEntropia = 0;
	public double sumaVarianza = 0;
	public double diferenciaVarianza = 0;
	public double diferenciaEntropia =  0;
	public double entropia = 0;
	
	public double miuI = 0;
	public double miuJ = 0;
	public double desviacionEstandarI = 0;
	public double desviacionEstandarJ = 0;
	public double epsilon = 0.0001;
	
	public NormalizarGLCM( double [][]MAT, int PIXMAX ){
		this.MATRIZ_GLCM = MAT;
	    this.PIXMAX = PIXMAX;
		R = calcularR( );
		calcularProbabilidad( R );
		calculoCaracteristicasGLCM( );
	}
	public double calcularR( ){
		double suma = 0;
		for( int i = 0; i < PIXMAX; i++ )
            for( int j = 0; j < PIXMAX; j++ )
            	suma = suma + MATRIZ_GLCM [ i ][ j ];
            
		
		return suma;
	}
	
	public void calcularProbabilidad( double r){
		for( int i = 0; i < PIXMAX; i++ )
            for( int j = 0; j < PIXMAX; j++ )
            	MATRIZ_GLCM[ i ] [ j ] = MATRIZ_GLCM[ i ][ j ] / r;
   
	}
	
	
	// f1 Angular Second Moment
	public void calculoCaracteristicasGLCM( ){
		// f8
		sumaEntropia = sumEntropy( );
		 miuI( );
		 standardDeviationsI( );
		int x = 0;
		for( int i = 0; i < PIXMAX; i++ ){
            for( int j = 0; j < PIXMAX; j++ ){
            	//f1
            	segundoMA = segundoMA + Math.pow( MATRIZ_GLCM[ i ][ j ], 2 );
            	// f2 Contrast
            	contraste = contraste + ( MATRIZ_GLCM[ i ][ j ] * Math.pow( i - j, 2 ) );
            	// f3 Correlation
            	correlacion = correlacion + ( MATRIZ_GLCM[ i ][ j ] * ( ( ( i - miuI ) * ( j - miuJ) ) / ( Math.sqrt( desviacionEstandarI * desviacionEstandarJ ) ) ) );
            	// f4 Sum of Squares: Variance 
            	varianza = varianza + ( Math.pow( i - miuI , 2 ) * MATRIZ_GLCM[ i ][ j ] );
            	// f5 Inverse Diferent Moment ( Homogeneity ) IDM
            	momentDI = momentDI + ( MATRIZ_GLCM[ i ][ j ] / ( 1 + Math.pow( i - j , 2 ) ) );    	
            	// f6 Sum Average
            	double val = probablidadPx_mas_y( x );
            	
            	sumaPromedio = sumaPromedio + ( x * val );
            	// f7 Sum Variance
            	sumaVarianza = sumaVarianza + ( Math.pow( x - sumaEntropia, 2 ) * val );
            	// f9 Entropy
            	val = MATRIZ_GLCM[ i ][ j ];
            	if( val == 0 )
            		val = epsilon;
            	entropia = entropia + ( MATRIZ_GLCM[i][j] * Math.log( val ) );
            	x++;
            }
		}
		energia = energy( );
		sumaVarianza = sumaVarianza * -1;
		diferenciaVarianza = diferenciaVarianza * -1;
	}
	
		
	
	// f8 Sum Entropy
	private double sumEntropy( ){
		double suma = 0;
		for( int i = 0; i < ( PIXMAX * 2); i++ ){
			double val = probablidadPx_mas_y( i );
        	if( val == 0 )
        		val = epsilon;
			suma = suma + ( val * Math.log( val ));
			
		}

		return -suma;
	}

	
	
	// Energy
	private double energy( ){
		return Math.sqrt( segundoMA );
	}
	
	// miuI
	public void miuI( ){
		for( int i = 0; i < PIXMAX; i++ ){
			for( int j = 0; j < PIXMAX; j++ ){
	            miuI = miuI + ( i * MATRIZ_GLCM[ i ][ j ] );
	            miuJ = miuJ + ( j * MATRIZ_GLCM[ i ][ j ] );
			}
		}
	}
	
	// Standard deviationsI 
	public void standardDeviationsI( ){
		for( int i = 0; i < PIXMAX; i++ ){
			for( int j = 0; j < PIXMAX; j++ ){
				desviacionEstandarI = desviacionEstandarI + ( MATRIZ_GLCM[ i ][ j ] * Math.pow( miuI, 2 ) );
				desviacionEstandarJ = desviacionEstandarJ + ( MATRIZ_GLCM[ i ][ j ] * Math.pow( miuJ, 2 ) );
			}
		}
		desviacionEstandarI = Math.pow( desviacionEstandarI, 2 );
		desviacionEstandarJ = Math.pow( desviacionEstandarJ, 2 );
	}
	
	// Obtener Px+y(i)
	public double probablidadPx_mas_y( int k ){
		double suma = 0;
		for( int i = 0; i < PIXMAX; i++ )
			for( int j = 0; j < PIXMAX; j++ )
				if( ( i + j ) == k )
					suma = suma + MATRIZ_GLCM[ i ][ j ];
		
		return suma;
	}
}
