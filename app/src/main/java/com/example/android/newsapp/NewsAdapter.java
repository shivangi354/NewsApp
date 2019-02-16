package com.example.android.newsapp;

/**
 * Created by Shivangi on 13-11-2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Tag for log messages
     **/
    private static final String LOG_TAG = NewsAdapter.class.getName();
    private Context context;

    NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }
        News currentNews = getItem(position);
        if (currentNews != null) {
            TextView contentTitle = listItemView.findViewById(R.id.title);
            String title = currentNews.getTitle();
            if (title != null) {
                contentTitle.setText(title);
            } else {
                contentTitle.setText(context.getString(R.string.no_title_found));
            }

            TextView contentSection = listItemView.findViewById(R.id.section);
            String section = currentNews.getSection();
            if (section != null) {
                contentSection.setText(section);
            } else {
                contentSection.setText(context.getString(R.string.no_section_found));
            }

            TextView contentPublishedDate = listItemView.findViewById(R.id.date);
            TextView contentPublishedTime = listItemView.findViewById(R.id.time);
            String currentDateAndTime = currentNews.getWebPublicationDateAndTime();
            if (currentDateAndTime != null) {
                try {
                    String date = getNewsPublicationDate(currentDateAndTime);
                    String time = getNewsPublicationTime(currentDateAndTime);
                    contentPublishedDate.setText(date);
                    contentPublishedTime.setText(time);
                    contentPublishedDate.setVisibility(View.VISIBLE);
                    contentPublishedTime.setVisibility(View.VISIBLE);
                } catch (ParseException e) {
                    Log.e(LOG_TAG, context.getString(R.string.problem_passing_date_and_time), e);
                }
            } else {
                contentPublishedDate.setText(context.getString(R.string.no_date_found));
                contentPublishedTime.setText(context.getString(R.string.no_time_found));
            }

            TextView authorsTextView = listItemView.findViewById(R.id.authors);
            ArrayList<String> authorsArray = currentNews.getAuthors();
            if (authorsArray == null) {
                authorsTextView.setText(context.getString(R.string.no_author_found));
            } else {
                StringBuilder authorString = new StringBuilder();
                for (int i = 0; i < authorsArray.size(); i++) {
                    authorString.append(authorsArray.get(i));
                    if ((i + 1) < authorsArray.size()) {
                        authorString.append(", ");
                    }
                }
                authorsTextView.setText(authorString.toString());
                authorsTextView.setVisibility(View.VISIBLE);
            }
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean showAuthorName = sharedPreferences.getBoolean(
                context.getString(R.string.author_name_key),
                Boolean.parseBoolean(context.getString(R.string.default_author_value))
        );
        TextView authorsTextView = listItemView.findViewById(R.id.authors);
        ArrayList<String> authorsArray = currentNews.getAuthors();
        if(showAuthorName){
            authorsTextView.setVisibility(View.VISIBLE);
            StringBuilder authorString = new StringBuilder();
            for (int i = 0; i < authorsArray.size(); i++) {
                authorString.append(authorsArray.get(i));
                if ((i + 1) < authorsArray.size()) {
                    authorString.append(", ");
                }
            }
            authorsTextView.setText(authorString.toString());
        }else{
            authorsTextView.setVisibility(View.GONE);
        }
        return listItemView;
    }

    private String getNewsPublicationDate(String currentDateAndTime) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.received_date_and_time_format));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDate = new SimpleDateFormat(context.getString(R.string.displayed_date_format));
        return sdfDate.format(sdf.parse(currentDateAndTime));
    }

    private String getNewsPublicationTime(String currentDateAndTime) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.received_date_and_time_format));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfTime = new SimpleDateFormat(context.getString(R.string.displayed_time_format));
        sdfTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdfTime.format(sdf.parse(currentDateAndTime));
    }
}