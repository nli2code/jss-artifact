package entities.so;

import java.util.List;

public abstract class SOPost {

  String pid;
  int score;
  int viewCount;
  String body;

  String creationDate;
  String ownerUserId;
  String lastActivityDate;

  List<SOComment> comments;

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
