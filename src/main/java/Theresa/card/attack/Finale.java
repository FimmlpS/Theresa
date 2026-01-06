package Theresa.card.attack;

import Theresa.action.LongWaitAction;
import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.effect.FinaleEffect;
import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.silk.MemorySilk;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;


public class Finale extends AbstractTheresaCard {
    public static final String ID = "theresa:Finale";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static boolean isTest = false;
    int totalSize = -1;

    public Finale() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.RARE,CardTarget.ALL_ENEMY);
        baseDamage = damage = 80;
        this.isMultiDamage = true;
        this.tags.add(OtherEnum.Theresa_Darkness);
        SilkPatch.setSilkWithoutTrigger(this,new MemorySilk());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(!canSilkUse())
            return;
        addToBot(new TheresaAttackAction(true));
        FinaleEffect f1 = new FinaleEffect(false);
        FinaleEffect f2 = new FinaleEffect(true);
        if(FinaleEffect.compiled()){
            addToBot(new VFXAction(abstractPlayer,f1,0.15F,true));
            addToBot(new VFXAction(abstractPlayer,f2,0.15F,true));
            addToBot(new LongWaitAction(0.4F));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));
        }
        addToBot(new DamageAllEnemiesAction(abstractPlayer,multiDamage,damageTypeForTurn,skillEffect));
    }

    private boolean canSilkUse(){
        int amount = 0;
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if(SilkPatch.SilkCardField.silk.get(c)!=null){
                amount++;
            }
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if(SilkPatch.SilkCardField.silk.get(c)!=null){
                amount++;
            }
        }
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if(SilkPatch.SilkCardField.silk.get(c)!=null){
                amount++;
            }
        }
        for(AbstractCard c : DustPatch.dustManager.dustCards) {
            if(SilkPatch.SilkCardField.silk.get(c)!=null){
                amount++;
            }
        }
        totalSize = amount;
        if(amount<24){
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[3];
            return false;
        }
        return true;
    }

    @Override
    protected void renderTitle(SpriteBatch sb) {
        super.renderTitle(sb);
        if(AbstractDungeon.player == null || totalSize<0)
            return;
        String builder = cardStrings.EXTENDED_DESCRIPTION[4] + totalSize + cardStrings.EXTENDED_DESCRIPTION[5];
        Color color = Settings.LIGHT_YELLOW_COLOR.cpy();
        Color renderColor = ReflectionHacks.getPrivate(this, AbstractCard.class,"renderColor");
        color.a = renderColor.a;
        FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, builder, this.current_x, this.current_y, 0.0F, 235.0F * this.drawScale * Settings.scale, this.angle, false, color);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(isTest){
            return super.canUse(p,m);
        }
        if(!canSilkUse()){
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(20);
        }
    }
}



