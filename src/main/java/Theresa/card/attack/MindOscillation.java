package Theresa.card.attack;

import Theresa.action.MindOAction;
import Theresa.action.SetSilkAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.MindSilk;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class MindOscillation extends AbstractTheresaCard {
    public static final String ID = "theresa:MindOscillation";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public MindOscillation() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 9;
        SilkPatch.setSilkWithoutTrigger(this,new MindSilk());
        this.selfRetain = true;
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
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractCard c = triggerType();
        if(c!=null){
            addToBot(new MindOAction(c));
        }
    }

    private AbstractCard triggerType(){
        ArrayList<AbstractCard> tmp = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        if(!tmp.isEmpty()) {
            for(int i = tmp.size()-1;i>=0;i--) {
                AbstractCard c = tmp.get(i);
                if(c!=this){
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }
}




