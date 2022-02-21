package experiments;

import org.apache.commons.lang3.SystemUtils;

public class Instance_Level_Difficulties {

	public static void main(String[] args) throws Exception {
		
		// Download datasets from https://people.vcu.edu/~acano/ROSE/datasets-instance-level.zip
		
		// Splitting into 5 clusters and evaluating the impact of percentage of borderline and rare instances

		String[] datasets = new String[] {
				// IR 1
				"Split5",
				"Split5+Rare20",
				"Split5+Rare40",
				"Split5+Rare60",
				"Split5+Rare80",
				"Split5+Rare100",
				"Split5+Borderline20",
				"Split5+Borderline40",
				"Split5+Borderline60",
				"Split5+Borderline80",
				"Split5+Borderline100",
				"Split5+Borderline20+Rare20",
				"Split5+Borderline40+Rare40",

				// IR 10
				"Split5+Im10",
				"Split5+Im10+Rare20",
				"Split5+Im10+Rare40",
				"Split5+Im10+Rare60",
				"Split5+Im10+Rare80",
				"Split5+Im10+Rare100",
				"Split5+Im10+Borderline20",
				"Split5+Im10+Borderline40",
				"Split5+Im10+Borderline60",
				"Split5+Im10+Borderline80",
				"Split5+Im10+Borderline100",
				"Split5+Im10+Borderline20+Rare20",
				"Split5+Im10+Borderline40+Rare40",

				// IR 100
				"Split5+Im1",
				"Split5+Im1+Rare20",
				"Split5+Im1+Rare40",
				"Split5+Im1+Rare60",
				"Split5+Im1+Rare80",
				"Split5+Im1+Rare100",
				"Split5+Im1+Borderline20",
				"Split5+Im1+Borderline40",
				"Split5+Im1+Borderline60",
				"Split5+Im1+Borderline80",
				"Split5+Im1+Borderline100",
				"Split5+Im1+Borderline20+Rare20",
				"Split5+Im1+Borderline40+Rare40",
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
				// Replace 
					System.out.println("java -Xms16g -Xmx1024g -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0.jar" + classpathSeparator + "MOA-dependencies.jar "
							+ "moa.DoTask EvaluatePrequential"
							+ " -e \"(WindowAUCImbalancedPerformanceEvaluator)\""
							+ " -s \"(ArffFileStream -f datasets-instance-level-difficulties/" + datasets[dat] + ".arff)\"" 
							+ " -l \"(" + algorithms[alg] + ")\""
							+ " -f 1000"
							+ " -d results_instance_level_difficulties/" + algorithmsFilename[alg] + "-" + datasets[dat] + ".csv");
			}
		}
	}
}