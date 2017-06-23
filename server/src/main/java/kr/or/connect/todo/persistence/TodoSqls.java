package kr.or.connect.todo.persistence;

public class TodoSqls {
	
	static final String COUNT_Active =
			"SELECT COUNT(*) FROM todo WHERE completed = 0";
	
	static final String SELECT_ALL =
			"SELECT id, todo, completed, date FROM todo";

	static final String SELECT_Active = 
			"SELECT id, todo, completed, date FROM todo WHERE completed = 0";
	
	static final String SELECT_COMPLETED = 
			"SELECT id, todo, completed, date FROM todo WHERE completed = 1";
	
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	
	static final String UPDATE_COMPLETED =
			"UPDATE todo SET completed = :completed WHERE id = :id";
	
	static final String SELECT_BY_ID = 
			"SELECT id, todo, completed, date FROM todo where id = :id";
	
	static final String DELETE_COMPLETED =
			"DELETE FROM todo WHERE completed = 1";
}
