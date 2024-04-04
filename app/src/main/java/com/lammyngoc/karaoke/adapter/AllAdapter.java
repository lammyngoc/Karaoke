package com.lammyngoc.karaoke.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lammyngoc.karaoke.MainActivity;
import com.lammyngoc.karaoke.R;
import com.lammyngoc.karaoke.model.Song;

public class AllAdapter extends ArrayAdapter<Song> {
    Activity context;
    int resource;
    public AllAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // nhân bản giao diện
        View view=context.getLayoutInflater().inflate(resource,null);
        // truy xuất từng view trong giao diện
        TextView txtMa=view.findViewById(R.id.txtMa);
        TextView txtTenbaihat=view.findViewById(R.id.txtTenbaihat);
        TextView txtTacgia=view.findViewById(R.id.txtTacgia);
        ImageView btnLike=view.findViewById(R.id.btnLike);


        // lấy thông tin tại vị trí mà nó nhân bản
        Song song=getItem(position);
        // hiển thị view đã lấy
        if (song != null) {
            txtMa.setText(song.getMa());
            txtTenbaihat.setText(song.getTenbaihat());
            txtTacgia.setText(song.getTacgia());

            Integer favorite = song.getFavorite(); // Use Integer instead of int
            if (favorite != null) {
                if (favorite == 0) {
                    btnLike.setImageResource(R.mipmap.ic_like);
                } else {
                    btnLike.setImageResource(R.mipmap.ic_dislike);
                }
            }
            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toggle favorite attribute
                    if (favorite != null) {
                        if (favorite == 0) {
                            song.setFavorite(1);
                        } else {
                            song.setFavorite(0);
                        }
                        ((MainActivity) context).updateFavoriteStatus(song);
                        notifyDataSetChanged();
                    }
                }
            });
        }
        return view;
    }
}
