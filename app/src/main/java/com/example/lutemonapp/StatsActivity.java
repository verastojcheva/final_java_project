package com.example.lutemonapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.enums.Anchor;
import com.anychart.enums.TooltipPositionMode;
import com.example.lutemonapp.models.Lutemon;
import com.example.lutemonapp.storage.Storage;
import com.anychart.data.Set;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private AnyChartView anyChartView;
    private RecyclerView recyclerView;
    private StatsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        anyChartView = findViewById(R.id.any_chart_view);
        recyclerView = findViewById(R.id.recyclerViewStats);

        setupGroupedBarChart();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StatsAdapter(Storage.getInstance().getHomeLutemons());
        recyclerView.setAdapter(adapter);
    }

    private void setupGroupedBarChart() {
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> lutemonStats = new ArrayList<>();
        List<Lutemon> lutemons = Storage.getInstance().getHomeLutemons();

        for (Lutemon l : lutemons) {
            lutemonStats.add(new LutemonStatsEntry(
                    l.getName(),
                    l.getTrainingSessions(),
                    l.getBattlesFought(),
                    l.getBattlesWon()
            ));
        }

        Set dataSet = Set.instantiate();
        dataSet.data(lutemonStats);

        com.anychart.data.Mapping training = dataSet.mapAs("{ x: 'x', value: 'training' }");
        com.anychart.data.Mapping battles = dataSet.mapAs("{ x: 'x', value: 'battles' }");
        com.anychart.data.Mapping wins = dataSet.mapAs("{ x: 'x', value: 'wins' }");

        cartesian.column(training).name("Training");
        cartesian.column(battles).name("Battles");
        cartesian.column(wins).name("Wins");

        cartesian.title("Lutemon Stats Overview");
        cartesian.yScale().minimum(0);
        cartesian.tooltip()
                .positionMode(TooltipPositionMode.POINT)
                .anchor(Anchor.CENTER_BOTTOM)
                .position("top");

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(14d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    // Custom data entry
    private static class LutemonStatsEntry extends ValueDataEntry {
        LutemonStatsEntry(String name, int training, int battles, int wins) {
            super(name, training);
            setValue("training", training);
            setValue("battles", battles);
            setValue("wins", wins);
        }
    }
}
