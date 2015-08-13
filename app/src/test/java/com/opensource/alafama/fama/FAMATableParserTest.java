package com.opensource.alafama.fama;

import com.opensource.alafama.fama.model.FAMAItem;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class FAMATableParserTest
{

    @Test
    public void testParseHTML() throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        String url = "http://sdvi2.fama.net.my/price/direct/price/daily_commodityRpt.asp?Pricing=A&LevelCd=04&PricingDt=2015/8/13&PricingDtPrev=2015/8/11";
        final CountDownLatch latch = new CountDownLatch(1);
        Call call = client.newCall(new Request.Builder().get().url(url).build());
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                latch.countDown();
            }

            @Override
            public void onResponse(Response response) throws IOException
            {
                boolean succeed = false;
                try
                {

                    HashMap<String, List<FAMAItem>> outmap = FAMATableParser.parseHTML(response.body().string());
                    for (Map.Entry<String, List<FAMAItem>> set : outmap.entrySet())
                    {
                        log(set.getKey() + " size: " + set.getValue().size());
                        log("Sample zero: " + set.getValue().get(0).toString());
                    }
                    succeed = true;
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                finally
                {
                    latch.countDown();
                }

                if (!succeed) throw new AssertionError();
            }
        });

        latch.await();
    }

    private void log(String s)
    {
        System.out.println(s);
    }
}