package moa.evaluation;

import moa.core.Example;
import moa.core.Measurement;
import moa.core.ObjectRepository;
import moa.core.Utils;
import moa.options.AbstractOptionHandler;

import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstanceImpl;
import com.yahoo.labs.samoa.instances.Prediction;

import moa.tasks.TaskMonitor;

/**
 * Classification evaluator that updates evaluation results using a sliding window.
 * Performance measures designed for class imbalance problems (binary and multiclass).
 * 
 * @author Alberto Cano
 */
public class WindowImbalancedClassificationPerformanceEvaluator extends AbstractOptionHandler implements ClassificationPerformanceEvaluator {

	private static final long serialVersionUID = 1L;

	public IntOption widthOption = new IntOption("width", 'w', "Size of Window", 1000);

	protected int confusionMatrix[][];

	protected int numClasses;

	protected int totalObservedInstances;

	protected int positionWindow;

	protected int predictionsTrue[];

	protected int predictionsPredicted[];

	@Override
	public void reset() {
		reset(this.numClasses);
	}

	public void reset(int numClasses) {
		this.numClasses = numClasses;
		this.positionWindow = 0;
		this.totalObservedInstances = 0;
		this.confusionMatrix = new int[numClasses][numClasses];
		this.predictionsTrue = new int[this.widthOption.getValue()];
		this.predictionsPredicted = new int[this.widthOption.getValue()];
	}

	@Override
	public void addResult(Example<Instance> exampleInstance, double[] classVotes) {
		InstanceImpl inst = (InstanceImpl)exampleInstance.getData();
		double weight = inst.weight();

		if (inst.classIsMissing() == false){
			int trueClass = (int) inst.classValue();
			int predictedClass = Utils.maxIndex(classVotes);

			if (weight > 0.0) {
				// // initialize evaluator
				if (totalObservedInstances == 0) {
					reset(inst.dataset().numClasses());
				}

				this.totalObservedInstances++;

				if(totalObservedInstances > this.widthOption.getValue())
				{
					this.confusionMatrix[predictionsPredicted[positionWindow]][predictionsTrue[positionWindow]]--;
				}

				this.predictionsTrue[positionWindow] = trueClass;
				this.predictionsPredicted[positionWindow] = predictedClass;
				this.confusionMatrix[predictedClass][trueClass]++;

				positionWindow = (positionWindow + 1) % this.widthOption.getValue();
			}
		}
	}

	protected double Kappa(int[][] confusionMatrix)
	{
		int correctedClassified = 0;
		int numberInstancesTotal = 0;
		int[] numberInstances = new int[numClasses];
		int[] predictedInstances = new int[numClasses];

		for(int i = 0; i < numClasses; i++)
		{
			correctedClassified += confusionMatrix[i][i];

			for(int j = 0; j < numClasses; j++)
			{
				numberInstances[i] += confusionMatrix[j][i];
				predictedInstances[i] += confusionMatrix[i][j];
			}

			numberInstancesTotal += numberInstances[i];
		}

		double mul = 0;

		for(int i = 0; i < numClasses; i++)
			mul += numberInstances[i] * predictedInstances[i];

		if(numberInstancesTotal*numberInstancesTotal - mul  != 0)
			return ((numberInstancesTotal * correctedClassified) - mul) / (double) ((numberInstancesTotal*numberInstancesTotal) - mul);
		else
			return 1.0;
	}

	protected double AUC(int[][] confusionMatrix)
	{
		if(numClasses == 2)
			return AUC(confusionMatrix,0,1); // Assumes 0 positive (minority), 1 negative (majority)
		else
		{
			/** Multi-class AUC **/
			double auc = 0.0;

			for(int i = 0; i < numClasses; i++)
				for(int j = 0; j < numClasses; j++)
					if(i != j)
						auc += AUC(confusionMatrix,i,j);

			return auc / (double) (numClasses * (numClasses-1));
		}
	}

	protected double AUC(int[][] confusionMatrix, int Class1, int Class2)
	{
		int tp = confusionMatrix[Class1][Class1];
		int fp = confusionMatrix[Class1][Class2];
		int tn = confusionMatrix[Class2][Class2];
		int fn = confusionMatrix[Class2][Class1];

		double tpRate = 1.0, fpRate = 0.0;

		if(tp + fn != 0)
			tpRate = tp / (double) (tp + fn);

		if(fp + tn != 0)
			fpRate = fp / (double) (fp + tn);

		double auc = (1.0 + tpRate - fpRate) / 2.0;

		return auc;
	}

	protected double GMean(int[][] confusionMatrix)
	{
		double gmean = 1.0;

		int[] numberInstances = new int[numClasses];

		for(int i = 0; i < numClasses; i++)
		{
			for(int j = 0; j < numClasses; j++)
				numberInstances[i] += confusionMatrix[j][i];

			if(numberInstances[i] != 0)
				gmean *= (confusionMatrix[i][i] / (double) numberInstances[i]);
		}

		return Math.pow(gmean, 1.0 / (double) numClasses);
	}

	protected double Accuracy(int[][] confusionMatrix)
	{
		int correctedClassified = 0;
		int numberInstancesTotal = 0;

		for(int i = 0; i < numClasses; i++)
		{
			correctedClassified += confusionMatrix[i][i];

			for(int j = 0; j < numClasses; j++)
				numberInstancesTotal += confusionMatrix[i][j];
		}

		return correctedClassified / (double) numberInstancesTotal;
	}

	protected double AvgAccuracy(int[][] confusionMatrix)
	{
		int[] numberInstances = new int[numClasses];

		for(int i = 0; i < numClasses; i++)
			for(int j = 0; j < numClasses; j++)
				numberInstances[i] += confusionMatrix[j][i];

		double avgAccuracy = 0;
		int existingClasses = 0;

		for(int i = 0; i < numClasses; i++)
			if(numberInstances[i] != 0)
			{
				avgAccuracy += confusionMatrix[i][i] / (double) numberInstances[i];
				existingClasses++;
			}

		return avgAccuracy / (double) existingClasses;
	}

	protected double Precision(int[][] confusionMatrix)
	{
		int[] numberPredictions = new int[numClasses];

		for(int i = 0; i < numClasses; i++)
			for(int j = 0; j < numClasses; j++)
				numberPredictions[i] += confusionMatrix[i][j];

		double precision = 0;
		int existingClasses = 0;

		for(int i = 0; i < numClasses; i++)
			if(numberPredictions[i] != 0)
			{
				precision += confusionMatrix[i][i] / (double) numberPredictions[i];
				existingClasses++;
			}

		return precision / (double) existingClasses;
	}

	protected double Recall(int[][] confusionMatrix)
	{
		int[] numberInstances = new int[numClasses];

		for(int i = 0; i < numClasses; i++)
			for(int j = 0; j < numClasses; j++)
				numberInstances[i] += confusionMatrix[j][i];

		double recall = 0;
		int existingClasses = 0;

		for(int i = 0; i < numClasses; i++)
			if(numberInstances[i] != 0)
			{
				recall += confusionMatrix[i][i] / (double) numberInstances[i];
				existingClasses++;
			}

		return recall / (double) existingClasses;
	}

	protected double Ratio(int[][] confusionMatrix, int targetClass)
	{
		int numberInstances = 0;
		int numberInstancesTotal = 0;

		for(int i = 0; i < numClasses; i++)
		{
			numberInstances += confusionMatrix[i][targetClass];

			for(int j = 0; j < numClasses; j++)
				numberInstancesTotal += confusionMatrix[i][j];
		}

		return numberInstances / (double) numberInstancesTotal;
	}
	

	public double getAccuracy() {
		return Accuracy(this.confusionMatrix);
	}
	
	public double getKappa() {
		return Kappa(this.confusionMatrix);
	}

	@Override
	public Measurement[] getPerformanceMeasurements() {

		Measurement[] measurement = new Measurement[8 + numClasses];

		measurement[0] = new Measurement("classified instances", this.totalObservedInstances);			
		measurement[1] = new Measurement("Accuracy", Accuracy(this.confusionMatrix));
		measurement[2] = new Measurement("AvgAccuracy", AvgAccuracy(this.confusionMatrix));
		measurement[3] = new Measurement("AUC", AUC(this.confusionMatrix));
		measurement[4] = new Measurement("Kappa", Kappa(this.confusionMatrix));
		measurement[5] = new Measurement("G-Mean", GMean(this.confusionMatrix));
		measurement[6] = new Measurement("Precision", Precision(this.confusionMatrix));
		measurement[7] = new Measurement("Recall", Recall(this.confusionMatrix));

		for(int i = 0; i < numClasses; i++)
			measurement[8 + i] = new Measurement("Ratio-Class-" + i, Ratio(this.confusionMatrix, i));

		return measurement;
	}

	@Override
	public void getDescription(StringBuilder sb, int indent) {
		Measurement.getMeasurementsDescription(getPerformanceMeasurements(), sb, indent);
	}

	@Override
	public void prepareForUseImpl(TaskMonitor monitor,
			ObjectRepository repository) {
	}

	@Override
	public void addResult(Example<Instance> arg0, Prediction arg1) {
		throw new RuntimeException("Designed for scoring classifiers");
	}
}