package com.example.project.Adapter;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.DB.FireBaseDataHelper;
import com.example.project.Model.CurrentUser;
import com.example.project.Model.TrustedStatus;
import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    Context context;
    List<CurrentUser> list;
    LayoutInflater layoutInflater;

    public RequestAdapter(Context context, List<CurrentUser> currentUserList) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        list = currentUserList;

    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestViewHolder(layoutInflater.inflate(R.layout.search_itemtrusted, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {


        try {
            holder.send.setVisibility(View.VISIBLE);
            setClickListener(holder, position);


            if (!list.get(position).getName().isEmpty()) {
                holder.nameUser.setText(list.get(position).getName());
            } else if (!list.get(position).getPhoneNumber().isEmpty()) {
                holder.nameUser.setText(list.get(position).getPhoneNumber());
            } else {
                holder.nameUser.setText(R.string.unknown);
            }
            Picasso.get().load(list.get(position).getImageURL()).transform(new CircleTransform()).into(holder.profileImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setClickListener(@NonNull RequestViewHolder holder, final int pos) {
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireBaseDataHelper fireBaseDataHelper = new FireBaseDataHelper(context);
                TrustedStatus trustedStatus = new TrustedStatus(list.get(pos).getUID(), fireBaseDataHelper.getCurrentUSer().getUid(), "Pending");
                fireBaseDataHelper.adUSerAsTrustedContact(trustedStatus);
                list.remove(pos);
                notifyDataSetChanged();
            }
        });
    }

    public void updateList() {
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView nameUser;
        Button Confirm;
        Button send;
        Button Cancel;

        private RequestViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            nameUser = itemView.findViewById(R.id.nameRequest);
            Confirm = itemView.findViewById(R.id.requestconfirm);
            send = itemView.findViewById(R.id.requestsend);
            Cancel = itemView.findViewById(R.id.requestDecline);

        }
    }
}
