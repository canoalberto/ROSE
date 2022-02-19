# ROSE: Robust Online Self-Adjusting Ensemble for Continual Learning from Imbalanced Drifting Data Streams

<p align="justify">Data streams are potentially unbounded sequences of instances arriving over time to a classifier. Designing algorithms that are capable of dealing with massive, rapidly arriving information is one of the most dynamically developing areas of machine learning. Such learners must be able to deal with a phenomenon known as concept drift, where the data stream may be subject to various changes in its characteristics over time. Furthermore, distributions of classes may evolve over time, leading to a highly difficult non-stationary class imbalance. In this work we introduce Robust Online Self-Adjusting Ensemble (ROSE), a novel online ensemble classifier capable of dealing with all of the mentioned challenges. The main contributions of ROSE are: (i) online training of base classifiers on variable size random subsets of features; (ii) online detection of concept drift and creation of a background ensemble for faster adaptation to changes; (iii) sliding window per class to create skew-insensitive classifiers regardless of the current imbalance ratio; and (iv) self-adjusting bagging to enhance the exposure of difficult instances from minority classes. The interplay among these contributions leads to an improved performance in various data stream mining benchmarks. An extensive experimental study comparing with 30 ensemble classifiers shows that ROSE is a robust and well-rounded classifier for drifting imbalanced data streams, especially under the presence of noise and class imbalance drift. Results are supported by a thorough non-parametric statistical analysis.</p>

## Using ROSE

Download the pre-compiled jar files or import the project source code into [MOA](https://github.com/Waikato/moa). See the src/main/java/experiments folder to reproduce our research.

### Experiment 1: Static imbalance ratio
Use any algorithm in `moa.classifiers` and imbalanced generator in `moa.streams.generators.imbalanced`. The parameter `-m` controls the proportion of the minority vs majority class, e.g. `-m 0.01` reflects an imbalance ratio of 100.
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.jar:MOA-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 1000)" -s "(moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 1 -m 0.01)" -l "(moa.classifiers.meta.ROSE)" -i 1000000 -f 1000 -d results.csv
```

 | Generator | Instances | Features | Classes | Static Imbalance Ratios | Concept Drift | 
 | -------- | ---: | ---: | ---: |:-: |:-: |
 | Agrawal | 1,000,000 | 9 | 2 | {5, 10, 20, 50, 100} | None | 
 | AssetNegotiation | 1,000,000 | 5 | 2 | {5, 10, 20, 50, 100} | None | 
 | RandomRBF | 1,000,000 | 10 | 2 | {5, 10, 20, 50, 100} | None | 
 | SEA | 1,000,000 | 3 | 2 | {5, 10, 20, 50, 100} | None | 
 | Sine | 1,000,000 | 4 | 2 | {5, 10, 20, 50, 100} | None | 
 | Hyperplane | 1,000,000 | 10 | 2 | {5, 10, 20, 50, 100} | None |

### Experiment 2: Drifting imbalance ratio
Use any algorithm in `moa.classifiers` and imbalanced generator in `moa.streams.generators.imbalanced`. The parameter `-m` controls the proportion of the minority vs majority class, e.g. `-m 0.01` reflects an imbalance ratio of 100. Generate drifting imbalance ratios by chaining `ConceptDriftStream` streams with different imbalance ratios. The parameter `-p` controls the position of the drift and `-w` the width of the drift (sudden vs gradual). The example shows a sequence of increasing then decreasing imbalance ratio ({5, 10, 20, 100, 20, 10, 5}).
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.jar:MOA-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 1000)" -s "(ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.2) -r 1 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 2 -f 2 -m 0.1) -r 2 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 3 -f 2 -m 0.05) -r 3 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 4 -f 2 -m 0.01) -r 4 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 5 -f 2 -m 0.01) -r 5 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 6 -f 2 -m 0.05) -r 6 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 7 -f 2 -m 0.1) -r 7 -d (moa.streams.generators.imbalanced.AgrawalGenerator -i 8 -f 2 -m 0.2) -r 8 -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1)" -l "(moa.classifiers.meta.ROSE)" -i 1000000 -f 1000 -d results.csv
```

 | Generator | Instances | Features | Classes | Drifting imbalance ratios | Concept Drift | 
 | -------- | ---: | ---: | ---: |:-: |:-: |
 | Agrawal | 1,000,000 | 9 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | 
 | AssetNegotiation | 1,000,000 | 5 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | 
 | RandomRBF | 1,000,000 | 10 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | 
 | SEA | 1,000,000 | 3 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | 
 | Sine | 1,000,000 | 4 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | 
 | Hyperplane | 1,000,000 | 10 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | 

### Experiment 3: Instance-level difficulties
Use any algorithm in `moa.classifiers` and <a href="https://people.vcu.edu/~acano/ROSE/datasets-instance-level.zip">dataset for instance-level difficulties<a/> generated using these <a href="https://github.com/dabrze/imbalanced-stream-generator">imbalanced generators</a>

```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.jar:MOA-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 100)" -s "(ArffFileStream -f Split5+Im1+Borderline20+Rare20.arff)" -l "(moa.classifiers.meta.OSAKUE_20200935_t99_scaleacckappa)" -f 100 -d EvaluatePrequential-ARFF-dariusz/prime/moa.classifiers.meta.OSAKUE_20200935_t99_scaleacckappa-Split5+Im1+Borderline20+Rare20.csv
```

 | Generator | Instances | Features | Classes | Static Imbalance Ratios | Percentage of difficult instances | 
 | -------- | ---: | ---: | ---: |:-: |:-- |
 | Borderline | 200,000 | 5 | 2 | {1, 10, 100} | {20%, 40%, 60%, 80%, 100%} | 
 | Rare | 200,000 | 5 | 2 | {1, 10, 100} | {20%, 40%, 60%, 80%, 100%} | 
 | Borderline + Rare | 200,000 | 5 | 2 | {1, 10, 100} | {20%, 40%} | 

### Experiment 4: Robustness to noise drift
Use any algorithm in `moa.classifiers` and imbalanced generator in `moa.streams.generators.imbalanced`. The parameter `-f` controls the percentage of features with noise. The parameter `-m` controls the proportion of the minority vs majority class, e.g. `-m 0.01` reflects an imbalance ratio of 100. Generate drifting noise and imbalance ratios by chaining `ConceptDriftStream` streams with different imbalance ratios, percentages of features with noise, and noise seed `-r`. The parameter `-p` controls the position of the drift and `-w` the width of the drift (sudden vs gradual). The example shows a sequence of drifting noise to other features and increasing then decreasing imbalance ratio ({5, 10, 20, 100, 20, 10, 5}).
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.jar:MOA-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 1000)" -s "(ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.2) -f (AddNoiseFilterFeatures -r 1 -a 0.99 -f 0.40)) -r 1 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 2 -f 2 -m 0.1) -f (AddNoiseFilterFeatures -r 2 -a 0.99 -f 0.40)) -r 2 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 3 -f 2 -m 0.05) -f (AddNoiseFilterFeatures -r 3 -a 0.99 -f 0.40)) -r 3 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 4 -f 2 -m 0.01) -f (AddNoiseFilterFeatures -r 4 -a 0.99 -f 0.40)) -r 4 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 5 -f 2 -m 0.01) -f (AddNoiseFilterFeatures -r 5 -a 0.99 -f 0.40)) -r 5 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 6 -f 2 -m 0.05) -f (AddNoiseFilterFeatures -r 6 -a 0.99 -f 0.40)) -r 6 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 7 -f 2 -m 0.1) -f (AddNoiseFilterFeatures -r 7 -a 0.99 -f 0.40)) -r 7 -d (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 8 -f 2 -m 0.2) -f (AddNoiseFilterFeatures -r 8 -a 0.99 -f 0.40)) -r 8 -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1)" -l "(moa.classifiers.meta.imbalanced.ROSE)" -i 1000000 -f 1000 -d results.csv
```

 | Generator | Instances | Features | Classes | Drifting imbalance ratios | Concept Drift | Percentage of features with noise
 | -------- | ---: | ---: | ---: |:-: |:-: | :-- |
 | Agrawal | 1,000,000 | 9 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | {10%, 20%, 30%, 40%}
 | AssetNegotiation | 1,000,000 | 5 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | {10%, 20%, 30%, 40%}
 | RandomRBF | 1,000,000 | 10 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | {10%, 20%, 30%, 40%}
 | SEA | 1,000,000 | 3 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | {10%, 20%, 30%, 40%}
 | Sine | 1,000,000 | 4 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | {10%, 20%, 30%, 40%}
 | Hyperplane | 1,000,000 | 10 | 2 | {5, 10, 20, 100, 20, 10, 5} | 8 drifts {sudden, gradual} | {10%, 20%, 30%, 40%}

### Experiment 5: Datasets
Use any algorithm in `moa.classifiers` and <a href="https://people.vcu.edu/~acano/ROSE/datasets.zip">dataset</a> from UCI / KEEL dataset repositories.
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.jar:MOA-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowClassificationPerformanceEvaluator)" -s "(ArffFileStream -f dataset.arff)" -l "(moa.classifiers.meta.ROSE)" -f 1000 -d results.csv
```

| Dataset | Instances | Features | Classes |
| -------- | ---: | ---: | ---: |
 | adult | 45,222 | 14 | 2 | 
 | airlines | 539,383 | 7 | 2 | 
 | bridges | 1,000,000 | 12 | 6 | 
 | census | 299,284 | 41 | 2 | 
 | coil2000 | 9,822 | 85 | 2 | 
 | connect-4 | 67,557 | 42 | 3 | 
 | covtype | 581,012 | 54 | 7 | 
 | dj30 | 138,166 | 7 | 30 | 
 | electricity | 45,312 | 8 | 2 | 
 | fars | 100,968 | 29 | 8 | 
 | gas-sensor | 13,910 | 128 | 6 | 
 | gmsc | 150,000 | 10 | 2 | 
 | intel-lab | 2,313,153 | 5 | 58 | 
 | kddcup | 4,898,431 | 41 | 23 | 
 | kr-vs-k | 28,056 | 6 | 18 | 
 | letter | 20,000 | 16 | 26 | 
 | magic | 19,020 | 10 | 2 | 
 | nomao | 34,465 | 118 | 2 | 
 | penbased | 10,992 | 16 | 10 | 
 | poker | 829,201 | 10 | 10 | 
 | powersupply | 29,928 | 2 | 24 | 
 | shuttle | 57,999 | 9 | 7 | 
 | thyroid | 7,200 | 21 | 3 | 
 | zoo | 1,000,000 | 17 | 7 | 

