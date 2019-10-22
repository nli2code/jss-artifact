package cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg;

import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGNode.Type;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRExpression;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.IRStatement;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.expressions.*;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.ir.statements.*;
import com.google.inject.Inject;

import java.util.Set;
import java.util.stream.Collectors;

public class DFGNodeFactory {
    private final CodeUtil codeUtil;

    @Inject
    public DFGNodeFactory(CodeUtil codeUtil) {
        this.codeUtil = codeUtil;
    }

    private Set<DFGNode> type2nodes(String ty) {
        return codeUtil.getAllParentTypes(ty).stream()
            .map(t -> new DFGNode(Type.TYPE, t))
            .collect(Collectors.toSet());
    }

    public DFGNode statement2OpNode(IRStatement stmt) {
        if (stmt instanceof IRBinaryOperation) {
            return new DFGNode(Type.OP, ((IRBinaryOperation) stmt).getOpe().asString());
        }
        if (stmt instanceof IRUnaryOperation) {
            return new DFGNode(Type.UNARY, ((IRUnaryOperation) stmt).getOpe().asString());
        }
        if (stmt instanceof IREnumAccess) {
            return new DFGNode(Type.ENUM_ACCESS, ((IREnumAccess) stmt).getTy());
        }
        if (stmt instanceof IRFieldAccess) {
            return new DFGNode(Type.FIELD_ACCESS, ((IRFieldAccess) stmt).getField());
        }
        if (stmt instanceof IRStaticFieldAccess) {
            return new DFGNode(Type.FIELD_ACCESS, ((IRStaticFieldAccess) stmt).getTy());
        }
        if (stmt instanceof IRMethodInvocation) {
            return new DFGNode(Type.METHOD_INVOCATION, ((IRMethodInvocation) stmt).getQualifiedSignature());
        }
        if (stmt instanceof IRInstanceOf) {
            return new DFGNode(Type.INSTANCE_OF, ((IRInstanceOf) stmt).getTy());
        }
        if (stmt instanceof IRAssignment) {
            return new DFGNode(Type.OP, "ASSIGNMENT");
        }
        if (stmt instanceof IRArrayAccess) {
            return new DFGNode(Type.OP, "ARRAY_ACCESS");
        }
        if (stmt instanceof IRArrayCreation) {
            return new DFGNode(Type.OP, "ARRAY_CREATION");
        }
        if (stmt instanceof IRAssert) {
            return new DFGNode(Type.OP, "ASSERT");
        }
        if (stmt instanceof IRConditional) {
            return new DFGNode(Type.OP, "CONDITIONAL");
        }
        if (stmt instanceof IRPhi) {
            return new DFGNode(Type.OP, "PHI");
        }
        if (stmt instanceof IRReturn) {
            return new DFGNode(Type.OP, "RETURN");
        }
        if (stmt instanceof IRThrow) {
            return new DFGNode(Type.OP, "THROW");
        }

        throw new UnsupportedOperationException(String.format("Unknown statement type %s", stmt.getClass()));
    }

    public Set<DFGNode> statement2Type(IRStatement stmt) {
        if (stmt instanceof IRDefStatement) {
            return type2nodes(((IRDefStatement) stmt).getTy());
        }

        throw new UnsupportedOperationException("Unknown statement type");
    }

    public DFGNode expression2DataNode(IRExpression expression) {
        if (expression instanceof IRArg) {
            return new DFGNode(Type.ARG, ((IRArg) expression).getName());
        }

        return new DFGNode(Type.DATA, expression.toString());
    }

    public Set<DFGNode> expression2Type(IRExpression expr) {
        if (expr instanceof IRString) {
            return Set.of(new DFGNode(Type.TYPE, "java.lang.String"));
        }
        if (expr instanceof IRInteger) {
            return Set.of(new DFGNode(Type.TYPE, "int"));
        }
        if (expr instanceof IRLong) {
            return Set.of(new DFGNode(Type.TYPE, "long"));
        }
        if (expr instanceof IRDouble) {
            return Set.of(new DFGNode(Type.TYPE, "double"));
        }
        if (expr instanceof IRBoolean) {
            return Set.of(new DFGNode(Type.TYPE, "boolean"));
        }
        if (expr instanceof IRChar) {
            return Set.of(new DFGNode(Type.TYPE, "char"));
        }
        if (expr instanceof IRNull) {
            return type2nodes(((IRNull) expr).getTy());
        }
        if (expr instanceof IRArg) {
            return type2nodes(((IRArg) expr).getTy());
        }
        if (expr instanceof IRTemp) {
            return expression2Type(((IRTemp) expr).getReplaced());
        }
        if (expr instanceof IREnum) {
            return type2nodes(((IREnum) expr).getTy());
        }
        if (expr instanceof IRTypeObject) {
            return Set.of(new DFGNode(Type.TYPE, "java.lang.Class"));
        }
        if (expr instanceof IRThis) {
            return type2nodes(((IRThis) expr).getTy());
        }

        throw new UnsupportedOperationException("Unknown expression type");
    }
}
