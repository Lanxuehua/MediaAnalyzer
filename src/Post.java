/*
 * Class Post
 * @author: Xuehua Lan
 * @version JavaSE-17
*/ 

public class Post {
    private int postid;
    private int userid;
    private String content;
    private String author;  // come back to see if necessary when the userid is recorded, author is changeable
    private int likes;
    private int shares;
    private String dateTime;

    /**
     * Default Constructor
     * @param postid: a unique ID (int) associated with each post
     * @param userid: a unique ID (int) for user associated with each post
     * @param content: the content of the social media post (String), not contain any “,” symbol
     * @param author: an anonymous ID (String) of the post author
     * @param likes: number of likes (non-negative integer) of the post
     * @param shares: number of users (non-negative integer) that shared the post
     * @param dateTimes: the date and the time that the post was first posted, in the format of (DD/MM/YYYY HH:MM) 
     */
    public Post(int postid, int userid, String content, String author, int likes, int shares, String dateTime) {
        this.postid = postid;
        this.userid = userid;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
        this.dateTime = dateTime;
    }
    
    // Getter of postid
    public int getPostid() {
        return this.postid;
    }
    
    // Setter of postid
    public void setPostid(int postid) {
        this.postid = postid;
      }
    
    // Getter of userid
    public int getUserid() {
        return this.userid;
    }
    
    // Setter of userid
    public void setUserid(int userid) {
        this.userid = userid;
      }

    // Getter of content
    public String getContent() {
        return this.content;
    }
    
    // Setter of content
    public void setContent(String content) {
        this.content = content;
      }

    // Getter of author
    public String getAuthor() {
        return this.author;
    }
    
    // Setter of author
    public void setAuthor(String author) {
        this.author = author;
      }
    
    // Getter of likes
    public int getLikes() {
        return this.likes;
    }
    
    // Setter of likes
    public void setLikes(int likes) {
        this.likes = likes;
      }
    
    // Getter of shares
    public int getShares() {
        return this.shares;
    }
    
    // Setter of shares
    public void setShares(int shares) {
        this.shares = shares;
      }
    
    // Getter of dateTime
    public String getDateTime() {
        return this.dateTime;
    }
    
    // Setter of shares
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
      }
    
}
