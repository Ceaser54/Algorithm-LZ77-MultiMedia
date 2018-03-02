import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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

public class GUI_lz77 {
	
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
		
		byte[] r = new byte[res.size()] ;
                int g ;
		
		for( int i=0 ; i<res.size(); i++ ){
			
			g = (int)res.get(i) ;
			r[i] = (byte)g ;
		}
		
		OutputStream os = new FileOutputStream(s) ;
		os.write(r);
	}

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_lz77 window = new GUI_lz77();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_lz77() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Compress");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String s = textField.getText() ;
				
				try {
					comp(s) ;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Decompress");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String s1 = textField_1.getText() ;
				
				try {
					decomp(s1) ;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JLabel lblWriteThePath = new JLabel("Write the path of the file you want to compress");
		lblWriteThePath.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblWriteThePath_1 = new JLabel("Write the path of the file you want to decompress in");
		lblWriteThePath_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblWriteThePath_1)
							.addContainerGap())
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblWriteThePath)
								.addContainerGap())
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(textField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
									.addComponent(textField_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(btnNewButton)
										.addPreferredGap(ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
										.addComponent(btnNewButton_1)))
								.addGap(99)))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addComponent(lblWriteThePath)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblWriteThePath_1)
					.addGap(1)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1))
					.addGap(42))
		);
		frame.getContentPane().setLayout(groupLayout);
	}

}
