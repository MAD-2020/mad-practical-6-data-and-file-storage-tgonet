package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    /* Hint:
        1. This is the create new user page for user to log in
        2. The user can enter - Username and Password
        3. The user create is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user already exists.
        4. For the purpose the practical, successful creation of new account will send the user
           back to the login page and display the "User account created successfully".
           the page remains if the user already exists and "User already exist" toastbox message will appear.
        5. There is an option to cancel. This loads the login user page.
     */

    Button Cancel,Create;
    EditText Username,Password;

    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /* Hint:
            This prepares the create and cancel account buttons and interacts with the database to determine
            if the new user created exists already or is new.
            If it exists, information is displayed to notify the user.
            If it does not exist, the user is created in the DB with default data "0" for all levels
            and the login page is loaded.

            Log.v(TAG, FILENAME + ": New user created successfully!");
            Log.v(TAG, FILENAME + ": User already exist during new user creation!");

         */

        Cancel = findViewById(R.id.Cancel_btn);
        Create = findViewById(R.id.Create_btn);
        Username = findViewById(R.id.Create_user);
        Password = findViewById(R.id.Create_pw);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,MainActivity.class));
            }
        });

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHandler handler = new MyDBHandler(Main2Activity.this,null,null,1);
                String username = Username.getText().toString();
                String password = Password.getText().toString();
                if(!username.isEmpty() && !password.isEmpty()){
                    Log.v(TAG, FILENAME + "New user creation with:" + username + ":" + password );
                    if(handler.findUser(username) != null){
                        Log.v(TAG, FILENAME + "User already exist during new user creation!");
                        Toast.makeText(Main2Activity.this,"User already exist",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ArrayList<Integer> level  = new ArrayList<Integer>();
                        ArrayList<Integer> score = new ArrayList<Integer>();
                        for(int i = 0 ; i < 10; i++){
                            level.add(i+1);
                            score.add(0);
                        }
                        handler.addUser(new UserData(username,password,level,score));
                        Log.v(TAG, FILENAME + ": New user created successfully!");
                        Toast.makeText(Main2Activity.this,"User account created successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Main2Activity.this,MainActivity.class));
                    }
                }
                else{
                    Log.v(TAG, "Please do not submit empty fills");
                    Toast.makeText(Main2Activity.this,"Please do not submit empty fills",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    protected void onStop() {
        super.onStop();
        finish();
    }
}
