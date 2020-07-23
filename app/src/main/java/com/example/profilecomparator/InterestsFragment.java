package com.example.profilecomparator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class InterestsFragment extends Fragment implements View.OnTouchListener
{
    static Context mContext;
    static Activity mActivity;
    static CardView inCard[];
    static TextView inName[];
    static RelativeLayout cover;
    static int GUY_INTERESTS,GIRL_INTERESTS;
    static float d,SCREEN_HEIGHT,SCREEN_WIDTH;
    static int startX=0,startY=0,currentX=0,currentY=0,guyCardPt,girlCardPt,girlHobbyPos=0,guyHobbyPos=0,guyPrevPt,guyNextPt,girlPrevPt,girlNextPt;
    static Handler handler=new Handler();
    static String guyHobbyName[]={"TAKE A LONG WALK TOGETHER","MAKE OUT IN THE BACK SEAT OF A CAR","EAT LUNCH TOGETHER","GRAB DRINKS AT A BAR"},
            girlHobbyName[]={"TAKE A LONG WALK TOGETHER","MAKE OUT IN THE BACK SEAT OF A CAR","EAT LUNCH TOGETHER","GRAB DRINKS AT A BAR"};


    public InterestsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mContext=getContext();
        mActivity=getActivity();
        View view=inflater.inflate(R.layout.interests_fragment_layout,container,false);
        d = this.getResources().getDisplayMetrics().density;

        SCREEN_WIDTH=getActivity().getWindowManager().getDefaultDisplay().getWidth();
        SCREEN_HEIGHT=getActivity().getWindowManager().getDefaultDisplay().getHeight();

        GUY_INTERESTS=guyHobbyName.length;GIRL_INTERESTS=girlHobbyName.length;
        inCard=new CardView[6];
        inName=new TextView[6];

        inCard[0]=(CardView)view.findViewById(R.id.inCard1);
        inCard[1]=(CardView)view.findViewById(R.id.inCard2);
        inCard[2]=(CardView)view.findViewById(R.id.inCard3);
        inCard[3]=(CardView)view.findViewById(R.id.inCard4);
        inCard[4]=(CardView)view.findViewById(R.id.inCard5);
        inCard[5]=(CardView)view.findViewById(R.id.inCard6);


        inName[0]=(TextView)view.findViewById(R.id.inName1);
        inName[1]=(TextView)view.findViewById(R.id.inName2);
        inName[2]=(TextView)view.findViewById(R.id.inName3);
        inName[3]=(TextView)view.findViewById(R.id.inName4);
        inName[4]=(TextView)view.findViewById(R.id.inName5);
        inName[5]=(TextView)view.findViewById(R.id.inName6);

        cover=(RelativeLayout)view.findViewById(R.id.cover);
        cover.setElevation(35);

        inCard[2].setOnTouchListener(this);
        inCard[5].setOnTouchListener(this);
        guyCardPt=2;
        girlCardPt=5;
        setInitialCards();

        Bundle bundle=getArguments();
        String num=Integer.toString(bundle.getInt("count"));
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent)
    {
        final int action= motionEvent.getAction();
        if(v.equals(inCard[0]) || v.equals(inCard[1]) || v.equals(inCard[2]))       //guy cards
        {
            switch (action & MotionEvent.ACTION_MASK)
            {
                case MotionEvent.ACTION_DOWN: {
                    setStartX((int) motionEvent.getRawX());
                    setStartY((int) motionEvent.getRawY());
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                {
                    setCurrentX((int) motionEvent.getRawX());
                    setCurrentY((int) motionEvent.getRawY());
                    if (currentY<=startY)
                    {
                        v.setY(v.getY() + (currentY - startY));
                        setStartX(currentX);
                        setStartY(currentY);
                    }
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    if((int)motionEvent.getRawY()>=startY+30)
                    {
                        bringPrevGuyCard(v);
                        setStartX(currentX);
                        setStartY(currentY);
                    }
                    else if(v.getY()>0)
                    {
                        guyCardIn(v);
                    }
                    else if(v.getY()<0)
                    {
                        guyCardOut(v);
                    }
                    break;
                }
            }
        }
        else if(v.equals(inCard[3]) || v.equals(inCard[4]) || v.equals(inCard[5]))          //girl cards
        {
            switch (action & MotionEvent.ACTION_MASK)
            {
                case MotionEvent.ACTION_DOWN: {
                    setStartX((int) motionEvent.getRawX());
                    setStartY((int) motionEvent.getRawY());
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    setCurrentX((int) motionEvent.getRawX());
                    setCurrentY((int) motionEvent.getRawY());
                    if (currentY<=startY)
                    {
                        v.setY(v.getY() + (currentY - startY));
                        setStartX(currentX);
                        setStartY(currentY);
                    }

                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    if((int)motionEvent.getRawY()>=startY+30)
                    {
                        bringPrevGirlCard(v);
                        setStartX(currentX);
                        setStartY(currentY);
                    }
                    else if(v.getY()>SCREEN_HEIGHT/2)
                    {
                        girlCardIn(v);
                    }
                    else if(v.getY()<SCREEN_HEIGHT/2)
                    {
                        girlCardOut(v);
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
    public static void guyCardOut(final View v)
    {
        int temp=0;
        for (int a = (int)v.getY(); a>=0-v.getHeight() ;a-=10)      //Moving card up
        {
            temp++;
            final int index=a;
            handler.postDelayed(new Runnable() {

                @Override
                public void run()
                {
                    if(index-10<-v.getHeight())
                    {
                        v.setY(-v.getHeight());
                        int prev;
                        if(guyCardPt==2)
                        {
                            guyCardPt = 1;
                            prev=0;
                        }
                        else if(guyCardPt==1)
                        {
                            guyCardPt = 0;
                            prev=2;
                        }
                        else
                        {
                            guyCardPt = 2;
                            prev=1;
                        }
                        inCard[guyCardPt].setRotation(0);
                        inCard[prev].setRotation(-5);
                        inCard[guyCardPt].setElevation(60);
                        inCard[prev].setElevation(50);
                        inCard[guyCardPt].setOnTouchListener(new InterestsFragment());
                        sendGuyCardBack(v);
                    }
                    else
                    {
                        v.setY(index);
                    }
                }
            }, 5 * temp);
        }
    }
    public static void guyCardIn(final View v)
    {
        int temp=0;
        for (int a = (int)v.getY(); a<150 ;a+=10)      //Moving card down
        {
            temp++;
            final int index=a;
            handler.postDelayed(new Runnable() {

                @Override
                public void run()
                {
                    if(index+10>=150)
                    {
                        v.setY(150);
                    }
                    else
                    {
                        v.setY(index);
                    }
                }
            }, 5 * temp);
        }
    }
    public static void girlCardOut(final View v)
    {
        int temp=0;
        for (int a = (int)v.getY(); a>SCREEN_HEIGHT/2-v.getHeight() ;a-=10)      //Moving card up
        {
            temp++;
            final int index=a;
            handler.postDelayed(new Runnable() {

                @Override
                public void run()
                {
                    if(index-10<=SCREEN_HEIGHT/2-v.getHeight())
                    {
                        v.setY(SCREEN_HEIGHT);
                        int prev;
                        if(girlCardPt==5)
                        {
                            girlCardPt = 4;
                            prev=3;
                        }
                        else if(girlCardPt==4)
                        {
                            girlCardPt = 3;
                            prev=5;
                        }
                        else
                        {
                            girlCardPt = 5;
                            prev=4;
                        }
                        inCard[girlCardPt].setRotation(0);
                        inCard[prev].setRotation(5);
                        inCard[girlCardPt].setElevation(30);
                        inCard[prev].setElevation(20);
                        inCard[girlCardPt].setOnTouchListener(new InterestsFragment());
                        sendGirlCardBack(v);
                    }
                    else
                    {
                        v.setY(index);
                    }
                }
            }, 5 * temp);
        }
    }
    public static void girlCardIn(final View v)
    {
        int temp=0;
        for (int a = (int)v.getY(); a<SCREEN_HEIGHT-(100+v.getHeight()) ;a+=10)      //Moving card up
        {
            temp++;
            final int index=a;
            handler.postDelayed(new Runnable() {

                @Override
                public void run()
                {
                    if(index+10>=SCREEN_HEIGHT-(100+v.getHeight()))
                    {
                        v.setY(SCREEN_HEIGHT-(100+v.getHeight()));
                    }
                    else
                    {
                        v.setY(index);
                    }
                }
            }, 5 * temp);
        }
    }

    public static void sendGuyCardBack(View v)
    {
        v.setY(150);
        v.setOnTouchListener(null);
        v.setElevation(40);
        v.setRotation(-10);
        if(guyNextPt==GUY_INTERESTS)
            guyNextPt=0;
        if(v.equals(inCard[0]))
        {
            inName[0].setText(guyHobbyName[guyNextPt]);
        }
        else if(v.equals(inCard[1]))
        {
            inName[1].setText(guyHobbyName[guyNextPt]);
        }
        else
        {
            inName[2].setText(guyHobbyName[guyNextPt]);
        }
        guyNextPt++;
        guyPrevPt++;
        if(guyPrevPt==GUY_INTERESTS)
            guyPrevPt=0;
    }
    public static void sendGirlCardBack(View v)
    {
        v.setY(SCREEN_HEIGHT-(100+v.getHeight()));
        v.setOnTouchListener(null);
        v.setElevation(10);
        v.setRotation(10);
        if(girlNextPt==GIRL_INTERESTS)
            girlNextPt=0;
        if(v.equals(inCard[3]))
        {
            inName[3].setText(girlHobbyName[girlNextPt]);
        }
        else if(v.equals(inCard[4]))
        {
            inName[4].setText(girlHobbyName[girlNextPt]);
        }
        else
        {
            inName[5].setText(girlHobbyName[girlNextPt]);
        }
        girlNextPt++;
        girlPrevPt++;
        if(girlPrevPt==GIRL_INTERESTS)
            girlPrevPt=0;
    }
    public static void setInitialCards()
    {
        guyHobbyPos=0;girlHobbyPos=0;
        int temp=2;
        while(temp!=-1)
        {
            if(guyHobbyPos==GUY_INTERESTS)
                guyHobbyPos=0;
            inName[temp].setText(guyHobbyName[guyHobbyPos]);


            guyHobbyPos++;
            temp--;

        }
        temp=5;
        guyNextPt=guyHobbyPos;
        guyPrevPt=GUY_INTERESTS-1;
        while(temp!=2)
        {
            if(girlHobbyPos==GIRL_INTERESTS)
                girlHobbyPos=0;
            inName[temp].setText(girlHobbyName[girlHobbyPos]);


            girlHobbyPos++;
            temp--;

        }
        girlNextPt=girlHobbyPos;
        girlPrevPt=GIRL_INTERESTS-1;
        inCard[0].setElevation(40);
        inCard[1].setElevation(50);
        inCard[2].setElevation(60);

        inCard[3].setElevation(10);
        inCard[4].setElevation(20);
        inCard[5].setElevation(30);

    }
    public static void bringPrevGuyCard(View v)
    {
        View prev,last;
        if(v.equals(inCard[0]))
        {
            prev = inCard[1];
            last=inCard[2];
        }
        else if(v.equals(inCard[1]))
        {
            prev = inCard[2];
            last=inCard[0];
        }
        else
        {
            prev = inCard[0];
            last=inCard[1];
        }
        v.setOnTouchListener(null);
        v.setRotation(-5);
        last.setRotation(-10);
        v.setElevation(50);
        last.setElevation(40);
        prev.setElevation(60);
        prev.setRotation(0);
        prev.setOnTouchListener(new InterestsFragment());
        prev.setY(0-prev.getHeight());
        if(guyPrevPt<0)
            guyPrevPt=GUY_INTERESTS-1;
        if(prev.equals(inCard[0]))
        {
            inName[0].setText(guyHobbyName[guyPrevPt]);
        }
        else if(prev.equals(inCard[1]))
        {
            inName[1].setText(guyHobbyName[guyPrevPt]);
        }
        else
        {
            inName[2].setText(guyHobbyName[guyPrevPt]);
        }
        guyCardIn(prev);
        guyPrevPt--;
        guyNextPt--;
        if(guyNextPt==-1)
            guyNextPt=GUY_INTERESTS-1;
        if(guyCardPt==2)
        {
            guyCardPt = 0;
        }
        else if(guyCardPt==1)
        {
            guyCardPt = 2;
        }
        else
        {
            guyCardPt = 1;
        }
    }
    public static void bringPrevGirlCard(View v)
    {
        View prev,last;
        if(v.equals(inCard[3]))
        {
            prev = inCard[4];
            last=inCard[5];
        }
        else if(v.equals(inCard[4]))
        {
            prev = inCard[5];
            last=inCard[3];
        }
        else
        {
            prev = inCard[3];
            last=inCard[4];
        }
        v.setOnTouchListener(null);
        v.setRotation(5);
        last.setRotation(10);
        v.setElevation(20);
        last.setElevation(10);
        prev.setElevation(30);
        prev.setRotation(0);
        prev.setOnTouchListener(new InterestsFragment());
        prev.setY(SCREEN_HEIGHT/2-prev.getHeight());
        if(girlPrevPt<0)
            girlPrevPt=GIRL_INTERESTS-1;
        if(prev.equals(inCard[3]))
        {
            inName[3].setText(girlHobbyName[girlPrevPt]);
        }
        else if(prev.equals(inCard[4]))
        {
            inName[4].setText(girlHobbyName[girlPrevPt]);
        }
        else
        {
            inName[5].setText(girlHobbyName[girlPrevPt]);
        }
        girlCardIn(prev);
        girlPrevPt--;
        girlNextPt--;
        if(girlNextPt==-1)
            girlNextPt=GIRL_INTERESTS-1;
        if(girlCardPt==5)
        {
            girlCardPt = 3;
        }
        else if(girlCardPt==4)
        {
            girlCardPt = 5;
        }
        else
        {
            girlCardPt = 4;
        }
    }

}
