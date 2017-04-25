package yaohl.cn.commonutils.ui.mvp.model;

import java.util.List;

/**
 * markets 返回结果
 * Created by asus on 2016/9/30.
 */

public class MarketsResp
{

    /**
     * code : 2000
     * message : 返回正确
     * data : {"markets":[{"market":"BTCCNY","before":"797955","current":793951,"buy":793951,"sell":794000,"vol":"1015575","market_lots_ratio":"0.00100000"},{"market":"ETCCNY","before":"874","current":878,"buy":877,"sell":879,"vol":"5452224","market_lots_ratio":"0.01000000"},{"market":"ETHCNY","before":"10592","current":10992,"buy":10992,"sell":10993,"vol":"12974428","market_lots_ratio":"0.00100000"},{"market":"ETPCNY","before":"130","current":125,"buy":119,"sell":124,"vol":"278902676","market_lots_ratio":"0.00100000"},{"market":"ZECCNY","before":"25782","current":24974,"buy":24944,"sell":25049,"vol":"3943123833","market_lots_ratio":"0.00000100"}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean
    {
        private List<MarketsBean> markets;

        public List<MarketsBean> getMarkets() {
            return markets;
        }

        public void setMarkets(List<MarketsBean> markets) {
            this.markets = markets;
        }

        public static class MarketsBean
        {
            /**
             * market : BTCCNY
             * before : 797955
             * current : 793951
             * buy : 793951
             * sell : 794000
             * vol : 1015575
             * market_lots_ratio : 0.00100000
             */

            private String market;
            private String before;
            private int current;
            private int buy;
            private int sell;
            private long vol;
            private double market_lots_ratio;

            public String getMarket() {
                return market;
            }

            public void setMarket(String market) {
                this.market = market;
            }

            public String getBefore() {
                return before;
            }

            public void setBefore(String before) {
                this.before = before;
            }

            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public int getBuy() {
                return buy;
            }

            public void setBuy(int buy) {
                this.buy = buy;
            }

            public int getSell() {
                return sell;
            }

            public void setSell(int sell) {
                this.sell = sell;
            }

            public long getVol() {
                return vol;
            }

            public void setVol(long vol) {
                this.vol = vol;
            }

            public double getMarket_lots_ratio() {
                return market_lots_ratio;
            }

            public void setMarket_lots_ratio(double market_lots_ratio) {
                this.market_lots_ratio = market_lots_ratio;
            }
        }
    }
}
