package Theresa.silk;

import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.power.buff.SilkPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class AbstractSilk {
    public String silkID;
    public String name = "theresa:defaultName";
    public String description = "theresa:defaultDescription";
    public TextureRegion img;
    public AbstractCard card;
    public int baseAmount = -1;
    public int amount = -1;

    public boolean canSpreadAtTurnEnd(AbstractCard cardToSpread, boolean atTurnEnd) {
        AbstractSilk s = SilkPatch.SilkCardField.silk.get(cardToSpread);
        if (s == null)
            return true;
        return canReplace(s);
    }

    public AbstractSilk(String ID) {
        this.silkID = ID;
    }

    public void applyPowers(){
        if(AbstractDungeon.player==null)
            return;
        AbstractPower sp = AbstractDungeon.player.getPower(SilkPower.POWER_ID);
        if(sp!=null){
            this.amount = baseAmount + sp.amount;
        }
        else{
            this.amount = baseAmount;
        }
        updateDescription();
    }

    public void onCopied(){

    }

    public void specialRender(SpriteBatch sb, float x, float y, float drawScale){
        if (img != null && card != null) {
            sb.setColor(Color.WHITE.cpy());
            float originX = img.getRegionX();
            float originY = img.getRegionY();
            float width = img.getRegionWidth();
            float height = img.getRegionHeight();
            sb.draw(img, x - 200F * drawScale * Settings.scale, y - 16F * drawScale * Settings.scale, originX, originY, width, height, drawScale * Settings.scale, drawScale * Settings.scale, 0F);
            //sb.draw(img,card.current_x, card.current_y, originX,originY,width,height,card.drawScale*Settings.scale,card.drawScale*Settings.scale,card.angle);
            if (amount >= 0) {
                Color color = Color.GRAY.cpy();
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, String.valueOf(amount), x + -178F * drawScale * Settings.xScale, y + -24.0F * drawScale * Settings.yScale, color);
            }
        }
    }

    public void render(SpriteBatch sb, Color renderColor) {
        if (img != null && card != null) {
            sb.setColor(renderColor);
            float originX = img.getRegionX();
            float originY = img.getRegionY();
            float width = img.getRegionWidth();
            float height = img.getRegionHeight();
            sb.draw(img, card.current_x - 200F * card.drawScale * Settings.scale, card.current_y - 16F * card.drawScale * Settings.scale, originX, originY, width, height, card.drawScale * Settings.scale, card.drawScale * Settings.scale, card.angle);
            //sb.draw(img,card.current_x, card.current_y, originX,originY,width,height,card.drawScale*Settings.scale,card.drawScale*Settings.scale,card.angle);
            if (amount >= 0) {
                Color color = Color.GRAY.cpy();
                color.a = card.transparency;
                FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, String.valueOf(amount), card.current_x + -178F * card.drawScale * Settings.xScale, card.current_y + -24.0F * card.drawScale * Settings.yScale, color);
            }
        }
    }

    public void updateDescription() {
    }

    public void atTurnStart(CardGroup.CardGroupType type){

    }

    public boolean canReplace(AbstractSilk silkToReplace){
        return false;
    }

    public boolean canSetWhenSet(AbstractCard c){
        return !c.hasTag(OtherEnum.Theresa_Silk_Cannot_Replaced);
    }

    public void atTurnEnd(CardGroup.CardGroupType type) {

    }

    public void afterPlayed(){

    }

    public void triggeredOnce(){
        //power
//        AbstractPower p = AbstractDungeon.player.getPower(YoreLingerPower.POWER_ID);
//        if(p!=null){
//            AbstractDungeon.actionManager.addToBottom(new YoreLingerAction(card,p.amount));
//        }
    }

    public abstract AbstractSilk makeCopy();
}
