package com.benz.all.mvp.music;


import com.benz.all.R;
import com.benz.all.mvp.MVPBaseFragment;
import com.socks.library.KLog;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MusicFragment extends MVPBaseFragment<MusicContract.View, MusicPresenter> implements MusicContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initViewsAndEvents() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Document document = GetDoc("http://www.vmovier.com/index/index/p/1", Connection.Method.GET);
                if (document != null) {
//                    KLog.e("document.title = " + document.title());
//                    KLog.e("document.head = " + document.head());
//                    KLog.xml("document.html = " + document.html());

                    Elements elements = document.select("div.index-main");

                    Elements lists = document.getElementsByTag("ul");
                    for(Element element : lists){
                        Elements li = element.getElementsByTag("li");
                        KLog.e("document.title==== " + li.select("a").attr("title"));
                        KLog.e("document.img==== " + li.select("a").select("img").attr("src"));

                        Elements time = li.select("span.film-time");
                        KLog.e("document.time==== " + time.text());
                    }


                }
            }
        }).start();
    }

        /**
         * 获取doc对象
         *
         * @param URL    链接
         * @param method 访问的方法MethodGet MethodPost
         * @return doc对象
         */

    private Document GetDoc(String URL, Connection.Method method) {
        Document mdoc = null;
        try {
            mdoc = Jsoup.connect(URL)
                    .timeout(10000)
                    .method(method)
//                    .userAgent(useragent)
                    .execute()
                    .parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mdoc;
    }
}