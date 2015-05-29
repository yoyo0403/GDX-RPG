package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
/**
 * GDX-RPG Image组件
 * 允许开启抗锯齿、销毁的图形组件。
 *
 */
public class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image{
	
	public boolean visible=true;
	public boolean lazy=true;
	public Runnable loaded=()->{};
	Runnable run,drun;
	private int delaydbClickTime=30;
	public String texturePath;
	
	{
		addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(delaydbClickTime<30)
					if(drun!=null)
						drun.run();
				delaydbClickTime=0;
				return true;
			}
		});
	}
	
	public Image(String filename){
		super(new Texture(Gdx.files.internal(filename)));
		setAnti();
	}
	
	public Image onClick(Runnable r){
		run=r;
		return this;
	}
	
	public Image onDblClick(Runnable r){
		drun=r;
		return this;
	}
	
	public Image click(){
		run.run();
		return this;
	}
	
	
	public Image(NinePatch ninePatch){
		super(ninePatch);
	}
	
	public Image(Texture txt){
		super(txt);
		setAnti();
	}
	
	public Image(TextureRegion txt){
		super(txt);
		setAnti();
	}
	
	public Image(Sprite txt){
		super(txt);
		setAnti();
	}
	
	public Image() {
		super();
	}
	
	public Image(Image image) {
		super(image.getDrawable());
	}
	
	public Image(Drawable dr) {
		super(dr);
	}

	public Image(NinePatchDrawable ninePatchDrawable) {
		super(ninePatchDrawable);
	}

	public void draw(Batch sb){ 
		delaydbClickTime++;
		if(visible)
			this.draw(sb, 1);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		delaydbClickTime++;
		super.draw(batch, parentAlpha);
	}
	
	private void setAnti(){
		if(Setting.persistence.scaleAliasing)
			getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(x>0 && x<getWidth() && y>0 && y<getHeight())
					if(run!=null)
						run.run();
			}
		});
	}
	
	public Texture getTexture(){
		try{
			return ((TextureRegionDrawable)this.getDrawable()).getRegion().getTexture();
		}catch(Exception e){
			return Res.NO_TEXTURE;
		}
	}
	
	public TextureRegion getRegion(){
		return ((TextureRegionDrawable)this.getDrawable()).getRegion();
	}
	
	public Image dispose(){
		getTexture().dispose();
		return this;
	}
	
	public Image size(int width,int height){
		super.setSize(width, height);
		return this;
	}
	
	public Image scale(float s){
		super.setScale(s);
		return this;
	}
	
	public Image oranCenter(){
		setOrigin(0);
		return this;
	}
	
	public Image scaleX(float s){
		super.setScaleX(s);
		return this;
	}
	
	public Image color(float r,float g,float b,float a){
		super.setColor(r, g, b, a);
		return this;
	}
	
	public Image color(Color c){
		super.setColor(c);
		return this;
	}
	
	public Image scaleY(float s){
		super.setScaleY(s);
		return this;
	}
	
	public Image position(int x,int y){
		super.setPosition(x, y);
		return this;
	}
	
	public Image object(Object o){
		super.setUserObject(o);
		return this;
	}
	
	public Image position(float x,float y){
		super.setPosition(x, y);
		return this;
	}
	
	public Image X(int x){
		super.setX(x);
		return this;
	}
	
	public Image Y(int y){
		super.setY(y);
		return this;
	}
	
	public Image disableTouch(){
		super.setTouchable(null);
		return this;
	}
	
	public Image action(Action act){
		super.addAction(act);
		return this;
	}

	public void reGenerateSize() {
		if(getWidth()==0 || getWidth()==Res.NO_TEXTURE.getWidth())
			setWidth(getDrawable().getMinWidth());
		if(getHeight()==0 || getHeight()==Res.NO_TEXTURE.getHeight())
			setHeight(getDrawable().getMinHeight());
		if(originAlignment!=-1)
			setOrigin(originAlignment);
		setAnti();
	}
	
}
