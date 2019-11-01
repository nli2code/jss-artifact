# jss-nli2code-artifact
Implementation for JSS submission `Generating Natural Language Interface for Library Reuse`

**NLI2Code** is an abstract framework promoted in the paper which is designed to help developers reuse existing libraries.
We instantiate the framework and this repository contains source code, developed tools and user study materials mentioned in the paper.

## src
**NLI2Code** contains three components: functional feature, code pattern and synthesizer. In our instantiation, their source code are as follow:

1. `src/FFExtractor`
2. `src/Kincoder`
3. `src/PatternCompleter`

Each directory contains a simple `README.md` file to help you install and execute the demo.

## tool
We developed tools in different IDEs, they are:

1. `tool/mps`: a Jetbrains MPS plugin
2. `http://62.234.94.93/`: a web editor (domain name is on the way)

If you are not familiar with MPS, you can go to the [official website](https://www.jetbrains.com/mps/) for reference.

## user study
We conducted a user study with 8 developers on 5 programming tasks on Apache-POI. To experience the user study, you could download:

1. `user_study/PoiUserStudy.zip`, this zip file can be imported using Jetbrains IntelliJ Idea.
2. `user_study/Survey.pdf` is the survey file after the user study.

Notice you need to install a plugin named `EduTools` and at least have `JDK1.8` to reproduce our user study.

Here is a [reference](https://www.jetbrains.com/help/education/install-edutools-plugin.html) about `EduTools` of IntelliJ Idea. 

