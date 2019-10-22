package cn.edu.pku.hcst.kincoder.core.impl.session;

import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.api.ErrorResponse;
import cn.edu.pku.hcst.kincoder.core.api.QAResponse;
import cn.edu.pku.hcst.kincoder.core.api.UndoResponse;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class FinishedState implements SessionState {
    private final Context ctx;

    @Override
    public Pair<SessionState, QAResponse> selectSkeleton(int id) {
        return Pair.of(this, new ErrorResponse("Finished"));
    }

    @Override
    public Pair<SessionState, QAResponse> answer(String answer) {
        return Pair.of(this, new ErrorResponse("Finished"));
    }

    @Override
    public Pair<SessionState, UndoResponse> undo() {
        return Pair.of(this, new ErrorResponse("Finished"));
    }
}
