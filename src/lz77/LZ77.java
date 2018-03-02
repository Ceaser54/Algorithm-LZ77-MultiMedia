package LZ77;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;

public class LZ77 {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;

	public LZ77() {
		prepareGUI();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// GUI :"D

		// JFrame frame = new JFrame("Test");
		// frame.setVisible(true);
		// frame.setSize(500,200);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// JTextFieldDemo test = new JTextFieldDemo();
		// test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// JPanel panel = new JPanel();
		// frame.add(panel);
		// JButton button = new JButton("Compress");
		// panel.add(button);
		// button.addActionListener (new Action1());

		// JButton button2 = new JButton("Decompress");
		// panel.add(button2);
		// button.addActionListener (new Action2());

		LZ77 swingControlDemo = new LZ77();
		swingControlDemo.showTextFieldDemo();

	}

	private void prepareGUI() {
		mainFrame = new JFrame("Java Swing Examples");
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);

		statusLabel.setSize(350, 100);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);
	}

	private void showTextFieldDemo() {
		// headerLabel.setText("Control in action: JTextField");

		JLabel input = new JLabel("Input: ", JLabel.RIGHT);
		final JTextField userText = new JTextField(30);

		JButton Compress = new JButton("Compress");
		Compress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				//	FileOutputStream f2 = new FileOutputStream("input.txt");
					//DataOutputStream bw1 = new DataOutputStream(f2);
					//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(bw1));
					//bw.write(userText.getText());
					
					Compress(userText.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton Decompress = new JButton("Decompress");
		Decompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Decompress();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		controlPanel.add(userText);
		controlPanel.add(Compress);
		controlPanel.add(Decompress);
		mainFrame.setVisible(true);
	}

	public static void Compress(String s) throws IOException {
		// aacaacabcabaaac
		// abaababaabbbb
		// aabcbbabc

		String input = null;
		int c;
		// Scanner in = new Scanner(System.in);
		// System.out.println("1-Compress");
		// System.out.println("2-Decompress");
		// c = in.nextInt();

		//FileInputStream file = new FileInputStream("input.txt");
		//DataInputStream data = new DataInputStream(file);
		//BufferedReader br = new BufferedReader(new InputStreamReader(data));

		FileOutputStream f1 = new FileOutputStream("compress.txt");
		DataOutputStream bw = new DataOutputStream(f1);

		input=s;
		//input = br.readLine();
		System.out.println(input);

		int pointer = 0, length = 0;
		char NextChar = '0';
		String Window, y = null;
		int j = 0, z, a = 0;

		for (int i = 0; i < input.length(); i++) {
			Window = null;
			Window = input.substring(0, i);
			j++;
			do {
				//System.out.println("j= " + j);
				y = input.substring(i, j);
				z = Window.lastIndexOf(y);

				// Testing ^^

				// System.out.println("Window Till now--> " + Window);
				// System.out.println("our subString--->"+y);
				// System.out.println(z);

				if (z != -1) {
					j++;
					a = z;
					if (j > input.length() || (j >= input.length() && i == input.length() - 1)) {
						i = input.length();
						pointer = Window.length() - a;
						length = y.length();
						NextChar = '0';
						bw.writeInt(pointer);
						bw.writeInt(length);
						bw.writeChar(NextChar);
						System.out.println("pointer " + pointer + " Length " + length + " Next Char " + NextChar);
						break;
					}
				}

				else {
					i = Window.length() + (y.length() - 1);
					if (y.length() == 1) {
						pointer = 0;
						length = 0;
					} else {

						pointer = Window.length() - a;
						length = y.length() - 1;
					}
					NextChar = y.charAt(y.length() - 1);
					bw.writeInt(pointer);
					bw.writeInt(length);
					bw.writeChar(NextChar);
					System.out.println("pointer " + pointer + " Length " + length + " Next Char " + NextChar);

				}

			} while (z != -1);
		}
		bw.close();
		//data.close();

	}

	public static void Decompress() throws IOException {
		
		FileInputStream file1 = new FileInputStream("compress.txt");
		DataInputStream data1 = new DataInputStream(file1);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(data1));

		FileOutputStream f2 = new FileOutputStream("Decompress.txt");
		DataOutputStream bw1 = new DataOutputStream(f2);

		String decoding = "";
		int position = 0, length = 0;
		char Next_Char;

		while (file1.available() != 0) {

			position = data1.readInt();
			length = data1.readInt();
			Next_Char = data1.readChar();

			// Testing ^^

			System.out.println("position--> " + position);
			System.out.println("lentgh---> " + length);
			System.out.println("Next_ Char---> " + Next_Char);

			if (position != 0) {
				int h = decoding.length() - position;
				if (Next_Char == '0')
					decoding += decoding.substring(h, h + length);
				else
					decoding += decoding.substring(h, (h + length)) + Next_Char;
			} else
				decoding += Next_Char;
		}
		// Final String Testing ^^
		System.out.println(decoding);

		bw1.writeChars(decoding);

		br1.close();
		bw1.close();
	}

}