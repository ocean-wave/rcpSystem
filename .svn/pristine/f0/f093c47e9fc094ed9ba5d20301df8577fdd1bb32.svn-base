package cn.com.cdboost.collect.enums;

/**
 * Created by Administrator on 2017/6/13 0013.
 * 指令发送代码
 */
public enum InstructCode {

    Success(1), // 指令发送成功
    Fail(0), // 发送失败
    NoAns(-99), // 前置服务不响应
    Busy(3), // 终端忙
    Offline(-1), // 终端离线
    Timeout(-101), // :超时
    JzqLogin(-102), // :登录集中器失败
    Other(-105), // 其他异常-105
    Run(101);// :正在执行中

    private int value;

    private InstructCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
