package experiments;

import org.apache.commons.lang3.SystemUtils;

public class Drifting_Imbalance_Ratio {

	public static void main(String[] args) throws Exception {
		
		String[] generators = new String[] {
				// Sudden drift
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 2 -f 2 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 3 -f 2 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 4 -f 2 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 5 -f 2 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 6 -f 2 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 7 -f 2 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.AgrawalGenerator -i 8 -f 2 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 1 -f 1 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 2 -f 1 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 3 -f 1 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 4 -f 1 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 5 -f 1 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 6 -f 1 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 7 -f 1 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 8 -f 1 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 1 -a 10 -c 2 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 2 -a 10 -c 2 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 3 -a 10 -c 2 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 4 -a 10 -c 2 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 5 -a 10 -c 2 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 6 -a 10 -c 2 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 7 -a 10 -c 2 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.HyperplaneGenerator -i 8 -a 10 -c 2 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 1 -r 1 -a 10 -c 2 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 2 -r 2 -a 10 -c 2 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 3 -r 3 -a 10 -c 2 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 4 -r 4 -a 10 -c 2 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 5 -r 5 -a 10 -c 2 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 6 -r 6 -a 10 -c 2 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 7 -r 7 -a 10 -c 2 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.RandomRBFGenerator -i 8 -r 8 -a 10 -c 2 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 1 -f 1 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 2 -f 1 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 3 -f 1 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 4 -f 1 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 5 -f 1 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 6 -f 1 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 7 -f 1 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.SEAGenerator -i 8 -f 1 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 1 -f 1 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 2 -f 1 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 3 -f 1 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 4 -f 1 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 5 -f 1 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 6 -f 1 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 7 -f 1 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.SineGenerator -i 8 -f 1 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1) "
		  						 + "-p 125000 -w 1",			  						 
				 // Gradual drift
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 2 -f 2 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 3 -f 2 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 4 -f 2 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 5 -f 2 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 6 -f 2 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 7 -f 2 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.AgrawalGenerator -i 8 -f 2 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 1 -f 1 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 2 -f 1 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 3 -f 1 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 4 -f 1 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 5 -f 1 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 6 -f 1 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 7 -f 1 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.AssetNegotiationGenerator -i 8 -f 1 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 1 -a 10 -c 2 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 2 -a 10 -c 2 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 3 -a 10 -c 2 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 4 -a 10 -c 2 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 5 -a 10 -c 2 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 6 -a 10 -c 2 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.HyperplaneGenerator -i 7 -a 10 -c 2 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.HyperplaneGenerator -i 8 -a 10 -c 2 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 1 -r 1 -a 10 -c 2 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 2 -r 2 -a 10 -c 2 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 3 -r 3 -a 10 -c 2 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 4 -r 4 -a 10 -c 2 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 5 -r 5 -a 10 -c 2 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 6 -r 6 -a 10 -c 2 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.RandomRBFGenerator -i 7 -r 7 -a 10 -c 2 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.RandomRBFGenerator -i 8 -r 8 -a 10 -c 2 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 1 -f 1 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 2 -f 1 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 3 -f 1 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 4 -f 1 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 5 -f 1 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 6 -f 1 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SEAGenerator -i 7 -f 1 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.SEAGenerator -i 8 -f 1 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000",	
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 1 -f 1 -m 0.2) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 2 -f 1 -m 0.1) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 3 -f 1 -m 0.05) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 4 -f 1 -m 0.01) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 5 -f 1 -m 0.01) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 6 -f 1 -m 0.05) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.SineGenerator -i 7 -f 1 -m 0.1) -r 7 "
		  						 + "-d (moa.streams.generators.imbalanced.SineGenerator -i 8 -f 1 -m 0.2) -r 8 "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000) "
		  						 + "-p 125000 -w 50000",			  						 
		};
		
		String[] generatorsFilename = new String[] {
				"Sudden-AgrawalGenerator-IncreasingDecreasingIR",
				"Sudden-AssetNegotiationGenerator-IncreasingDecreasingIR",
				"Sudden-HyperplaneGenerator-IncreasingDecreasingIR",
				"Sudden-RandomRBFGenerator-IncreasingDecreasingIR",
				"Sudden-SEAGenerator-IncreasingDecreasingIR",
				"Sudden-SineGenerator-IncreasingDecreasingIR",
				"Gradual-AgrawalGenerator-IncreasingDecreasingIR",
				"Gradual-AssetNegotiationGenerator-IncreasingDecreasingIR",
				"Gradual-HyperplaneGenerator-IncreasingDecreasingIR",
				"Gradual-RandomRBFGenerator-IncreasingDecreasingIR",
				"Gradual-SEAGenerator-IncreasingDecreasingIR",
				"Gradual-SineGenerator-IncreasingDecreasingIR",
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

		for(int gen = 0; gen < generators.length; gen++)
		{
			for(int alg = 0; alg < algorithms.length; alg++)
			{
				System.out.println("java -Xms16g -Xmx1024g -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0.jar" + classpathSeparator + "MOA-dependencies.jar "
						+ "moa.DoTask EvaluateInterleavedTestThenTrain"
						+ " -e \"(WindowAUCImbalancedPerformanceEvaluator)\""
						+ " -s \"(" + generators[gen] + ")\"" 
						+ " -l \"(" + algorithms[alg] + ")\""
						+ " -f 500"
						+ " -d results_drifting_IR/" + algorithmsFilename[alg] + "-" + generatorsFilename[gen] + ".csv");
			}
		}
	}
}