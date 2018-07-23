import javax.swing.*;
import java.awt.*;

public class JPanelIndexKeeper extends JPanel{
    private int index;

    public JPanelIndexKeeper(int n){
        super();
        this.index = n;
    }

    public Component add(Component jc){
        super.add(jc);
        return jc;
    }

    public int getIndex(){return this.index;}
    public void setIndex(int i){this.index = i;}
}
