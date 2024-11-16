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
        this.hitbox.x = 3;
        this.hitbox.y = 9;
        this.hitbox.width = 42;
        this.hitbox.height = 33;
    }
}
