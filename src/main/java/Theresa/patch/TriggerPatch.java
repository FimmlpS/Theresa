package Theresa.patch;

import Theresa.helper.AttackHelper;
import Theresa.relic.LittleCube;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

public class TriggerPatch {
    @SpirePatch(clz = GameActionManager.class,method = "callEndOfTurnActions")
    public static class EndTurnPatch {
        @SpirePostfixPatch
        public static void Postfix(GameActionManager _inst) {
            DustPatch.dustManager.atTurnEnd();
            SilkPatch.atTurnEnd();
        }
    }

    @SpirePatch(clz = CardGroup.class,method = "applyPowers")
    public static class ApplyPowersPatch {
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst) {
            for(AbstractCard c : _inst.group) {
                SilkPatch.applyPowerToCard(c);
            }
            for(AbstractCard c : DustPatch.dustManager.dustCards){
                SilkPatch.applyPowerToCard(c);
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = "onModifyPower")
    public static class OnModifyPowerPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
                SilkPatch.applyPowerToCard(c);
            }
            for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
                SilkPatch.applyPowerToCard(c);
            }
        }
    }

    @SpirePatch(clz = UseCardAction.class,method = SpirePatch.CONSTRUCTOR,paramtypez = {AbstractCard.class, AbstractCreature.class})
    public static class OnUseCardActionPatch {
        @SpirePostfixPatch
        public static void Postfix(UseCardAction _inst, AbstractCard card, AbstractCreature target) {
            SilkPatch.playedCard(card);
            //小方块
            if(card.isInAutoplay){
                AbstractRelic littleCube = AbstractDungeon.player.getRelic(LittleCube.ID);
                if(littleCube != null){
                    littleCube.onTrigger();
                }
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class,method = "nextRoomTransition",paramtypez = {SaveFile.class})
    public static class OnNextRoomTransitionPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon _inst, SaveFile saveFile) {
            AttackHelper.Instance.clear();
        }
    }


}
