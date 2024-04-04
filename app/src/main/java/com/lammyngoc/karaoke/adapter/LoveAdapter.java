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

public class LoveAdapter extends ArrayAdapter<Song> {
    Activity context;
    int resource;
    public LoveAdapter(@NonNull Activity  context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
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
            // Chỉ hiển thị những bài hát có trạng thái yêu thích là 1
            if (song.getFavorite() != null && song.getFavorite() == 1) {
                txtMa.setText(song.getMa());
                txtTenbaihat.setText(song.getTenbaihat());
                txtTacgia.setText(song.getTacgia());
                btnLike.setImageResource(R.mipmap.ic_dislike); // Thay đổi hình ảnh thành dislike
            } else {
                // Nếu bài hát không có trạng thái yêu thích là 1, ẩn item đi
                view.setVisibility(View.GONE);
            }
            // Xử lý sự kiện khi người dùng nhấn vào nút like/dislike
            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Thay đổi trạng thái yêu thích của bài hát
                    if (song.getFavorite() != null && song.getFavorite() == 1) {
                        song.setFavorite(0); // Chuyển sang trạng thái không yêu thích
                        btnLike.setImageResource(R.mipmap.ic_like); // Hiển thị nút like
                    } else {
                        song.setFavorite(1); // Chuyển sang trạng thái yêu thích
                        btnLike.setImageResource(R.mipmap.ic_dislike); // Hiển thị nút dislike
                    }

                    // Cập nhật dữ liệu trong cơ sở dữ liệu
                    ((MainActivity) context).updateFavoriteStatus(song);
                }
            });
        }
        return view;
    }
}
