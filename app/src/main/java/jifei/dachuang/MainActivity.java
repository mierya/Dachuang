package jifei.dachuang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
{
    private DrawerLayout drawer;
    private TextView title;
    private BottomNavigationBar navBar;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        drawer=findViewById(R.id.drawer);
        title=findViewById(R.id.title);
        navBar=findViewById(R.id.navBar);
        toolbar=findViewById(R.id.toolbar);
        //Toolbar、ActionBar
        toolbar.setTitle("");//除去label指定的文字，自定义居中
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size);
        }

        //BottomNavigationBar底部导航
        navBar.addItem(new BottomNavigationItem(R.mipmap.discover,"发现")).
                addItem(new BottomNavigationItem(R.mipmap.album,"相册")).
                addItem(new BottomNavigationItem(R.mipmap.share,"分享")).initialise();
        navBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(int position)//未选中 -> 选中
            {
                //根据选中的Item更改Toolbar的标题与图标
                //没找到直接返回Item并获取title的方法，只能一次次自己写
                switch (position)
                {
                    case  0:
                        title.setText("发现");
                        toolbar.getMenu().findItem(R.id.camera).setVisible(true);
                    break;
                    case  1:
                        title.setText("相册");
                        try//莫名其妙会NullPointerException！
                        {
                            toolbar.getMenu().findItem(R.id.share).setVisible(true);
                        }
                        catch (NullPointerException e)
                        {

                        }
                    break;
                    case  2:
                        title.setText("分享");
                    break;
                }
            }

            @Override
            public void onTabUnselected(int position)//选中 -> 未选中
            {
                switch (position)
                {
                    case  0:
                        toolbar.getMenu().findItem(R.id.camera).setVisible(false);
                        break;
                    case  1:
                        toolbar.getMenu().findItem(R.id.share).setVisible(false);
                        break;
                    case  2:
                        break;
                }
            }

            @Override
            public void onTabReselected(int position)//选中 -> 选中
            {

            }
        });
        navBar.selectTab(1);//默认选中中间（相册）

        //NavigationView侧滑菜单
        NavigationView navView=findViewById(R.id.navView);
            //header部分
        View nav=navView.getHeaderView(0);//获取header
        CircleImageView profilePic=nav.findViewById(R.id.profilePic);
        profilePic.setImageResource(R.mipmap.head);//此处设置头像
        TextView name=nav.findViewById(R.id.name);
        name.setText("空");//此处设置用户名
        TextView bio=nav.findViewById(R.id.bio);
        bio.setText("77（セブンズ） ～And, two stars meet again～");//此处设置自我介绍
            //menu部分
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.exit:
                        finish();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.tbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
            break;
            case R.id.camera:

            break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        menu.getItem(0).setIcon(android.R.drawable.ic_menu_camera);
        toolbar.getMenu().findItem(R.id.camera).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
