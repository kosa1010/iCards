package com.example.kosa1010.icards;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosa1010.icards.fragments.AddCardFragment;
import com.example.kosa1010.icards.fragments.DeleteCardFragment;
import com.example.kosa1010.icards.fragments.EditCardFragment;
import com.example.kosa1010.icards.fragments.HomeFragment;
import com.example.kosa1010.icards.controllers.CircleTransform;
import com.example.kosa1010.icards.controllers.GetGoogleInf;
import com.example.kosa1010.icards.fragments.ManageNotesFragment;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    FragmentManager fm;
    boolean add = false;
    boolean doubleBackToExitPressedOnce = false;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fm = getFragmentManager();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
        fab.setVisibility(View.GONE);
//            @Override
//            public void onClick(View view) {
//                FloatingActionButton fabb = fab;
//                clickFAB(fabb);
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        cardsList = (ListView) findViewById(R.id.lvCards);
//        cardsList.setAdapter(new CardsListAdapter());
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Twoje karty");

        View headerView = navigationView.getHeaderView(0);
        ImageView imgProfile = (ImageView) headerView.findViewById(R.id.avatar);
        TextView email = (TextView) headerView.findViewById(R.id.userEmail);
        TextView samText = (TextView) headerView.findViewById(R.id.user);
        email.setText(getUsername());


        Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null);
//        startActivityForResult(googlePicker, RESULT_OK);
        GetGoogleInf googleInfo = new GetGoogleInf();
        googleInfo.execute(getUsername());
        String s = "";
        try {
            s = googleInfo.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String[] accountInfo = getInfo(s);
        samText.setText(accountInfo[0]);
        imgProfile.setImageResource(R.mipmap.user);
        if (isOnline()) {
            imgProfile = (ImageView) headerView.findViewById(R.id.avatar);
            URL imageurl = getImage(accountInfo[1]);
            Picasso.with(this) // Context
                    .load(String.valueOf(imageurl)) // URL or file
                    .resize(120, 120)
                    .transform(new CircleTransform())
                    .into(imgProfile);
        }
        mainActivity = this;
        fm.beginTransaction().replace(R.id.content_frame, new HomeFragment())
                .addToBackStack("main").commit();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Naciśnij ponownie aby zamknąć aplikację", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        String title = "";
//        FragmentManager fm = getFragmentManager();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            add = true;
//            title = "Karta briju";
//            fm.beginTransaction().replace(R.id.content_frame, new ShowCardFragment()).commit();
//            return true;
//        }
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(title);
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title = getString(R.string.app_name);
        FragmentManager fm = getFragmentManager();
        if (id == R.id.nav_add) {
            add = true;
            title = "Dodawanie karty";
            fm.beginTransaction().replace(R.id.content_frame, new AddCardFragment()).commit();
        } else if (id == R.id.nav_home) {
            fm.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
            title = "Twoje karty";
            add = false;
        } else if (id == R.id.nav_delete) {
            add = false;
            title = "Usuwanie karty";
            fm.beginTransaction().replace(R.id.content_frame, new DeleteCardFragment()).commit();
        } else if (id == R.id.nav_edit) {
            add = false;
            title = "Edycja karty";
            fm.beginTransaction().replace(R.id.content_frame, new EditCardFragment()).commit();
        } else {
            add = false;
            title = "Notatki";
            fm.beginTransaction().replace(R.id.content_frame, new ManageNotesFragment()).commit();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void displayView(int viewId) {
//
//        Fragment fragment = null;
//        String title = getString(R.string.app_name);
//
//
//        if (viewId == R.id.nav_add) {
//            fragment = new AddCardFragment();
//            title = "Dodawanie karty";
//        } else if (viewId == R.id.nav_delete) {
//            fragment = new DeleteCardFragment();
//            title = "Usówanie karty";
//        } else if (viewId == R.id.nav_edit) {
//            fragment = new EditCardFragment();
//            title = "Edycja karty";
//        }
////        switch (viewId) {
////            case R.id.nav_add:
////                fragment = new AddCardFragment();
////                title  = "Dodawanie karty";
////
////                break;
////            case R.id.nav_delete:
////                fragment = new DeleteCardFragment();
////                title = "Usówanie karty";
////                break;
////            case R.id.nav_edit:
////                fragment = new EditCardFragment();
////                title = "Edycja karty";
////                break;
//
////    }
//
//    if(fragment!=null)
//    {   FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.content_frame, fragment);
//        ft.commit();
//    }
//
//    // set the toolbar title
//    if(    getSupportActionBar()
//
//    !=null)
//
//    {
//        getSupportActionBar().setTitle(title);
//    }
//
//    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//    drawer.closeDrawer(GravityCompat.START);
//
//}

    public String getUsername() {
        AccountManager manager = AccountManager.get(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type
            // values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");
            if (parts.length > 0 && parts[0] != null)
                return email;
            else
                return null;
        } else
            return null;
    }

    public String[] getInfo(String s) {
        String[] info = new String[3];
        if (s.contains("<name>")) {
            int indexBegin = s.indexOf("<name>") + 6;
            int indexEnd = s.indexOf("</name>");
            info[0] = s.substring(indexBegin, indexEnd);
        }
        if (s.contains("<gphoto:thumbnail>")) {
            int indexBegin = s.indexOf("<gphoto:thumbnail>") + 18;
            int indexEnd = s.indexOf("</gphoto:thumbnail>");
            info[1] = s.substring(indexBegin, indexEnd);
        }
        return info;
    }

    public URL getImage(String s) {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

// Activate the new trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }

// And as before now you can use URL and URLConnection
        URL url = null; //Some instantiated URL object
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public void clickFAB(FloatingActionButton fab) {
        FragmentManager fm = getFragmentManager();
        MenuItem menuItem;
        if (add) {
            menuItem = (MenuItem) findViewById(R.id.nav_home);
            menuItem.setChecked(true);
//            onOptionsItemSelected(menuItem);
            fm.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
//                    fab.setImageDrawable(ContextCompat.getDrawable(getResources().getDrawable(R.drawable.ic_home_black_24dp));
//            getSupportActionBar().setTitle("Twoje karty");
//            FragmentManager fm = getFragmentManager();
//            fm.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
            add = true;
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_manage));
        } else {
            menuItem = (MenuItem) findViewById(R.id.nav_add);
            menuItem.setChecked(true);
//            onOptionsItemSelected(menuItem);
            fm.beginTransaction().replace(R.id.content_frame, new AddCardFragment()).commit();
//            getSupportActionBar().setTitle("Dodawanie karty");
//            FragmentManager fm = getFragmentManager();
//            fm.beginTransaction().replace(R.id.content_frame, new AddCardFragment()).commit();
            add = false;
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_black_24dp));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        AddCardFragment.codeContent = scanningResult.getContents();
        AddCardFragment.codeFormat = scanningResult.getFormatName();
    }
}