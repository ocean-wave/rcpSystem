package cn.com.cdboost.collect.dto;

public class LineLossStatistics {

        private String meterElect;
        private String deviceElect;
        private String lossElect;
        private String lossRate="";

        public String getMeterElect() {
            return meterElect;
        }

        public void setMeterElect(String meterElect) {
            this.meterElect = meterElect;
        }

        public String getDeviceElect() {
            return deviceElect;
        }

        public void setDeviceElect(String deviceElect) {
            this.deviceElect = deviceElect;
        }

        public String getLossElect() {
            return lossElect;
        }

        public void setLossElect(String lossElect) {
            this.lossElect = lossElect;
        }

        public String getLossRate() {
            return lossRate;
        }

        public void setLossRate(String lossRate) {
            this.lossRate = lossRate;
        }
    }