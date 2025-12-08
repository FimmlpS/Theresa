package Theresa.patch;

import Theresa.helper.AttackHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

public class AttackPatch {
    @SpirePatch(clz = MonsterGroup.class,method = "update")
    public static class MonsterUpdatePatch {
        @SpirePostfixPatch
        public static void Postfix(MonsterGroup _inst) {
            AttackHelper.Instance.update();
        }
    }

    @SpirePatch(clz = MonsterGroup.class,method = "render")
    public static class MonsterRenderPatch {
        @SpirePostfixPatch
        public static void Postfix(MonsterGroup _inst, SpriteBatch sb) {
            AttackHelper.Instance.render(sb);
        }
    }
}
