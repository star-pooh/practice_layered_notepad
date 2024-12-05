package org.example.practice_layered_notepad.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.example.practice_layered_notepad.dto.NotepadResponseDto;
import org.example.practice_layered_notepad.entity.Notepad;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class JdbcTemplateNotepadRepository implements NotepadRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateNotepadRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public NotepadResponseDto saveNotepad(Notepad notepad) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("notepad").usingGeneratedKeyColumns("id");

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("title", notepad.getTitle());
    parameters.put("contents", notepad.getContents());

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    return new NotepadResponseDto(key.longValue(), notepad.getTitle(), notepad.getContents());
  }

  @Override
  public List<NotepadResponseDto> findAllNotepads() {
    return jdbcTemplate.query("SELECT * FROM notepad", notepadRowMapper());
  }

  @Override
  public Optional<Notepad> findByNotepadId(Long id) {
    List<Notepad> result =
        jdbcTemplate.query("SELECT * FROM notepad WHERE id = ?", notepadRowMapperV2(), id);
    return result.stream().findAny();
  }

  @Override
  public Notepad findNotepadByIdOrElseThrow(Long id) {
    List<Notepad> result =
        jdbcTemplate.query("SELECT * FROM notepad WHERE id = ?", notepadRowMapperV2(), id);

    return result.stream()
        .findAny()
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
  }

  @Override
  public int updateNotepad(Long id, String title, String contents) {
    return jdbcTemplate.update(
        "UPDATE notepad SET title = ?, contents = ? WHERE id = ?", title, contents, id);
  }

  @Override
  public int updateTitle(Long id, String title) {
    return jdbcTemplate.update("UPDATE notepad SET title = ? WHERE id = ?", title, id);
  }

  @Override
  public int deleteNotepad(Long id) {
    return jdbcTemplate.update("DELETE FROM notepad WHERE id = ?", id);
  }

  private RowMapper<NotepadResponseDto> notepadRowMapper() {
    return new RowMapper<NotepadResponseDto>() {
      @Override
      public NotepadResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new NotepadResponseDto(
            rs.getLong("id"), rs.getString("title"), rs.getString("contents"));
      }
    };
  }

  private RowMapper<Notepad> notepadRowMapperV2() {
    return new RowMapper<Notepad>() {
      @Override
      public Notepad mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Notepad(rs.getLong("id"), rs.getString("title"), rs.getString("contents"));
      }
    };
  }
}
