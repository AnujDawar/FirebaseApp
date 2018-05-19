package com.example.anujdawar.firebaseapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

// krna kya hai...  routes available hai bus-wise
// source and destination dali...

//  source ke according search kiya.. ki kitni

public class MainActivity extends AppCompatActivity
{

    TextView myText;
//    ListView mlistview;
//    String selectedState = "AvailableStates/";
//    String defaultURL = "https://fir-app-bde23.firebaseio.com/";
//    ProgressBar waitingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_graph);

        myText = (TextView) findViewById(R.id.textView);

        RouteGraph graph = new RouteGraph(5);

        graph.addEdge(0,1);
        graph.addEdge(1,2);
        graph.addEdge(0,3);
        graph.addEdge(2,3);
        graph.addEdge(3,4);
        graph.addEdge(4,0);
        graph.addEdge(4,1);

        graph.dfs(0);

        graph.printGraph();

//        init();
    }

//    private void init()
//    {
//        mlistview = (ListView) findViewById(R.id.mlistview);
//
//        refreshListview();
//
////        for(int ULink = 1; ULink <= 100; ULink++)
////            for(int x = 65, y = 66; x < 65+26; x++, y++)
////            {
////                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-app-bde23.firebaseio.com/bus routes/bus" + String.valueOf(ULink));
////                databaseReference.child(String.valueOf((char)x)).setValue(String.valueOf((char)y));
////            }
//
//        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedState = String.valueOf(mlistview.getItemAtPosition(i));
//                refreshListview();
//            }
//        });
//    }
//
//    private void refreshListview()
//    {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(defaultURL + selectedState);
//
//        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
//                this, String.class, android.R.layout.simple_list_item_1, databaseReference
//        )
//        {
//            @Override
//            protected void populateView(View v, String model, int position)
//            {
//                TextView textView = (TextView) v.findViewById(android.R.id.text1);
//                textView.setText(model);
//            }
//        };
//
//        mlistview.setAdapter(firebaseListAdapter);
//    }

    class RouteGraph
    {
        private LinkedList<Integer>[] adj;
        private boolean isMarked[];
        private int V;
        private int E;
        private int edgeTo[];

        RouteGraph(int V)
        {
            this.V = V;
            this.E = 0;
            this.isMarked = new boolean[this.V];
            this.edgeTo = new int[this.V];

            this.adj = new LinkedList[this.V];

            for(int i = 0; i < this.V; i++)
                this.adj[i] = new LinkedList<>();
        }

        Iterable<Integer> adj(int v)
        {
            return this.adj[v];
        }

        void addEdge(int a, int b)
        {
            this.adj[a].add(b);
            this.E++;
            this.edgeTo[a] = b;
        }

        void dfs(int v)
        {
            isMarked[v] = true;

            for(int i : adj(v))
                if(!isMarked[i])
                    dfs(i);
        }

        void printGraph()
        {
            String result = "";

            for(int i = 0; i < this.V; i++)
                for(int j : adj(i))
                    result += String.valueOf(i) + "-" + String.valueOf(j) + " : " + isMarked[j] + "\n";

            myText.setText(result);
        }
    }


}