package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintConfig;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintContext;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.RecommendChoice;
import com.google.common.collect.Streams;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RecommendQuestion implements Question {
    private final Question wrappedQuestion;
    private final List<RecommendChoice> recommendations;

    @Override
    public String description() {
        var printer = new Printer(PrintConfig.builder().build());
        var choiceStringStream = recommendations.stream()
            .map(c -> printer.visit(c.getFilled(), new PrintContext("")) );
        return String.format("%s\n%s", wrappedQuestion.description(), Streams.mapWithIndex(
            choiceStringStream,
            (c, i) -> String.format("#%s. %s", i + 1, c)
        ).collect(Collectors.joining("\n")));
    }

    @Override
    public QuestionResult processInput(Context ctx, HoleExpr hole, String input) {
        var pattern = Pattern.compile("#(\\d+)");
        var matcher = pattern.matcher(input);
        if (matcher.find()) {
            var choice = recommendations.get(Integer.valueOf(matcher.group(1)) - 1);
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
            wrappedQuestion.processInput(ctx, hole, input);
            return new ErrorInput("Error Format!");
        }
    }
}
