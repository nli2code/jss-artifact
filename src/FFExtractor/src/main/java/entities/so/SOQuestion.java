package entities.so;

import java.util.List;

public class SOQuestion extends SOPost {

  String title;
  String[] tags;
  String acceptedAnswerId;
  List<SOAnswer> answers;

  String lastEditDate;
  String lastEditorUserId;
}
