package org.example.practice_layered_notepad.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.practice_layered_notepad.dto.NotepadResponseDto;
import org.example.practice_layered_notepad.entity.Notepad;
import org.springframework.stereotype.Repository;

/**
 * Annotation @Repository는 @Component와 같다, Spring Bean으로 등록한다는 뜻. Spring Bean으로 등록되면 다른 클래스에서 주입하여
 * 사용할 수 있다. 명시적으로 Repository Layer 라는것을 나타낸다. DB와 상호작용하여 데이터를 CRUD하는 작업을 수행한다.
 */
@Repository
public class NotepadRepositoryImpl implements NotepadRepository {
  private final Map<Long, Notepad> notepadList = new HashMap<>();

  @Override
  public Notepad saveNotepad(Notepad notepad) {

    Long notepadId = notepadList.isEmpty() ? 1 : Collections.max(notepadList.keySet()) + 1;
    notepad.setId(notepadId);
    notepadList.put(notepadId, notepad);

    return notepad;
  }

  @Override
  public List<NotepadResponseDto> findAllNotepads() {
    return notepadList.values().stream().map(NotepadResponseDto::new).toList();
  }

  @Override
  public Notepad findByNotepadId(Long id) {
    return notepadList.get(id);
  }

  @Override
  public void deleteNotepad(Long id) {
    notepadList.remove(id);
  }
}
