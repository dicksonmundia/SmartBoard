package DataObjects;

import org.w3c.dom.ranges.RangeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Quote {
    private ArrayList quote = new ArrayList<>(Arrays.asList(
            "Don't be pushed around by the fears in your mind. Be led by the dreams in your heart.― Roy T. Bennett, The Light in the Heart",
            "Live the Life of Your Dreams: Be brave enough to live the life of your dreams according to your vision and purpose instead of the expectations and opinions of others.Roy T. Bennett, The Light in the Heart",
            "It's not the load that breaks you down, it's the way you carry it.― Lou Holtz",
            "Pursue what catches your heart, not what catches your eyes.― Roy T. Bennett, The Light in the Heart"));


    //get a random quote
    public String getQuote() {
        String qte = "";
        int randomQuote = 1;
        try {
            randomQuote = ThreadLocalRandom
                    .current()
                    .nextInt(0, quote.size() );

            qte = (String) quote.get(randomQuote);

        } catch (ExceptionInInitializerError | RangeException | IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        return qte;
    }
}
