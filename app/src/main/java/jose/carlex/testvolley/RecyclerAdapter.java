package jose.carlex.testvolley;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Carlex on 5/2/2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    Context ctx;

    ArrayList<TutorPost> arrayList = new ArrayList<>();
    public RecyclerAdapter(ArrayList<TutorPost> arrayList, Context ctx)
    {
        this.arrayList = arrayList;
        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view,ctx,arrayList);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.Date.setText(arrayList.get(position).getDate());
        holder.Subject.setText(arrayList.get(position).getSubject());
        holder.Price.setText(arrayList.get(position).getPrice());
        //Log.i("listview element Price",arrayList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView Date, Subject, Price;
        ArrayList<TutorPost> tutorPosts = new ArrayList<TutorPost>();
        Context ctx;

        public MyViewHolder(View itemView, Context ctx, ArrayList<TutorPost> tutorPosts) {
            super(itemView);
            this.tutorPosts = tutorPosts;
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            Date = (TextView)itemView.findViewById(R.id.row_date);
            Subject = (TextView)itemView.findViewById(R.id.row_subject);
            Price = (TextView)itemView.findViewById(R.id.row_price);
            //Log.i("listview element Price","Carl here");

        }
        @Override
        public void onClick(View v){
            int postition = getAdapterPosition();
            TutorPost tutorPost = this.tutorPosts.get(postition);
            Intent intent = new Intent(this.ctx,PosterDetails.class);

            intent.putExtra("email",tutorPost.getEmail());
            intent.putExtra("price",tutorPost.getPrice());
            intent.putExtra("date",tutorPost.getDate());
            intent.putExtra("description",tutorPost.getDescription());
            intent.putExtra("subject",tutorPost.getSubject());
            this.ctx.startActivity(intent);

        }

    }
}
