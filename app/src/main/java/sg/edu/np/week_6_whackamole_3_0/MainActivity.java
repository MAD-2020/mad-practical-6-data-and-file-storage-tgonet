package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    /*
        1. This is the main page for user to log in
        2. The user can enter - Username and Password
        3. The user login is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user does not exist. This loads the level selection page.
        4. There is an option to create a new user account. This loads the create user page.
     */
    Button Login;
    EditText Username,Password;
    TextView Createuser;
    UserData user;
    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = findViewById(R.id.Login_btn);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        Createuser = findViewById(R.id.Createuser);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Username.getText().toString().isEmpty() && !Password.getText().toString().isEmpty()){
                    Log.v(TAG, FILENAME + ": Logging in with: " + Username.getText().toString() + ": " + Password.getText().toString());
                    if(isValidUser(Username.getText().toString(), Password.getText().toString())){
                        Log.v(TAG, FILENAME + ": Valid User! Logging in");
                        Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                        intent.putExtra("username",Username.getText().toString());
                        Log.d(TAG, Username.getText().toString());
                        startActivity(intent);
                    }
                }
                else{
                    Log.v(TAG, "Please do not submit empty fills");
                    Toast.makeText(MainActivity.this,"Please do not submit empty fills",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Createuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, FILENAME + ": Create new user!");
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });


        /* Hint:
            This method creates the necessary login inputs and the new user creation ontouch.
            It also does the checks on button selected.
            Log.v(TAG, FILENAME + ": Create new user!");
            Log.v(TAG, FILENAME + ": Logging in with: " + etUsername.getText().toString() + ": " + etPassword.getText().toString());
            Log.v(TAG, FILENAME + ": Valid User! Logging in");
            Log.v(TAG, FILENAME + ": Invalid user!");

        */


    }

    protected void onStop() {
        super.onStop();
        finish();
    }

    public boolean isValidUser(String userName, String password) {


         /* HINT:
            This method is called to access the database and return a true if user is valid and false if not.
            Log.v(TAG, FILENAME + ": Running Checks..." + dbData.getMyUserName() + ": " + dbData.getMyPassword() +" <--> "+ userName + " " + password);
            You may choose to use this or modify to suit your design.
         */
        MyDBHandler handler = new MyDBHandler(this,null,null,1);
        boolean result = false;
        user = handler.findUser(userName);
        if (user != null) {
            if (user.getMyUserName().equals(userName) && user.getMyPassword().equals(password)){
                return true;
            }
            else{
                Log.v(TAG, FILENAME + ": Invalid user!");
                Toast.makeText(MainActivity.this,"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else{
            Log.v(TAG, FILENAME + ": Invalid user!");
            Toast.makeText(MainActivity.this,"Invalid Username",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}


