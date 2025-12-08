package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.MindSilk;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class DeadBreath extends AbstractTheresaCard {
    public static final String ID = "theresa:DeadBreath";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DeadBreath() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        baseBlock = block = 6;
        baseMagicNumber = magicNumber = 1;
        SilkPatch.setSilkWithoutTrigger(this,new MindSilk());
        //this.exhaust = true;
        shouldLocked = true;
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = Settings.BLUE_TEXT_COLOR.cpy();
        if(triggerType()!=null){
            this.glowColor = Settings.GOLD_COLOR.cpy();
        }
    }

    @Override
    public boolean shouldExhaust() {
        return triggerType()!=null;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        AbstractCard c = triggerType();
        if(c != null) {
            for(int i = 0; i < magicNumber; i++)
                SilkPatch.triggerSilk(SilkPatch.TriggerType.ALL,this, CardGroup.CardGroupType.HAND);
            addToBot(new MakeTempCardInHandAction(this.makeSameInstanceOf(),1));
            this.exhaust = true;
        }
        else {
            this.exhaust = false;
        }
    }

    private AbstractCard triggerType(){
        ArrayList<AbstractCard> tmp = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        if(!tmp.isEmpty()) {
            for(int i = tmp.size()-1;i>=0;i--) {
                AbstractCard c = tmp.get(i);
                if(c!=this){
                    if(c.type != this.type){
                        return c;
                    }
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}






