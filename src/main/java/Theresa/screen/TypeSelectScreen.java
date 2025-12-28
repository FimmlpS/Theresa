package Theresa.screen;

import Theresa.card.special.EasyMode;
import Theresa.card.special.HardMode;
import Theresa.card.special.NormalMode;
import Theresa.modcore.TheresaMod;
import Theresa.patch.ClassEnum;
import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.io.IOException;
import java.util.ArrayList;

public class TypeSelectScreen {
    public static TypeSelectScreen Inst;
    public int index = 0;
    private AbstractCard cardToPreview;
    public Hitbox leftHb;
    public Hitbox rightHb;
    public String curName = "";
    public String nextName = "";

    private static final UIStrings uiStrings;
    private static final ArrayList<AbstractCard> list;
    public static int defaultIndex = -1;

    public static int getType(){
        if(Inst==null)
            return 0;
        return Inst.index;
    }

    public TypeSelectScreen(){
        this.index = 0;
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        Initialize();
    }

    public void Initialize(){
        int i = TheresaMod.DefaultTypeIndex;
        if (index != i && i >= 0) {
            index = i;
        }
        refresh();
    }

    public int prevIndex() {
        return this.index - 1 < 0 ? list.size() - 1 : this.index - 1;
    }

    public int nextIndex() {
        return this.index + 1 > list.size() - 1 ? 0 : this.index + 1;
    }

    public void refresh(){
        this.curName = uiStrings.EXTRA_TEXT[index];
        this.nextName = uiStrings.EXTRA_TEXT[nextIndex()];
        this.cardToPreview = list.get(index);
    }

    public void update(){
        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.375F;
        this.leftHb.move(centerX - 200.0F * Settings.scale, centerY);
        this.rightHb.move(centerX + 200.0F * Settings.scale, centerY);
        this.updateInput();
    }

    private void updateInput(){
        if (CardCrawlGame.chosenCharacter == ClassEnum.Theresa_CLASS) {

            this.leftHb.update();
            this.rightHb.update();
            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.index = this.prevIndex();
                try {
                    SpireConfig config = new SpireConfig("Theresa_FimmlpS","Common");
                    config.setInt("defaultType",this.index);
                    config.save();
                }catch (IOException var2){
                    var2.printStackTrace();
                }
                this.refresh();
            }

            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.index = this.nextIndex();
                try {
                    SpireConfig config = new SpireConfig("Theresa_FimmlpS","Common");
                    config.setInt("defaultType",this.index);
                    config.save();
                }catch (IOException var2){
                    var2.printStackTrace();
                }
                this.refresh();
            }

            if (InputHelper.justClickedLeft) {
                if (this.leftHb.hovered) {
                    this.leftHb.clickStarted = true;
                }

                if (this.rightHb.hovered) {
                    this.rightHb.clickStarted = true;
                }
            }
        }
    }

    public void render(SpriteBatch sb){
        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.375F;
        this.renderCard(sb,centerX,centerY);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, uiStrings.TEXT[0], centerX, centerY + 300.0F * Settings.scale, Color.WHITE, 1.25F);
        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.curName, centerX, centerY, Settings.GOLD_COLOR);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.nextName, centerX + dist * 1.5F, centerY - dist*0.5F, color);
        if (this.leftHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb.cX - 24.0F, this.leftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if (this.rightHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }

        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.rightHb.cX - 24.0F, this.rightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        this.rightHb.render(sb);
        this.leftHb.render(sb);
    }

    private boolean isHovered(Hitbox hb){
        return (float)InputHelper.mX > hb.x && (float)InputHelper.mX < hb.x + hb.width && (float)InputHelper.mY > hb.y && (float)InputHelper.mY < hb.y + hb.height;
    }

    public void renderCard(SpriteBatch sb,float x,float y){
        if(this.cardToPreview!=null){
            cardToPreview.current_x = x;
            cardToPreview.current_y = y+150F*Settings.scale;
            cardToPreview.hb.move(x,y+150F*Settings.scale);
            cardToPreview.drawScale = 0.7F;
            cardToPreview.render(sb);
            if(isHovered(cardToPreview.hb))
                TipHelper.renderTipForCard(cardToPreview, sb, cardToPreview.keywords);
        }
    }


    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("theresa:TypeSelect");
        list = new ArrayList<>();
        list.add(new EasyMode());
        list.add(new NormalMode());
        list.add(new HardMode());
        Inst = new TypeSelectScreen();
    }
}
