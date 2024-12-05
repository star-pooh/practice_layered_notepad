package org.example.practice_layered_notepad.service;

import java.util.List;
import org.example.practice_layered_notepad.dto.NotepadRequestDto;
import org.example.practice_layered_notepad.dto.NotepadResponseDto;

public interface NotepadService {
  NotepadResponseDto saveNotepad(NotepadRequestDto dto);

  List<NotepadResponseDto> findAllNotepads();

  NotepadResponseDto findByNotepadId(Long id);

  NotepadResponseDto updateNotepad(Long id, String title, String contents);

  NotepadResponseDto updateTitle(Long id, String title, String contents);

  void deleteNotepad(Long id);
}
