package Components;

import javax.swing.JFrame;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import Interfaces.PetriObject;
import OETPNGraphics.GraphArc;
import OETPNGraphics.GraphPlace;
import OETPNGraphics.GraphTransition;
import OETPNGraphics.OETPNShape;
import OETPNGraphics.Point;
import Utilities.Functions;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;

public class PetriNetWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PetriNet petriNet = null;
	Thread networkThread;
	boolean AutoStart = false;
	JPanel pnlGraphics = new JPanel();
	JScrollPane scrollPane = new JScrollPane();
	DefaultListModel<String> model = new DefaultListModel<String>();
	JList<String> lstMsg = new JList<String>(model);

	JPanel SelectionPanel = new JPanel();
	// JList PlaceSelectlist = new JList();

	public PetriNetWindow(boolean autoStart) {
		setBounds(100, 100, 909, 700);
		this.AutoStart = autoStart;

		JComboBox<String> cbGraphFilter = new JComboBox<String>();
		cbGraphFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawOETPN(pnlGraphics, cbGraphFilter);
			}
		});

		JTextPane txtName = new JTextPane();
		txtName.setFont(new Font("Consolas", Font.BOLD, 12));

		MyCellRenderer cellRenderer = new MyCellRenderer(800);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (cbGraphFilter.getItemCount() == 0) {
					cbGraphFilter.addItem("ALL");
					cbGraphFilter.addItem("Follow The Transition");
					for (PetriTransition t : petriNet.Transitions) {
						cbGraphFilter.addItem(t.TransitionName);
					}
					for (PetriObject p : petriNet.PlaceList) {
						cbGraphFilter.addItem(p.GetName());
					}
					cbGraphFilter.setSelectedIndex(1);
				}

				for (PetriObject p : petriNet.PlaceList) {
					SelectionPanel.add(new Checkbox(p.GetName(), false));
				}

				if (!petriNet.PauseFlag) {
					networkThread = new Thread(petriNet);
					networkThread.start();

					txtName.setText(petriNet.PetriNetName + " [Network Port:" + petriNet.NetworkPort + "]");
					petriNet.setDataLoadFinishedListener(new PetriNet.DataLoadFinishedListener() {
						@Override
						public void onDataLoadFinishedListener(String data_type) {
							if (data_type == "DrawGraphOnly")
							{
								drawOETPN(pnlGraphics, cbGraphFilter);
								return;
							}
							drawOETPN(pnlGraphics, cbGraphFilter);
							addString(model, scrollPane, data_type, lstMsg);
						}
					});
				} else {
					addString(model, scrollPane, "Continued....", lstMsg);
					petriNet.PauseFlag = false;
					drawOETPN(pnlGraphics, cbGraphFilter);
				}

			}
		});

		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				petriNet.PauseFlag = true;
				addString(model, scrollPane, "Paused....", lstMsg);
				drawOETPN(pnlGraphics, cbGraphFilter);
			}
		});

		JTextPane txtMetrices = new JTextPane();
		JButton btnMetrics = new JButton("Print Metrics");
		btnMetrics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtMetrices.setText(petriNet.PrintMatrics());
				addString(model, scrollPane, petriNet.PrintMatrics(), lstMsg);
				drawOETPN(pnlGraphics, cbGraphFilter);
			}
		});

		txtMetrices.setFont(new Font("Consolas", Font.BOLD, 11));

		JButton btnSaveLog = new JButton("Save Log");
		btnSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SaveLog(model);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(splitPane,
								GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(SelectionPanel,
								GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addGap(10).addGroup(groupLayout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 89,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10).addComponent(btnPause, GroupLayout.PREFERRED_SIZE, 89,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup().addComponent(btnSaveLog)
												.addGap(18).addComponent(cbGraphFilter, GroupLayout.PREFERRED_SIZE, 283,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(btnMetrics, GroupLayout.PREFERRED_SIZE, 109,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(txtMetrices,
														GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)))))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(11)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(btnStart)
						.addComponent(btnPause).addComponent(btnMetrics).addComponent(txtMetrices,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(11)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnSaveLog)
								.addComponent(cbGraphFilter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(SelectionPanel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE).addGap(8)));

//		JList<? extends E> placeSelectionList = new JList();
//		panel.add(list);
//		PlaceSelectlist.setCellRenderer(new CheckboxListCellRenderer());
//		SelectionPanel.add(PlaceSelectlist);
		SelectionPanel.setBackground(Color.LIGHT_GRAY);

		JButton btnShowGraph = new JButton("Show Graph");
		SelectionPanel.add(btnShowGraph);
		btnShowGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> filter = new ArrayList<String>();
				for (Component c : SelectionPanel.getComponents()) {
					if (c instanceof Checkbox) {
						Checkbox cc = ((Checkbox) c);
						if (cc.getState()) {
							filter.add(cc.getLabel());
						}
					}
				}
				petriNet.ShowChart(filter);
			}
		});

		splitPane.setRightComponent(pnlGraphics);
		pnlGraphics.setBackground(Color.white);

		splitPane.setLeftComponent(scrollPane);

		scrollPane.setViewportView(lstMsg);
		lstMsg.setCellRenderer(cellRenderer);
		lstMsg.setFont(new Font("Consolas", Font.BOLD, 12));
		getContentPane().setLayout(groupLayout);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				petriNet.Stop();
				System.exit(0);
			}

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				txtName.setText(petriNet.PetriNetName + " [Network Port:" + petriNet.NetworkPort + "]");
				if (autoStart == true) {

					if (cbGraphFilter.getItemCount() == 0) {
						cbGraphFilter.addItem("ALL");
						cbGraphFilter.addItem("Follow The Transition");
						for (PetriTransition t : petriNet.Transitions) {
							cbGraphFilter.addItem(t.TransitionName);
						}
						for (PetriObject p : petriNet.PlaceList) {
							cbGraphFilter.addItem(p.GetName());
						}
						cbGraphFilter.setSelectedIndex(1);
					}
					if (!petriNet.PauseFlag) {
						networkThread = new Thread();
						networkThread = new Thread(petriNet);
						networkThread.start();

						txtName.setText(petriNet.PetriNetName + " [Network Port:" + petriNet.NetworkPort + "]");
						petriNet.setDataLoadFinishedListener(new PetriNet.DataLoadFinishedListener() {
							@Override
							public void onDataLoadFinishedListener(String data_type) {
								addString(model, scrollPane, data_type, lstMsg);
								drawOETPN(pnlGraphics, cbGraphFilter);
							}
						});
					} else {
						addString(model, scrollPane, "Continued....", lstMsg);
						drawOETPN(pnlGraphics, cbGraphFilter);
						petriNet.PauseFlag = false;
					}
				}
			}

		});

	}

	public void SaveLog(DefaultListModel<String> model) throws IOException {

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fileChooser.setFileFilter(filter);
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			FileWriter fw = new FileWriter(file.getPath());
//			for (int i = 0; i < model.getSize(); i++) {
//				fw.write(model.get(i));
//				fw.write("\n");
//			}
			for (int i = model.getSize() - 1; i >= 0; i--) {
				fw.write(model.get(i));
				fw.write("\n");
			}
			fw.close();
		}
	}

	public void drawOETPN(JPanel pnlGraphics, JComboBox<String> filter) {

		if (petriNet.PrintingSpeed == 1) {

		} else {
			if (petriNet.PrintingSpeedIndex > 0) {
				petriNet.PrintingSpeedIndex--;
				return;
			} else {
				petriNet.PrintingSpeedIndex = petriNet.PrintingSpeed;
			}
		}
		Functions fns = new Functions();

		Graphics g = pnlGraphics.getGraphics();
		g.setFont(new Font("Consolas", Font.BOLD, 13));
		g.setColor(Color.white);
		g.fillRect(0, 0, pnlGraphics.getWidth(), pnlGraphics.getHeight());

		ArrayList<OETPNShape> transitions = new ArrayList<OETPNShape>();
		ArrayList<OETPNShape> places = new ArrayList<OETPNShape>();
		ArrayList<OETPNShape> arcs = new ArrayList<OETPNShape>();

		ArrayList<String> redTransitions = new ArrayList<String>();

		int transitionColumn = -20;
		int transitionRow = 20;
		int placeDistance = 60;

		for (PetriTransition t : petriNet.Transitions) {
			if (transitionColumn < pnlGraphics.getWidth() - 200) {
				transitionColumn += 120;
			} else {
				transitionColumn = 100;
				transitionRow += 200;
			}

			boolean activated = petriNet.ExecutionList != null && petriNet.ExecutionList.lastIndexOf(t) > -1;
			OETPNShape currentTransition = new GraphTransition(new Point(transitionColumn, transitionRow),
					t.TransitionName, activated);
			if (activated) {
				redTransitions.add(t.TransitionName);
			}
			transitions.add(currentTransition);
			for (String nm : t.InputPlaceName) {
				boolean token = false;
				PetriObject pls = fns.GetPetriObjectByName(nm, petriNet.PlaceList);
				if (pls != null) {
					token = pls.GetToken();
				}

				OETPNShape currentPlace = GetByName(places, nm);
				if (currentPlace == null) {
					currentPlace = new GraphPlace(new Point(transitionColumn - placeDistance, 0), nm, token);
					if (!AddPlace(places, currentPlace)) {
						currentPlace.SetFlag(token);
					}
				}
				AddArc(arcs, currentPlace, currentTransition);
			}
		}
		for (PetriTransition t : petriNet.Transitions) {
			OETPNShape currentTransition = GetByName(transitions, t.TransitionName);
			for (GuardMapping gm : t.GuardMappingList) {
				for (Activation ac : gm.Activations) {

					if (ac.OutputPlaceName != null) {
						boolean token = false;
						PetriObject pls = fns.GetPetriObjectByName(ac.OutputPlaceName, petriNet.PlaceList);
						if (pls != null) {
							token = pls.GetToken();
						}
						OETPNShape currentPlace = new GraphPlace(new Point(transitionColumn + placeDistance, 0),
								ac.OutputPlaceName, token);
						AddPlace(places, currentPlace);
						AddArc(arcs, currentTransition, GetByName(places, currentPlace.GetName()));
					} else if (ac.OutputPlaceNames != null && ac.OutputPlaceNames.size() > 0) {
						for (String outName : ac.OutputPlaceNames) {
							boolean token = false;
							PetriObject pls = fns.GetPetriObjectByName(outName, petriNet.PlaceList);
							if (pls != null) {
								token = pls.GetToken();
							}
							OETPNShape currentPlace = new GraphPlace(new Point(transitionColumn + placeDistance, 0),
									outName, token);
							AddPlace(places, currentPlace);
							AddArc(arcs, currentTransition, GetByName(places, currentPlace.GetName()));
						}
					}
				}
			}
		}

		for (OETPNShape a : arcs) {
			String item = filter.getSelectedItem().toString();
			GraphArc ar = (GraphArc) a;

			switch (item) {
			case "ALL": {
				break;
			}
			case "Follow The Transition": {
				if (redTransitions.contains(ar.Start.GetName()) || redTransitions.contains(ar.End.GetName())) {

				} else {
					continue;
				}
				break;
			}
			default: {
				if (ar.Start.GetName() != item && ar.End.GetName() != item) {
					continue;
				}
				break;
			}
			}
			a.Draw(g);
		}

		for (OETPNShape t : transitions) {

			t.Draw(g);
		}

		for (OETPNShape p : places) {
			p.Draw(g);
		}
	}

	public OETPNShape GetByName(ArrayList<OETPNShape> lst, String name) {
		for (OETPNShape s : lst) {
			if (s.GetName() == name) {
				return s;
			}
		}
		return null;
	}

	public GraphArc GetArc(ArrayList<OETPNShape> lst, OETPNShape start, OETPNShape end) {
		for (OETPNShape a : lst) {
			GraphArc result = (GraphArc) a;
			if (result.Start.GetName().equals(start.GetName()) && result.End.GetName().equals(end.GetName())) {
				return result;
			}
		}
		return null;
	}

	public boolean AddPlace(ArrayList<OETPNShape> lst, OETPNShape place) {
		if (GetByName(lst, place.GetName()) == null) {
			if (place.GetName() == "")
				return false;
			if (place.GetName().contains("-"))
				return false;
			int count = 1;
			for (OETPNShape s : lst) {
				if (s.GetLocation().X == place.GetLocation().X) {
					count++;
				}
			}
			place.SetLocation(new Point(place.GetLocation().X, 50 * count));
			lst.add(place);
			return true;
		}
		return false;
	}

	public boolean AddArc(ArrayList<OETPNShape> lst, OETPNShape start, OETPNShape end) {
		if (start == null || start.GetName() == "" || start.GetName().contains("-"))
			return false;
		if (end == null || end.GetName() == "" || end.GetName().contains("-"))
			return false;

		if (GetArc(lst, start, end) == null) {
			lst.add(new GraphArc(start, end));
			return true;
		}
		return false;
	}

	public void addString(DefaultListModel<String> model, JScrollPane scrollPane, String msg, JList<String> lstMsg) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.add(0, msg);
				scrollPane.updateUI();
				lstMsg.updateUI();
			}
		});

	}

	class MyCellRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;
		private int width;

		public MyCellRenderer(int width) {
			this.width = width;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			String col = "";
			if (value.toString().contains("ExecutionList")) {
				col = "color:Black";
			}

			if (value.toString().contains("PlaceList")) {
				col = "color:Blue";
			}

			if (value.toString().contains("I got an Input From NetWork")) {
				col = "color:Green";
			}

			if (value.toString().contains("conditions are false")) {
				col = "color:Red";
			}

			String text = String.format("<html><body style='width:%dpx; %s'>%s</html>", width, col, value.toString());
			return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
		}

	}
}
