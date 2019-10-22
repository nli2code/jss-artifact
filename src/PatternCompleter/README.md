# PatternCompletion

Given a pattern (list of API) and some code using it, this tool returns a code fragment according to responding pattern.

There are some holes in the code fragment, this tool also recommends some expression to each single hole.


## Usage

1. put your pattern and source code files in `data` directory
2. modify `src/main/java/utils/Config.java`, set `getFoucsPatternDir` to your pattern directory (or you can set `batchMode` to `true`)
3. run `Main.java`
4. results will be in the same directory (file `recommendation` and`instances` )