package org.example.practice_layered_notepad.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Notepad {

  @Setter private Long id;
  private String title;
  private String contents;

  public Notepad(String title, String contents) {
    this.title = title;
    this.contents = contents;
  }

  public void update(String title, String contents) {
    this.title = title;
    this.contents = contents;
  }

  public void updateTitle(String title) {
    this.title = title;
  }
}
