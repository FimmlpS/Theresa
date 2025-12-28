package Theresa.card.attack;

import Theresa.action.LongWaitAction;
import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.effect.FinaleEffect;
import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.silk.MemorySilk;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;


public class Finale extends AbstractTheresaCard {
    public static final String ID = "theresa:Finale";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static boolean isTest = false;

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
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if((c.type == CardType.ATTACK ||c.type==CardType.SKILL)&&SilkPatch.SilkCardField.silk.get(c) == null){
                cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
                return false;
            }
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if((c.type == CardType.ATTACK ||c.type==CardType.SKILL)&&SilkPatch.SilkCardField.silk.get(c) == null){
                cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
                return false;
            }
        }
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if((c.type == CardType.ATTACK ||c.type==CardType.SKILL)&&SilkPatch.SilkCardField.silk.get(c) == null){
                cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[2];
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(isTest){
            return super.canUse(p,m);
        }
        if(!canSilkUse()){
            return false;
        }
        //不判了 恼
//        for(AbstractCard c : DustPatch.dustManager.dustCards){
//            if((c.type == CardType.ATTACK ||c.type==CardType.SKILL)&&SilkPatch.SilkCardField.silk.get(c) == null)
//                return false;
//        }
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



