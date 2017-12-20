package bioLib.test.phobia3.model;

import java.io.Serializable;

/**
 * Created by ricardo-abreu on 12/4/2017.
 */

public class VJRecord implements Serializable {

    private int id;
    //private String ecg;
    //private String author;
    private int hr;
    //private Float rr;
    private String time;
    private int session;

    public VJRecord(){}

    //public VJRecord(String ecg, Float hr, Float rr, String author) {
    //public VJRecord(String ecg, Float hr, Float rr) {
    //public VJRecord(String time, Float hr) {
    public VJRecord(int hr, int session){
        super();
        //this.ecg = ecg;
        //this.time=time;
        this.hr=hr;
        //this.rr=rr;
        this.session=session;
        //this.author = author;
    }

    //getters & setters

    @Override
    public String toString() {
        //return "VJRecord [id=" + id + ", ecg=" + ecg + ", author=" + author + "]";
        //return "VJRecord [id=" + id + ", ecg=" + ecg + ", hr=" + hr +", rr=" + rr +", author=" + author + "]";
        //return "VJRecord [id=" + id + ", ecg=" + ecg + ", hr=" + hr +", rr=" + rr + "]";
        return "VJRecord [id=" + id + ", time=" + time +", hr=" + hr + ", session="+session+"]";
    }



       // getter methods
       //public String getECG(){return this.ecg;}
       //public String getAuthor(){return this.author;}
    public int getId(){
        return this.id;
    }
    public int getHR(){
        return this.hr;
    }
    public String getTime(){return this.time;}
    //public Float getRR(){return this.rr;}
    public int getSession(){return this.session;}


    // setter methods
   // public void setECG(String ecg){this.ecg=ecg;}
    //public void setAuthor(String author){this.author=author;}
    public void setId(int id){
        this.id=id;
    }
    public void setHR(int hr){
        this.hr=hr;
    }
    //public void setRR(Float rr){this.rr=rr;}
    public void setTime(String time){this.time=time;}
    public void setSession(int session){this.session=session;}



}
