package org.example.practice_layered_notepad.controller;

import java.util.List;
import org.example.practice_layered_notepad.dto.NotepadRequestDto;
import org.example.practice_layered_notepad.dto.NotepadResponseDto;
import org.example.practice_layered_notepad.service.NotepadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Contrller + @ResponseBody
@RequestMapping("/notepads")
public class NotepadController {

  // 주입된 의존성을 변경할 수 없어 객체의 상태를 안전하게 유지할 수 있다.
  private final NotepadService notepadService;

  /**
   * 생성자 주입 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
   *
   * @param notepadService @Service로 등록된 MemoService 구현체인 Impl
   */
  public NotepadController(NotepadService notepadService) {
    this.notepadService = notepadService;
  }

  @PostMapping
  public ResponseEntity<NotepadResponseDto> createNotepad(@RequestBody NotepadRequestDto dto) {
    return new ResponseEntity<>(notepadService.saveNotepad(dto), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<NotepadResponseDto>> findAllNotepads() {
    return new ResponseEntity<>(notepadService.findAllNotepads(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<NotepadResponseDto> findByNotepadId(@PathVariable Long id) {
    return new ResponseEntity<>(notepadService.findByNotepadId(id), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<NotepadResponseDto> updateNotepad(
      @PathVariable Long id, @RequestBody NotepadRequestDto dto) {
    return new ResponseEntity<>(
        notepadService.updateNotepad(id, dto.getTitle(), dto.getContents()), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<NotepadResponseDto> updateTitle(
      @PathVariable Long id, @RequestBody NotepadRequestDto dto) {

    return new ResponseEntity<>(
        notepadService.updateTitle(id, dto.getTitle(), dto.getContents()), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNotepad(@PathVariable Long id) {
    notepadService.deleteNotepad(id);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
