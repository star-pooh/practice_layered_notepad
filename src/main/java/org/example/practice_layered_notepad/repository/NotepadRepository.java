package org.example.practice_layered_notepad.repository;

import java.util.List;
import java.util.Optional;
import org.example.practice_layered_notepad.dto.NotepadResponseDto;
import org.example.practice_layered_notepad.entity.Notepad;

public interface NotepadRepository {
  NotepadResponseDto saveNotepad(Notepad notepad);

  List<NotepadResponseDto> findAllNotepads();

  Optional<Notepad> findByNotepadId(Long id);

  Notepad findNotepadByIdOrElseThrow(Long id);

  int updateNotepad(Long id, String title, String contents);

  int updateTitle(Long id, String title);

  int deleteNotepad(Long id);
}
