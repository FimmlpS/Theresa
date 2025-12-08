package Theresa.card.attack;

import Theresa.action.ResetItAction;
import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ThereItIs extends AbstractTheresaCard {
    public static final String ID = "theresa:ThereItIs";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public int terminalDamage = 0;
    int lastIndex = -1;

    public void resetSelf(){
        terminalDamage = 0;
        lastIndex = -1;
    }

    public ThereItIs() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new TheresaAttackAction(true));
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), skillEffect));
        //addToBot(new ResetItAction(this));
    }

    @Override
    public void update() {
        super.update();
        if(!AbstractDungeon.isScreenUp){
            if(AbstractDungeon.player !=null && AbstractDungeon.player.hand.contains(this)){
                try {
                    int index = AbstractDungeon.player.hand.group.indexOf(this);
                    if(index != lastIndex){
                        lastIndex = index;
                        this.terminalDamage += this.magicNumber;
                    }
                }
                catch (Exception e) {
                    //do nothing
                }
            }
            else {
                this.lastIndex = -1;
            }
        }
    }

    @Override
    public void applyPowers() {
        int tmp = baseDamage;
        baseDamage += terminalDamage;
        super.applyPowers();
        baseDamage = tmp;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = baseDamage;
        baseDamage += terminalDamage;
        super.calculateCardDamage(mo);
        baseDamage = tmp;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        if(c instanceof ThereItIs){
            ((ThereItIs) c).terminalDamage = this.terminalDamage;
        }
        return c;
    }
}


