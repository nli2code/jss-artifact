package cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements;

import cn.edu.pku.hcst.kincoder.pattern.javaimpl.cfg.CFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.utils.IRUtil.UsesBuilder;
import com.github.javaparser.ast.Node;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class IRArrayCreation extends IRDefStatement {
    private final List<IRExpression> levels;
    @Nullable
    private final IRExpression init;

    public IRArrayCreation(CFG cfg, String ty, Set<Node> from, List<IRExpression> levels, @Nullable IRExpression init) {
        super(cfg, ty, from);
        this.levels = levels;
        this.init = init;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return new UsesBuilder()
            .addAll(levels)
            .add(init)
            .build();
    }
}

//class IRMethodInvocation(cfg: CFG, val ty: String, val name: String, val receiver: Option[IRExpression], val args: Seq[IRExpression], fromNode: Set[Node]) extends IRDefStatement(cfg, fromNode) {
//    override def toString: String = s"$target=${receiver.map(_ + ".").getOrElse("")}$name(${args.mkString(", ")})"
//
//    override def uses: Seq[IRExpression] = receiver.toSeq ++ args
//
//    init()
//    }