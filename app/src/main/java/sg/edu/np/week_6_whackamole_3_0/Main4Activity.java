package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Main4Activity extends AppCompatActivity {

    /* Hint:
        1. This creates the Whack-A-Mole layout and starts a countdown to ready the user
        2. The game difficulty is based on the selected level
        3. The levels are with the following difficulties.
            a. Level 1 will have a new mole at each 10000ms.
                - i.e. level 1 - 10000ms
                       level 2 - 9000ms
                       level 3 - 8000ms
                       ...
                       level 10 - 1000ms
            b. Each level up will shorten the time to next mole by 100ms with level 10 as 1000 second per mole.
            c. For level 1 ~ 5, there is only 1 mole.
            d. For level 6 ~ 10, there are 2 moles.
            e. Each location of the mole is randomised.
        4. There is an option return to the login page.
     */
    TextView Score2;
    int advancedScore;
    Button[] buttons;
    int level;
    Button Back;
    String username;
    private static final String FILENAME = "Main4Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    CountDownTimer readyTimer;
    CountDownTimer newMolePlaceTimer;

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

        readyTimer = new CountDownTimer(10000,1000){
            @Override
            public void onTick(long l) {
                Toast.makeText(Main4Activity.this,"Get ready in " + String.valueOf(l/1000) + " seconds",Toast.LENGTH_SHORT).show();
                Log.v(TAG,  FILENAME + "Ready CountDown!" + String.valueOf(l/1000)) ;
            }

            @Override
            public void onFinish() {
                Toast.makeText(Main4Activity.this,"Go!",Toast.LENGTH_SHORT).show();
                Log.v(TAG, FILENAME + "Ready CountDown Complete!");
                placeMoleTimer();
                Back.setEnabled(true);
                for(Button i : buttons){
                    i.setEnabled(true);
                }
            }

        }.start();
    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        int interval = 11000 - getIntent().getIntExtra("level",0)*1000;
        newMolePlaceTimer = new CountDownTimer(interval,interval){
            @Override
            public void onTick(long l) {
                setNewMole();
            }
            @Override
            public void onFinish() {
                placeMoleTimer();
            }
        }.start();
    }
    private static final int[] BUTTON_IDS = {
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares level difficulty.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
            It also prepares the back button and updates the user score to the database
            if the back button is selected.
         */
        Back = findViewById(R.id.Back);
        Score2 = findViewById(R.id.Score2);
        advancedScore = getIntent().getIntExtra("score",0);
        level = getIntent().getIntExtra("level",0);
        username = getIntent().getStringExtra("username");
        Score2.setText(String.valueOf(advancedScore));
        Back.setEnabled(false);
        buttons = new Button[9];
        for(int i=0; i<buttons.length; i++){
            String buttonID = "button" + (i+1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setEnabled(false);
            final int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(buttons[finalI].getText() == "*"){
                        advancedScore++;
                        Log.v(TAG,FILENAME + "Hit, Score added!");
                    }
                    else{
                        advancedScore--;
                        Log.v(TAG, FILENAME +"Missed, Score deducted!");
                    }
                    Score2.setText(String.valueOf(advancedScore));
                }
            });
        }
        Log.v(TAG, FILENAME + "Current User Score: " + advancedScore);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserScore();
                Intent intent = new Intent(Main4Activity.this,Main3Activity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });



        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
    }


    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, FILENAME + ": Hit, score added!");
            Log.v(TAG, FILENAME + ": Missed, point deducted!");
            belongs here.
        */

    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole. Adds additional mole if the level difficulty is from 6 to 10.
         */
        int randomLocation;
        int randomLocation1;
        Random ran = new Random();
        for(Button i : buttons){
            i.setText("0");
        }
        if(level <= 5){
            randomLocation = ran.nextInt(9);
            buttons[randomLocation].setText("*");
            Log.v(TAG,FILENAME + "New Mole Location");
        }
        else{
            randomLocation = ran.nextInt(9);
            randomLocation1 = ran.nextInt(9);
            while (randomLocation == randomLocation1){
                randomLocation1 = ran.nextInt(9);
            }
            buttons[randomLocation].setText("*");
            buttons[randomLocation1].setText("*");
            Log.v(TAG,FILENAME + "New Mole Locations");
        }
    }

    private void updateUserScore()
    {

     /* Hint:
        This updates the user score to the database if needed. Also stops the timers.
        Log.v(TAG, FILENAME + ": Update User Score...");
      */
        Log.v(TAG, FILENAME + ": Update User Score...");
        newMolePlaceTimer.cancel();
        readyTimer.cancel();
        MyDBHandler myDBHandler = new MyDBHandler(this,null,null,1);
        UserData userData = myDBHandler.findUser(username);
        myDBHandler.deleteAccount(username);
        userData.getScores().set(level-1,advancedScore);
        myDBHandler.addUser(userData);
    }

}
