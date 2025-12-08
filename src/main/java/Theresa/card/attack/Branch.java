package Theresa.card.attack;

import Theresa.action.ChooseDustToPileAction;
import Theresa.action.DustAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.DustPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Branch extends AbstractTheresaCard {
    public static final String ID = "theresa:Branch";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public int selection = -1;

    public Branch() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 14;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
//        if(!dontTriggerOnUseCard && !DustPatch.dustManager.dustCards.isEmpty()){
//            ArrayList<AbstractCard> selections = new ArrayList<>();
//            for(int i =0;i<4;i++){
//                Branch b = new Branch();
//                b.rawDescription = cardStrings.EXTENDED_DESCRIPTION[i];
//                b.initializeDescription();
//                selections.add(b);
//                b.selection = i;
//            }
//            addToBot(new ChooseOneAction(selections));
//        }
        if(canDust()){
            addToBot(new DustAction(1));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = Settings.BLUE_TEXT_COLOR.cpy();
        if(canDust()){
            this.glowColor = Settings.GOLD_COLOR.cpy();
        }
    }

    private boolean canDust(){
        for(AbstractCard c:DustPatch.dustManager.dustCards){
            if(!(c instanceof Branch))
                return true;
        }
        return false;
    }

    @Override
    public void onChoseThisOption() {
        if(selection>=0&&selection<=3){
            CardGroup.CardGroupType type = CardGroup.CardGroupType.DRAW_PILE;
            if(selection==0){
                type = CardGroup.CardGroupType.DRAW_PILE;
            }
            else if(selection==1){
                type = CardGroup.CardGroupType.HAND;
            }
            else if(selection==2){
                type = CardGroup.CardGroupType.DISCARD_PILE;
            }
            else if(selection==3){
                type = CardGroup.CardGroupType.EXHAUST_PILE;
            }
            addToTop(new ChooseDustToPileAction(type,1));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }
}




