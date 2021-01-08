import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Test extends Thread{


    public static void main(String[] args) throws ParseException {

        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date jiezhi = null;
        System.out.println(jiezhi);

        Date finish = new Date();
        System.out.println(finish);

        if(finish.after(jiezhi)){
            System.out.println("no");
        }
        if(finish.before(jiezhi)){
            System.out.println("yes");
        }

    }
}
