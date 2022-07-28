package com.example.recyclerviewapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    FlowerAdapter adapter;
    ArrayList<Flower> list;
    int position;

    Dialog dialog_add, dialog_rd;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;

    int count;
    Handler hand, hand2;
    TextView tv_count, tv_flower;
    ArrayList<String> flowername;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("꽃말사랑");
        //Log.d("zn_check", "1");

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        dialog_add = new Dialog(MainActivity.this); //Dialog 초기화
        //dialog_add.requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 제거
        dialog_add.setContentView(R.layout.activity_dialog); //xml 레이아웃 파일과 연결

        dialog_rd = new Dialog(MainActivity.this);
        dialog_rd.setContentView(R.layout.activity_random);

        tv_count = dialog_rd.findViewById(R.id.tv_count);
        tv_flower = dialog_rd.findViewById(R.id.tv_flower);

        recyclerView = findViewById(R.id.recyclerView);
        //Log.d("zn_check", "4");
        list = new ArrayList<>();
        //Log.d("zn_check", "5");
        flowername = new ArrayList<>();
        System.out.println("hihi");

        //LayoutManager 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        //Log.d("zn_check", "6");
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("zn_check", "7");

        //Flower flower = new Flower();
        adapter = new FlowerAdapter(list);

        //Handler---------------------------------------------------------------------
        flowername = new ArrayList<>();
        //핸들러를 통해 UI 스레드에 접근
        hand = new Handler(Looper.getMainLooper()){ //생성 시 해당 핸들러를 호출한 스레드의 메시지 큐와 루퍼에 자동으로 연결
            @Override
            public void handleMessage(Message msg) { //메시지 값 받음
                super.handleMessage(msg);
                tv_count.setText(msg.arg1+""); //메시지 값 사용
                Log.d("zn_timer_handler", "초 : " + count);
            }
        };

        hand2 = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String name_rd = flowername.get(msg.arg1).toString(); //배열의 인덱스로 값 가져오기
                Log.d("zn_timer_handler", "꽃 이름 : " + name_rd);
                tv_flower.setText(name_rd+""); //메시지 값 사용
                Log.d("zn_timer_handler", "인덱스 : " + msg.arg1);
            }
        };
        //----------------------------------------------------------------------------

        //click 이벤트------------------------------------------------------------------
        adapter.setOnItemClickListener(new FlowerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Flower item = adapter.getItem(position); //선택한 위치의 아이템뷰를 가져옴
                Intent intent = new Intent(getApplicationContext(), FloatingActivity_add.class);
                intent.putExtra("img", item.getFoto());
                intent.putExtra("name", item.getName());
                intent.putExtra("story", item.getStory());
                startActivity(intent);
            }
        });

        adapter.setOnLongItemClickListener(new FlowerAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int position) {
                setPosition(position); //선택한 위치 값 넘겨줌
                showDelDialog(); //삭제 다이얼로그 실행
            }
        });
        //----------------------------------------------------------------------------
        //리사이클러뷰에 어댑터를 연결
        recyclerView.setAdapter(adapter);
        //Log.d("zn_check", "8");

        addItem(R.drawable.img_amaryllis,"아마릴리스","은은한 아름다움");
        addItem(R.drawable.img_anemone,"아네모네","속절없는 사랑");
        addItem(R.drawable.img_calla,"칼라","천년의 사랑");
        addItem(R.drawable.img_camellia,"동백","그 누구보다 당신을 사랑합니다");
        addItem(R.drawable.img_carnation,"카네이션","감사와 존경");
        addItem(R.drawable.img_cranesbill,"제라늄","그대가 있어 행복합니다");
        addItem(R.drawable.img_daffodil,"수선화","사랑해 주세요");
        addItem(R.drawable.img_dahlias,"달리아","당신의 사랑이 날 아름답게 합니다");
        addItem(R.drawable.img_daisy,"데이지","순수한 마음으로 당신을 사랑합니다");
        addItem(R.drawable.img_forgetmenot,"물망초","진실한 사랑");
        addItem(R.drawable.img_freesia,"프리지아","당신의 시작을 응원합니다");
        addItem(R.drawable.img_gladiolus,"글라디올러스","정열적인 사랑");
        addItem(R.drawable.img_gloxinia,"글록시니아","화려한 모습");
        addItem(R.drawable.img_hyacinth,"히야신스","겸손한 마음으로 당신을 사랑합니다");
        addItem(R.drawable.img_lilac,"라일락","당신의 시작을 응원합니다");
        addItem(R.drawable.img_lily,"백합","당신에게 변함없는 사랑을 드립니다");
        addItem(R.drawable.img_lisianthus,"리시안셔스","변치않는 사랑");
        addItem(R.drawable.img_marigold,"메리골드","반드시 찾아오고야 말 행복");
        addItem(R.drawable.img_mist,"안개꽃","깨끗한 마음으로 사랑을 드립니다");
        addItem(R.drawable.img_paeonia,"작약","수줍음");
        addItem(R.drawable.img_pansy,"팬지","나를 생각해 주세요");
        addItem(R.drawable.img_rose,"장미","정열적인 사랑");
        addItem(R.drawable.img_sunflower,"해바라기","당신만을 바라봅니다");
        addItem(R.drawable.img_sweetpea,"스위트피","나를 기억해 주세요");
        addItem(R.drawable.img_tulip,"튤립","당신은 나의 영원한 사랑입니다");
        Log.d("zn_check", "9");
        Log.d("zn_main", "onCreate");
        adapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영

        /*for(int i = 0; i < list.size(); i++){ //각 아이템뷰 설정 확인
            String list_get = list.get(i).toString();
            Log.d("zn_main", list_get);
        }*/
    }

    //View.OnClickListener를 상속 받아 버튼을 onClick 할 때마다 id 값을 받아와주고 각 id에 맞는 기능 수행
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                showDialog_add();
                break;
            case R.id.fab2:
                anim();
                showDialog_random();
                break;
        }
    }

    //Flaoting Button 애니메이션
    public void anim() {
        if (isFabOpen) {
            fab1.startAnimation(fab_close); //애니메이션을 실행
            fab2.startAnimation(fab_close);
            fab1.setClickable(false); //버튼 활성화, 비활성화
            fab2.setClickable(false);
            //startAnimatino(fab_open)을 통해 애니메이션을 실행 -> setClickable(true)로 버튼을 활성화
            isFabOpen = false; //버튼이 클릭 됐는지 판단
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    //배열에 아이템 값 추가
    public void addItem(int foto, String name, String story) {
        Flower flower = new Flower();
        flower.setFoto(foto);
        flower.setName(name);
        flower.setStory(story);
        list.add(flower);

        //랜덤 뽑기를 위한 새 배열 생성
        flowername.add(name);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //선택한 위치의 아이템뷰 삭제
    public void removeItem(int position) {
        list.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();
    }

    //삭제 dialog
    public void showDelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("삭제");
                builder.setMessage("삭제하시겠습니까?");
                builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        removeItem(position);
                        //list.remove (position);
                        adapter.notifyItemRemoved (position);
                    }
                });
        AlertDialog delDlog = builder.create();
        delDlog.show();
    }

    //추가 dialog
    public void showDialog_add(){
        EditText name_et = dialog_add.findViewById(R.id.flower_answer);
        EditText story_et = dialog_add.findViewById(R.id.story_answer);

        dialog_add.show(); // 다이얼로그 띄우기
        //Log.d("zn_add", "추가 다이얼로그 시작");

        //재사용에 용이한 방법
        Button addBtn = dialog_add.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_et.getText().toString();
                String story = story_et.getText().toString();
                //Log.d("zn_add", "edittext 내용 가져오기");
                addItem(R.drawable.img_flower, name, story);
                adapter.notifyDataSetChanged();
                //Log.d("zn_add", name);
                //Log.d("zn_add", story);
                dialog_add.dismiss(); // 다이얼로그 닫기
                //Log.d("zn_add", "추가 다이얼로그 종료");
                name_et.setText(null);
                story_et.setText(null);
                Toast.makeText(MainActivity.this, "추가되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
        //바로 연결해서 일회성으로 사용하기 편리
        dialog_add.findViewById(R.id.noBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                //Log.d("zn_add", "추가 다이얼로그 취소 메시지");
                dialog_add.dismiss(); // 다이얼로그 닫기
                //Log.d("zn_add", "추가 다이얼로그 종료");
            }
        });
    }

    //추가 dialog
    public void showDialog_random(){
        TextView tv_count = dialog_rd.findViewById(R.id.tv_count);
        TextView tv_flower = dialog_rd.findViewById(R.id.tv_flower);

        dialog_rd.show(); // 다이얼로그 띄우기
        //Log.d("zn_rd", "추가 다이얼로그 시작");

        Button pickBtn = dialog_rd.findViewById(R.id.pickBtn);
        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread th = new Thread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        count = 4; //tv_count에 나타나도록 핸들러로 전달할 값
                        while (true) {
                            Message msg = hand.obtainMessage(); //메시지 객체 참조
                            count--;
                            msg.arg1 = count;
                            hand.sendMessage(msg); //Message 객체를 메시지 큐에 전달하는 함수
                            Log.d("zn_timer", "초 : " + count);

                            if(count == 0){
                                break; //0초일 때 멈춤
                            }

                            try {
                                Thread.sleep(1000); //1초마다 체크
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                Thread th2 = new Thread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = hand2.obtainMessage(); //메시지 객체 참조
                        Random random = new Random();
                        String rd_flowername = flowername.get(random.nextInt(flowername.size())); //랜덤으로 얻은 배열의 값
                        Log.d("zn_rd", "배열의 값 : " + rd_flowername);
                        int index = flowername.indexOf(rd_flowername); //랜덤으로 얻은 배열 값의 인덱스
                        Log.d("zn_rd", "인덱스의 값 : " + index);
                        msg.arg1 = index;
                        Log.d("zn_rd", "arg1의 값 : " + msg.arg1);
                        hand2.sendMessage(msg); //Message 객체를 메시지 큐에 전달하는 함수
                    }
                });
                th.start();
                th2.start();
            }
        });

        Button closeBtn = dialog_rd.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_rd.dismiss(); // 다이얼로그 닫기
                //Log.d("zn_rd", "추가 다이얼로그 종료");
            }
        });
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        Log.d("zn_main","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("zn_main","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("zn_main","onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("zn_main","onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("zn_main","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("zn_main","onDestroy");
    }*/
}