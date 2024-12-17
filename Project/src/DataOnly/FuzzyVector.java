package DataOnly;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.stream.Collectors;

import Enumerations.FZ;

public class FuzzyVector implements Serializable {
	public ArrayList<FuzzyVectorValue> Values = new ArrayList<FuzzyVectorValue>();

	public FuzzyVector(Float NL, Float NM, Float ZR, Float PM, Float PL) {
		Values.add(new FuzzyVectorValue(FZ.NL, NL));
		Values.add(new FuzzyVectorValue(FZ.NM, NM));
		Values.add(new FuzzyVectorValue(FZ.ZR, ZR));
		Values.add(new FuzzyVectorValue(FZ.PM, PM));
		Values.add(new FuzzyVectorValue(FZ.PL, PL));
	}

	public ArrayList<FuzzyVectorValue> GetNoneZeroValues() {
		ArrayList<FuzzyVectorValue> result = new ArrayList<FuzzyVectorValue>();
		for (FuzzyVectorValue fuzzyVectorValue : Values) {
			if (fuzzyVectorValue.Value > 0f) {
				result.add(fuzzyVectorValue);
			}
		}
		return result;
	}

	public String toString() {
		if (Values.size() > 1) {
			ArrayList<String> test = (ArrayList<String>) Values.stream().map(x -> x.Value.toString())
					.collect(Collectors.toList());
			return "< " + String.join(" , ", test) + " >";
		}
		return "< No Items Yet >";
	}

	Float SetScale(Float f) {
		BigDecimal bd = new BigDecimal(f).setScale(2, RoundingMode.HALF_UP);
		return bd.floatValue();
	}

	public void GenerateFuzzyToken(Float v) {
		Values = new ArrayList<FuzzyVectorValue>();
		Values.add(new FuzzyVectorValue(FZ.NL, 0f));
		Values.add(new FuzzyVectorValue(FZ.NM, 0f));
		Values.add(new FuzzyVectorValue(FZ.ZR, 0f));
		Values.add(new FuzzyVectorValue(FZ.PM, 0f));
		Values.add(new FuzzyVectorValue(FZ.PL, 0f));
		
		if (v == -1F) {
			FuzzyVectorValue val = Values.stream().filter((x) -> x.Zone == FZ.NL).findFirst().orElse(null);

			val.Value = 1f;
			return;
		}

		if (v == -0.5F) {
			FuzzyVectorValue val = Values.stream().filter((x) -> x.Zone == FZ.NM).findFirst().orElse(null);

			val.Value = 1f;
			return;
		}

		if (v == 0.0F) {
			FuzzyVectorValue val = Values.stream().filter((x) -> x.Zone == FZ.ZR).findFirst().orElse(null);

			val.Value = 1f;
			return;
		}

		if (v == 0.5F) {
			FuzzyVectorValue val = Values.stream().filter((x) -> x.Zone == FZ.PM).findFirst().orElse(null);

			val.Value = 1f;
			return;
		}

		if (v == 1F) {
			FuzzyVectorValue val = Values.stream().filter((x) -> x.Zone == FZ.PL).findFirst().orElse(null);

			val.Value = 1f;
			return;
		}

		if (v > -1F && v < -0.5) {
			FuzzyVectorValue nl = Values.stream().filter((x) -> x.Zone == FZ.NL).findFirst().orElse(null);

			FuzzyVectorValue nm = Values.stream().filter((x) -> x.Zone == FZ.NM).findFirst().orElse(null);

			nl.Value = (v + 0.5f) / -0.5f;
			nm.Value = (-1f - v) / -0.5f;

			nl.Value = SetScale(nl.Value);
			nm.Value = SetScale(nm.Value);
			return;
		}

		if (v > -0.5F && v < 0) {
			FuzzyVectorValue nm = Values.stream().filter((x) -> x.Zone == FZ.NM).findFirst().orElse(null);

			FuzzyVectorValue zr = Values.stream().filter((x) -> x.Zone == FZ.ZR).findFirst().orElse(null);

			nm.Value = v / -0.5f;
			zr.Value = (-0.5f - v) / -0.5f;

			nm.Value = SetScale(nm.Value);
			zr.Value = SetScale(zr.Value);
			return;
		}

		if (v > 0 && v < 0.5f) {
			FuzzyVectorValue zr = Values.stream().filter((x) -> x.Zone == FZ.ZR).findFirst().orElse(null);

			FuzzyVectorValue pm = Values.stream().filter((x) -> x.Zone == FZ.PM).findFirst().orElse(null);

			zr.Value = (0.5f - v) / 0.5f;
			pm.Value = v / 0.5f;

			zr.Value = SetScale(zr.Value);
			pm.Value = SetScale(pm.Value);
			return;
		}

		if (v > 0.5 && v < 1f) {
			FuzzyVectorValue pm = Values.stream().filter((x) -> x.Zone == FZ.PM).findFirst().orElse(null);
			FuzzyVectorValue pl = Values.stream().filter((x) -> x.Zone == FZ.PL).findFirst().orElse(null);

			pm.Value = (1f - v) / 0.5f;
			pl.Value = (v - 0.5f) / 0.5f;

			pm.Value = SetScale(pm.Value);
			pl.Value = SetScale(pl.Value);

			return;
		}
		
		if (v > 1F) {
			FuzzyVectorValue val = Values.stream().filter((x) -> x.Zone == FZ.PL).findFirst().orElse(null);

			val.Value = 1f;
			return;
		}
		
		if (v < -1F) {
			FuzzyVectorValue val = Values.stream().filter((x) -> x.Zone == FZ.NL).findFirst().orElse(null);

			val.Value = 1f;
			return;
		}
	}
}
