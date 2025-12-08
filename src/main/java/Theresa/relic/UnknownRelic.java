package Theresa.relic;

import Theresa.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class UnknownRelic extends CustomRelic {
    public static final String ID = "theresa:UnknownRelic";

    public UnknownRelic() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.STARTER,LandingSound.SOLID);
        this.counter = 0;
    }

    @Override
    public void obtain() {
        if(AbstractDungeon.player.hasRelic(UnknownRelic.ID)){
            AbstractDungeon.player.getRelic(UnknownRelic.ID).counter+=10000;
            return;
        }
        super.obtain();
    }

    @SpirePatch(clz = GameActionManager.class,method = "addToBottom")
    @SpirePatch(clz = GameActionManager.class,method = "addToTop")
    public static class AddToBottomPatch {
        public static void Postfix(GameActionManager _inst, AbstractGameAction action) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AbstractRelic relic = AbstractDungeon.player.getRelic(ID);
                if (relic != null) {
                    relic.counter++;
                }
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

