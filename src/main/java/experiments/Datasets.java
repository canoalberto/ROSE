package experiments;

import org.apache.commons.lang3.SystemUtils;

public class Datasets {

	public static void main(String[] args) throws Exception {
		
		// Download datasets from https://people.vcu.edu/~acano/ROSE/datasets.zip

		String[] datasets = new String[] {
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
		
		String classpathSeparator = SystemUtils.IS_OS_UNIX ? ":" : ";";

		for(int dat = 0; dat < datasets.length; dat++)
		{
			for(int alg = 0; alg < algorithms.length; alg++)
			{
				// Replace evaluator with WindowAUCMultiClassImbalancedPerformanceEvaluator for multi-class datasets
				System.out.println("java -Xms16g -Xmx1024g -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0.jar" + classpathSeparator + "MOA-dependencies.jar "
						+ "moa.DoTask EvaluateInterleavedTestThenTrain"
						+ " -e \"(WindowAUCMultiClassImbalancedPerformanceEvaluator)\""
						+ " -s \"(ArffFileStream -f datasets/" + datasets[dat] + ".arff)\"" 
						+ " -l \"(" + algorithms[alg] + ")\""
						+ " -f 500"
						+ " -d results_datasets/" + algorithmsFilename[alg] + "-" + datasets[dat] + ".csv");
			}
		}
	}
}