/*
 *    AddNoiseFilter.java
 *    Copyright (C) 2007 University of Waikato, Hamilton, New Zealand
 *    @author Richard Kirkby (rkirkby@cs.waikato.ac.nz)
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program. If not, see <http://www.gnu.org/licenses/>.
 *    
 */
package moa.streams.filters;

import java.util.ArrayList;
import java.util.Random;

import moa.core.AutoExpandVector;
import moa.core.DoubleVector;
import moa.core.GaussianEstimator;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;

/**
 * Filter for adding random noise to examples in a stream.
 *
 * @author Alberto Cano (acano@vcu.edu)
 * @version $Revision: 1 $
 */
public class AddNoiseFilterFeatures extends AbstractStreamFilter {

	@Override
	public String getPurposeString() {
		return "Adds random noise to features in a stream.";
	}

	private static final long serialVersionUID = 1L;

	public IntOption randomSeedOption = new IntOption("randomSeed", 'r', "Seed for random noise.", 1);

	public FloatOption attNoiseFractionOption = new FloatOption("attNoise", 'f', "Probability of an attribute to be disturbed", 0.2, 0.0, 1.0);

	public FloatOption attValueNoiseFractionOption = new FloatOption("attValueNoise", 'a', "The fraction of attribute values to disturb.", 0.1, 0.0, 1.0);

	protected Random random;

	protected AutoExpandVector<Object> attValObservers;

	protected boolean[] disturbeAttribute;

	@Override
	protected void restartImpl() {
		this.disturbeAttribute = null;
		this.random = new Random(this.randomSeedOption.getValue());
		this.attValObservers = new AutoExpandVector<Object>();
	}

	@Override
	public InstancesHeader getHeader() {
		return this.inputStream.getHeader();
	}

	public Instance filterInstance(Instance inst) {

		if(this.disturbeAttribute == null) {
			this.disturbeAttribute = new boolean[inst.numAttributes()-1];
			
			int numberAttributesDisturbed = (int) Math.ceil((inst.numAttributes()-1) * this.attNoiseFractionOption.getValue());
			
			ArrayList<Integer> attributesPool = new ArrayList<Integer>();
			
			for(int i = 0; i < inst.numAttributes()-1; i++) {
				attributesPool.add(i);
			}
			
			for(int i = 0; i < numberAttributesDisturbed; i++) {
				this.disturbeAttribute[attributesPool.remove(this.random.nextInt(attributesPool.size()))] = true;
			}
		}

		for (int i = 0; i < inst.numAttributes() -1 ; i++) {
			if(this.disturbeAttribute[i]) {
				double noiseFrac = this.attValueNoiseFractionOption.getValue();
				
				if (inst.attribute(i).isNominal()) {
					DoubleVector obs = (DoubleVector) this.attValObservers.get(i);
					if (obs == null) {
						obs = new DoubleVector();
						this.attValObservers.set(i, obs);
					}
					int originalVal = (int) inst.value(i);
					if (!inst.isMissing(i)) {
						obs.addToValue(originalVal, inst.weight());
					}
					if ((this.random.nextDouble() < noiseFrac) && (obs.numNonZeroEntries() > 1)) {
						do {
							inst.setValue(i, this.random.nextInt(obs.numValues()));
						} while (((int) inst.value(i) == originalVal) || (obs.getValue((int) inst.value(i)) == 0.0));
					}
				} else {
					GaussianEstimator obs = (GaussianEstimator) this.attValObservers.get(i);
					if (obs == null) {
						obs = new GaussianEstimator();
						this.attValObservers.set(i, obs);
					}
					obs.addObservation(inst.value(i), inst.weight());
					inst.setValue(i, inst.value(i) + this.random.nextGaussian() * obs.getStdDev() * noiseFrac);
				}
			}
		}
		return inst;
	}

	@Override
	public void getDescription(StringBuilder sb, int indent) {
	}
}
