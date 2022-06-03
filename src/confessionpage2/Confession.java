/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package confessionpage2;

/**
 *
 * @author ngsua
 */
public class Confession {
    
    String id;
    String confession;
    String replyid;
    String date;

    public Confession(String id, String confession, String replyid, String date) {
        this.id = id;
        this.confession = confession;
        this.replyid = replyid;
        this.date = date;
    }

    
    //getter and setter
    public String getConfession() {
        return confession;
    }

    public void setConfession(String confession) {
        this.confession = confession;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    
    
    
    
    
    
}
