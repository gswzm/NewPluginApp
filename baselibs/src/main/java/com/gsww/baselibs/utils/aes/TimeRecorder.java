package com.gsww.baselibs.utils.aes;

/**
 * 时间记录记录仪
 * @author  wuyy
 * @date 2018-5-25
 */
public class TimeRecorder {

    public static TimeRecorder start(){
        TimeRecorder recorder=new TimeRecorder();
        recorder.begin();
        return recorder;
    }

    private Long beginMillis;
    private Long endMillis;

    private TimeRecorder() {
    }

    private void begin(){
        this.beginMillis=System.currentTimeMillis();
    }

    public void end(){
        this.endMillis=System.currentTimeMillis();
    }

    public Long getRunMillis(){
         return endMillis-beginMillis;
    }
}
