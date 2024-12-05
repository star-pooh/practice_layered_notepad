package org.example.practice_layered_notepad.dto;

import lombok.Getter;
import org.example.practice_layered_notepad.entity.Notepad;

@Getter
public class NotepadResponseDto {
  private Long id;
  private String title;
  private String contents;

  public NotepadResponseDto(Notepad notepad) {
    this.id = notepad.getId();
    this.title = notepad.getTitle();
    this.contents = notepad.getContents();
  }
}
