package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.todo.domain.Todo;

@Repository
public class TodoDao {
	
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private TodoSqls MyConstants;
	
	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");
	}
	
	public int countActive() { //할 일의 갯수
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(MyConstants.COUNT_Active, params, Integer.class);
	}
	
	private RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);
	
	public List<Todo> selectAll() { //모든 Todo 조회
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(MyConstants.SELECT_ALL, params, rowMapper);
	}
	
	public List<Todo> selectActive() { //해야 할 일 조회
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(MyConstants.SELECT_Active, params, rowMapper);
	}
	
	public List<Todo> selectCompletd() { //완료된 일 조회
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(MyConstants.SELECT_COMPLETED, params, rowMapper);
	}
	
	public Integer insert(Todo todo){ //todo 작성
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public int deleteById(Integer id){ //todo 삭제
		Map<String, ?>params = Collections.singletonMap("id", id);
		return jdbc.update(MyConstants.DELETE_BY_ID, params);
	}
	
	public int update(Todo todo) { //todo 갱신
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return jdbc.update(MyConstants.UPDATE_COMPLETED, params);
	}
	
	public Todo selectById(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(MyConstants.SELECT_BY_ID, params, rowMapper);
	}
	
	public int completedTodoDelete() {
		Map<String, ?> params = Collections.singletonMap("completed", 1);
		return jdbc.update(MyConstants.DELETE_COMPLETED, params);
	}
	}

