package Theresa.effect;

import Theresa.modcore.ModConfig;
import Theresa.modcore.TheresaMod;
import Theresa.power.buff.HatePower;
import Theresa.power.buff.HopePower;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame.ApplyScreenPostProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.BlightHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BlushEffect extends AbstractGameEffect {
    private static ShaderProgram blushShader;
    private Color renderColor = Color.WHITE.cpy();

    private float targetBlush = 0F;
    private float currentBlush = 0F;
    private float targetOffset = 0F;
    private float currentOffset = 0F;

    public static void clear(){
        blushShader = null;
    }

    public BlushEffect() {
        duration = startingDuration = 1F;
        if(blushShader == null) {
            String vertexShader = Gdx.files.internal("TheresaResources/shader/blush/blush.vs").readString();
            String fragShader = Gdx.files.internal("TheresaResources/shader/blush/blush.fs").readString();
            blushShader = new ShaderProgram(vertexShader, fragShader);
            if (!blushShader.isCompiled() || !ModConfig.enableShader) {
                 blushShader = null;
                this.isDone = true;
                //throw new RuntimeException(blushShader.getLog());
            }
            else {
                TheresaMod.logSomething("=== ALREADY SPAWN !!! ===");
            }
        }
    }

    private static boolean enableOpenGL(){
        return Gdx.gl20.glIsEnabled(GL20.GL_COMPILE_STATUS);
    }

    @Override
    public void dispose() {

    }

    private float ariaMath(float start, float end, float degree){
        return start + (end - start) * degree;
    }

    private boolean truthUpdate(){
        if(AbstractDungeon.getCurrMapNode() == null || AbstractDungeon.getCurrRoom() == null)
            return false;
        if(AbstractDungeon.player == null)
            return false;
        return AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    @Override
    public void update() {
        //TheresaMod.logSomething("=== I'M UPDATING !!! ===");
        if(truthUpdate()){
            //TheresaMod.logSomething("=== I'M TRUTHFULLY UPDATING !!! ===");
            AbstractPower hPower = AbstractDungeon.player.getPower(HopePower.POWER_ID);
            AbstractPower aPower = AbstractDungeon.player.getPower(HatePower.POWER_ID);
            int hope = hPower == null ? 0 : hPower.amount;
            int hate = aPower == null ? 0 : aPower.amount;
            //targetBlush = (float) (Math.log(hope + hate + 1) / Math.log(40));
            //targetOffset = (float) (Math.log(Math.abs(hope-hate) + 1) / Math.log(40));
            targetBlush = (float) (hope+hate) / 32F;
            targetOffset = (float) Math.abs(hope-hate) / 40F;
            if(targetBlush>1F)
                targetBlush = 1F;
            if(targetOffset>0.5F)
                targetOffset = 0.5F;
            if(hate>hope)
                targetOffset = -targetOffset;
        }
        else {
            targetBlush = 0F;
            targetOffset = 0F;
        }

        //float delta = Gdx.graphics.getDeltaTime();
        currentBlush = MathHelper.slowColorLerpSnap(currentBlush,targetBlush);
        currentOffset = MathHelper.slowColorLerpSnap(currentOffset,targetOffset);
    }

    private void setTheShader(SpriteBatch sb){
        sb.end();
        sb.setShader(blushShader);
        sb.begin();
        blushShader.setUniformf("blurProgress",currentBlush);
        blushShader.setUniformf("colorOffset",currentOffset);
    }

    @Override
    public void render(SpriteBatch sb) {
        if(currentBlush == 0F || blushShader==null)
            return;

        //sb.draw(emptyRegion,0F,0F, Settings.WIDTH, Settings.HEIGHT);
        sb.end();
        TextureRegion region = getScreenAsTexture(sb);
        sb.begin();

        setTheShader(sb);
        //ShaderHelper.setShader(sb, ShaderHelper.Shader.GRAYSCALE);

        sb.setColor(Color.WHITE);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //TheresaMod.logSomething("=== FINALE SHADER RENDER===");
        //sb.draw(region, 0,0);
        sb.flush();
        sb.draw(region,0F,0F,region.getRegionWidth(),region.getRegionHeight());

        ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
    }

    private TextureRegion getScreenAsTexture(SpriteBatch sb){
        FrameBuffer buffer = ReflectionHacks.getPrivateStatic(ApplyScreenPostProcessor.class, "secondaryFrameBuffer");
        buffer.begin();
        sb.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setColor(Color.WHITE);
        sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);
        sb.draw(ReflectionHacks.<TextureRegion>getPrivateStatic(ApplyScreenPostProcessor.class, "primaryFboRegion"), 0, 0);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sb.end();
        buffer.end();
        return ReflectionHacks.getPrivateStatic(ApplyScreenPostProcessor.class, "secondaryFboRegion");
    }

    public static void initialize() {
        for(AbstractGameEffect e:AbstractDungeon.effectList){
            if(e instanceof BlushEffect)
                return;
        }
        BlushEffect effect = new BlushEffect();
        if(!effect.isDone)
            AbstractDungeon.effectList.add(effect);
    }

    @SpirePatch(clz = AbstractDungeon.class,method = "reset")
    public static class ResetEffect {
        @SpirePostfixPatch
        public static void Postfix() {
            BlushEffect effect = new BlushEffect();
            if(!effect.isDone)
                AbstractDungeon.effectList.add(effect);
        }
    }
}

