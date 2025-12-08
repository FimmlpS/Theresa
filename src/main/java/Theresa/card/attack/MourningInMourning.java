package Theresa.card.attack;

import Theresa.action.SpecialAnimationAction;
import Theresa.action.WarAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.AttackHelper;
import Theresa.patch.OtherEnum;
import Theresa.power.debuff.DeadDustPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MourningInMourning extends AbstractTheresaCard {
    public static final String ID = "theresa:MourningInMourning";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public MourningInMourning() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY);
        baseDamage = damage = 6;
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new DeadDustPower(abstractMonster,1),1));
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), attackEffect));
        if(abstractMonster.hasPower(DeadDustPower.POWER_ID)){
            addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), attackEffect));
            this.exhaustOnUseOnce = true;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = Settings.BLUE_TEXT_COLOR.cpy();
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters){
            if(!mo.isDeadOrEscaped() && mo.hasPower(DeadDustPower.POWER_ID)){
                this.glowColor = Settings.GOLD_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}



