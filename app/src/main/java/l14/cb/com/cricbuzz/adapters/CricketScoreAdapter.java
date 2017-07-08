package l14.cb.com.cricbuzz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import l14.cb.com.cricbuzz.R;
import l14.cb.com.cricbuzz.asyntasks.CricketScoreTask;
import l14.cb.com.cricbuzz.models.CricketScorePOJO;

/**
 * Created by ip510 feih on 08-07-2017.
 */

public class CricketScoreAdapter extends RecyclerView.Adapter<CricketScoreAdapter.CricketScoreViewHolder> {

    CricketScorePOJO liveScore;
    Context context;
    Boolean matchStarted;

    public CricketScoreAdapter(CricketScorePOJO liveScore, Context context, Boolean matchStarted) {
        this.liveScore = liveScore;
        this.context = context;
        this.matchStarted = matchStarted;
    }

    public void updateList(CricketScorePOJO liveScore) {

        this.liveScore = liveScore;
        notifyDataSetChanged();
    }

    @Override
    public CricketScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.item_cricket_score, parent, false);

        return new CricketScoreAdapter.CricketScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CricketScoreViewHolder holder, int position) {

        holder.tvMatchType.setText(liveScore.getType()+" Match");

        holder.tvTeam1.setText(liveScore.getTeam_1());
        holder.tvTeam2.setText(liveScore.getTeam_2());

        if (matchStarted)
            holder.tvMatchStarted.setText("In Progress");
        else
            holder.tvMatchStarted.setText(liveScore.getInnings_requirement());

        if (matchStarted)
            holder.tvInningsRequirement.setText(liveScore.getInnings_requirement());
        else {
            holder.tvInningsRequirement.setVisibility(View.GONE);
        }
        if (matchStarted)
            holder.tvScore.setText(liveScore.getScore());
        else {
            holder.tvScore.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount()
    {
        return 1;
    }

    class CricketScoreViewHolder extends RecyclerView.ViewHolder {

        TextView tvTeam1, tvTeam2, tvMatchType, tvMatchStarted, tvInningsRequirement, tvScore;

        public CricketScoreViewHolder(View itemView) {

            super(itemView);
            tvTeam1 = (TextView) itemView.findViewById(R.id.tvTeam1);
            tvTeam2 = (TextView) itemView.findViewById(R.id.tvTeam2);
            tvMatchType = (TextView) itemView.findViewById(R.id.tvType);
            tvMatchStarted = (TextView) itemView.findViewById(R.id.tvMatchStarted);
            tvInningsRequirement = (TextView) itemView.findViewById(R.id.tvInningsRequirement);
            tvScore = (TextView) itemView.findViewById(R.id.tvScore);

        }
    }
}
