package sg.edu.np.week_6_whackamole_3_0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {
    /* Hint:
        1. This is the custom adaptor for the recyclerView list @ levels selection page

     */
    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    UserData Userdata;
    Context Context;
    ArrayList<Integer> level_List;
    ArrayList<Integer> score_List;

    public CustomScoreAdaptor(UserData userdata,Context context){
        /* Hint:
        This method takes in the data and readies it for processing.
         */
        Userdata = userdata;
        level_List = userdata.getLevels();
        score_List = userdata.getScores();
        Context = context;
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        /* Hint:
        This method dictates how the viewholder layuout is to be once the viewholder is created.
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.levelitem,parent,false);
        CustomScoreViewHolder viewHolder = new CustomScoreViewHolder(v);
        return viewHolder;
    }

    public void onBindViewHolder(CustomScoreViewHolder holder, final int position){

        /* Hint:
        This method passes the data to the viewholder upon bounded to the viewholder.
        It may also be used to do an onclick listener here to activate upon user level selections.

        Log.v(TAG, FILENAME + " Showing level " + level_list.get(position) + " with highest score: " + score_list.get(position));
        Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + list_members.getMyUserName());
         */

        holder.scoretext.setText("Highest score:  " + score_List.get(position));
        holder.leveltext.setText("Level " + String.valueOf(position+1));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, FILENAME + " Showing level " + level_List.get(position) + " with highest score: " + score_List.get(position));
                Log.v(TAG, FILENAME+ ": Load level " + String.valueOf(position+1) +" for: " + Userdata.getMyUserName());
                Intent intent = new Intent(Context,Main4Activity.class);
                intent.putExtra("username",Userdata.getMyUserName());
                intent.putExtra("level",position+1);
                intent.putExtra("Time",10-position);
                Context.startActivity(intent);
            }
        });

}

    public int getItemCount(){
        /* Hint:
        This method returns the the size of the overall data.
         */
        return level_List.size();
    }
}