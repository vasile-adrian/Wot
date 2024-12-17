package DCS_FuzzyLab.RoomHeatingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

//For testing the PI controller alone
public class WheatherInput {
	public static void main(String[] args) throws InterruptedException, IOException {
		File plant = new File("/home/adrian/Documents/DCS_Lab/All_Petri_FW/All_Petri_FW/src/DCS_FuzzyLab/RoomHeatingSystem/OutsideTemp.txt");
		Files.deleteIfExists(plant.toPath());

		File RTC = new File("/home/adrian/Documents/DCS_Lab/All_Petri_FW/All_Petri_FW/src/DCS_FuzzyLab/RoomHeatingSystem/RTC.txt");
		Files.deleteIfExists(RTC.toPath());

		File HTC = new File("/home/adrian/Documents/DCS_Lab/All_Petri_FW/All_Petri_FW/src/DCS_FuzzyLab/RoomHeatingSystem/HTC.txt");
		Files.deleteIfExists(HTC.toPath());

		// Scenarios:
		float winterDay[] = new float[] { -12.5F / 25, -15.0F / 25, -17.0F / 25, -20.0F / 25, -21.0F / 25, -19.0F / 25,
				-17.0F / 25, -15.0F / 25, -12.0F / 25, -8.0F / 25, -7.0F / 25, -5.0F / 25, -4.0F / 25, -3.5F / 25,
				-5.0F / 25, -4.0F / 25, -5.0F / 25, -6.0F / 25, -7.5F / 25, -8.5F / 25, -9.0F / 25, -11.0F / 25,
				-11.5F / 25, -12.0F / 25, -12.0F / 25 };

		float winterMorning[] = new float[] { -19.0F / 25, -18.5F / 25, -18.0F / 25, -17.5F / 25, -17.0F / 25,
				-16.5F / 25, -16.0F / 25, -15.5F / 25, -15.0F / 25, -14.5F / 25, -14.0F / 25, -13.5F / 25, -13.0F / 25,
				-12.5F / 25, -12.0F / 25 };

		float extermeEvening[] = new float[] { -5.0F / 25, -6.0F / 25, -8.0F / 25, -10.0F / 25, -12.0F / 25,
				-13.0F / 25, -14.0F / 25, -16.0F / 25, -18.0F / 25, -22.0F / 25, -27.0F / 25 };
		// -------------------------------

		float roomReference = 24 / 25F;
		float waterReference = 48 / 70F;

		FileWriter fwPlant = new FileWriter(plant.getPath());
		FileWriter fwRTC = new FileWriter(RTC.getPath());
		FileWriter fwHTC = new FileWriter(HTC.getPath());

		for (int i = 0; i < winterDay.length; i++) {
			fwPlant.write("v1:" + winterDay[i] + "F\n");
			fwRTC.write("P1:" + roomReference + "F\n");
			fwHTC.write("P1:" + waterReference + "F\n");
		}

		fwPlant.close();
		fwRTC.close();
		fwHTC.close();
		System.out.println("Done!");
	}
}
