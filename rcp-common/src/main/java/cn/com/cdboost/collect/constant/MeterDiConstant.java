package cn.com.cdboost.collect.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实时抄表，设备DI常量
 */
public class MeterDiConstant {

    /**
     * 普通用户，13表抄表di对应关系
     */
    public enum CommonUser13 {
        // 远程费控表，电能费率数1 总
        PR0_ROMOTE_CONTROL_CNT_ONE("0001ff00",2,new Integer[] {1},"00010000"),
        // 远程费控表，电能费率数4 总
        PR0_ROMOTE_CONTROL_CNT_FOUR("0001ff00",2,new Integer[] {4},"0001ff00"),
        // 远程费控表，电能费率数1,4 月冻结
        MONTH_FRONZE_ROMOTE_CONTROL("00010001",2,new Integer[] {1,4},"00010001"),
        // 远程费控表，电能费率数1,4 日冻结
        DAY_FRONZE_ROMOTE_CONTROL("05060101",2,new Integer[] {1,4},"05060101"),
        // 本地费控表，电能费率数1 总
        PR0_LOCAL_CONTROL_CNT_ONT("0001ff00",1,new Integer[] {1},"00010000"),
        // 本地费控表，电能费率数4 总
        PR0_LOCAL_CONTROL_CNT_FOUR("0001ff00",1,new Integer[] {4},"0001ff00"),
        // 本地费控表，电能费率数1,4 剩余金额
        BALANCE_LOCAL_CONTROL("00900200",1,new Integer[] {1,4},"00900200"),
        // 本地费控表，电能费率数1,4 月冻结
        MONTH_FRONZE_LOCAL_CONTROL("00010001",1,new Integer[] {1,4},"00010001"),
        // 本地费控表，电能费率数1,4 日冻结
        DAY_FRONZE_LOCAL_CONTROL("05060101",1,new Integer[] {1,4},"05060101");

        /**
         * 前端传给我的抄表类型
         */
        private String type;

        /**
         * 费控类型
         */
        private Integer localControl;

        /**
         * 电能费率个数
         */
        private Integer[] commFactorCntArray;

        /**
         * 对应的di，多个以逗号分隔
         */
        private String diArray;

        CommonUser13(String type, Integer localControl, Integer[] commFactorCntArray, String diArray) {
            this.type = type;
            this.localControl = localControl;
            this.commFactorCntArray = commFactorCntArray;
            this.diArray = diArray;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getLocalControl() {
            return localControl;
        }

        public void setLocalControl(Integer localControl) {
            this.localControl = localControl;
        }

        public Integer[] getCommFactorCntArray() {
            return commFactorCntArray;
        }

        public void setCommFactorCntArray(Integer[] commFactorCntArray) {
            this.commFactorCntArray = commFactorCntArray;
        }

        public String getDiArray() {
            return diArray;
        }

        public void setDiArray(String diArray) {
            this.diArray = diArray;
        }

        public static List<String> getDiList(String type,Integer localControl,Integer commFactorCnt) {
            List<String> result = new ArrayList<>();
            for (CommonUser13 commonUser13 : CommonUser13.values()) {
                Integer[] cntArray = commonUser13.getCommFactorCntArray();
                List<Integer> list = Arrays.asList(cntArray);
                if (commonUser13.getType().equals(type)
                        && commonUser13.getLocalControl().equals(localControl)
                        && list.contains(commFactorCnt)) {
                    result.add(commonUser13.getDiArray());
                    break;
                }
            }
            return result;
        }
    }

    /**
     * 重点用户，13表抄表di对应关系
     */
    public enum ImpUser13 {
        // 电能费率数1 总
        PR0_CNT_ONE("0001ff00",new Integer[] {1},"00010000"),
        // 电能费率数4 总
        PR0_CNT_FOUR("0001ff00",new Integer[] {4},"0001ff00"),
        // 电能费率数1,4 电流
        CURRENT("0202ff00",new Integer[] {1,4},"0202ff00"),
        // 电能费率数1,4 电压
        VOLTAGE("0201ff00",new Integer[] {1,4},"0201ff00"),
        // 电能费率数1,4 瞬时有功功率
        INSTANT_POWER("0203FF00",new Integer[] {1,4},"0203FF00");

        /**
         * 前端传给我的抄表类型
         */
        private String type;

        /**
         * 电能费率个数
         */
        private Integer[] commFactorCntArray;

        /**
         * 对应的di，多个以逗号分隔
         */
        private String diArray;

        ImpUser13(String type, Integer[] commFactorCntArray, String diArray) {
            this.type = type;
            this.commFactorCntArray = commFactorCntArray;
            this.diArray = diArray;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer[] getCommFactorCntArray() {
            return commFactorCntArray;
        }

        public void setCommFactorCntArray(Integer[] commFactorCntArray) {
            this.commFactorCntArray = commFactorCntArray;
        }

        public String getDiArray() {
            return diArray;
        }

        public void setDiArray(String diArray) {
            this.diArray = diArray;
        }

        public static List<String> getDiList(String type,Integer commFactorCnt) {
            List<String> result = new ArrayList<>();
            for (ImpUser13 impUser13 : ImpUser13.values()) {
                Integer[] cntArray = impUser13.getCommFactorCntArray();
                List<Integer> list = Arrays.asList(cntArray);
                if (impUser13.getType().equals(type) && list.contains(commFactorCnt)) {
                    result.add(impUser13.getDiArray());
                    break;
                }
            }
            return result;
        }
    }

    /**
     * 普通用户，97表抄表di对应关系
     */
    public enum CommonUser97 {
        // 总
        PR0("0001ff00","901f");

        /**
         * 前端传给我的抄表类型
         */
        private String type;

        /**
         * 对应的di，多个以逗号分隔
         */
        private String diArray;

        CommonUser97(String type, String diArray) {
            this.type = type;
            this.diArray = diArray;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDiArray() {
            return diArray;
        }

        public void setDiArray(String diArray) {
            this.diArray = diArray;
        }

        public static List<String> getDiList(String type) {
            List<String> result = new ArrayList<>();
            for (CommonUser97 commonUser97 : CommonUser97.values()) {
                if (commonUser97.getType().equals(type)) {
                    result.add(commonUser97.getDiArray());
                    break;
                }
            }
            return result;
        }
    }

    /**
     * 重点用户，97表抄表di对应关系
     */
    public enum ImpUser97 {
        // 总
        PR0("0001ff00",new String[] {"901f"}),
        // 电流,电流数据块B62f
        CURRENT("0202ff00",new String[] {"B62f"}),
        // 电压,电压数据块B61f
        VOLTAGE("0201ff00",new String[] {"B61f"}),
        // 瞬时有功功率 (瞬时有功功率B630,瞬时A相有功功率B631,瞬时B相有功功率B632,瞬时C相有功功率B633)
        INSTANT_POWER("0203FF00",new String[] {"B630","B631","B632","B633"});

        /**
         * 前端传给我的抄表类型
         */
        private String type;

        /**
         * 对应的di，多个以逗号分隔
         */
        private String[] diArray;

        ImpUser97(String type, String[] diArray) {
            this.type = type;
            this.diArray = diArray;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String[] getDiArray() {
            return diArray;
        }

        public void setDiArray(String[] diArray) {
            this.diArray = diArray;
        }

        public static List<String> getDiList(String type) {
            List<String> result = new ArrayList<>();
            for (ImpUser97 impUser97 : ImpUser97.values()) {
                if (impUser97.getType().equals(type)) {
                    String[] diArray = impUser97.getDiArray();
                    result.addAll(Arrays.asList(diArray));
                    break;
                }
            }
            return result;
        }
    }

    /**
     * 普通用户，红外协议抄表，di对应关系
     */
    public enum CommonUser4Red {
        // 正向有功总0169
        PR0("0001ff00","0169");

        /**
         * 前端传给我的抄表类型
         */
        private String type;

        /**
         * 对应的di，多个以逗号分隔
         */
        private String diArray;

        CommonUser4Red(String type, String diArray) {
            this.type = type;
            this.diArray = diArray;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDiArray() {
            return diArray;
        }

        public void setDiArray(String diArray) {
            this.diArray = diArray;
        }

        public static List<String> getDiList(String type) {
            List<String> result = new ArrayList<>();
            for (CommonUser4Red commonUser4Red : CommonUser4Red.values()) {
                if (commonUser4Red.getType().equals(type)) {
                    result.add(commonUser4Red.getDiArray());
                    break;
                }
            }
            return result;
        }
    }

    /**
     * 重点用户，红外协议抄表，di对应关系
     */
    public enum ImpUser4Red {
        // 正向有功总0169
        PR0("0001ff00",new String[] {"0169"}),
        // 电流 (A相电流E010,B相电流E011,C相电流E012)
        CURRENT("0202ff00",new String[] {"E010","E011","E012"}),
        // 电压 (A相电压E000,B相电压E001,C相电压E002)
        VOLTAGE("0201ff00",new String[] {"E000","E001","E002"}),
        // 瞬时有功功率 (瞬时有功功率E033,瞬时A相有功功率E030,瞬时B相有功功率E031,瞬时C相有功功率E032)
        INSTANT_POWER("0203FF00",new String[] {"E033","E030","E031","E032"});

        /**
         * 前端传给我的抄表类型
         */
        private String type;

        /**
         * 对应的di，多个以逗号分隔
         */
        private String[] diArray;

        ImpUser4Red(String type, String[] diArray) {
            this.type = type;
            this.diArray = diArray;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String[] getDiArray() {
            return diArray;
        }

        public void setDiArray(String[] diArray) {
            this.diArray = diArray;
        }

        public static List<String> getDiList(String type) {
            List<String> result = new ArrayList<>();
            for (ImpUser4Red impUser4Red : ImpUser4Red.values()) {
                if (impUser4Red.getType().equals(type)) {
                    String[] diArray = impUser4Red.getDiArray();
                    result.addAll(Arrays.asList(diArray));
                    break;
                }
            }
            return result;
        }
    }
}
