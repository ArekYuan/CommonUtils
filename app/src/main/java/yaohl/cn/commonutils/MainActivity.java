package yaohl.cn.commonutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView textBtn, textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textBtn = (TextView) findViewById(R.id.textBtn);
        textContent = (TextView) findViewById(R.id.textContent);

        textBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                initDataFromNet();
            }
        });
    }

    private void initDataFromNet()
    {
        String serverUrl = "api.szzc.com/api/web/index.php/markets";

    }
}
