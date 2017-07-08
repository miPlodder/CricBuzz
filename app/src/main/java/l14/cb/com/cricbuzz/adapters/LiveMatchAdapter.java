package l14.cb.com.cricbuzz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import l14.cb.com.cricbuzz.R;
import l14.cb.com.cricbuzz.activities.CricketScoreActivity;
import l14.cb.com.cricbuzz.models.LiveMatchPOJO;

/**
 * Created by ip510 feih on 08-07-2017.
 */

public class LiveMatchAdapter extends RecyclerView.Adapter<LiveMatchAdapter.LiveMatchViewHolder>{

    ArrayList<LiveMatchPOJO> liveMatches;
    Context context;

    public LiveMatchAdapter(ArrayList<LiveMatchPOJO> liveMatches, Context context) {

        this.liveMatches = liveMatches;
        this.context = context;

    }

    public void updateData(ArrayList<LiveMatchPOJO> liveMatches){

        this.liveMatches = liveMatches;
        notifyDataSetChanged();

    }

    @Override
    public LiveMatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = li.inflate(R.layout.item_live_match,parent,false);

        return new LiveMatchViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(LiveMatchViewHolder holder, final int position) {

        LiveMatchPOJO.MatchDetail currItem = liveMatches.get(position).getMatchDetail();

        holder.tvTeam1.setText(currItem.getTeam_1());
        holder.tvTeam2.setText(currItem.getTeam_2());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, CricketScoreActivity.class);
                i.putExtra("unique_id",liveMatches.get(position).getMatchDetail().getUnique_key());
                i.putExtra("matchStarted",liveMatches.get(position).getMatchDetail().getMatchStarted());
                //Toast.makeText(context, "Clicked Item "+ position, Toast.LENGTH_SHORT).show();
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {

        return liveMatches.size();
    }



    class LiveMatchViewHolder extends RecyclerView.ViewHolder{

        TextView tvTeam1, tvTeam2;
        View itemView;

        public LiveMatchViewHolder(View itemView) {

            super(itemView);
            this.itemView = itemView;
            tvTeam1 = (TextView) itemView.findViewById(R.id.tvTeam1);
            tvTeam2 = (TextView) itemView.findViewById(R.id.tvTeam2);

        }
    }
}
