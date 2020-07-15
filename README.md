# jss-nli2code-artifact
Implementation for paper `From API to NLI: A New Interface for Library Reuse` , which is published in Journal of Systems and Software (JSS), vol.169.

Citation information:
```
@article{SHEN2020110728,
title = "From API to NLI: A new interface for library reuse",
journal = "Journal of Systems and Software",
volume = "169",
pages = "110728",
year = "2020",
issn = "0164-1212",
doi = "https://doi.org/10.1016/j.jss.2020.110728",
url = "http://www.sciencedirect.com/science/article/pii/S016412122030162X",
author = "Qi Shen and Shijun Wu and Yanzhen Zou and Zixiao Zhu and Bing Xie"
}
```

**NLI2Code** is an abstract framework promoted in the paper which is designed to help developers reuse existing libraries.
We instantiate the framework and this repository contains source code, developed tools and user study materials mentioned in the paper.

## src
**NLI2Code** contains three components: functional feature, code pattern and synthesizer. In our instantiation, their source code are as follow:

1. `src/FFExtractor`
2. `src/Kincoder`
3. `src/PatternCompleter`

Each directory contains a simple `README.md` file to help you install and execute the demo.

## tool
We developed tools for two environments, they are:

1. `tool/mps`: a Jetbrains MPS plugin
2. `http://nli2code.cn`: a web editor

If you are not familiar with MPS, you can go to the [official website](https://www.jetbrains.com/mps/) for reference.

## user study
We conducted a user study with 8 developers on 5 programming tasks on Apache-POI. To experience the user study, you could download:

1. `user_study/PoiUserStudy.zip`, this zip file can be imported using Jetbrains IntelliJ Idea.
2. `user_study/Survey.pdf` is the survey file after the user study.

Notice you need to install a plugin named `EduTools` and at least have `JDK1.8` to reproduce our user study.

Here is a [reference](https://www.jetbrains.com/help/education/install-edutools-plugin.html) about `EduTools` of IntelliJ Idea. 

