package edu.niu.z1885782.termsearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private String listTerm = "";
    private final String URL
            = "https://www.autosport.com/rss/f1/news/";
    private ListView listView;
    private ArrayList<Item> listItems;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        ParseTask task = new ParseTask(this);
        task.execute(URL);
        EditText search = (EditText) findViewById(R.id.search);
        Button searchBut = (Button) findViewById(R.id.button3);
        searchBut.setOnClickListener(new View.OnClickListener() {//navigation back to main activity
            @Override
            public void onClick(View v) {

                listTerm = search.getText().toString();
                displayList(listItems);

            }
        });

    }

    public void setList(ArrayList<Item> items)
    {
        listItems = items;
    }

    public void displayList(ArrayList<Item> items)
    {
        if (items != null)
        {
            // Build ArrayList of titles to display
            ArrayList<String> titles = new ArrayList<String>();
            for (Item item : items) {
                if (item.getTitle().toLowerCase().contains(listTerm.toLowerCase()))
                    titles.add(item.getTitle());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, titles);
            listView.setAdapter(adapter);
            ListItemHandler lih = new ListItemHandler();
            listView.setOnItemClickListener(lih);
        }
        else
            Toast.makeText(this, "Sorry - No data found",
                    Toast.LENGTH_LONG).show();
    }

    private class ListItemHandler implements AdapterView.OnItemClickListener
    {
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id)
        {
            Item selectedItem = listItems.get(position);
            Uri uri = Uri.parse(selectedItem.getLink());
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(browserIntent);
        }
    }
}