package Theresa.card.attack;

import Theresa.action.SpecialAnimationAction;
import Theresa.action.WarAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.AttackHelper;
import Theresa.patch.OtherEnum;
import Theresa.power.debuff.DyingPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TheWar extends AbstractTheresaCard {
    public static final String ID = "theresa:TheWar";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheWar() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.RARE,CardTarget.ALL_ENEMY);
        baseDamage = damage = 1;
        baseMagicNumber = magicNumber = 8;
        isMultiDamage = true;
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SpecialAnimationAction(AttackHelper.ForCharacter.War));
        for(int i = 0; i < this.magicNumber; i++){
            addToBot(new DamageAllEnemiesAction(abstractPlayer,multiDamage,damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE,true));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    @SpirePatch(clz = AbstractCard.class,method = "calculateCardDamage")
    public static class DamageAllEnemiesActionPatch {
        @SpireInsertPatch(rloc = 65,localvars = {"tmp"})
        public static void Insert(AbstractCard _inst, AbstractMonster mo, float[] tmp) {
            if(_inst instanceof TheWar){
                for(int i =0;i<tmp.length;i++){
                    if(AbstractDungeon.getCurrRoom().monsters.monsters.size()>i){
                        AbstractMonster mon = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                        if(!mon.isDeadOrEscaped()){
                            AbstractPower dying = mon.getPower(DyingPower.POWER_ID);
                            if(dying instanceof DyingPower){
                                float percent = ((DyingPower) dying).calculateReduced(true);
                                int times = (int)(percent/5F);
                                tmp[i] += times;
                            }
                        }
                    }
                }
            }
        }
    }
}


