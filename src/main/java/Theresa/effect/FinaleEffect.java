package Theresa.effect;

import Theresa.modcore.ModConfig;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame.ApplyScreenPostProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FinaleEffect extends AbstractGameEffect {
    private static ShaderProgram finaleShader;
    private static ShaderProgram twistShader;
    private float transitionDegree = 0F;
    private float transitionTime = 0F;
    private static final Texture the512 = ImageMaster.loadImage("TheresaResources/img/512/512.png");
    private Color renderColor = Color.WHITE.cpy();

    boolean isTwist;

    public static void clear(){
        finaleShader = null;
        twistShader = null;
    }

    public static boolean compiled(){
        return finaleShader != null && twistShader != null;
    }

    public FinaleEffect(boolean isTwist) {
        duration = startingDuration = 1.2F;
        transitionTime = 0F;
        this.isTwist = isTwist;
        if(finaleShader == null) {
            String vertexShader = Gdx.files.internal("TheresaResources/shader/greyFilter/greyFilter.vs").readString();
            String fragShader = Gdx.files.internal("TheresaResources/shader/greyFilter/greyFilter.fs").readString();
            finaleShader = new ShaderProgram(vertexShader, fragShader);
            if (!finaleShader.isCompiled() || !ModConfig.enableShader) {
                //throw new RuntimeException(finaleShader.getLog());
                finaleShader = null;
                this.isDone = true;
            }
        }
        if(twistShader == null) {
            String vertexShader = Gdx.files.internal("TheresaResources/shader/twist/twist.vs").readString();
            String fragShader = Gdx.files.internal("TheresaResources/shader/twist/twist.fs").readString();
            twistShader = new ShaderProgram(vertexShader, fragShader);
            if (!twistShader.isCompiled() || !ModConfig.enableShader) {
                //throw new RuntimeException(twistShader.getLog());
                twistShader = null;
                this.isDone = true;
            }
        }
    }

    @Override
    public void dispose() {

    }

    private float ariaMath(float start, float end, float degree){
        return start + (end - start) * degree;
    }

    @Override
    public void update() {
        transitionTime = startingDuration - duration;
        if(transitionTime<=0.15F){
            transitionDegree = ariaMath(0,0.9F,transitionTime/0.15F);
        }
        else if(transitionTime<=0.6F){
            transitionDegree = 0.9F;
        }
        else{
            transitionDegree = ariaMath(0F,0.9F,duration/(startingDuration-0.6F));
        }
        renderColor.a = transitionDegree;
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    private void setTheShader(SpriteBatch sb){
        sb.end();
        sb.setShader(finaleShader);
        sb.begin();
        finaleShader.setUniformf("progress",transitionDegree);
        finaleShader.setUniformf("u_maxContrast",1.8f);
        finaleShader.setUniformf("u_maxBrightness",0.3f);
    }

    private void setTwistShader(SpriteBatch sb){
        sb.end();
        sb.setShader(twistShader);
        sb.begin();
        twistShader.setUniformf("degree",transitionTime/startingDuration);
    }

    @Override
    public void render(SpriteBatch sb) {
        //sb.draw(emptyRegion,0F,0F, Settings.WIDTH, Settings.HEIGHT);
        sb.end();
        TextureRegion region = getScreenAsTexture(sb);
        sb.begin();

        if(!isTwist)
            setTheShader(sb);
        else
            setTwistShader(sb);
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
}
