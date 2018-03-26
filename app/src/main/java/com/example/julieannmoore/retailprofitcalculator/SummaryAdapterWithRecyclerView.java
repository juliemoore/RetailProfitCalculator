package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mData.Formula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julie Moore on 3/13/2018.
 */
public class SummaryAdapterWithRecyclerView extends RecyclerView.Adapter<SummaryAdapterWithRecyclerView.FormulaViewHolder> {

    private static final String TAG = SummaryAdapterWithRecyclerView.class.getSimpleName();

    public interface FormulaModifier{
        public void onFormulaSelected(int position);
    }

    private List<Formula> formulas;
    private Context context;
    private int position;

    private FormulaModifier formulaModifier;
    private FormulaViewHolder formulaViewHolder;
    private View view;

    public SummaryAdapterWithRecyclerView(Context context, List<Formula> formulas){
        this.context=context;
        this.formulas = formulas;
    }

    @Override
    public FormulaViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.layout_list_item,parent,false);
        formulaViewHolder = new FormulaViewHolder(view);
        position = formulaViewHolder.getAdapterPosition();
        formulas = new ArrayList();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This toggles the background color and text of the store list recyclerviews when clicked.
                int colorName = -1;
                int textColor = 1979711488;
                int color = formulaViewHolder.formulaTextView.getCurrentTextColor();

                if(color != textColor) {
                    view.setBackgroundColor(Color.parseColor("#FF6D00"));
                    formulaViewHolder.formulaNameTextView.setTextColor(Color.parseColor("#FFFFFF"));
                    formulaViewHolder.formulaTextView.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#00B0F0"));
                    formulaViewHolder.formulaNameTextView.setTextColor(textColor);
                    formulaViewHolder.formulaTextView.setTextColor(textColor);
                }

            }
        });
        return formulaViewHolder;
    }

    @Override
    public void onBindViewHolder(final FormulaViewHolder holder, int position) {
        final Formula formula = new Formula();
        holder.formulaNameTextView.setText(formula.getFormulaName());
        holder.formulaTextView.setText(formula.getFormulaId());
        position = formulaViewHolder.getAdapterPosition();


        Log.i(TAG,"onBindViewHolder invoked: "+position);
    }

    @Override
    public int getItemCount() {
        return formulas.size();
    }

    public int getPosition(){ return position; };

    class FormulaViewHolder extends RecyclerView.ViewHolder{
        public TextView formulaNameTextView;
        public TextView formulaTextView;

        public FormulaViewHolder(View view){
            super(view);
            formulaNameTextView = view.findViewById(R.id.textView2);
            formulaTextView = view.findViewById(R.id.textView1);
        }
    }
}
