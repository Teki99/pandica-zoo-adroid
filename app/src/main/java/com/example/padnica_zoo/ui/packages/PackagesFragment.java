package com.example.padnica_zoo.ui.packages;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.padnica_zoo.R;
import com.example.padnica_zoo.ui.cart.CartFragment;
import com.pandica_zoo.models.JsonFile;
import com.pandica_zoo.models.Package;
import com.pandica_zoo.models.User;
import com.pandica_zoo.models.UserList;
import com.pandica_zoo.utils.AssetsUtils;
import com.pandica_zoo.utils.TinyDB;

import java.util.List;

public class PackagesFragment extends Fragment {

    private PackagesViewModel viewModel;
    private JsonFile jsonFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_packages, container, false);
        viewModel = new PackagesViewModel(this.getActivity().getApplication());
        jsonFile = AssetsUtils.readJsonFromFile(this.getActivity());
        LinearLayout containerLayout = root.findViewById(R.id.packages);
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

        //DESCRIPTION
        TextView description = new TextView(application);
        description.setText(pack.getDescription());
        description.setId(View.generateViewId());
        description.setTextColor(ContextCompat.getColor(application, R.color.white));
        description.setTypeface(customTypeface);

        RelativeLayout.LayoutParams descriptionParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        descriptionParams.addRule(RelativeLayout.BELOW,price.getId());
        descriptionParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        descriptionParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        descriptionParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);

        //ADD TO CART BUTTON
        Button addToCartButton = new Button(this.getActivity().getApplication());
        addToCartButton.setBackgroundColor(ContextCompat.getColor(application, R.color.light_green));
        addToCartButton.setTextColor(ContextCompat.getColor(application, R.color.white));
        addToCartButton.setText(R.string.add_to_cart_button);
        addToCartButton.setTypeface(customTypeface);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add item to cart (add to packages of user)
                //get user id from shared preference
                SharedPreferences sharedPreferences = application.getSharedPreferences("logged", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "none"); //I don't know what to put in default
                if(username != "none") //someone is logged in
                {
                    //get jsonFile
                    jsonFile = AssetsUtils.readJsonFromFile(application);
                    List<User> users = jsonFile.getUsersList().getUsers();
                    //add to user packages
                    for(int i=0;i<users.size();i++)
                    {
                        if(users.get(i).getUsername().equals(username)) //the logged user
                        {
                            //add to his packages
                            users.get(i).getPackages().add(pack);
                        }
                    }

                    UserList userList = new UserList();
                    userList.setUsers(users);
                    jsonFile.setUsersList(userList);

                    //write to json
                    AssetsUtils.updateJsonFile(jsonFile,application);
                }
                // Create and show the AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Alert Dialog Example")
                        .setMessage("This is a simple AlertDialog.")
                        .setPositiveButton(R.string.go_to_cart, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //navigate to cart
                                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_home).navigate(R.id.action_nav_packages_to_nav_cart);
                            }
                        })
                        .setNegativeButton(R.string.continue_shopping, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Close the dialog
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        addToCartButton.setId(View.generateViewId());
        RelativeLayout.LayoutParams addToCartButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        addToCartButtonParams.addRule(RelativeLayout.BELOW,description.getId());
        addToCartButtonParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addToCartButtonParams.topMargin = getResources().getDimensionPixelSize(R.dimen.top_margin);
        addToCartButtonParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_margin);


        relativeLayout.addView(name, nameParams);
        relativeLayout.addView(price, priceParams);
        relativeLayout.addView(description, descriptionParams);
        relativeLayout.addView(addToCartButton, addToCartButtonParams);
        relativeLayout.setLayoutParams(layoutParams);

        return relativeLayout;
    }
}
