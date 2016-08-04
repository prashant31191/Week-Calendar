package noman.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;
import noman.weekcalendar.listener.OnWeekChangeListener;

public class MainActivity extends AppCompatActivity {
    private WeekCalendar weekCalendar;

    Button btnPrev,btnNext;
    TextView tvDetails,tvDateDetails,tvMinuteDetails;

    DateTime getDateTime;

    String strDisplayDate;
    String strDisplayMonth;

    RecyclerView horizontal_recycler_view,horizontal_recycler_view_minute;
    LinearLayoutManager horizontalLayoutManagaer,horizontalLayoutManagaer_minute;
    HorizontalAdapter horizontalAdapter;



    int intNext=  0;


    int iyyyy=2015,imm=1,idd=1;
    int iCyyyy=2015,iCmm=1,iCdd=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        initTime();
    }





    private void init() {
        Button todaysDate = (Button) findViewById(R.id.today);
        Button selectedDate = (Button) findViewById(R.id.selectedDateButton);
        Button startDate = (Button) findViewById(R.id.startDate);
         btnNext = (Button) findViewById(R.id.btnNext);
         btnPrev = (Button) findViewById(R.id.btnPrev);
        tvDetails = (TextView)findViewById(R.id.tvDetails);

        tvDateDetails = (TextView)findViewById(R.id.tvDateDetails);
        tvMinuteDetails= (TextView)findViewById(R.id.tvMinuteDetails);

        weekCalendar = (WeekCalendar) findViewById(R.id.weekCalendar);

        setGetDateTime(new DateTime());

        weekCalendar.setSelectedDate(getDateTime);

        String convertedDate = "";
        try
        {
            DateTime firstDayOfTheWeek = getGetDateTime();
            convertedDate = firstDayOfTheWeek.getDayOfMonth() +"-"+firstDayOfTheWeek.getMonthOfYear() +"-"+ firstDayOfTheWeek.getYear();
            Log.i("==Parsting date===","----"+convertedDate+"---");
//            tvDetails.setText(""+convertedDate);

            tvDetails.setText(getMonthName(firstDayOfTheWeek.getMonthOfYear())+" "+firstDayOfTheWeek.getYear());

            // for the changes able or select able date
            iyyyy = firstDayOfTheWeek.getYear();
            imm = firstDayOfTheWeek.getMonthOfYear();
            idd = firstDayOfTheWeek.getDayOfMonth();

            // for the current date
            iCyyyy = firstDayOfTheWeek.getYear();
            iCmm = firstDayOfTheWeek.getMonthOfYear();
            iCdd = firstDayOfTheWeek.getDayOfMonth();


            getDayName(iyyyy,imm,idd);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        todaysDate.setText(new DateTime().toLocalDate().toString() + " (Reset Button)");
        selectedDate.setText(new DateTime().plusDays(50).toLocalDate().toString()
                + " (Set Selected Date Button)");
        startDate.setText(new DateTime().plusDays(7).toLocalDate().toString()
                + " (Set Start Date Button)");


        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
              //  Toast.makeText(MainActivity.this, "You Selected " + dateTime.toString(), Toast.LENGTH_SHORT).show();
                try
                {
                    setGetDateTime(dateTime);
                    weekCalendar.setSelectedDate(getGetDateTime());

                    setTextData();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Log.i("=>>ccc click <<=","==firstDayOfTheWeek="+dateTime);
                Log.i("=>>ccc click<<=","Week changed: " + dateTime +" Forward: " + false);
            }

        });
        weekCalendar.setOnWeekChangeListener(new OnWeekChangeListener() {
            @Override
            public void onWeekChange(DateTime firstDayOfTheWeek, boolean forward) {

                try
                {
                    setGetDateTime(firstDayOfTheWeek);
                  //  weekCalendar.setSelectedDate(firstDayOfTheWeek);
                    setTextData();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                Log.i("=>>www scroll<<=","==firstDayOfTheWeek="+firstDayOfTheWeek);
                Log.i("=>>wwww scroll<<=","Week changed: " + firstDayOfTheWeek +" Forward: " + forward);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNextMonth();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrevMonth();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_github){
            openGithubRepo();
        }
        return true;
    }


    public void setGetDateTime(DateTime getDateTime2) {
        getDateTime = getDateTime2;

        Log.i("==Parsting ===","--getDateTime2--"+getDateTime2.getMonthOfYear()+"---");
        Log.i("==Parsting date===","--getDateTime--"+getDateTime.getMonthOfYear()+"---");
    }

    public DateTime getGetDateTime() {
        return getDateTime;
    }




    private void openGithubRepo() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/prashant31191/Week-Calendar"));
        startActivity(intent);
    }

    public void onNextClick(View veiw) {
        weekCalendar.moveToNext();
    }


    public void onPreviousClick(View view) {
        weekCalendar.moveToPrevious();
    }

    public void onResetClick(View view) {
        weekCalendar.reset();

    }
    public void onSelectedDateClick(View view){
       // weekCalendar.setSelectedDate(new DateTime().plusDays(50));

    }
    public void onStartDateClick(View view){
       // weekCalendar.setSelectedDate(new DateTime().plusDays(7));
    }

    public void  getPrevMonth()
    {
        try
        {
            weekCalendar.setSelectedDate(getGetDateTime().minusMonths(1));
            setGetDateTime(getGetDateTime().minusMonths(1));
            setTextData();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void  getNextMonth()
    {
        try
        {
            weekCalendar.setSelectedDate(getGetDateTime().plusMonths(1));
            setGetDateTime(getGetDateTime().plusMonths(1));
            setTextData();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private  void setTextData()
    {
        try {
            String convertedDate ="";

            DateTime firstDayOfTheWeek = getGetDateTime();
            convertedDate = firstDayOfTheWeek.getDayOfMonth() + "-" + firstDayOfTheWeek.getMonthOfYear() + "-" + firstDayOfTheWeek.getYear();
            Log.i("==Parsting date===", "----" + convertedDate + "---");
            tvDetails.setText(getMonthName(firstDayOfTheWeek.getMonthOfYear())+" "+firstDayOfTheWeek.getYear());

            iyyyy = firstDayOfTheWeek.getYear();
            imm = firstDayOfTheWeek.getMonthOfYear();
            idd = firstDayOfTheWeek.getDayOfMonth();

            getDayName(iyyyy,imm,idd);

            if (isAfterToday(iyyyy, imm, idd)) {
                Log.i("==000==", "==isAfterToday - true=");
                btnPrev.setEnabled(true);
            } else {
                Log.i("==000==", "==isAfterToday - false=");
                Toast.makeText(MainActivity.this, "Invalid date. \nPlease select feature date time." , Toast.LENGTH_SHORT).show();
                btnPrev.setEnabled(false);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public boolean isAfterToday(int year, int month, int day)
    {
        String d1;
        String d2;

        d1 =iCyyyy+"-"+iCmm+"-"+iCdd;
        d2 = year+"-"+month+"-"+day;

        return isCheckDates(d1,d2);
    }


    public  SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");
    public boolean isCheckDates(String d1,String d2)
    {
        boolean b = false;
        try {
            if(dfDate.parse(d1).before(dfDate.parse(d2)))
            {
                b = true;//If start date is before end date
            }
            else if(dfDate.parse(d1).equals(dfDate.parse(d2)))
            {
                b = true;//If two dates are equal
            }
            else
            {
                b = false; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }



   public static String getMonthName(int month){
        switch(month)
        {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return "January";
    }


    private String getDayName(int yyyy,int mm,int dd)
    {
        String strData="1 Jan Mon",dayOfWeek = "Mon",monthOfYear = "Jan";
        try {
            /*// Assuming that you already have this.
            int year = 2011;
            int month = 7;
            int day = 22;*/


            // First convert to Date. This is one of the many ways.
            String dateString = String.format("%d-%d-%d", yyyy, mm, dd);
            Date date = new SimpleDateFormat("yyyy-M-d").parse(dateString);

            // Then get the day of week from the Date based on specific locale.
            //dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);  // full day name
            dayOfWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(date); // first 3 latter day name
            monthOfYear = new SimpleDateFormat("MMMM", Locale.ENGLISH).format(date); // first 3 latter month name

            strData = dd + " "+ monthOfYear +" "+dayOfWeek;
            System.out.println("=Day Name-->==="+dayOfWeek ); // Friday

            System.out.println("=Data=="+strData);
            tvDateDetails.setText(strData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return strData;
    }










class TimeHourModel
{
    String strHour="1";
    String strHourAM="AM";
    boolean blnSelected = false;
}





    class TimeMinuteModel
    {
        String strHour="1";
        String strMinute="1";
        String strHourAM="AM";
        boolean blnSelected = false;
    }


    private void initTime()
    {
         horizontal_recycler_view = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        horizontal_recycler_view_minute = (RecyclerView) findViewById(R.id.horizontal_recycler_view_minute);
        Calendar c = Calendar.getInstance();

        int Hr24=c.get(Calendar.HOUR_OF_DAY) -1;
        int Min=c.get(Calendar.MINUTE);


        ArrayList<TimeHourModel> arrayListTimeHourModel=new ArrayList<TimeHourModel>();
        int hour12 = 0;
       for(int i=0; i < 24;i++)
       {
           TimeHourModel timeHourModel = new TimeHourModel();


           if(hour12 > 11)
           {
               hour12 = 1;
           }
           else
           {
               if(i==0)
               {
                   hour12 = 12;
               }
               else {

                   if(i==1)
                   {
                       hour12 = 1;
                   }
                   else
                   {
                       hour12 = hour12 + 1;
                   }
               }

           }
           if(i > 11)
           {
               timeHourModel.strHourAM = "PM";
           }
           else
           {
               timeHourModel.strHourAM = "AM";
           }




           if((Hr24+1) == (i))
           {
               timeHourModel.blnSelected = true;
           }
           else
           {
               timeHourModel.blnSelected = false;
           }
           timeHourModel.strHour = ""+hour12;
           arrayListTimeHourModel.add(timeHourModel);

       }

         horizontalAdapter = new HorizontalAdapter(arrayListTimeHourModel);




        horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
        horizontal_recycler_view.setAdapter(horizontalAdapter);

        for(int iSelected = 0; iSelected < arrayListTimeHourModel.size();iSelected++)
        {
            if(arrayListTimeHourModel.get(iSelected).blnSelected == true)
            {
                if(iSelected > 1)
                {
                    horizontalAdapter.setSelected_main(iSelected-1);
                }

            }
        }



        horizontalLayoutManagaer_minute = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view_minute.setLayoutManager(horizontalLayoutManagaer_minute);


    }



    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private ArrayList<TimeHourModel>  horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTitle_a,tvTitle_b;
            public LinearLayout llTime;

            public MyViewHolder(View view) {
                super(view);
                tvTitle_a = (TextView) view.findViewById(R.id.tvTitle_a);
                tvTitle_b = (TextView) view.findViewById(R.id.tvTitle_b);
                llTime = (LinearLayout)view.findViewById(R.id.llTime);
            }
        }


        public HorizontalAdapter(ArrayList<TimeHourModel>  horizontalList) {
            this.horizontalList = horizontalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.raw_h_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tvTitle_a.setText(horizontalList.get(position).strHour);
            holder.tvTitle_b.setText(horizontalList.get(position).strHourAM);

            if(horizontalList.get(position).blnSelected == true)
            {
                holder.tvTitle_a.setSelected(true);
                holder.tvTitle_b.setSelected(true);
                holder.llTime.setSelected(true);

                setMinuteAdapter(horizontalList.get(position).strHour,horizontalList.get(position).strHourAM);
            }
            else
            {
                holder.tvTitle_a.setSelected(false);
                holder.tvTitle_b.setSelected(false);
                holder.llTime.setSelected(false);
            }

            holder.llTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i=0;i<horizontalList.size();i++)
                    {
                        horizontalList.get(i).blnSelected = false;
                    }
                    horizontalList.get(position).blnSelected = true;

                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }

        public  void setSelected_main(int pos)
        {
            horizontal_recycler_view.scrollToPosition(pos);
            notifyDataSetChanged();

        }
    }







    private  void setMinuteAdapter(String strHour,String strAMPM)
    {

        ArrayList<TimeMinuteModel>  arrayListTimeMinuteModel = new ArrayList<>();


        for(int iMinute = 0;iMinute <60; iMinute++)
        {
            if(iMinute %5 == 0)
            {
                TimeMinuteModel timeMinuteModel = new TimeMinuteModel();
                timeMinuteModel.strHour = strHour;
                timeMinuteModel.strHourAM = strAMPM;

                if(iMinute <= 9)
                {
                    timeMinuteModel.strMinute = "0"+iMinute;
                }
                else
                {
                    timeMinuteModel.strMinute = ""+iMinute;
                }

                if(iMinute == 0)
                {
                    timeMinuteModel.blnSelected = true;
                }
                else
                {
                    timeMinuteModel.blnSelected = false;
                }


                arrayListTimeMinuteModel.add(timeMinuteModel);
            }

        }



        HorizontalMinuteAdapter horizontalMinuteAdapter = new HorizontalMinuteAdapter(arrayListTimeMinuteModel);
        horizontal_recycler_view_minute.setAdapter(horizontalMinuteAdapter);
    }



    public class HorizontalMinuteAdapter extends RecyclerView.Adapter<HorizontalMinuteAdapter.MyViewHolder> {

        private ArrayList<TimeMinuteModel>  horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTitle_a,tvTitle_b;
            public LinearLayout llTime;

            public MyViewHolder(View view) {
                super(view);
                tvTitle_a = (TextView) view.findViewById(R.id.tvTitle_a);
                tvTitle_b = (TextView) view.findViewById(R.id.tvTitle_b);
                llTime = (LinearLayout)view.findViewById(R.id.llTime);
            }
        }


        public HorizontalMinuteAdapter(ArrayList<TimeMinuteModel>  horizontalList) {
            this.horizontalList = horizontalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.raw_h_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tvTitle_a.setText(horizontalList.get(position).strHour + ":" + horizontalList.get(position).strMinute);
            holder.tvTitle_b.setText(horizontalList.get(position).strHourAM);

            if(horizontalList.get(position).blnSelected == true)
            {
                holder.tvTitle_a.setSelected(true);
                holder.tvTitle_b.setSelected(true);
                holder.llTime.setSelected(true);

                tvMinuteDetails.setText(horizontalList.get(position).strHour + ":" + horizontalList.get(position).strMinute +" "+horizontalList.get(position).strHourAM);
            }
            else
            {
                holder.tvTitle_a.setSelected(false);
                holder.tvTitle_b.setSelected(false);
                holder.llTime.setSelected(false);
            }

            holder.llTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i=0;i<horizontalList.size();i++)
                    {
                        horizontalList.get(i).blnSelected = false;
                    }
                    horizontalList.get(position).blnSelected = true;

                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }














}
