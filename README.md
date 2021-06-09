# ROSE: Robust Online Self-Adjusting Ensemble for Continual Learning on Imbalanced Drifting Data Streams

Data streams are potentially unbounded collections of instances arriving over time to a classifier. Designing algorithms that are capable of dealing with massive, rapidly arriving information is one of the most dynamically developing areas of machine learning. Such learners must be able to deal with a phenomenon known as concept drift, where the data stream may be subject to various changes in its characteristics over time. Furthermore, distributions of classes and their sizes may evolve over time, leading to a highly difficult non-stationary class imbalance. In this work we introduce Robust Online Self-Adjusting Ensemble (ROSE), a novel online classifier capable of dealing with all of the mentioned challenges. It is composed of multiple self-adjusting modules: (i) online training of base classifiers based on variable size random subsets of features; (ii) online detection of concept drift and creation of a background ensemble for faster adaptation to changes; (iii) sampling per-class sliding windows that allow to create skew-insensitive classifiers regardless of the current imbalance ratio and number of classes; and (iv) enhancing the exposure of difficult instances from minority classes to base learners in order to better capture their unique properties. Interplay between these modules leads to an excellent performance in various data stream mining tasks. An extensive experimental study comprising 25 classifiers shows that ROSE is a robust and well-rounded classifier for drifting imbalanced data streams, especially under the presence of noise and class imbalance drift. Results are supported by a thorough non-parametric statistical analysis.

## Using ROSE

Download the pre-compiled `ROSE-1.0-jar-with-dependencies.jar` or import the project source code into [MOA](https://github.com/Waikato/moa)

### Experiment 1: Fixed imbalanced ratio
Use any algorithm in `moa.classifiers` and imbalanced generator in `moa.streams.generators.imbalanced`. The parameter `-m` controls the proportion of the minority vs majority class, e.g. `-m 0.01` reflects an imbalance ratio of 100.
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0-jar-with-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 1000)" -s "(moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 1 -m 0.01)" -l "(moa.classifiers.meta.ROSE)" -i 1000000 -f 1000 -d results.csv
```

### Experiment 2: Drifting imbalanced ratio
Use any algorithm in `moa.classifiers` and imbalanced generator in `moa.streams.generators.imbalanced`. The parameter `-m` controls the proportion of the minority vs majority class, e.g. `-m 0.01` reflects an imbalance ratio of 100. Generate drifting imbalance ratios by chaining `ConceptDriftStream` streams with different imbalance ratios. The parameter `-p` controls the position of the drift and `-w` the width of the drift (sudden vs gradual).
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0-jar-with-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 1000)" -s "(ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.2) -r 1 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 2 -f 2 -m 0.1) -r 2 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 3 -f 2 -m 0.05) -r 3 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 4 -f 2 -m 0.01) -r 4 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 5 -f 2 -m 0.01) -r 5 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 6 -f 2 -m 0.05) -r 6 -d (ConceptDriftStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 7 -f 2 -m 0.1) -r 7 -d (moa.streams.generators.imbalanced.AgrawalGenerator -i 8 -f 2 -m 0.2) -r 8 -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1)" -l "(moa.classifiers.meta.ROSE)" -i 1000000 -f 1000 -d results.csv
```

### Experiment 3: Instance-level difficulties
Use any algorithm in `moa.classifiers` and [dataset for instance-level difficulties](http://people.vcu.edu/~acano/ROSE/datasets-instance-level.zip) generated using these [imbalanced generators](https://github.com/dabrze/imbalanced-stream-generator)

```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0-jar-with-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 100)" -s "(ArffFileStream -f Split5+Im1+Borderline20+Rare20.arff)" -l "(moa.classifiers.meta.OSAKUE_20200935_t99_scaleacckappa)" -f 100 -d EvaluatePrequential-ARFF-dariusz/prime/moa.classifiers.meta.OSAKUE_20200935_t99_scaleacckappa-Split5+Im1+Borderline20+Rare20.csv
```

### Experiment 4: Robustness to noise drift
Use any algorithm in `moa.classifiers` and imbalanced generator in `moa.streams.generators.imbalanced`. The parameter `-f` controls the percentage of features with noise. The parameter `-m` controls the proportion of the minority vs majority class, e.g. `-m 0.01` reflects an imbalance ratio of 100. Generate drifting noise and imbalance ratios by chaining `ConceptDriftStream` streams with different imbalance ratios, percentages of features with noise, and noise seed `-r`. The parameter `-p` controls the position of the drift and `-w` the width of the drift (sudden vs gradual).
```
java -javaagent:sizeofag-1.0.4.jar -cp ROSE-1.0-jar-with-dependencies.jar moa.DoTask EvaluatePrequential -e "(WindowImbalancedClassificationPerformanceEvaluator -w 1000)" -s "(ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 1 -f 2 -m 0.2) -f (AddNoiseFilterFeatures -r 1 -a 0.99 -f 0.40)) -r 1 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 2 -f 2 -m 0.1) -f (AddNoiseFilterFeatures -r 2 -a 0.99 -f 0.40)) -r 2 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 3 -f 2 -m 0.05) -f (AddNoiseFilterFeatures -r 3 -a 0.99 -f 0.40)) -r 3 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 4 -f 2 -m 0.01) -f (AddNoiseFilterFeatures -r 4 -a 0.99 -f 0.40)) -r 4 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 5 -f 2 -m 0.01) -f (AddNoiseFilterFeatures -r 5 -a 0.99 -f 0.40)) -r 5 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 6 -f 2 -m 0.05) -f (AddNoiseFilterFeatures -r 6 -a 0.99 -f 0.40)) -r 6 -d (ConceptDriftStream -s (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 7 -f 2 -m 0.1) -f (AddNoiseFilterFeatures -r 7 -a 0.99 -f 0.40)) -r 7 -d (FilteredStream -s (moa.streams.generators.imbalanced.AgrawalGenerator -i 8 -f 2 -m 0.2) -f (AddNoiseFilterFeatures -r 8 -a 0.99 -f 0.40)) -r 8 -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1) -p 125000 -w 1)" -l "(moa.classifiers.meta.imbalanced.ROSE)" -i 1000000 -f 1000 -d results.csv
```

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
| kr-vs-k | 28056 | 6 | 18 |
| letter | 20000 | 16 | 26 |
| magic | 19020 | 10 | 2 |
| penbased | 10992 | 16 | 10 |
| poker | 829201 | 10 | 10 |
| powersupply | 29928 | 2 | 24 |
| shuttle | 57999 | 9 | 7 |
| thyroid | 7200 | 21 | 3 |
| zoo | 1000000 | 17 | 7 |



