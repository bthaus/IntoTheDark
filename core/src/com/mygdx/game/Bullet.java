package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.css.Rect;



public class Bullet {
    Rectangle rect=new Rectangle();
    Texture text;
    int x,y,xd,yd,xf,yf;

    public Bullet( Rectangle hero, int xd, int yd) {

        text=new Texture(Gdx.files.internal("shuriken.png"));
        rect.width=50;
        rect.height=50;
        rect.x = hero.x+150;
        rect.y = hero.y+70;
        this.xf=xd;
        this.yf=yd;
        this.xd= (int) ((xd-rect.x)/10);
        this.yd=(yd-this.y)/10;
      /*  if(xd>x) this.xd=(xd-x)/10;
         else this.xd=(x-xd)/10;
        if(yd>y) this.yd=(yd-y)/10;
        else this.yd=(y-yd)/10;
*/

    }

    public boolean move(){
        rect.x+=xd;
        rect.y+=yd;

        return true;
    }
}
