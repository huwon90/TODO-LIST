package kr.or.connect.todo.persistence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.h2.util.ToDateParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.todo.domain.Todo;

@RunWith(SpringRunner.class) 
@SpringBootTest
@Transactional
public class TodoDaoTest {

	@Autowired
	private TodoDao dao;
	
	@Test
	public void shouldInsertAndSelect() {
		Calendar cal = Calendar.getInstance();
		Timestamp now = new Timestamp(cal.getTime().getTime());
		Todo todo = new Todo("테스트", 0, now);
		Integer id = dao.insert(todo);
		
		//then
		Todo selected = dao.selectById(id);
		System.out.println(selected);
		assertThat(selected.getTodo(), is("테스트"));
	}
	
	@Test
	public void shouldCount() {
		int count = dao.countActive();
		System.out.println(count);
	}
	
	@Test
	public void shouldDelete() {
		//given
		Calendar cal = Calendar.getInstance();
		Timestamp now = new Timestamp(cal.getTime().getTime());
		Todo todo = new Todo("테스트", 0, now);
		Integer id = dao.insert(todo);
		
		// when
		int affected = dao.deleteById(id);

		// Then
		assertThat(affected, is(1));
	}
	
	@Test
	public void shouldUpdate() {
		//given
		Calendar cal = Calendar.getInstance();
		Timestamp now = new Timestamp(cal.getTime().getTime());
		Todo todo = new Todo("테스트", 0, now);
		Integer id = dao.insert(todo);
		
		// When
		todo.setId(id);
		todo.setCompleted(1);
		int affected  = dao.update(todo);
		
		//Then
		assertThat(affected, is(1));
		Todo updated = dao.selectById(id);
		assertThat(updated.getCompleted(), is(1));
	}
	
}
