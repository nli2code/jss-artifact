package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintConfig;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintContext;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.RecommendChoice;
import com.google.common.collect.Streams;
import lombok.Value;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Value
public class ChoiceQuestion implements Question {
    private final String content;
    private final List<Choice> choices;

    @Override
    public String description() {
        var printer = new Printer(PrintConfig.builder().build());
        var choiceStringStream = choices.stream()
            .map(c -> c instanceof RecommendChoice ? printer.visit(((RecommendChoice) c).getFilled(), new PrintContext("")) : c.toString());
        return Streams.mapWithIndex(
            choiceStringStream,
            (c, i) -> String.format("#%s. %s", i + 1, c)
        ).collect(Collectors.joining("\n"));
    }

    @Override
    public QuestionResult processInput(Context ctx, HoleExpr hole, String input) {
        var pattern = Pattern.compile("#(\\d+)");
        var matcher = pattern.matcher(input);
        if (matcher.find()) {
            var choice = choices.get(Integer.valueOf(matcher.group(1)) - 1);
            var result = choice.action(ctx, hole);
            if (result instanceof NewQuestion) {
                return new cn.edu.pku.hcst.kincoder.core.api.NewQuestion(ctx, hole, ((NewQuestion) result).getQuestion());
            }

            if (result instanceof Filled) {
                return (QuestionResult) result;
            }

            if (result instanceof UnImplemented) {
                return new ErrorInput("Not Implemented! Please try other choices.");
            }

            throw new UnsupportedOperationException();
        } else {
            return new ErrorInput("Error Format!");
        }
    }
}
