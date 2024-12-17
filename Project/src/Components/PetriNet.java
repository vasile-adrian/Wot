package Components;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import DataObjects.DataFloat;
import DataObjects.DataFuzzy;
import DataObjects.DataInteger;
import DataObjects.DataSubPetriNet;
import DataOnly.Fuzzy;
import DataOnly.NetworkCommand;
import DataOnly.SubPetri;
import Enumerations.PetriNetState;
import Enumerations.PetriObjectType;
import Interfaces.PetriObject;
import PetriDataPackage.PetriData;
import Utilities.DataOverNetwork;
import Utilities.Functions;
import Utilities.LineChart;
import Utilities.Text;
import MetricsClasses.Metrics;
import org.jfree.data.category.DefaultCategoryDataset;

public class PetriNet implements PetriObject, Runnable, Cloneable, Serializable {

	public Metrics Metrics = new Metrics();
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	public void ShowChart(ArrayList<String> filter) {
		if (filter.size() == 0) {
			LineChart.dataset = dataset;
		} else {
			LineChart.dataset = new DefaultCategoryDataset();
			for (int i = 0; i < dataset.getRowKeys().size(); i++) {
				if (filter.contains(dataset.getRowKeys().get(i).toString())) {
					for (int j = 0; j < dataset.getColumnCount(); j++) {
						if (dataset.getValue(i, j) != null) {
							msg = dataset.getValue(i, j).toString() + "-" + dataset.getRowKeys().get(i) + "-" + j;
							LogThis(msg);
							LineChart.dataset.addValue(dataset.getValue(i, j), dataset.getRowKeys().get(i).toString(),
									j);
						}
					}
				}
			}
		}
		LineChart petrichart = new LineChart(this.PetriNetName);
		petrichart.pack();
		petrichart.setVisible(true);
		petrichart.setExtendedState(petrichart.getExtendedState() | petrichart.MAXIMIZED_BOTH);

	}

	public String PrintMatrics() {
		util.ComputeMatrics(this);
		return Metrics.toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PetriNetState PetriState = PetriNetState.None;
	// ----------------------------------------------------------------------------------------------

	public interface DataLoadFinishedListener {
		public void onDataLoadFinishedListener(String data_type);
	}

	public DataLoadFinishedListener m_lDataLoadFinished = new DataLoadFinishedListener() {

		@Override
		public void onDataLoadFinishedListener(String data_type) {
			// TODO Auto-generated method stub

		}
	};

	public void setDataLoadFinishedListener(DataLoadFinishedListener dlf) {
		this.m_lDataLoadFinished = dlf;
	}

	// ----------------------------------------------------------------------------------------------
	@Override
	public void AddElement(Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Execute() {
		// TODO Auto-generated method stub
	}

	@Override
	public PetriObjectType GetType() {
		return PetriObjectType.PetriNet;
	}

	public Object GetValue() {
		return null;
	}

	@Override
	public void SetValue(Object value) {

	}

	private String name = "";

	@Override
	public String GetName() {
		return name;
	}

	@Override
	public void SetName(String name) {
		this.name = name;
	}

	public ArrayList<PetriObject> PlaceList;
	public ArrayList<PetriObject> ConstantPlaceList;
	public Functions util;

	public PetriNet() {
		util = new Functions();
		Transitions = new ArrayList<PetriTransition>();
		PlaceList = new ArrayList<PetriObject>();
		ConstantPlaceList = new ArrayList<PetriObject>();
	}

	public ArrayList<String> InputStrings = new ArrayList<>();

	public void SetInputFile(String InputFilePath) throws FileNotFoundException {
		if (InputFilePath != "") {
			Utilities.Text InputText = new Text(InputFilePath);

			while (InputText.hasLine) {
				InputStrings.add(InputText.nexline());
			}
		}
	}

	public ArrayList<PetriTransition> Transitions;

	public String PetriNetName;

	public boolean StopFlag;
	public boolean PauseFlag;
	public Integer Delay = 1000;
	public ArrayList<PetriTransition> ExecutionList;

	private Thread networkThread;
	private ArrayList<Thread> AsyncTransitionThreads;

	public String msg;
	int fileInputIndex = -1;
	public int PrintingSpeed = 1;
	int PrintingSpeedIndex = PrintingSpeed;

	@Override
	public void Start() {
		PetriState = PetriNetState.Started;
		networkThread = new Thread();

		NetworkListener myRunnable = new NetworkListener(this);
		networkThread = new Thread(myRunnable);
		networkThread.start();

		msg = "####################  " + PetriNetName + " Started  #####################";
		LogThis(msg);
		AsyncTransitionThreads = new ArrayList<Thread>();
		for (int i = 0; i < Transitions.size(); ++i) {
			if (Transitions.get(i).IsAsync) {
				Thread tThread = new Thread();

				AsyncTransitionRunnableThread tRunnable = new AsyncTransitionRunnableThread(this, Transitions.get(i));
				tThread = new Thread(tRunnable);
				tThread.start();
				AsyncTransitionThreads.add(tThread);
			}
		}

		ExecutionList = new ArrayList<PetriTransition>();
		StopFlag = false;
		PauseFlag = false;

		if (InputStrings.size() >= 1) {
			fileInputIndex = 0;
		}
		int tickNumber = 1;
		while (!StopFlag) {
			try {
				Thread.sleep(Delay);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			if (PauseFlag) {
				continue;
			}

			if (fileInputIndex > -1 && fileInputIndex < InputStrings.size()) {

				String inputLine = InputStrings.get(fileInputIndex);

				if (inputLine != null) {
					String[] InputPlacesFromText = inputLine.split(",");
					for (String InputPlaceFromText : InputPlacesFromText) {
						String[] InputPlacesFromTextElements = InputPlaceFromText.split(":");
						String InputPlacesFromTextElementsPlaceName = InputPlacesFromTextElements[0];
						String InputPlacesFromTextElementsPlaceValue = InputPlacesFromTextElements[1];
						PetriObject InputPlacesFromTextElementsPlaceObject = util
								.GetFromListByName(InputPlacesFromTextElementsPlaceName, PlaceList);
						if (InputPlacesFromTextElementsPlaceObject instanceof DataFuzzy) {
							if (((DataFuzzy) InputPlacesFromTextElementsPlaceObject).Value == null) {
								InputPlacesFromTextElementsPlaceObject
										.SetValue(new Fuzzy(Float.valueOf(InputPlacesFromTextElementsPlaceValue)));
								fileInputIndex++;
							}
						}
					}
				} else {
					// ShowChart();
					StopFlag = true;
					msg = "******************************Input Data Ended******************************************";
					LogThis(msg);
					msg = "****************************************************************************************";
					LogThis(msg);
				}
			}

			PrintPetri();
			SetToDataSet();
			msg = "________________________________________ TICK " + tickNumber++
					+ " ________________________________________________________";
			LogThis(msg);

			PrintExeList();
			CheckTransitions();

			for (int i = 0; i < ExecutionList.size(); ++i) {
				if (ExecutionList.get(i).InitialDelay == 0) {
					try {
						ExecutionList.get(i).Activate();
						SetToDataSet();
						CheckTransitions();// To return to old algorithm comment this line
						PrintExeList();// To return to old algorithm comment this line
					} catch (CloneNotSupportedException e) {
						msg = e.getMessage();
						e.printStackTrace();
						LogThis(msg);
					}
				}
				ExecutionList.get(i).InitialDelay--;
			}

			for (int i = 0; i < ExecutionList.size(); ++i) {
				if (ExecutionList.get(i).InitialDelay < 0) {
					ExecutionList.remove(i);
					i--;
				}
			}
		}
	}

	public boolean ShowLogInWindow = true;
	public boolean ShowLog = true;

	public void LogThis(String log) {
		if (ShowLogInWindow) {
			m_lDataLoadFinished.onDataLoadFinishedListener(log);
		} else {
			m_lDataLoadFinished.onDataLoadFinishedListener("DrawGraphOnly");
		}

		if (ShowLog) {
			System.out.println(log);
		}
	}

	public void CheckTransitions() {
		String conditionsStatus = "";
		for (int i = 0; i < Transitions.size(); ++i) {
			if (Transitions.get(i).IsAsync)
				continue;
			if (!util.TransitionExist(Transitions.get(i).GetName(), ExecutionList)) {
				if (Transitions.get(i).CheckConditions()) {
					try {
						Transitions.get(i).BookTokens();
					} catch (CloneNotSupportedException e) {
						msg = e.getMessage();
						e.printStackTrace();
						LogThis(msg);
					}
					PetriTransition trr = Transitions.get(i);
					trr.InitialDelay = trr.Delay;
					ExecutionList.add(trr);
				} else {
					conditionsStatus += "[" + Transitions.get(i).TransitionName + " conditions are false]"
							+ " Delay:" + Transitions.get(i).Delay + " ";
				}
			}
		}
		if (conditionsStatus != "") {
			LogThis(conditionsStatus);
		}
	}

	@Override
	public void Stop() {
		StopFlag = true;
		for (int i = 0; i < Transitions.size(); ++i) {
			Transitions.get(i).Stop();
		}

		msg = "####################  " + PetriNetName + " Ended  #####################";
		LogThis(msg);
		PetriState = PetriNetState.Stopped;
	}

	public void SetToDataSet() {
		for (PetriObject petriObject : PlaceList) {
			if (fileInputIndex > -1) {
				if (petriObject instanceof DataFuzzy) {
					if (((DataFuzzy) petriObject).Value != null)
						dataset.addValue(((DataFuzzy) petriObject).Value.Value, petriObject.GetName(),
								String.valueOf(fileInputIndex));
				}

				if (petriObject instanceof DataInteger) {
					if (((DataInteger) petriObject).Value != null)
						dataset.addValue(((DataInteger) petriObject).Value, petriObject.GetName(),
								String.valueOf(fileInputIndex));
				}

				if (petriObject instanceof DataFloat) {
					if (((DataFloat) petriObject).Value != null)
						dataset.addValue(((DataFloat) petriObject).Value, petriObject.GetName(),
								String.valueOf(fileInputIndex));
				}
			} else {
				if (petriObject instanceof DataFuzzy) {
					if (((DataFuzzy) petriObject).Value != null)
						dataset.addValue(((DataFuzzy) petriObject).Value.Value, petriObject.GetName(),
								String.valueOf(dataset.getRowCount()));
				}

				if (petriObject instanceof DataInteger) {
					if (((DataInteger) petriObject).Value != null)
						dataset.addValue(((DataInteger) petriObject).Value, petriObject.GetName(),
								String.valueOf(dataset.getRowCount()));
				}

				if (petriObject instanceof DataFloat) {
					if (((DataFloat) petriObject).Value != null)
						dataset.addValue(((DataFloat) petriObject).Value, petriObject.GetName(),
								String.valueOf(dataset.getRowCount()));
				}
			}
		}
	}

	public void PrintPetri() {
		ArrayList<String> temp1 = new ArrayList<String>();
		for (PetriObject petriObject : PlaceList) {
			if (petriObject == null)
				temp1.add("NULL");
			else if (petriObject.IsPrintable()) {
				temp1.add(petriObject.toString());
			}
		}

		msg = name + " PlaceList [" + String.join("  ", temp1) + "]";
		LogThis(msg);

		temp1 = new ArrayList<String>();
		for (

		PetriObject petriObject : ConstantPlaceList) {
			if (petriObject == null)
				temp1.add("NULL");
			else if (petriObject.IsPrintable())
				temp1.add(petriObject.toString());
		}

		msg = name + " ConstantPlaceList [" + String.join("  ", temp1) + "]";
		LogThis(msg);
	}

	public void PrintExeList() {
		ArrayList<String> temp1 = new ArrayList<String>();
		for (PetriObject petriObject : ExecutionList) {
			if (petriObject == null)
				temp1.add("NULL");
			else
				temp1.add(petriObject.toString());
		}

		msg = name + " ExecutionList [" + String.join(",", temp1) + "]";
		LogThis(msg);
	}

	private DataOverNetwork inputdata = new DataOverNetwork();
	private boolean stop;
	public boolean NonBooking = false;
	public Integer NetworkPort = 0;

	public class NetworkListener implements Runnable {
		private PetriNet net;

		public NetworkListener(PetriNet net) {
			this.net = net;
		}

		public void run() {
			ServerSocket ss = null;
			try {
				if (NetworkPort == 0)
					return;
				ss = new ServerSocket(NetworkPort);

				msg = "Waiting For Commands over this port:" + NetworkPort;
				LogThis(msg);

				Socket s;
				ObjectInputStream ois;
				while (!net.stop) {
					s = ss.accept();
					s.setReuseAddress(true);
					ois = new ObjectInputStream(s.getInputStream());

					try {
						net.inputdata = (DataOverNetwork) ois.readObject();
						Integer index = net.util.GetIndexByName(net.inputdata.petriObject.GetName(), net.PlaceList);

						if (net.inputdata.petriObject.GetType() == PetriObjectType.PetriData) {
							Functions Fun = new Functions();

							DataSubPetriNet sub = new DataSubPetriNet();
							sub.SetName(net.inputdata.petriObject.GetName());
							SubPetri sbb = new SubPetri(Fun.PetriDataToPetriNet((PetriData) net.inputdata.petriObject));
							sub.SetValue(sbb);

							net.PlaceList.set(index, sub);

						} else {
							if (net.inputdata.petriObject.GetType() == PetriObjectType.DataNetworkCommand) {
								NetworkCommand cmd = (NetworkCommand) net.inputdata.petriObject.GetValue();
								switch (cmd.command) {
								case Pause:
									PauseFlag = true;
									LogThis("-------------------Got Network Command Pause-------------------");
									break;
								case Start:
									PauseFlag = false;
									LogThis("-------------------Got Network Command Start-------------------");
									break;
								case Stop:
									StopFlag = true;
									LogThis("-------------------Got Network Command Stop-------------------");
									break;
								default:
									break;
								}
							} else {
								net.PlaceList.set(index, net.inputdata.petriObject);
							}
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					msg = "$$$$$$$$$$$$$$$ I got an Input From NetWork for " + net.inputdata.petriObject.GetName();
					LogThis(msg);
					SetToDataSet();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class AsyncTransitionRunnableThread implements Runnable {
		private PetriNet net;
		private PetriTransition transition;

		public AsyncTransitionRunnableThread(PetriNet net, PetriTransition t) {
			this.net = net;
			this.transition = t;
		}

		public void run() {
			msg = "ASYNC Transition have been started " + transition.TransitionName;
			LogThis(msg);

			while (!StopFlag) {
				try {
					Thread.sleep(transition.Delay);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				if (transition.CheckConditions()) {
					try {
						transition.BookTokens();
						transition.Activate();
					} catch (CloneNotSupportedException e) {
						msg = e.getMessage();
						e.printStackTrace();
						LogThis(msg);
					}
				}
			}
		}
	}

	@Override
	public void run() {
		if (NonBooking) {
			StartNonBooking();
		} else {
			Start();
		}
	}

	public boolean Printable = true;

	@Override
	public boolean IsPrintable() {
		return Printable;
	}

	private boolean token;

	@Override
	public void SetToken(boolean token) {
		this.token = token;
	}

	@Override
	public boolean GetToken() {
		return this.token;
	}

	public void StartNonBooking() {
		PetriState = PetriNetState.Started;
		networkThread = new Thread();

		NetworkListener myRunnable = new NetworkListener(this);
		networkThread = new Thread(myRunnable);
		networkThread.start();

		msg = "####################  " + PetriNetName + " StartNonBooking  #####################";
		LogThis(msg);

		ExecutionList = new ArrayList<PetriTransition>();
		StopFlag = false;
		PauseFlag = false;
		while (!StopFlag) {
			try {
				Thread.sleep(Delay);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			if (PauseFlag) {
				continue;
			}

			PrintPetri();

			String conditionsStatus = "";
			for (int i = 0; i < Transitions.size(); ++i) {
				if (Transitions.get(i).IsAsync)
					continue;
				if (!util.TransitionExist(Transitions.get(i).GetName(), ExecutionList)) {
					if (Transitions.get(i).CheckConditions()) {

						PetriTransition trr = Transitions.get(i);
						trr.InitialDelay = trr.Delay;
						ExecutionList.add(trr);
					} else {
						conditionsStatus += "[" + Transitions.get(i).TransitionName + " conditions are false]"
								+ " Delay:" + Transitions.get(i).Delay+ " ";
					}
				}
			}
			if (conditionsStatus != "") {
				LogThis(conditionsStatus);
			}
			PrintExeList();
			for (int i = 0; i < ExecutionList.size(); ++i) {
				if (ExecutionList.get(i).InitialDelay == 0) {
					try {

						if (ExecutionList.get(i).CheckConditions()) {
							try {
								ExecutionList.get(i).BookTokens();
							} catch (CloneNotSupportedException e) {
								msg = e.getMessage();
								e.printStackTrace();
								LogThis(msg);
							}
							ExecutionList.get(i).Activate();
						}
					} catch (CloneNotSupportedException e) {
						msg = e.getMessage();
						e.printStackTrace();
						LogThis(msg);
					}
				}
				ExecutionList.get(i).InitialDelay--;
			}

			for (int i = 0; i < ExecutionList.size(); ++i) {
				if (ExecutionList.get(i).InitialDelay < 0) {
					ExecutionList.remove(i);
					i--;
				}
			}
		}
	}

}
