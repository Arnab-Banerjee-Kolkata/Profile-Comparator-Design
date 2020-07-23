package com.example.profilecomparator;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends FragmentActivity implements View.OnTouchListener, ViewPager.OnPageChangeListener
{
    ViewPager screenSwitcher;
    ImageView upSwipeImg,downSwipeImg;
    static RelativeLayout matchScreen;
    static float d,SCREEN_HEIGHT,SCREEN_WIDTH,lowestY;
    static int startX=0,startY=0,currentX=0,currentY=0;
    static boolean START_SLIDE=false;
    Handler handler=new Handler();
    static float BOTTOM_SPACE;
    static Context mContext;
    static int NO_OF_TABS=6;
    static TextView tabText[];
    static RelativeLayout tabCol[];
    static HorizontalScrollView tabScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        d = this.getResources().getDisplayMetrics().density;

        SCREEN_WIDTH=getWindowManager().getDefaultDisplay().getWidth();
        SCREEN_HEIGHT=getWindowManager().getDefaultDisplay().getHeight();
        BOTTOM_SPACE=this.getResources().getDimension(R.dimen.swipeBarHeight)+20*d;

        screenSwitcher=(ViewPager)findViewById(R.id.screenSwitcher);
        upSwipeImg=(ImageView)findViewById(R.id.upSwipeImg);
        downSwipeImg=(ImageView)findViewById(R.id.downSwipeImg);
        matchScreen=(RelativeLayout)findViewById(R.id.matchScreen);
        tabScroll=(HorizontalScrollView)findViewById(R.id.tabScroll);

        tabCol=new RelativeLayout[NO_OF_TABS];
        tabText=new TextView[NO_OF_TABS];

        tabCol[0]=(RelativeLayout)findViewById(R.id.tabCol1);
        tabCol[1]=(RelativeLayout)findViewById(R.id.tabCol2);
        tabCol[2]=(RelativeLayout)findViewById(R.id.tabCol3);
        tabCol[3]=(RelativeLayout)findViewById(R.id.tabCol4);
        tabCol[4]=(RelativeLayout)findViewById(R.id.tabCol5);
        tabCol[5]=(RelativeLayout)findViewById(R.id.tabCol6);

        tabText[0]=(TextView)findViewById(R.id.tabText1);
        tabText[1]=(TextView)findViewById(R.id.tabText2);
        tabText[2]=(TextView)findViewById(R.id.tabText3);
        tabText[3]=(TextView)findViewById(R.id.tabText4);
        tabText[4]=(TextView)findViewById(R.id.tabText5);
        tabText[5]=(TextView)findViewById(R.id.tabText6);



        mContext=getApplicationContext();


        SwipeAdapter swipeAdapter=new SwipeAdapter(getSupportFragmentManager());
        screenSwitcher.setAdapter(swipeAdapter);
        matchScreen.setY(SCREEN_HEIGHT-BOTTOM_SPACE);
        lowestY=matchScreen.getY();
        setHeadingBackground(0);

        upSwipeImg.setOnTouchListener(this);
        downSwipeImg.setOnTouchListener(this);
        screenSwitcher.setOnPageChangeListener(this);
        downSwipeImg.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent)
    {
        final int action= motionEvent.getAction();
        if(v.equals(upSwipeImg))
        {
            switch(action & MotionEvent.ACTION_MASK)
            {
                case MotionEvent.ACTION_DOWN:
                {
                    setStartX((int)motionEvent.getRawX());
                    setStartY((int)motionEvent.getRawY());
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                {
                    setCurrentX((int)motionEvent.getRawX());
                    setCurrentY((int)motionEvent.getRawY());
                    if(currentY<=lowestY)
                        matchScreen.setY(currentY);
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    if(matchScreen.getY()<SCREEN_HEIGHT/2)
                    {
                        showMatchScreen();
                    }
                    else
                    {
                        hideMatchScreen();
                    }
                    break;
                }
            }
        }
        else if(v.equals(downSwipeImg))
        {
            switch(action & MotionEvent.ACTION_MASK)
            {
                case MotionEvent.ACTION_DOWN:
                {
                    setStartX((int)motionEvent.getRawX());
                    setStartY((int)motionEvent.getRawY());
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                {
                    setCurrentX((int)motionEvent.getRawX());
                    setCurrentY((int)motionEvent.getRawY());
                    matchScreen.setY(currentY);
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    if(matchScreen.getY()>SCREEN_HEIGHT/2)
                    {
                        hideMatchScreen();
                    }
                    else
                    {
                        showMatchScreen();
                    }
                    break;
                }
            }
        }


        return true;
    }

    static void setCurrentX(int x)
    {
        currentX=x;
    }
    static void setCurrentY(int y)
    {
        currentY=y;
    }
    static void setStartX(int x)
    {
        startX=x;
    }
    static void setStartY(int y)
    {
        startY=y;
    }

    public void showMatchScreen()
    {
        int temp=0;
        for (int a = (int)matchScreen.getY(); a>=0 ;a-=10)      //Moving screen up
        {
            temp++;
            final int index=a;
            handler.postDelayed(new Runnable() {

                @Override
                public void run()
                {
                    if(index<=9)
                    {
                        matchScreen.setY(0);
                        upSwipeImg.setVisibility(View.GONE);
                        downSwipeImg.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        matchScreen.setY(index);
                    }
                }
            }, 5 * temp);
        }
    }
    public void hideMatchScreen()
    {
        int temp=0;
        for (int a = (int)matchScreen.getY(); a<=SCREEN_HEIGHT-BOTTOM_SPACE ;a+=10)      //Moving screen up
        {
            temp++;
            final int index=a;
            final float btm=BOTTOM_SPACE;
            handler.postDelayed(new Runnable() {

                @Override
                public void run()
                {
                    if(index>=SCREEN_HEIGHT-(btm+9))
                    {
                        matchScreen.setY(SCREEN_HEIGHT-btm);
                        upSwipeImg.setVisibility(View.VISIBLE);
                        downSwipeImg.setVisibility(View.GONE);
                    }
                    else
                    {
                        matchScreen.setY(index);
                    }
                }
            }, 5 * temp);
        }
    }
    public static void setHeadingBackground(int num)
    {
        for(int a=0;a<NO_OF_TABS;a++)
        {
            tabCol[a].setBackgroundColor(mContext.getResources().getColor(R.color.purple));
        }
        tabCol[num].setBackgroundColor(mContext.getResources().getColor(R.color.lightPurple));

        tabScroll.requestChildFocus(tabText[num],tabText[num]);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position)
    {
        setHeadingBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
