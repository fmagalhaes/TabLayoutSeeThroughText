package pt.fmagalhaes.tablayoutseethroughtext;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import pt.fmagalhaes.tablayoutseethroughtextlib.AttrUtils;
import pt.fmagalhaes.tablayoutseethroughtextlib.TabLayout.TabLayoutSeeThroughText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayoutSeeThroughText tabLayoutPlus = (TabLayoutSeeThroughText) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        if(getSupportActionBar() != null)
            getSupportActionBar().setElevation(0f);
        ViewCompat.setElevation(tabLayoutPlus, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));

        ViewPagerSimpleAdapter adapter = new ViewPagerSimpleAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayoutPlus.setDistributeEvenly(true);

        tabLayoutPlus.setNotSelectedTextColor(Color.parseColor("#BBBBBB"));
        tabLayoutPlus.setCustomTabColorizer(new TabLayoutSeeThroughText.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.RED;
            }

            @Override
            public int getTabColor(int position) {
                return AttrUtils.fetchPrimaryColor(MainActivity.this);
            }
        });

//        //Set tabs text typeface
//        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Corbel-Bold.ttf");
//        mTabLayoutPlus.setTypeface(tf);

        tabLayoutPlus.setViewPager(viewPager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ViewPagerSimpleAdapter extends FragmentStatePagerAdapter {

        public ViewPagerSimpleAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new BlankFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position % 3) {
                case 0:
                    return "Lorem";
                case 1:
                    return "ipsum";
                default:
                case 2:
                    return "dolor";
            }
        }
    }
}
