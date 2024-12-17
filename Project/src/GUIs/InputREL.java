package GUIs;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DataOnly.REL;
import DataObjects.DataREL;
import Utilities.DataOverNetwork;

import javax.swing.JTextPane;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class InputREL extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputREL frame = new InputREL();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InputREL() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 278);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane txtE = new JTextPane();
		txtE.setText("4");
		txtE.setToolTipText("");
		txtE.setBounds(10, 92, 285, 20);
		contentPane.add(txtE);

		JTextPane petriname = new JTextPane();
		petriname.setText("1082");
		petriname.setBounds(10, 159, 285, 20);
		contentPane.add(petriname);

		JTextPane txtR = new JTextPane();
		txtR.setText("5");
		txtR.setBounds(10, 61, 285, 20);
		contentPane.add(txtR);

		JTextPane txtL = new JTextPane();
		txtL.setToolTipText("");
		txtL.setText("6");
		txtL.setBounds(10, 123, 285, 20);
		contentPane.add(txtL);

		JTextPane txtPlace = new JTextPane();
		txtPlace.setText("ps_i1");
		txtPlace.setBounds(10, 21, 285, 20);
		contentPane.add(txtPlace);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Socket s;
				try {
					s = new Socket(InetAddress.getByName("localhost"), Integer.parseInt(petriname.getText()));
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					DataOverNetwork DataToSend = new DataOverNetwork();

					DataREL temp = new DataREL();
					REL c = new REL(Integer.parseInt(txtR.getText()), Integer.parseInt(txtE.getText()), Integer.parseInt(txtL.getText()));
					temp.SetValue(c);
					temp.SetName(txtPlace.getText());
					DataToSend.petriObject = temp;

					DataToSend.NetWorkPort = Integer.parseInt(petriname.getText());
					oos.writeObject(DataToSend);
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSend.setBounds(10, 192, 285, 44);
		contentPane.add(btnSend);

	}
}
