package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_Key extends SuperObject {

    public Obj_Key() {
        name = "key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
