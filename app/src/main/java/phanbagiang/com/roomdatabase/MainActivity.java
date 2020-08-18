package phanbagiang.com.roomdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import phanbagiang.com.roomdatabase.room.Todo;
import phanbagiang.com.roomdatabase.room.TodoRoomDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.diag);
                dialog.show();
            }
        });
    }

    public void insertASingleTodo(View view) {
        Todo todo=new Todo("try hard Python", false);
        InsertAsynTask insertAsynTask=new InsertAsynTask();
        insertAsynTask.execute(todo);
    }

    public void getAllTodos(View view) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo> todoList=TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .getAllTodos();
                Log.e(TAG, "run: "+todoList.toString() );
            }
        });
        thread.start();
    }

    public void deleteTodo(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Todo todo=TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .findById(6);
                if(todo!=null){
                    TodoRoomDatabase.getInstance(getApplicationContext())
                            .todoDao()
                            .deleteTodo(todo);
                    Log.e(TAG, "Todo "+todo.getUid()+" has been deleted!" );
                    //Toast.makeText(MainActivity.this, "Todo "+todo.getUid()+" has been deleted!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.e(TAG, "Todo not found!" );
                    //Toast.makeText(MainActivity.this, "Todo "+todo.getUid()+" not found!", Toast.LENGTH_SHORT).show();
                    //return;
                }
            }
        }).start();
    }

    public void updateTodo(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Todo todo=TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .findById(7);
                if(todo!=null){
                    todo.setCompleted(true);
                    TodoRoomDatabase.getInstance(getApplicationContext())
                            .todoDao()
                            .updateTodo(todo);
                    Log.e(TAG, "Todo has been updated !" );
                }
                else{
                    Log.e(TAG, "Todo not found !" );
                }
            }
        }).start();
    }

    public void insertMultipleTodo(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo>todoList=new ArrayList<>();
                todoList.add(new Todo("Create video about tutorial Java",false));
                todoList.add(new Todo("Tutorial update rom",true));
                todoList.add(new Todo("Create video about tutorial Android",false));
                todoList.add(new Todo("Create video about tutorial VPN",true));

                TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .insertMultipleTodo(todoList);
                Log.d(TAG, "Insert listTodo OK !");
            }
        }).start();
    }

    public void getTodoCompleted(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo> todoList=TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .getTodoComplete();
                Log.e(TAG, "run: "+todoList.toString());
            }
        }).start();
    }

    class InsertAsynTask extends AsyncTask<Todo,Void,Void>{
        @Override
        protected Void doInBackground(Todo... todos) {
            TodoRoomDatabase.getInstance(getApplicationContext())
                    .todoDao().insertTodo(todos[0]);
            return null;
        }
    }
}