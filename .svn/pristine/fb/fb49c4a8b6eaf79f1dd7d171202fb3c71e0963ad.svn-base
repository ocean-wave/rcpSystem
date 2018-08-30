package cn.com.cdboost.collect.enums;

/**
 * Created by Administrator on 2017/6/13 0013.
 * 指令AFN
 */
public enum InstructAFN {

    Confirm((byte) 0x00), // 确认否认
    Reboot((byte) 0x01), // 重启
    ConInit((byte) 0x02), // 初始化
    DownFile((byte) 0x03), // 下发资料
    RealCollect((byte) 0x04), // 实时采集
    OnOff((byte) 0x08), // 通断
    Recharge((byte) 0x09); //充值

    private byte value;

    private InstructAFN(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public String GetValueToStr() {
        return Byte.toString(value);
    }
}
