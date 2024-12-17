package GUIs;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DataObjects.DataCar;
import DataOnly.Car;
import Utilities.DataOverNetwork;

import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class InputCar extends JFrame {

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
					InputCar frame = new InputCar();
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
	public InputCar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 350); // Increased height for radio buttons
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Text fields
		JTextPane txtNumber = new JTextPane();
		txtNumber.setText("number");
		txtNumber.setToolTipText("");
		txtNumber.setBounds(10, 92, 285, 20);
		contentPane.add(txtNumber);

		JTextPane petriname = new JTextPane();
		petriname.setText("1080");
		petriname.setBounds(10, 159, 285, 20);
		contentPane.add(petriname);

		JTextPane txtModel = new JTextPane();
		txtModel.setText("model");
		txtModel.setBounds(10, 61, 285, 20);
		contentPane.add(txtModel);

		JTextPane txtTarget = new JTextPane();
		txtTarget.setToolTipText("");
		txtTarget.setText("target");
		txtTarget.setBounds(10, 123, 285, 20);
		contentPane.add(txtTarget);

		JTextPane txtPlace = new JTextPane();
		txtPlace.setText("P");
		txtPlace.setBounds(10, 21, 285, 20);
		contentPane.add(txtPlace);

		// Radio Buttons
		JRadioButton rdbtnNormal = new JRadioButton("Normal");
		rdbtnNormal.setBounds(10, 190, 100, 23);
		contentPane.add(rdbtnNormal);

		JRadioButton rdbtnPriority = new JRadioButton("Priority");
		rdbtnPriority.setBounds(120, 190, 100, 23);
		contentPane.add(rdbtnPriority);

		JRadioButton rdbtnBus = new JRadioButton("Bus");
		rdbtnBus.setBounds(10, 220, 100, 23);
		contentPane.add(rdbtnBus);

		JRadioButton rdbtnTaxi = new JRadioButton("Taxi");
		rdbtnTaxi.setBounds(120, 220, 100, 23);
		contentPane.add(rdbtnTaxi);

		// Group radio buttons together
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNormal);
		group.add(rdbtnPriority);
		group.add(rdbtnBus);
		group.add(rdbtnTaxi);

		// Send button
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Socket s;
				try {
					s = new Socket(InetAddress.getByName("localhost"), Integer.parseInt(petriname.getText()));
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					DataOverNetwork DataToSend = new DataOverNetwork();

					DataCar temp = new DataCar();
					Car c = new Car(txtModel.getText(), txtNumber.getText(), txtTarget.getText().split(","));

					// Determine selected radio button
					String selectedType = "Normal"; // Default
					if (rdbtnNormal.isSelected()) {
						selectedType = "Normal";
					} else if (rdbtnPriority.isSelected()) {
						selectedType = "Priority";
					} else if (rdbtnBus.isSelected()) {
						selectedType = "Bus";
					} else if (rdbtnTaxi.isSelected()) {
						selectedType = "Taxi";
					}

					// Set car type based on radio button selection
					c.setType(selectedType); // Assuming the 'Car' class has a setType() method
					temp.SetValue(c);
					temp.SetName(txtPlace.getText());
					DataToSend.petriObject = temp;

					DataToSend.NetWorkPort = Integer.parseInt(petriname.getText());
					oos.writeObject(DataToSend);
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnSend.setBounds(10, 260, 285, 44);
		contentPane.add(btnSend);
	}
}

