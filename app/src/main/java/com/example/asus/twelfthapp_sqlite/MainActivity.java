package com.example.asus.twelfthapp_sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";

    private Button btnAdd, btnRead, btnClear;
    private EditText etName, etLastName, etEmail, etLogin, etPass;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initElements();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // создаем меню
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //подключим меню с выводом логов:
        int id = item.getItemId();
        switch (id){
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                Log.d("LOG_TAG,", "--- Pressed item menu setting ---");
                break;
            case R.id.menu_show_users:
                Intent intent1 = new Intent(this, ShowUsersActivity.class);
                startActivity(intent1);
                Log.d("LOG_TAG,", "------ Pressed item menu show users ------");
                break;
            case R.id.menu_about:
                Intent intent2 = new Intent(this,AboutActivity.class);
                startActivity(intent2);
                Log.d("LOG_TAG,", "--------- Pressed item menu about ---------");
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String last = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String login = etLogin.getText().toString();
        String pass = etPass.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {

            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("name", name);
                cv.put("last", last);
                cv.put("email", email);
                cv.put("login", login);
                cv.put("pass", pass);

                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("mytable", null, null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int lastColIndex = c.getColumnIndex("last");
                    int emailColIndex = c.getColumnIndex("email");
                    int loginColIndex = c.getColumnIndex("login");
                    int passColIndex = c.getColumnIndex("pass");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", last name = " + c.getString(lastColIndex) +
                                        ", email = " + c.getString(emailColIndex) +
                                        ", login = " + c.getString(loginColIndex) +
                                        ", pass = " + c.getString(passColIndex)
                                         );
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }

    private void initElements () {
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
          //додполнительные элементи
          etLastName = (EditText) findViewById(R.id.etLastName);
          etEmail = (EditText) findViewById(R.id.etEmail);
          etLogin = (EditText) findViewById(R.id.etLogin);
          etPass = (EditText) findViewById(R.id.etPass);


        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }

}























