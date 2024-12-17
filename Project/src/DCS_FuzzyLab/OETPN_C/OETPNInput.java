package DCS_FuzzyLab.OETPN_C;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

//For OETPN-c 
public class OETPNInput {
	public static void main(String[] args) throws InterruptedException, IOException {
		File file = new File("D:\\PetriInputData\\OETPNInput.txt");
		Files.deleteIfExists(file.toPath());
		FileWriter fw = new FileWriter(file.getPath());
		Float command = 0.55F;

		for (float i = 0; i < 100; i++) {
			if (i > 50)
				command = 0.35f;

			fw.write("r:" + command + "F\n");
		}
		fw.close();
		System.out.println("Done!");
	}
}
