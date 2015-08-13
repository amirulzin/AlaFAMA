package com.opensource.alafama.fama;

import com.opensource.alafama.fama.model.FAMAItem;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class FAMATableParserTest
{
    String urlLadang = "http://sdvi2.fama.net.my/price/direct/generate.asp?levelcd=04";
    String urlBorong = "http://sdvi2.fama.net.my/price/direct/generate.asp?levelcd=01";
    String urlRuncit = "http://sdvi2.fama.net.my/price/direct/generate.asp?levelcd=03";
    String urlDefiniteLadang = "http://sdvi2.fama.net.my/price/direct/price/daily_commodityRpt.asp?Pricing=A&LevelCd=04&PricingDt=2015/8/13&PricingDtPrev=2015/8/11";

    @Test
    public void testParseHTML() throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        List<String> urls = new ArrayList<>();
        urls.add(urlLadang);
        urls.add(urlBorong);
        urls.add(urlRuncit);

        final CountDownLatch latch = new CountDownLatch(urls.size());
        for (final String url : urls)
        {
            final long startTime = System.currentTimeMillis();
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
                    long responseTime = System.currentTimeMillis();
                    boolean succeed = false;
                    try
                    {
                        final long parseStart = System.currentTimeMillis();
                        HashMap<String, List<FAMAItem>> outmap = FAMATableParser.parseHTML(response.body().string());

                        final long parseEnd = System.currentTimeMillis();

                        log(url);
                        log(String.format("Response time: %d ms  Parse time: %d ms", responseTime - startTime, parseEnd - parseStart));

                        for (Map.Entry<String, List<FAMAItem>> set : outmap.entrySet())
                        {
                            log(set.getKey() + " size: " + set.getValue().size());
                            log("Sample zero: " + set.getValue().get(0).toString());
                        }
                        succeed = true;
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        latch.countDown();
                    }

                    if (!succeed) throw new AssertionError();
                }
            });

        }

        latch.await();
    }

    private void log(String s)
    {
        System.out.println(s);
    }
}