package cn.edu.pku.hcst.kincoder.core.impl.session;

import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.api.QAResponse;
import cn.edu.pku.hcst.kincoder.core.api.UndoResponse;

public interface SessionState {
    Pair<SessionState, QAResponse> selectSkeleton(int id);

    Pair<SessionState, QAResponse> answer(String answer);

    Pair<SessionState, UndoResponse> undo();


}
