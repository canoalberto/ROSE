# ROSE: Robust Online Self-Adjusting Ensemble for Continual Learning on Imbalanced Drifting Data Streams

Data streams are potentially unbounded collections of instances arriving over time to a classifier. Designing algorithms that are capable of dealing with massive, rapidly arriving information is one of the most dynamically developing areas of machine learning. Such learners must be able to deal with a phenomenon known as concept drift, where the data stream may be subject to various changes in its characteristics over time. Furthermore, distributions of classes and their sizes may evolve over time, leading to a highly difficult non-stationary class imbalance. In this work we introduce Robust Online Self-Adjusting Ensemble (ROSE), a novel online classifier capable of dealing with all of the mentioned challenges. It is composed of multiple self-adjusting modules: (i) online training of base classifiers based on variable size random subsets of features; (ii) online detection of concept drift and creation of a background ensemble for faster adaptation to changes; (iii) sampling per-class sliding windows that allow to create skew-insensitive classifiers regardless of the current imbalance ratio and number of classes; and (iv) enhancing the exposure of difficult instances from minority classes to base learners in order to better capture their unique properties. Interplay between these modules leads to an excellent performance in various data stream mining tasks. An extensive experimental study comprising 25 classifiers shows that ROSE is a robust and well-rounded classifier for drifting imbalanced data streams, especially under the presence of noise and class imbalance drift. Results are supported by a thorough non-parametric statistical analysis.