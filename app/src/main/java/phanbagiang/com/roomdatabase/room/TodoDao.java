package phanbagiang.com.roomdatabase.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insertTodo(Todo todo);

    @Query("SELECT * FROM todo_table")
    List<Todo> getAllTodos();

    @Query("SELECT * FROM todo_table WHERE todo_uid LIKE :id")
    Todo findById(int id);

    @Delete
    void deleteTodo(Todo todo);

    @Update
    void updateTodo(Todo todo);

    @Insert
    void insertMultipleTodo(List<Todo> listTodo);

    @Query("SELECT * FROM todo_table WHERE todo_completed LIKE 1")
    List<Todo> getTodoComplete();

    @Query("SELECT * FROM todo_table")
    LiveData<List<Todo>> getAllTodosLiveData();
}
