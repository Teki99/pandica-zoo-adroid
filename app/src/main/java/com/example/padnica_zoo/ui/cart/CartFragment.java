package com.example.padnica_zoo.ui.cart;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.padnica_zoo.R;
import com.example.padnica_zoo.databinding.ActivityHomeBinding;
import com.example.padnica_zoo.ui.packages.PackagesViewModel;
import com.google.android.material.navigation.NavigationView;
import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.models.Package;
import com.pandica_zoo.models.User;
import com.pandica_zoo.models.UserList;
import com.pandica_zoo.utils.AssetsUtils;

import java.util.List;

public class CartFragment extends Fragment {
    private CartViewModel viewModel;
    private JsonFile jsonFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        viewModel = new CartViewModel(this.getActivity().getApplication());

        jsonFile = AssetsUtils.readJsonFromFile(this.getActivity());
        LinearLayout containerLayout = root.findViewById(R.id.cart);
        for (int i=0;i<viewModel.getPackagesSize();i++) {
            RelativeLayout relativeLayout = createRelativeLayoutWithTextView(this.getActivity().getApplication(), viewModel.getPackageAtIndex(i));
            containerLayout.addView(relativeLayout);
        }
        return root;
    }

    private RelativeLayout createRelativeLayoutWithTextView(Application application, Package pack) {
        RelativeLayout relativeLayout = new RelativeLayout(application);
        Typeface customTypeface = ResourcesCompat.getFont(application, R.font.irish_grover);
        relativeLayout.setBackgroundColor(ContextCompat.getColor(application, R.color.dark_green));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                800,550
        );
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        //NAME
        TextView name = new TextView(application);
        name.setText(pack.getName());
        name.setId(View.generateViewId());
        name.setTextColor(ContextCompat.getColor(application, R.color.light_green));
        name.setBackgroundColor(ContextCompat.getColor(application, R.color.white));
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        name.setTypeface(customTypeface);

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

        //MINUS BUTTON
        Button minusButton = new Button(this.getActivity().getApplication());
        minusButton.setBackgroundColor(ContextCompat.getColor(application, R.color.white));
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
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        minusButtonParams.addRule(RelativeLayout.BELOW,price.getId());

        //!to be able to see it
        TextView quantity = new TextView(application);
        minusButtonParams.addRule(RelativeLayout.LEFT_OF,quantity.getId());
        //minusButtonParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        minusButtonParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        minusButtonParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);

        //QUANTITY
        //TextView quantity = new TextView(application);
        quantity.setText(""+pack.getQuantity()+"");
        quantity.setId(View.generateViewId());
        quantity.setBackgroundColor(ContextCompat.getColor(application, R.color.white));
        quantity.setTextColor(ContextCompat.getColor(application, R.color.light_green));
        quantity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        quantity.setTypeface(customTypeface);

        RelativeLayout.LayoutParams quantityParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        quantityParams.addRule(RelativeLayout.BELOW,price.getId());
        quantityParams.addRule(RelativeLayout.RIGHT_OF,minusButton.getId());
        Button plusButton = new Button(this.getActivity().getApplication());
        quantityParams.addRule(RelativeLayout.LEFT_OF,plusButton.getId());
        quantityParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        priceParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        priceParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);

        //PLUS BUTTON

        plusButton.setBackgroundColor(ContextCompat.getColor(application, R.color.white));
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
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        plusButtonParams.addRule(RelativeLayout.BELOW,price.getId());
        plusButtonParams.addRule(RelativeLayout.RIGHT_OF,quantity.getId());
        plusButtonParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        plusButtonParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        plusButtonParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);

        relativeLayout.addView(name, nameParams);
        relativeLayout.addView(price, priceParams);
        relativeLayout.addView(minusButton,minusButtonParams);
        relativeLayout.addView(quantity, quantityParams);
        relativeLayout.addView(plusButton, plusButtonParams);
        relativeLayout.setLayoutParams(layoutParams);

        return relativeLayout;
    }
}
