package beginner;

import com.sandwich.koan.Koan;

import static com.sandwich.util.Assert.assertTrue;
import static com.sandwich.util.Assert.fail;

public class AboutKoans {

    @Koan
    public void findAboutKoansFile() {
        assertTrue(true);
    }

    @Koan
    public void definitionOfKoanCompletion() {
        boolean koanIsComplete = true;
        if (!koanIsComplete) {
            fail("what if koanIsComplete variable was true?");
        }
    }

}
