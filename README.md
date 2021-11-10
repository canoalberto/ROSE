# ROSE: Robust Online Self-Adjusting Ensemble for Continual Learning on Imbalanced Drifting Data Streams

Data streams are potentially unbounded sequences of instances arriving over time to a classifier. Designing algorithms that are capable of dealing with massive, rapidly arriving information is one of the most dynamically developing areas of machine learning. Such learners must be able to deal with a phenomenon known as concept drift, where the data stream may be subject to various changes in its characteristics over time. Furthermore, distributions of classes may evolve over time, leading to a highly difficult non-stationary class imbalance. In this work we introduce Robust Online Self-Adjusting Ensemble (ROSE), a novel online ensemble classifier capable of dealing with all of the mentioned challenges. The main contributions of ROSE are: (i) online training of base classifiers on variable size random subsets of features; (ii) online detection of concept drift and creation of a background ensemble for faster adaptation to changes; (iii) sliding window per-class to create skew-insensitive classifiers regardless of the current imbalance ratio; and (iv) self-adaptive bagging to enhance the exposure of difficult instances from minority classes. Interplay between these contributions leads to an improved performance in various data stream mining benchmarks. An extensive experimental study comprising 31 ensemble classifiers shows that ROSE is a robust and well-rounded classifier for drifting imbalanced data streams, especially under the presence of noise and class imbalance drift. Results are supported by a thorough non-parametric statistical analysis.

## Using ROSE

Download the pre-compiled `ROSE-1.0-jar-with-dependencies.jar` or import the project source code into [MOA](https://github.com/Waikato/moa)

### Experiment 1: Static imbalance ratio
Use any algorithm in `moa.classifiers` and imbalanced generator in `moa.streams.generators.imbalanced`. The parameter `-m` controls the proportion of the minority vs majority class, e.g. `-m 0.01` reflects an imbalance ratio of 100.
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0-jar-with-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 1000)" -s "(moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 1 -m 0.01)" -l "(moa.classifiers.meta.ROSE)" -i 1000000 -f 1000 -d results.csv
```

 | Generator | Instances | Features | Classes | Static Imbalance Ratios | Concept Drift | 
 | -------- | ---: | ---: | ---: |---: |---: |
 | Agrawal | 1,000,000 | 9 | 2 | {5, 10, 20, 50, 100} | None | 
 | AssetNegotiation | 1,000,000 | 5 | 2 | {5, 10, 20, 50, 100} | None | 
 | RandomRBF | 1,000,000 | 10 | 2 | {5, 10, 20, 50, 100} | None | 
 | SEA | 1,000,000 | 3 | 2 | {5, 10, 20, 50, 100} | None | 
 | Sine | 1,000,000 | 4 | 2 | {5, 10, 20, 50, 100} | None | 
 | Hyperplane | 1,000,000 | 10 | 2 | {5, 10, 20, 50, 100} | None | 
 | RandomRBFDrift | 1,000,000 | 10 | 2 | {5, 10, 20, 50, 100} | None | 

### Experiment 2: Drifting imbalance ratio
Use any algorithm in `moa.classifiers` and imbalanced generator in `moa.streams.generators.imbalanced`. The parameter `-m` controls the proportion of the minority vs majority class, e.g. `-m 0.01` reflects an imbalance ratio of 100. Generate drifting imbalance ratios by chaining `ConceptDriftStream` streams with different imbalance ratios. The parameter `-p` controls the position of the drift and `-w` the width of the drift (sudden vs gradual). The example shows a sequence of increasing then decreasing imbalance ratio (5 - 10 - 20 - 100 - 20 - 10 - 5).
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0-jar-with-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 1000)" -s "(ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.2) -r 1 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 2 -f 2 -m 0.1) -r 2 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 3 -f 2 -m 0.05) -r 3 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 4 -f 2 -m 0.01) -r 4 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 5 -f 2 -m 0.01) -r 5 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 6 -f 2 -m 0.05) -r 6 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 7 -f 2 -m 0.1) -r 7 -d (moa.streams.generators.imbalanced.AgrawalGenerator -i 8 -f 2 -m 0.2) -r 8 -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1)" -l "(moa.classifiers.meta.ROSE)" -i 1000000 -f 1000 -d results.csv
```

 | Generator | Instances | Features | Classes | Drifting imbalance ratios | Concept Drift | 
 | -------- | ---: | ---: | ---: |---: |---: |
 | Agrawal | 1,000,000 | 9 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | 
 | AssetNegotiation | 1,000,000 | 5 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | 
 | RandomRBF | 1,000,000 | 10 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | 
 | RandomTree | 1,000,000 | 10 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | 
 | SEA | 1,000,000 | 3 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | 
 | STAGGER | 1,000,000 | 3 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | 
 | Sine | 1,000,000 | 4 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | 
 | Hyperplane | 1,000,000 | 10 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | 
 | RandomRBFDrift | 1,000,000 | 10 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | 

### Experiment 3: Instance-level difficulties
Use any algorithm in `moa.classifiers` and [dataset for instance-level difficulties](http://people.vcu.edu/~acano/ROSE/datasets-instance-level.zip) generated using these [imbalanced generators](https://github.com/dabrze/imbalanced-stream-generator)

```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0-jar-with-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 100)" -s "(ArffFileStream -f Split5+Im1+Borderline20+Rare20.arff)" -l "(moa.classifiers.meta.OSAKUE_20200935_t99_scaleacckappa)" -f 100 -d EvaluatePrequential-ARFF-dariusz/prime/moa.classifiers.meta.OSAKUE_20200935_t99_scaleacckappa-Split5+Im1+Borderline20+Rare20.csv
```

 | Generator | Instances | Features | Classes | Static Imbalance Ratios | Percentage of difficult instances | 
 | -------- | ---: | ---: | ---: |---: |---: |
 | Borderline | 200,000 | 5 | 2 | {1, 10, 100} | {0%, 20%, 40%, 60%, 80%, 100%} | 
 | Rare | 200,000 | 5 | 2 | {1, 10, 100} | {0%, 20%, 40%, 60%, 80%, 100%} | 
 | Borderline + rare | 200,000 | 5 | 2 | {1, 10, 100} | {0%, 20%, 40%} | 

### Experiment 4: Robustness to noise drift
Use any algorithm in `moa.classifiers` and imbalanced generator in `moa.streams.generators.imbalanced`. The parameter `-f` controls the percentage of features with noise. The parameter `-m` controls the proportion of the minority vs majority class, e.g. `-m 0.01` reflects an imbalance ratio of 100. Generate drifting noise and imbalance ratios by chaining `ConceptDriftStream` streams with different imbalance ratios, percentages of features with noise, and noise seed `-r`. The parameter `-p` controls the position of the drift and `-w` the width of the drift (sudden vs gradual). The example shows a sequence of drifting noise to other features and increasing then decreasing imbalance ratio (5 - 10 - 20 - 100 - 20 - 10 - 5).
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0-jar-with-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 1000)" -s "(ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.2) -f (AddNoiseFilterFeatures -r 1 -a 0.99 -f 0.40)) -r 1 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 2 -f 2 -m 0.1) -f (AddNoiseFilterFeatures -r 2 -a 0.99 -f 0.40)) -r 2 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 3 -f 2 -m 0.05) -f (AddNoiseFilterFeatures -r 3 -a 0.99 -f 0.40)) -r 3 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 4 -f 2 -m 0.01) -f (AddNoiseFilterFeatures -r 4 -a 0.99 -f 0.40)) -r 4 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 5 -f 2 -m 0.01) -f (AddNoiseFilterFeatures -r 5 -a 0.99 -f 0.40)) -r 5 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 6 -f 2 -m 0.05) -f (AddNoiseFilterFeatures -r 6 -a 0.99 -f 0.40)) -r 6 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 7 -f 2 -m 0.1) -f (AddNoiseFilterFeatures -r 7 -a 0.99 -f 0.40)) -r 7 -d (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 8 -f 2 -m 0.2) -f (AddNoiseFilterFeatures -r 8 -a 0.99 -f 0.40)) -r 8 -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1)" -l "(moa.classifiers.meta.imbalanced.ROSE)" -i 1000000 -f 1000 -d results.csv
```

 | Generator | Instances | Features | Classes | Drifting imbalance ratios | Concept Drift | Percentage of features with noise
 | -------- | ---: | ---: | ---: |---: |---: | ---: |
 | Agrawal | 1,000,000 | 9 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | {0%, 10%, 20%, 30%, 40%}
 | AssetNegotiation | 1,000,000 | 5 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | {0%, 10%, 20%, 30%, 40%}
 | RandomRBF | 1,000,000 | 10 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | {0%, 10%, 20%, 30%, 40%}
 | RandomTree | 1,000,000 | 10 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | {0%, 10%, 20%, 30%, 40%}
 | SEA | 1,000,000 | 3 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | {0%, 10%, 20%, 30%, 40%}
 | STAGGER | 1,000,000 | 3 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | {0%, 10%, 20%, 30%, 40%}
 | Sine | 1,000,000 | 4 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | {0%, 10%, 20%, 30%, 40%}
 | Text | 1,000,000 | 1,000 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | {0%, 10%, 20%, 30%, 40%}
 | Hyperplane | 1,000,000 | 10 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | {0%, 10%, 20%, 30%, 40%}
 | RandomRBFDrift | 1,000,000 | 10 | 2 | 5 - 10 - 20 - 100 - 20 - 10 - 5 | 8 drifts: sudden / gradual | {0%, 10%, 20%, 30%, 40%}

### Experiment 5: Datasets
Use any algorithm in `moa.classifiers` and [dataset](http://people.vcu.edu/~acano/ROSE/datasets.zip) from UCI / KEEL dataset repositories.
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0-jar-with-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowClassificationPerformanceEvaluator)" -s "(ArffFileStream -f dataset.arff)" -l "(moa.classifiers.meta.ROSE)" -f 1000 -d results.csv
```

| Dataset | Instances | Features | Classes |
| -------- | ---: | ---: | ---: |
| adult | 45222 | 14 | 2 |
| airlines | 539383 | 7 | 2 |
| bridges | 1000000 | 12 | 6 |
| census | 299284 | 41 | 2 |
| coil2000 | 9822 | 85 | 2 |
| connect-4 | 67557 | 42 | 3 |
| covtype | 581012 | 54 | 7 |
| dj30 | 138166 | 7 | 30 |
| electricity | 45312 | 8 | 2 |
| fars | 100968 | 29 | 8 |
| gas-sensor | 13910 | 128 | 6 |
| gmsc | 150000 | 10 | 2 |
| intel-lab | 2313153 | 5 | 58 |
| kddcup | 4898431 | 41 | 23 |
| kr-vs-k | 28056 | 6 | 18 |
| letter | 20000 | 16 | 26 |
| magic | 19020 | 10 | 2 |
| nomao | 34465 | 118 | 2 |
| penbased | 10992 | 16 | 10 |
| poker | 829201 | 10 | 10 |
| powersupply | 29928 | 2 | 24 |
| shuttle | 57999 | 9 | 7 |
| thyroid | 7200 | 21 | 3 |
| zoo | 1000000 | 17 | 7 |
