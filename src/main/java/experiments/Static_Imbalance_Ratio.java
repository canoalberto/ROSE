package experiments;

import org.apache.commons.lang3.SystemUtils;

public class Static_Imbalance_Ratio {

	public static void main(String[] args) throws Exception {
		
		String[] generators = new String[] {
				// IR 100
				"moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.01",
				"moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 1 -f 3 -m 0.01",
				"moa.streams.generators.imbalanced.RandomRBFGenerator -i 1 -r 1 -a 50 -c 2 -m 0.01",
				"moa.streams.generators.imbalanced.SEAGenerator -i 1 -f 1 -m 0.01",
				"moa.streams.generators.imbalanced.SineGenerator -i 1 -f 1 -m 0.01",
				"moa.streams.generators.imbalanced.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.05 -m 0.01",

				// IR 50
				"moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.02",
				"moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 1 -f 3 -m 0.02",
				"moa.streams.generators.imbalanced.RandomRBFGenerator -i 1 -r 1 -a 50 -c 2 -m 0.02",
				"moa.streams.generators.imbalanced.SEAGenerator -i 1 -f 1 -m 0.02",
				"moa.streams.generators.imbalanced.SineGenerator -i 1 -f 1 -m 0.02",
				"moa.streams.generators.imbalanced.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.05 -m 0.02",
				
				// IR 20
				"moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.05",
				"moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 1 -f 3 -m 0.05",
				"moa.streams.generators.imbalanced.RandomRBFGenerator -i 1 -r 1 -a 50 -c 2 -m 0.05",
				"moa.streams.generators.imbalanced.SEAGenerator -i 1 -f 1 -m 0.05",
				"moa.streams.generators.imbalanced.SineGenerator -i 1 -f 1 -m 0.05",
				"moa.streams.generators.imbalanced.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.05 -m 0.05",
				
				// IR 10
				"moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.1",
				"moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 1 -f 3 -m 0.1",
				"moa.streams.generators.imbalanced.RandomRBFGenerator -i 1 -r 1 -a 50 -c 2 -m 0.1",
				"moa.streams.generators.imbalanced.SEAGenerator -i 1 -f 1 -m 0.1",
				"moa.streams.generators.imbalanced.SineGenerator -i 1 -f 1 -m 0.1",
				"moa.streams.generators.imbalanced.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.05 -m 0.1",
				
				// IR 5
				"moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.2",
				"moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 1 -f 3 -m 0.2",
				"moa.streams.generators.imbalanced.RandomRBFGenerator -i 1 -r 1 -a 50 -c 2 -m 0.2",
				"moa.streams.generators.imbalanced.SEAGenerator -i 1 -f 1 -m 0.2",
				"moa.streams.generators.imbalanced.SineGenerator -i 1 -f 1 -m 0.2",
				"moa.streams.generators.imbalanced.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.05 -m 0.2",
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
		String[] generatorsFilename = new String[generators.length];
		
		for(int alg = 0; alg < algorithms.length; alg++) {
			algorithmsFilename[alg] = algorithms[alg].replaceAll("moa.classifiers.meta.", "").replaceAll("imbalanced.", "");
		}
		
		for(int gen = 0; gen < generators.length; gen++) {
			generatorsFilename[gen] = generators[gen].replaceAll("moa.streams.generators.imbalanced.", "").replaceAll(" ", "");
		}
		
		String classpathSeparator = SystemUtils.IS_OS_UNIX ? ":" : ";";

		for(int gen = 0; gen < generators.length; gen++)
		{
			for(int alg = 0; alg < algorithms.length; alg++)
			{
				// Replace 
					System.out.println("java -Xms16g -Xmx1024g -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0.jar" + classpathSeparator + "MOA-dependencies.jar "
							+ "moa.DoTask EvaluatePrequential"
							+ " -e \"(WindowAUCImbalancedPerformanceEvaluator)\""
							+ " -s \"(" + generators[gen] + ")\"" 
							+ " -l \"(" + algorithms[alg] + ")\""
							+ " -f 1000"
							+ " -d results_static_IR/" + algorithmsFilename[alg] + "-" + generatorsFilename[gen] + ".csv");
			}
		}
	}
}