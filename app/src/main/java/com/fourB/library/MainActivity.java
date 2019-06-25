package com.fourB.library;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.fourB.library.Anouncement.AnoucementDetailActivity;
import com.fourB.library.Anouncement.AnouncementItem;
import com.fourB.library.Anouncement.AnouncementItemView;
import com.fourB.library.Barcode.BarcodeLinkActivity;
import com.fourB.library.Barcode.CustomScannerActivity;
import com.fourB.library.Anouncement.AnouncementActivity;
import com.fourB.library.MyPage.AppointmentBookActivity;
import com.fourB.library.MyPage.LendBookActivity;
import com.fourB.library.MyPage.LendBookRecordActivity;
import com.fourB.library.Report.ReportDialogActivity;
import com.fourB.library.Ebook.EbookActivity;
import com.fourB.library.GuideAll.GuideActivity;
import com.fourB.library.ChatBot.ChatBotActivity;
import com.fourB.library.ReadingRoom.ReadingRoomActivity;
import com.fourB.library.SearchBook.SearchBookActivity;
import com.fourB.library.StudyRoom.StudyRoomActivity;
import com.fourB.library.Util.HttpManager;
import com.fourB.library.Widgets.MovableFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.api.Http;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AnouncementAdapter mAdapter;
    private ListView mlistview;
    private ArrayList<AnouncementItem> mitems;


    CardView mEbookView;
    CardView mAnouncementView;
    CardView mBarcodeView;

    CardView mDeclareView;

    Slider banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AllFindViewById();

        mAdapter.addItem(new AnouncementItem("하계방학 중 학술정보원 이용안내", "2019-06-19", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=924"));
        mAdapter.addItem(new AnouncementItem("2019년 1학기 학위논문 제출안내", "2019-06-18", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=923"));
        mAdapter.addItem(new AnouncementItem("학술정보원 전층 대청소 일정공지", "2019-06-18", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=922"));
        mAdapter.addItem(new AnouncementItem("[공모전] RISS 영상 · 카드뉴스 공모전 안내", "2019-05-13", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=921"));
        mAdapter.addItem(new AnouncementItem("전세계 신문 & 매거진 PressReader 2019 퀴즈 이벤트 안내", "2019-05-09", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=920"));
        mAdapter.addItem(new AnouncementItem("KSDC DB(통계정보 조사/분석 시스템) 및 ICPSR DB 이용 교육 안내", "2019-05-09", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=919"));
        mAdapter.addItem(new AnouncementItem("제44회 독서경시대회 장소안내", "2019-04-25", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=918"));
        mAdapter.addItem(new AnouncementItem("학술정보원 휴원 안내(5월)", "2019-04-23", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=917"));
        mAdapter.addItem(new AnouncementItem("국내학회지 DBpia 서비스 일시 중지 안내 4.21(일)", "2019-04-16", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=916"));
        mAdapter.addItem(new AnouncementItem("중간고사 기간중 자유열람실 이용방법 변경안내", "2019-04-12", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=915"));
        mAdapter.addItem(new AnouncementItem("제44회 독서경시대회 개최", "2019-04-05", "http://library.sejong.ac.kr/bbs/Detail.ax?bbsID=1&articleID=914"));

        mlistview.setAdapter(mAdapter);

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AnoucementDetailActivity.class);
                AnouncementItem item = (AnouncementItem) mAdapter.getItem(position);
                intent.putExtra("Url", item.getAnouncement_Detail_Url());
                startActivity(intent);
            }
        });

        initView();

        CardView readingRoom = (CardView)findViewById(R.id.cardView_reading_room);
        readingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReadingRoomActivity.class);
                startActivity(intent);
            }
        });

        CardView searchBook = (CardView)findViewById(R.id.cardView_search_book);
        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchBookActivity.class);
                startActivity(intent);
            }
        });

        MovableFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
                startActivity(intent);
            }
        });

        CoordinatorLayout.LayoutParams lp  = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        fab.setCoordinatorLayout(lp);

        CardView studyRoomCardView = (CardView) findViewById(R.id.studyroom_cardview);
        studyRoomCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudyRoomActivity.class);
                startActivity(intent);
            }
        });

        CardView guideCardView = (CardView) findViewById(R.id.guide_cardview);
        guideCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(intent);
            }
        });

        CardView mobileCardView = (CardView) findViewById(R.id.MobileCardView);
        mobileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        mEbookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EbookActivity.class);
                startActivity(intent);
            }
         });

        mAnouncementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnouncementActivity.class);
                startActivity(intent);
            }
        });

        mBarcodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                    integrator.setCaptureActivity(CustomScannerActivity.class);
                    integrator.setOrientationLocked(false);
                    integrator.initiateScan();
            }
        });

        Slider.init(new PicassoImageLoadingService());
        banner.setAdapter(new MainSliderAdapter());
        banner.setSelectedSlide(2);

        mDeclareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDialogActivity DeclareDialog = new ReportDialogActivity(MainActivity.this);

                DeclareDialog.callFunction();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void show() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_mobilecard);

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String title = getIntent().hasExtra("title") ? getIntent().getStringExtra("title") : "";

        if( !title.equals("") ) {
            final String body = getIntent().getStringExtra("body");
            final String type = getIntent().getStringExtra("type");
            final String content = getIntent().getStringExtra("content");
            final String date = getIntent().getStringExtra("date");

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title);
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder
                    .append(body).append("\n\n").append("시간 : ").append(date).append('\n')
                    .append("사유 : ").append(type).append('\n')
                    .append("접수내용 : ").append(content);
            builder.setMessage(stringBuilder.toString());
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });
            builder.setCancelable(true);
            builder.show();

            getIntent().putExtra("title", "");
        }

        new Thread() {
            public void run() {
                try {
                    HttpManager.searchBookDetailRealServer("394460");
//                    HttpManager.searchBookRealServer("이순신", 1, 1, 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanResult == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                if(scanResult.getContents().equals(getString(R.string.barcode_bookBarcode_01)) || scanResult.getContents().equals(getString(R.string.barcode_bookBarcode_02))){
                    Intent newIntent = new Intent(getApplicationContext(), BarcodeLinkActivity.class);
                    newIntent.putExtra("BarcodeScanNumber", scanResult.getContents());
                    startActivity(newIntent);
                }else {
                    Toast.makeText(this, "등록되지 않은 도서입니다!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }

    }


    private void initView() {
        banner = (Slider) findViewById(R.id.banner_main);
        mEbookView = (CardView)findViewById(R.id.EbookView);
        mAnouncementView = (CardView)findViewById(R.id.AnouncementView);
        mBarcodeView = (CardView) findViewById(R.id.barcode_cardView);
        mDeclareView = (CardView) findViewById(R.id.declare_cardview);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lend_book) {

            Intent intent = new Intent(getApplicationContext(), LendBookActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_lend_book_record) {
            Intent intent = new Intent(getApplicationContext(), LendBookRecordActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_appointment_book) {
            Intent intent = new Intent(getApplicationContext(), AppointmentBookActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MainSliderAdapter extends SliderAdapter {

        @Override
        public int getItemCount() {
            return 3;
        }

        @Override
        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            switch (position) {
                case 0:
                    viewHolder.bindImageSlide(R.drawable.banner_1);
                    break;
                case 1:
                    viewHolder.bindImageSlide(R.drawable.banner_2);
                    break;
                case 2:
                    viewHolder.bindImageSlide(R.drawable.banner_4);
                    break;
            }
        }
    }
    public class PicassoImageLoadingService implements ImageLoadingService {

        @Override
        public void loadImage(String url, ImageView imageView) {
            //Picasso.with(context).load(imageUrl).resize(30, 30).into(imageView); }
            Picasso.get().load(url).into(imageView);
        }

        @Override
        public void loadImage(int resource, ImageView imageView) {
            Picasso.get().load(resource).into(imageView);
        }

        @Override
        public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
            Picasso.get().load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
        }
    }

    private void AllFindViewById(){

        mlistview = findViewById(R.id.listView);
        mAdapter = new AnouncementAdapter();
        mitems = mAdapter.getItems();
    }

    class AnouncementAdapter extends BaseAdapter {

        ArrayList<AnouncementItem> items = new ArrayList<AnouncementItem>();

        public ArrayList<AnouncementItem> getItems() {
            return items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(AnouncementItem item){
            items.add(item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            AnouncementItemView view = new AnouncementItemView(getApplicationContext());

            AnouncementItem item = items.get(position);
            view.setmAnouncement_Title(item.getAnouncement_Title());
            view.setmAnouncement_Update_Date(item.getAnouncement_Update_Date());

            return view;
        }
    }

}
