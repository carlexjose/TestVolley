package jose.carlex.testvolley;

/**
 * Created by Carlex on 5/2/2017.
 */
public class TutorPost {
    private String Date, Subject, Email, Price, Description, Status;

    public TutorPost(String Date, String Subject, String Email, String Price, String Description, String Status){

        this.setDate(Date);
        this.setSubject(Subject);
        this.setEmail(Email);
        this.setPrice(Price);
        this.setDescription(Description);
        this.setStatus(Status);
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
