package experiments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Collect_Results {

	public static void main(String[] args) throws Exception {
		
		String resultsPath = "results_datasets";

		// Replace with the collection of files (datasets / generators) to parse
		String[] files = new String[] {
				"thyroid",
				"coil2000",
				"penbased",
				"gas-sensor",
				"magic",
				"letter",
				"kr-vs-k",
				"powersupply",
				"adult",
				"electricity",
				"shuttle",
				"connect-4",
				"fars",
				"dj30",
				"census",
				"airlines",
				"covtype",
				"poker",
				"bridges",
				"zoo",
				"IntelLabSensors",
				"kddcup",	
				"GMSC",
				"nomao",
		};

		String[] algorithms = new String[] {
				"moa.classifiers.meta.imbalanced.ROSE",
				"moa.classifiers.meta.KUE",
				"moa.classifiers.meta.AccuracyWeightedEnsemble",
				"moa.classifiers.meta.AccuracyUpdatedEnsemble1",
				"moa.classifiers.meta.AccuracyUpdatedEnsemble2",
				"moa.classifiers.meta.DynamicWeightedMajority",
				"moa.classifiers.meta.SAE2",
				"moa.classifiers.meta.DACC",
				"moa.classifiers.meta.ADACC",
				"moa.classifiers.meta.AdaptiveRandomForest",
				"moa.classifiers.meta.ADOB",
				"moa.classifiers.meta.BOLE",
				"moa.classifiers.meta.GOOWE",
				"moa.classifiers.meta.HeterogeneousEnsembleBlast",
				"moa.classifiers.meta.LeveragingBag",
				"moa.classifiers.meta.OCBoost",
				"moa.classifiers.meta.OzaBag",
				"moa.classifiers.meta.OzaBagAdwin",
				"moa.classifiers.meta.OzaBagASHT",
				"moa.classifiers.meta.OzaBoost",
				"moa.classifiers.meta.OzaBoostAdwin",
				"moa.classifiers.meta.StreamingRandomPatches",
				"moa.classifiers.meta.UOB",
				"moa.classifiers.meta.OOB",
				"moa.classifiers.meta.imbalanced.OnlineSMOTEBagging",
				"moa.classifiers.meta.imbalanced.OnlineUnderOverBagging",
				"moa.classifiers.meta.imbalanced.CSMOTE",
				"moa.classifiers.meta.imbalanced.OnlineAdaBoost",
				"moa.classifiers.meta.imbalanced.OnlineAdaC2",
				"moa.classifiers.meta.imbalanced.OnlineRUSBoost",
				"moa.classifiers.meta.imbalanced.RebalanceStream",
		};
		
		String[] algorithmsFilename = new String[algorithms.length];
		
		for(int alg = 0; alg < algorithms.length; alg++) {
			algorithmsFilename[alg] = algorithms[alg].replaceAll("moa.classifiers.meta.", "").replaceAll("imbalanced.", "");
		}

		metric("Accuracy", "averaged", resultsPath, files, algorithmsFilename);
		metric("Kappa", "averaged", resultsPath,files, algorithmsFilename);
		metric("AUC", "averaged", resultsPath, files, algorithmsFilename);
	}

	private static void metric(String metricName, String outcome, String resultsPath, String[] files, String[] algorithms) throws Exception {

		System.out.print(metricName + "\t");
		
		for(String algorithm : algorithms)
			System.out.print(algorithm + "\t");
		System.out.println("");
		
		for(String file : files)
		{
			System.out.print(file + "\t");

			for(int alg = 0; alg < algorithms.length; alg++)
			{
				int count = 0;
				double sum = 0;
				double lastValue = 0;

				String filename = resultsPath + "/" + algorithms[alg] + "-" + file + ".csv";

				if(new File(filename).exists())
				{
					BufferedReader br = new BufferedReader(new FileReader(new File(filename)));

					String line;
					line = br.readLine(); // header line
					
					String[] columns = null;
					
					try {
						columns = line.split(",");
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(-1);
					}

					int index = -1;
					for(int i = 0; i < columns.length; i++)
						if(columns[i].equals(metricName))
							index = i;

					line = br.readLine(); // first chunk (?)

					while((line = br.readLine()) != null)
					{
						try {
							if(!line.split(",")[index].equals("?"))
							{
								lastValue = Double.parseDouble(line.split(",")[index]);
								sum += lastValue;
								count++;
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.exit(-1);
						}
					}

					br.close();
				}

				if(outcome.equalsIgnoreCase("averaged"))
					System.out.print((sum/count) + "\t");
				else
					System.out.print(lastValue + "\t");
			}

			System.out.println("");
		}
	}
}