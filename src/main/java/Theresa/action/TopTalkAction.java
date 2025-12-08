package Theresa.action;

import Theresa.effect.TopSpeechBubble;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

public class TopTalkAction extends AbstractGameAction {
    private String msg;
    private boolean used;
    private float bubbleDuration;
    private boolean player;

    public TopTalkAction(AbstractCreature source, String text, float duration, float bubbleDuration) {
        this.used = false;
        this.player = false;
        this.setValues(source, source);
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_MED;
        } else {
            this.duration = duration;
        }

        this.msg = text;
        this.actionType = ActionType.TEXT;
        this.bubbleDuration = bubbleDuration;
        this.player = false;
    }

    public TopTalkAction(AbstractCreature source, String text) {
        this(source, text, 2.0F, 2.0F);
    }

    public TopTalkAction(boolean isPlayer, String text, float duration, float bubbleDuration) {
        this(AbstractDungeon.player, text, duration, bubbleDuration);
        this.player = isPlayer;
    }

    public void update() {
        if (!this.used) {
            if (this.player) {
                AbstractDungeon.topLevelEffects.add(new TopSpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, this.bubbleDuration, this.msg, this.source.isPlayer));
            } else {
                AbstractDungeon.topLevelEffects.add(new TopSpeechBubble(this.source.hb.cX + this.source.dialogX, this.source.hb.cY + this.source.dialogY, this.bubbleDuration, this.msg, this.source.isPlayer));
            }

            this.used = true;
        }

        this.tickDuration();
    }
}

