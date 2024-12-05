package org.example.practice_layered_notepad.repository;

import java.util.List;
import org.example.practice_layered_notepad.dto.NotepadResponseDto;
import org.example.practice_layered_notepad.entity.Notepad;

public interface NotepadRepository {
  Notepad saveNotepad(Notepad notepad);

  List<NotepadResponseDto> findAllNotepads();

  Notepad findByNotepadId(Long id);

  void deleteNotepad(Long id);
}
