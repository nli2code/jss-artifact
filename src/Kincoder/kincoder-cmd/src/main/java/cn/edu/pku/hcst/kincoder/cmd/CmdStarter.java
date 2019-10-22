package cn.edu.pku.hcst.kincoder.cmd;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintConfig;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintContext;
import cn.edu.pku.hcst.kincoder.core.api.*;
import cn.edu.pku.hcst.kincoder.core.nlp.NlpServerConfig;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphConfig;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CmdStarter {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Printer printer = new Printer(
        PrintConfig.builder().build()
    );

    public static void main(String[] args) {
        KinCoderConfig kinCoderConfig = KinCoderConfig.builder().build();
        NlpServerConfig nlpServerConfig = NlpServerConfig.builder().build();
        KnowledgeGraphConfig knowledgeGraphConfig = KnowledgeGraphConfig.builder()
            .uri("bolt://10.128.171.3:10002")
            .username("neo4j")
            .password("neo4jpoi")
            .build();

        var task = scanner.nextLine();
        var service = KinCoderService.start(kinCoderConfig, nlpServerConfig, knowledgeGraphConfig);
        var response = service.startSession(task, Map.of("sheet", "org.apache.poi.ss.usermodel.Sheet"), Set.of("java.lang.Object"));
        response.getSkeletons().forEach(s -> {
            System.out.println("-----");
            System.out.println(s.getLeft());
            System.out.println(s.getRight());
        });
        var index = scanner.nextInt();
        running(service, response.getId(), service.selectPattern(response.getId(), index));
    }

    private static void running(KinCoderService service, long id, QAResponse response) {
        if (response instanceof NewQuestion) {
            var r = ((NewQuestion) response);
            waiting(service, id, r.getCtx(), r.getQuestion());
        } else if (response instanceof Finished) {
            var r = ((Finished) response);
            System.out.println(printer.visit(r.getCtx().getSkeleton().getStmts(), new PrintContext("")));
        } else if (response instanceof ErrorAnswer) {
            var r = ((ErrorAnswer) response);
            System.out.println(r.getMessage());
            waiting(service, id, r.getCtx(), r.getQuestion());
        } else if (response instanceof ErrorResponse) {
            var r = ((ErrorResponse) response);
            System.out.println("---Fatal Error Occurred---");
            System.out.println(r.getMessage());
        }
    }

    private static void waiting(KinCoderService service, long id, Context ctx, Question question) {
        System.out.println("----- code -----");
        System.out.println(printer.visit(ctx.getSkeleton().getStmts(), new PrintContext("")));
        System.out.println("----- end -----");

        System.out.println(question.description());

        var answer = scanner.nextLine();
        running(service, id, service.response(id, answer));
    }

}
