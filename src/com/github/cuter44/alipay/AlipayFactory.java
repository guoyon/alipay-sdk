package com.github.cuter44.alipay;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.MissingResourceException;

import com.github.cuter44.alipay.reqs.*;

/** 创建支付宝业务的工厂类
 */
public class AlipayFactory
{
    private static final String RESOURCE_ALIPAY_PROPERTIES = "/alipay.properties";

  // CONFIG
    protected Properties conf;

  // CONSTRUCT
    public AlipayFactory(Properties aConf) {
        this.conf = aConf;
    }

    /**
     * Default constructor which looking for a .properties resource in order below:
     * 1. System.getProperty("com.github.cuter44.alipay.alipay_properties")
     * 2. AlipayFactory.class.getResource("/alipay.properties")
     * Properties MUST stored in utf-8.
     */
    public AlipayFactory()
        throws MissingResourceException
    {
        String res = "";

        try
        {
            res = System.getProperty("com.github.cuter44.alipay.alipay_properties");
            res = res!=null ? res : RESOURCE_ALIPAY_PROPERTIES;

            this.conf = new Properties();
            this.conf.load(
                new InputStreamReader(
                    AlipayFactory.class.getResourceAsStream(res),
                    "utf-8"
            ));
        }
        catch (Exception ex)
        {
            MissingResourceException mrex = new MissingResourceException(
                "Failed to load conf resource.",
                AlipayFactory.class.getName(),
                res
            );
            mrex.initCause(ex);

            throw(mrex);
        }
    }

  // FACTORY
    public TradeCreateByBuyer newTradeCreateByBuyer()
    {
        return(
            new TradeCreateByBuyer(
                new Properties(this.conf)
        ));
    }
    public TradeCreateByBuyer newTradeCreateByBuyer(Properties p)
    {
        return(
            new TradeCreateByBuyer(
                buildConf(p, this.conf)
        ));
    }

    //public SendGoodsConfirmByPlatform newSendGoodsConfirmByUser()
    //{
        //return(
            //new SendGoodsConfirmByPlatform(
                //new Properties(this.conf)
        //));
    //}
    //public SendGoodsConfirmByPlatform newSendGoodsConfirmByUser(Properties p)
    //{
        //return(
            //new SendGoodsConfirmByPlatform(
                //buildConf(p, this.conf)
        //));
    //}

    public WapTradeCreateDirect newWapTradeCreateDirect()
    {
        return(
            new WapTradeCreateDirect(
                new Properties(this.conf)
        ));
    }
    public WapTradeCreateDirect newWapTradeCreateDirect(Properties p)
    {
        return(
            new WapTradeCreateDirect(
                buildConf(p, this.conf)
        ));
    }

  // MISC
    protected static Properties buildConf(Properties prop, Properties defaults)
    {
        Properties ret = new Properties(defaults);
        Iterator<String> iter = prop.stringPropertyNames().iterator();
        while (iter.hasNext())
        {
            String key = iter.next();
            ret.setProperty(key, prop.getProperty(key));
        }

        return(ret);
    }

    public static void main(String[] args)
    {
        System.out.println("Hello World!");
    }
}