package com.example.padnica_zoo.ui.cart;

import android.app.Application;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.padnica_zoo.R;
import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.models.Package;
import com.pandica_zoo.utils.AssetsUtils;

public class CartFragment extends Fragment {
    private CartViewModel viewModel;
    private JsonFile jsonFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        viewModel = new CartViewModel(this.getActivity().getApplication());

        jsonFile = AssetsUtils.readJsonFromFile(this.getActivity());
        LinearLayout containerLayout = root.findViewById(R.id.cart);
        int totalPrice = 0;
        for (int i=0;i<viewModel.getPackagesSize();i++) {
            RelativeLayout relativeLayout = createRelativeLayoutWithTextView(this.getActivity().getApplication(), viewModel.getPackageAtIndex(i));
            containerLayout.addView(relativeLayout);
            totalPrice+=viewModel.getPackageAtIndex(i).getPrice()*viewModel.getPackageAtIndex(i).getQuantity();
        }

        //TOTAL
        TextView total = root.findViewById(R.id.total);
        total.setText("Total : "+totalPrice+" RSD");

        return root;
    }

    private RelativeLayout createRelativeLayoutWithTextView(Application application, Package pack) {
        Typeface customTypeface = ResourcesCompat.getFont(application, R.font.irish_grover);
        Drawable outlineLightGreen = ContextCompat.getDrawable(application, R.drawable.light_green_outline);

        RelativeLayout relativeLayout = new RelativeLayout(application);
        relativeLayout.setBackgroundResource(R.drawable.dark_green_outline);
        relativeLayout.setBackgroundTintList(ContextCompat.getColorStateList(application, R.color.dark_green));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                700,500
        );
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        //NAME
        TextView name = new TextView(application);
        name.setText(pack.getName());
        name.setId(View.generateViewId());
        name.setTextColor(ContextCompat.getColor(application, R.color.light_green));
        name.setBackground(outlineLightGreen);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        name.setTypeface(customTypeface);
        name.setPadding(20,10,20,10);
        // Set the background drawable for the view

        RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        nameParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        nameParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        nameParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        nameParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);

        //PRICE
        TextView price = new TextView(application);
        price.setText(""+pack.getPrice()+" RSD");
        price.setId(View.generateViewId());
        price.setTextColor(ContextCompat.getColor(application, R.color.white));
        price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        price.setTypeface(customTypeface);

        RelativeLayout.LayoutParams priceParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        priceParams.addRule(RelativeLayout.BELOW,name.getId());
        priceParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        priceParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        priceParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        priceParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);

        // Create a RelativeLayout to contain the buttons and quantity TextView
        RelativeLayout buttonContainer = new RelativeLayout(this.getActivity().getApplication());

        // Define layout parameters for the button container
        RelativeLayout.LayoutParams containerParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        containerParams.addRule(RelativeLayout.CENTER_HORIZONTAL); // Center horizontally
        containerParams.addRule(RelativeLayout.BELOW,price.getId());

        //MINUS BUTTON
        Button minusButton = new Button(this.getActivity().getApplication());
        minusButton.setBackground(outlineLightGreen);
        minusButton.setTextColor(ContextCompat.getColor(application, R.color.light_green));
        minusButton.setText("-");
        minusButton.setTypeface(customTypeface);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        minusButton.setId(View.generateViewId());
        RelativeLayout.LayoutParams minusButtonParams = new RelativeLayout.LayoutParams(
                100,
                100
        );
        minusButtonParams.addRule(RelativeLayout.BELOW,price.getId());
        //!to be able to see it
        TextView quantity = new TextView(application);
        minusButtonParams.addRule(RelativeLayout.LEFT_OF,quantity.getId());

        //QUANTITY
        //TextView quantity = new TextView(application);
        quantity.setText(""+pack.getQuantity()+"");
        quantity.setId(View.generateViewId());
        //quantity.setBackgroundColor(ContextCompat.getColor(application, R.color.white));
        quantity.setBackground(outlineLightGreen);
        quantity.setTextColor(ContextCompat.getColor(application, R.color.light_green));
        quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        quantity.setTypeface(customTypeface);
        quantity.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        RelativeLayout.LayoutParams quantityParams = new RelativeLayout.LayoutParams(
                100,
                100
        );
        quantityParams.addRule(RelativeLayout.BELOW,price.getId());
        quantityParams.addRule(RelativeLayout.RIGHT_OF,minusButton.getId());
        Button plusButton = new Button(this.getActivity().getApplication());
        quantityParams.addRule(RelativeLayout.LEFT_OF,plusButton.getId());
        quantityParams.leftMargin = 10;
        quantityParams.rightMargin = 10;

        //PLUS BUTTON
        //plusButton.setBackgroundColor(ContextCompat.getColor(application, R.color.white));
        plusButton.setBackground(outlineLightGreen);
        plusButton.setTextColor(ContextCompat.getColor(application, R.color.light_green));
        plusButton.setText("+");
        plusButton.setTypeface(customTypeface);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        plusButton.setId(View.generateViewId());
        RelativeLayout.LayoutParams plusButtonParams = new RelativeLayout.LayoutParams(
                100,
                100
        );
        plusButtonParams.addRule(RelativeLayout.BELOW,price.getId());
        plusButtonParams.addRule(RelativeLayout.RIGHT_OF,quantity.getId());

        buttonContainer.addView(minusButton, minusButtonParams );
        buttonContainer.addView(quantity, quantityParams );
        buttonContainer.addView(plusButton, plusButtonParams );

        relativeLayout.addView(name, nameParams);
        relativeLayout.addView(price, priceParams);
        relativeLayout.addView(buttonContainer, containerParams);
        relativeLayout.setLayoutParams(layoutParams);

        return relativeLayout;
    }
}
