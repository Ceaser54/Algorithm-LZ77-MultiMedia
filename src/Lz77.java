import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

public class Lz77 
{
	
	public static void comp( String s ) throws IOException {
		
	String data = new String(Files.readAllBytes(Paths.get(s)));
		
		Character[] arr =  new Character[data.length()] ;
		
		for( int i=0 ; i<data.length() ; i++ ){
			arr[i] = data.charAt(i) ;
		}
		
		Vector <Integer> v1=new Vector<Integer>() ;
		Vector <Integer> v2=new Vector<Integer>() ;
		Vector <Character> v3=new Vector<Character>() ;
		Vector <Integer> index=new Vector<Integer>() ;
                
		
		Boolean k=false ;
		
		for( int i=0 ; i<data.length() ; i++ ){
			
			int p=i , n=0 , c=1 , max=0 , p1 , pn=0 , temp ;
			
			for( int j=0 ; j<i ; j++ ){
				
				if( arr[j]==arr[i] ){					
					index.add(j) ;
					k = true ;
				}
			}
			
			if( !k ){
				
				v1.add(0) ;
				v2.add(0) ;
				v3.add(arr[i]) ;
			}
			else{
				
				for( int j=0 ; j<index.size() ; j++ ){
					
					temp = (index.get(j)+1) ; p1=p+1 ; k=true ;
					
					while( k && p1<data.length() ){
						
						if( arr[p1]==arr[temp] ){
							
							k = true ;
							p1 ++ ;
							temp ++ ;
							c ++ ;
						}
						else{
							k = false ;
						}
					}
					
					if( c>max ){
						max = c ;
						n = index.get(j) ;
						pn = p1 ;
					}
					c = 1 ;
				}
				
				v1.add(p-n) ;
				
				if( pn<=data.length() ){
					
					if( pn==data.length() ){
						
						v2.add(max-1) ;
						v3.add(arr[pn-1]) ;
					}
					else{
						v2.add(max) ;
						v3.add(arr[pn]) ;
					}
				}
				i = pn ;
			}
			
			k = false ;
			index.clear() ;
		}
		
		for( int i=0 ; i<v1.size() ; i++ ){
			
			System.out.print(v1.get(i) + " " + v2.get(i) + " " + v3.get(i) + "\n") ;
		}
		
		File f = new File("comp.txt");
		FileOutputStream fop = new FileOutputStream(f);
		
		for( int i=0 ; i<v1.size(); i++ ){
			
			fop.write(v1.get(i)) ; 
			fop.write( v2.get(i) ) ; 
			fop.write( v3.get(i) ) ; 
		}
		
		fop.flush();
		fop.close();
	}
	
	public static void decomp( String s ) throws IOException {
		
		Vector <Integer> r1=new Vector<Integer>() ;
		Vector <Integer> r2=new Vector<Integer>() ;
		Vector <Character> r3=new Vector<Character>() ;
		
		InputStream input = new FileInputStream("comp.txt");

		int d = input.read() , l=0 ;
		char q ;
		while(d != -1) {
			
		  r1.add(d) ;
		  l++ ;
		  d = input.read();
		  r2.add(d) ;
		  l++ ;
		  d = input.read();
		  q = (char)d ;
		  r3.add(q) ;
		  l++ ;
		  d = input.read();
		}
		input.close();
		
		/*for( int i=0 ; i<v1.size() ; i++ ){
			
			System.out.print(r1.get(i) + " " + r2.get(i) + " " + r3.get(i) + "\n") ;
		}*/
		
		Vector <Character> res=new Vector<Character>() ;
		
		int z , y ; Character t ;
		
		for( int i=0 ; i<r1.size() ; i++ ){
			
			if( r1.get(i)==0 ){
				res.add(r3.get(i)) ;
			}
			else{
				
				y = res.size() - r1.get(i) ;
				z = r2.get(i) ;
				
				while( z>0 ){
					
					t = res.get(y) ; 
					res.add(t) ; 
					y ++ ;
					z -- ;
				}
				res.add(r3.get(i)) ;
			}
		}
		
		byte[] r = new byte[res.size()] ; int g ;
		
		for( int i=0 ; i<res.size(); i++ ){
			
			g = (int)res.get(i) ;
			r[i] = (byte)g ;
		}
		
		OutputStream os = new FileOutputStream(s) ;
		os.write(r);
	}
	
	public static void main(String[] args) throws IOException {
		
		String p1 , p2 ;
		
		Scanner input1 = new Scanner(System.in);
		p1 = input1.next();
		
		
		comp(p1) ;
		
		Scanner input2 = new Scanner(System.in);
		p2 = input2.next();
		
		decomp(p2) ;
	}

}
