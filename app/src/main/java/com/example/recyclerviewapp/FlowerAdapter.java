package com.example.recyclerviewapp;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.ViewHolder> {
    ArrayList<Flower> items;

    FlowerAdapter(ArrayList<Flower> list) {
        this.items = list;
    }
    //Click 이벤트 구현------------------------------------------------------------------
    //OnItemClickListener 인터페이스 선언
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    //OnItemClickListener 리스너 객체 참조를 저장하는 변수 선언
    private OnItemClickListener onitemClickListener = null;

    //OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메소드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onitemClickListener = listener;
        Log.d("zn_click", "1");
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(int position);
    }

    private OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }
    //--------------------------------------------------------------------------------
    //뷰홀더 클래스-----------------------------------------------------------------------
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView ftext;
        TextView stext;

        //OnItemClickListener onItemClickListener;
        //OnLongItemClickListener onLongItemClickListener;

        public ViewHolder(View itemVIew){
            super(itemVIew);
            Log.d("zn_check", "11");

            imageView= itemVIew.findViewById(R.id.imageView);
            ftext = itemVIew.findViewById(R.id.ftext);
            stext = itemVIew.findViewById(R.id.stext);

            //Click 이벤트 구현--------------------------------------------------------------
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        if (onitemClickListener != null) {
                            onitemClickListener.onItemClick(v, position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        if (onLongItemClickListener != null) {
                            onLongItemClickListener.onLongItemClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });
            //----------------------------------------------------------------------------
        }

        public void setItem(Flower item){
            Log.d("zn_check", "12");
            imageView.setImageResource(item.getFoto());
            ftext.setText(item.getName());
            stext.setText(item.getStory());
        }
    }
    //--------------------------------------------------------------------------------
    //뷰홀더가 새로 만들어질 때 호출(RecyclerView Adapter 필수 구현 항목)-------------------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // view를 생성할때 실행되어  viewholder를 리턴
        // 파라미터로 전달되는 뷰그룹 객체는 각 아이템을 위한 뷰그룹 객체이므로 XML 파일을 클래스로 변환하여 이 뷰그룹 객체에 전달
        //Log.d("zn_check", "13");
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext()); //화면 요소이므로 comtext 필요
        //Log.d("zn_check", "14");
        View itemView = inflater.inflate(R.layout.activity_item,viewGroup,false);
        //Log.d("zn_check", "15");
        FlowerAdapter.ViewHolder viewHolder = new FlowerAdapter.ViewHolder(itemView);

        return viewHolder; //new ViewHolder(itemView);
    }
    //--------------------------------------------------------------------------------
    // 뷰홀더가 재사용될 때 호출(이 메서드는 재활용할 수 있는 뷰홀더 객체를 파라미터로 전달)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //viewholder의 내용을 position에 해당되는 데이터로 교체
        // 뷰 객체는 기존 것을 그대로 사용하고 데이터만 바꿔준다
        //Log.d("zn_check", "16");
        Flower item = items.get(position);
        //Log.d("zn_check", "17");
        viewHolder.setItem(item);
    }
    //--------------------------------------------------------------------------------
    //아이템(전체 데이터)의 개수 조회---------------------------------------------------------
    @Override
    public int getItemCount() {
        Log.d("zn_check", "18");
        return items.size();
    }
    //--------------------------------------------------------------------------------
    //아이템 한 개 추가
    public void addItem(Flower item){
        items.add(item);
    }

    //아이템 한꺼번에 추가
    public void addItems(ArrayList<Flower> items){
        this.items = items;
    }

    //선택한 위치에 있는 아이템을 조회
    public Flower getItem(int position){
        return items.get(position);
    }
}