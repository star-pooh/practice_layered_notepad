package org.example.practice_layered_notepad.service;

import java.util.List;
import org.example.practice_layered_notepad.dto.NotepadRequestDto;
import org.example.practice_layered_notepad.dto.NotepadResponseDto;
import org.example.practice_layered_notepad.entity.Notepad;
import org.example.practice_layered_notepad.repository.NotepadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Annotation @Service는 @Component와 같다, Spring Bean으로 등록한다는 뜻. Spring Bean으로 등록되면 다른 클래스에서 주입하여 사용할
 * 수 있다. 명시적으로 Service Layer 라는것을 나타낸다. 비지니스 로직을 수행한다.
 */
@Service
public class NotepadServiceImpl implements NotepadService {

  private final NotepadRepository notepadRepository;

  public NotepadServiceImpl(NotepadRepository notepadRepository) {
    this.notepadRepository = notepadRepository;
  }

  @Override
  public NotepadResponseDto saveNotepad(NotepadRequestDto dto) {
    Notepad notepad = new Notepad(dto.getTitle(), dto.getContents());

    Notepad savedNotepad = notepadRepository.saveNotepad(notepad);

    return new NotepadResponseDto(savedNotepad);
  }

  @Override
  public List<NotepadResponseDto> findAllNotepads() {
    return notepadRepository.findAllNotepads();
  }

  @Override
  public NotepadResponseDto findByNotepadId(Long id) {
    Notepad notepad = notepadRepository.findByNotepadId(id);

    if (notepad == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
    }

    return new NotepadResponseDto(notepad);
  }

  @Override
  public NotepadResponseDto updateNotepad(Long id, String title, String contents) {
    Notepad notepad = notepadRepository.findByNotepadId(id);

    if (notepad == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
    }

    if (title == null || contents == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "The title and content are required values.");
    }

    notepad.update(title, contents);

    return new NotepadResponseDto(notepad);
  }

  @Override
  public NotepadResponseDto updateTitle(Long id, String title, String contents) {
    Notepad notepad = notepadRepository.findByNotepadId(id);

    if (notepad == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
    }

    if (title == null || contents != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title are required values.");
    }

    notepad.updateTitle(title);

    return new NotepadResponseDto(notepad);
  }

  @Override
  public void deleteNotepad(Long id) {
    Notepad notepad = notepadRepository.findByNotepadId(id);

    if (notepad == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
    }

    notepadRepository.deleteNotepad(id);
  }
}
